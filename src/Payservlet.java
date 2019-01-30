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
@WebServlet(name = "Payservlet", urlPatterns = "/api/pay")
public class Payservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Resource(name = "jdbc/moviedb")


    private DataSource dataSource;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String cardnumber = request.getParameter("cardnumber");
        String lastname = request.getParameter("lastname");
        String firstname = request.getParameter("firstname");
        String expiration = request.getParameter("date");
        String check="";
        String card="";
        PrintWriter out = response.getWriter();


        try {

            // Get a connection from dataSource
            Connection dbcon = dataSource.getConnection();

            String query1="select count(id) " +
                    "from creditcards " +
                    "where id=?  and firstName=? and lastName=? and expiration=? ";
            PreparedStatement statement1 = dbcon.prepareStatement(query1);
            statement1.setString(1, cardnumber);
            statement1.setString(2, firstname);
            statement1.setString(3, lastname);
            statement1.setString(4, expiration);
            ResultSet rs1 = statement1.executeQuery();

            if(rs1.next()){
                check = rs1.getString("count(id)");
            }

            rs1.close();
            statement1.close();



            String query = "select count(id)  " +
                    "from creditcards " +
                    "where id=?  " ;
            PreparedStatement statement = dbcon.prepareStatement(query);
            statement.setString(1, cardnumber);
            ResultSet rs = statement.executeQuery();



            if(rs.next()){
                card = rs.getString("count(id)");
            }


            rs.close();
            statement.close();
            dbcon.close();

            /**
             * This example only allows username/password to be anteater/123456
             * In real world projects, you should talk to the database to verify username/password
             */
            if (check.equals("1")) {



                JsonObject responseJsonObject = new JsonObject();
                responseJsonObject.addProperty("status", "success");
                responseJsonObject.addProperty("message", "success");

                out.write(responseJsonObject.toString());
            }
            else {
                // Login fails
                JsonObject responseJsonObject = new JsonObject();
                responseJsonObject.addProperty("status", "fail");

                if (!card.equals("1")) {
                    responseJsonObject.addProperty("message", "user " + cardnumber + " doesn't exist");
                } else {
                    responseJsonObject.addProperty("message", "card information doesnt match");
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
