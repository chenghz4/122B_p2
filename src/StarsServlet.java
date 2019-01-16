import com.google.gson.JsonArray;
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
import java.sql.ResultSet;
import java.sql.Statement;


// Declaring a WebServlet called StarsServlet, which maps to url "/api/stars"
@WebServlet(name = "StarsServlet", urlPatterns = "/api/stars")
public class StarsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Create a dataSource which registered in web.xml
    @Resource(name = "jdbc/moviedb")
    private DataSource dataSource;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json"); // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        try {
            // Get a connection from dataSource
            Connection dbcon = dataSource.getConnection();

            // Declare our statement
            Statement statement = dbcon.createStatement();

            String query =
                    "select distinct a.id, a.title, a.year, a.director, " +
                            "GROUP_CONCAT(distinct a.genre_name) as genre_name, a.rating,  " +
                            "GROUP_CONCAT(distinct s.name) as star_name,  GROUP_CONCAT(distinct s.id) as star_id " +
                            "from " +
                            "(select distinct m.id, m.title, m.year, m.director, " +
                            " GROUP_CONCAT(distinct g.name) as genre_name, r.rating " +
                            "from movies as m, ratings as r, genres as g, genres_in_movies as y " +
                            "where m.id=y.movieId and y.genreId=g.id and r.movieId=m.id " +
                            "group by m.id " +
                            "order by r.rating desc " +
                            "limit 20 " +
                            ") as a,  " +
                            " " +
                            "stars as s, stars_in_movies as x " +
                            "where a.id=x.movieId and x.starId=s.id  " +
                            "group by a.id " +
                            "order by a.rating desc " +
                            "limit 20 ";

            // Perform the query
            String query1 =
                    "select distinct a.id, a.title, a.year, a.director, " +
                            " a.genre_name as genre_name, a.rating,  " +
                            "  s.name as star_name,  s.id as star_id " +
                            "from " +
                            "(select distinct m.id, m.title, m.year, m.director,g.name as genre_name, r.rating " +
                            "from movies as m, ratings as r, genres as g, genres_in_movies as y " +
                            "where m.id=y.movieId and y.genreId=g.id and r.movieId=m.id  " +
                            "order by r.rating desc " +
                            "limit 20) as a,  " +
                            " " +
                            "stars as s, stars_in_movies as x " +
                            "where a.id=x.movieId and x.starId=s.id  " ;


            // Perform the query
            ResultSet rs = statement.executeQuery(query);
            //





            JsonArray jsonArray = new JsonArray();

            // Iterate through each row of rs
            while (rs.next()) {


                String movie_id = rs.getString("id");
                String movie_title = rs.getString("title");
                String movie_year = rs.getString("year");
                String movie_director = rs.getString("director");
                String rate = rs.getString("rating");
                String star_id = rs.getString("star_id");
                String star_name = rs.getString("star_name");
                String genre_name=rs.getString("genre_name");

                // Create a JsonObject based on the data we retrieve from rs


                    JsonObject jsonObject = new JsonObject();

                    jsonObject.addProperty("movie_id", movie_id);
                    jsonObject.addProperty("movie_title", movie_title);
                    jsonObject.addProperty("movie_year", movie_year);
                    jsonObject.addProperty("movie_director", movie_director);
                    jsonObject.addProperty("list_g", genre_name);
                    jsonObject.addProperty("list_s", star_name);
                    jsonObject.addProperty("s.id", star_id);
                    jsonObject.addProperty("rating", rate);

                    jsonArray.add(jsonObject);

                }



            
            // write JSON string to output
            out.write(jsonArray.toString());
            // set response status to 200 (OK)
            response.setStatus(200);

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
}
