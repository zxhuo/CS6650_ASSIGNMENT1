
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkiersServlet extends HttpServlet {

    private final String VERTICAL="vertical";
    private final String RESORT="resorts";
    private final String SEASONS="seasons";
    private final String DAYS = "days";
    private final String SKIERS = "skiers";
    private final int MINYEAR = 0;
    private final int MAXYEAR = 2022;
    private final int MINDAY = 0;
    private final int MAXDAY = 366;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        String urlPath = req.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing paramterers");
            return;
        }

        String[] urlParts = urlPath.split("/");
        // and now validate url path and return the response status code
        // (and maybe also some value if input is valid)

        if (!isUrlValid(urlParts, req)) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            res.setStatus(HttpServletResponse.SC_OK);
            // do any sophisticated processing with urlParts which contains all the url params
            // TODO: process url params in `urlParts`
            res.getWriter().write("It works!");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        String urlPath = req.getPathInfo();

        // check we have a URL!
        if (urlPath == null || urlPath.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("missing paramterers");
            return;
        }

        String[] urlParts = urlPath.split("/");
        // and now validate url path and return the response status code
        // (and maybe also some value if input is valid)

        if (!isUrlValid(urlParts, req)) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            res.setStatus(HttpServletResponse.SC_OK);
            // do any sophisticated processing with urlParts which contains all the url params
            // TODO: process url params in `urlParts`
            res.getWriter().write("It works!");
        }
    }

    private boolean isUrlValid(String[] urlPath, HttpServletRequest req) {
        // TODO: validate the request url path according to the API spec
        // urlPath  = "/1/seasons/2019/day/1/skier/123"
        // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]

        if(urlPath.length == 8){
            try {
                int resortID = Integer.parseInt(urlPath[1]);
                int seasonID = Integer.parseInt(urlPath[3]);
                int dayID = Integer.parseInt(urlPath[5]);
                int skierID = Integer.parseInt(urlPath[7]);

                return (seasonID > MINYEAR &&
                        seasonID <= MAXYEAR &&
                        dayID >= MINDAY &&
                        dayID <= MAXDAY &&
                        urlPath[2].equals(SEASONS) &&
                        urlPath[4].equals(DAYS) &&
                        urlPath[6].equals(SKIERS)
                );
            }
            catch (Exception e){
                return false;
            }

        }else if(urlPath.length == 3){
            try{
                int skierID = Integer.parseInt(urlPath[1]);
                return(urlPath[2].equals(VERTICAL) && req.getParameter(RESORT) != null);
            } catch (Exception e){
                return false;
            }
        }else{
            return false;
        }
    }

}
