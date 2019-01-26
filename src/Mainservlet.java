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
@WebServlet(name = "Mainservlet", urlPatterns = "/api/Main")
    public class Mainservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Resource(name = "jdbc/moviedb")


    private DataSource dataSource;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String searchtitle = request.getParameter("search");
        String searchyear = request.getParameter("search_year");
        String searchdirector = request.getParameter("search_director");
        String searchstar = request.getParameter("search_star");

        PrintWriter out = response.getWriter();
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("title", searchtitle);
        jsonObject.addProperty("year", searchyear);
        jsonObject.addProperty("director", searchdirector);
        jsonObject.addProperty("star", searchstar);

        out.write(jsonObject.toString());
        out.close();



    }
}
