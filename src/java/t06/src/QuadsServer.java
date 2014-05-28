package t06.src;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by arsenykogan on 27/05/14.
 */
public class QuadsServer extends HttpServlet {

    private static final String FOLDER = "/Users/arsenykogan/Documents/github/tech/src/java/t06/src/";
    private static final String CONTENT_TYPE = "text/html; charset=utf-8";
    private final String HTML_TEMPLATE;
    private final String QUADS_DIV;

    private final HashMap<Integer, Quad> quads = new HashMap<>();
    private int currentID = 0;

    public QuadsServer() throws FileNotFoundException {
        super();
        /* Reading HTML template from file. */
        HTML_TEMPLATE = new Scanner(new File(FOLDER + "template.html")).useDelimiter("\\Z").next();
        /* Reading HTML template of Div. */
        QUADS_DIV = new Scanner(new File(FOLDER + "div.html")).useDelimiter("\\Z").next();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final String path = req.getPathInfo();
        resp.setContentType(CONTENT_TYPE);
        if (path == null || path.equals("/")) {
            /* Print all quads. */
            resp.getWriter().print(getQuadsHTML());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            /* If one of the quads specified or there is an error. */
            if (path.split("/").length > 2 || !path.replace("/", "").matches("[0-9]+")) {
                /* If there are any more subfolders like 10/10/10
                * or any letters like /10/hello/ return an error */
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Wrong URL format");
            } else {
                /* If everything looks fine.. */
                final int id = safeParseInt(path.replace("/", ""));
                if (quads.containsKey(id)) {
                    /* Then show this quad. */
                    resp.getWriter().print(String.format(HTML_TEMPLATE, getSingleQuadHTML(id)));
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    /* If there is no quad with this ID. */
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No square with this ID");
                }
            }
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            /* URL is nice, let's check parameters. */
            final String xPos = req.getParameter("x");
            final String yPos = req.getParameter("y");
            final String size = req.getParameter("size");
            final String color = req.getParameter("color");
            /* Check some obvious things */
            if (xPos == null || safeParseInt(xPos) == null || safeParseInt(xPos) < 0 ||
                    yPos == null || safeParseInt(yPos) == null || safeParseInt(yPos) < 0 ||
                    size == null || safeParseInt(size) == null || safeParseInt(size) <= 0 ||
                    color == null || !isColor(color)) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong parameters. Can't create square.");
            } else {
                quads.put(++currentID, new Quad(xPos, yPos, size, color));
                resp.setStatus(HttpServletResponse.SC_CREATED, "id=" + currentID);
            }
        } else {
            /* Wrong URL format, return error. */
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Wrong URL format");
        }
    }

    @Override
    protected void doPut(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            /* No quads specified, do nothing. */
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Can't change all squares");
        } else {
            /* If one of the quads specified or there is an error. */
            if (path.split("/").length > 2 || !path.replace("/", "").matches("[0-9]+")) {
                /* If there are any more subfolders like 10/10/10
                * or any letters like /10/hello/ return an error */
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Wrong URL format");
            } else {
                /* If everything looks fine.. */
                final int id = safeParseInt(path.replace("/", ""));
                if (quads.containsKey(id)) {
                    /* Lets change this quad. */
                    final String xPos = req.getParameter("x");
                    final String yPos = req.getParameter("y");
                    final String size = req.getParameter("size");
                    final String color = req.getParameter("color");
                    if (xPos != null && safeParseInt(xPos) != null && safeParseInt(xPos) >= 0) {
                        quads.get(id).setX(xPos);
                    }
                    if (yPos != null && safeParseInt(yPos) != null && safeParseInt(yPos) >= 0) {
                        quads.get(id).setY(yPos);
                    }
                    if (size != null && safeParseInt(size) != null && safeParseInt(size) > 0) {
                        quads.get(id).setSize(size);
                    }
                    if (color != null && isColor(color)) {
                        quads.get(id).setColor(color);
                    }
                    resp.setStatus(HttpServletResponse.SC_OK, "id=" + id + " : " + quads.get(id).toString());
                } else {
                    /* If there is no quad with this ID. */
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No square with this ID");
                }
            }
        }
    }

    @Override
    protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            /* No quads specified, do nothing. */
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Can't delete all squares");
        } else {
            /* If one of the quads specified or there is an error. */
            if (path.split("/").length > 2 || !path.replace("/", "").matches("[0-9]+")) {
                /* If there are any more subfolders like 10/10/10
                * or any letters like /10/hello/ return an error */
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Wrong URL format");
            } else {
                /* If everything looks fine.. */
                final int id = safeParseInt(path.replace("/", ""));
                if (quads.containsKey(id)) {
                    /* Then remove this quad. */
                    resp.setStatus(HttpServletResponse.SC_OK, quads.get(id).toString());
                    quads.remove(id);
                } else {
                    /* If there is no quad with this ID. */
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No square with this ID");
                }
            }
        }
    }


    /* Some auxiliary methods. */

    /* (Un)safe int parser. If string can't be parsed returns null. */
    private Integer safeParseInt(final String s) {
        Integer res;
        try {
            res = Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            return null;
        }
        return res;
    }

    /* Simple check for color format. */
    private boolean isColor(final String s) {
        String rgb = s.replaceAll("[0-9]", "");
        return (rgb.equals("rgb(,,)"));
    }

    /* Returns HTML for all quads. */
    private String getQuadsHTML() {
        final StringBuilder builder = new StringBuilder();
        for (Integer quadID : quads.keySet()) {
            builder.append(getSingleQuadHTML(quadID));
            builder.append("\n");
        }
        return String.format(HTML_TEMPLATE, builder.toString());
    }

    /* Returns HTML for single quad. */
    private String getSingleQuadHTML(final int quadID) {
        final Quad quad = quads.get(quadID);
        return String.format(QUADS_DIV, quad.getX(), quad.getY(), quad.getSize(), quad.getSize(), quad.getColor(), quadID);
    }
}
