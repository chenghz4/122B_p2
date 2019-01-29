import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

/**
 * This IndexServlet is declared in the web annotation below,
 * which is mapped to the URL pattern /api/index.
 */
@WebServlet(name = "Cartservlet", urlPatterns = "/api/cart")
public class Cartservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Resource(name = "jdbc/moviedb")
    private DataSource dataSource;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String movieid=request.getParameter("id");
        PrintWriter out = response.getWriter();
        int flag=0;
        try {
            // Get a connection from dataSource
            Connection dbcon = dataSource.getConnection();
            String query ="select id, title, year,director " +
                    "from movies " +
                    "where id=? ";
            PreparedStatement statement = dbcon.prepareStatement(query);
            statement.setString(1, movieid);
            ResultSet rs = statement.executeQuery();
            String movie_id = "";
            String movie_title ="";


            if(rs.next()){
                movie_id = rs.getString("id");
                movie_title = rs.getString("title");
            }



            ArrayList<String> previousmovies = (ArrayList<String>) session.getAttribute("previousmovies");

            if (previousmovies == null) {
                previousmovies = new ArrayList<>();
                if(!movie_title.equals("")) {
                    previousmovies.add(movie_title);
                }

                session.setAttribute("previousmovies", previousmovies);
            }
            else {
                for(int i=0;i<previousmovies.size();i++){
                    String str=previousmovies.get(i);
                    if(str==movie_title)    flag=1;
                }
                if(!movie_title.equals("")) {
                    synchronized (previousmovies) {

                        //previousmovies.add(movie_id);
                        previousmovies.add(movie_title);
                    }
                }

            }


            if(flag==0) {
                out.write(String.join(",", previousmovies));
            }



           // JsonObject responseJsonObject = new JsonObject();
            //responseJsonObject.addProperty("movie_id", movie_id);
            //responseJsonObject.addProperty("movie_title", movie_title);
            //responseJsonObject.addProperty("movie_year", movie_year);
            //responseJsonObject.addProperty("movie_director", movie_director);

            // write all the data into the jsonObject
            //response.getWriter().write(responseJsonObject.toString());


            rs.close();
            statement.close();
            dbcon.close();
        } catch (Exception e) {

            // write error message JSON object to output
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("errorMessage", e.getMessage());
            out.write(jsonObject.toString());

            // set reponse status to 500 (Internal Server Error)
            response.setStatus(500);

        }
        out.close();
    }

    /**
     * handles GET requests to add and show the item list information
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String item = request.getParameter("item");
        System.out.println(item);
        HttpSession session = request.getSession();

        // get the previous items in a ArrayList
        ArrayList<String> previousItems = (ArrayList<String>) session.getAttribute("previousItems");
        if (previousItems == null) {
            previousItems = new ArrayList<>();
            previousItems.add(item);
            session.setAttribute("previousItems", previousItems);
        } else {
            // prevent corrupted states through sharing under multi-threads
            // will only be executed by one thread at a time
            synchronized (previousItems) {
                previousItems.add(item);
            }
        }

        response.getWriter().write(String.join(",", previousItems));
    }
}
