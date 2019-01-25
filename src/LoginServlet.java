import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is declared as LoginServlet in web annotation,
 * which is mapped to the URL pattern /api/login
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/api/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Resource(name = "jdbc/moviedb")


    private DataSource dataSource;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();


        try {
            // Get a connection from dataSource
            Connection dbcon = dataSource.getConnection();

            String query = "select count(id) " +
                    "from customers " +
                    "where id=? and password=? " ;
            PreparedStatement statement = dbcon.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);



            ResultSet rs = statement.executeQuery();
            String check = "";
            if(rs.next()){
                check = rs.getString("count(id)");
            }




//            out.write(check);
            rs.close();
            statement.close();
            dbcon.close();

            /**
             * This example only allows username/password to be anteater/123456
             * In real world projects, you should talk to the database to verify username/password
             */
            if (check.equals("1")) {
            //if (username.equals("anteater") && password.equals("123456")) {
                // Login succeeds
                // Set this user into current session
                String sessionId = ((HttpServletRequest) request).getSession().getId();
                Long lastAccessTime = ((HttpServletRequest) request).getSession().getLastAccessedTime();
                request.getSession().setAttribute("user", new User(username));

                JsonObject responseJsonObject = new JsonObject();
                responseJsonObject.addProperty("status", "success");
                responseJsonObject.addProperty("message", "success");

                out.write(responseJsonObject.toString());
            }
            else {
                // Login fails
                JsonObject responseJsonObject = new JsonObject();
                responseJsonObject.addProperty("status", "fail");
                if (!username.equals("anteater")) {
                    responseJsonObject.addProperty("message", "user " + username + " doesn't exist");
                } else {
                    responseJsonObject.addProperty("message", "incorrect password");
                }
                out.write(responseJsonObject.toString());
            }
        }
        catch (Exception e){

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("errorMessage", e.getMessage());
            out.write(jsonObject.toString());
            e.printStackTrace();
            // set reponse status to 500 (Internal Server Error)
            response.setStatus(500);

        }
    }
}
