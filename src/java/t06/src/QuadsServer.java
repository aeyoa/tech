package t06.src;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        if (req.getPathInfo() == null) {
            final String xPos = req.getParameter("x");
            final String yPos = req.getParameter("y");
            final String size = req.getParameter("size");
            final String color = req.getParameter("color");
            if (isInt(xPos) && isInt(yPos) && isInt(size) && isColor(color)) {
                quads.put(currentID++, new Quad(xPos, yPos, size, color));
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        printQuads();
    }

    @Override
    protected void doPut(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        if (isInt(req.getPathInfo().replace("/", "")) && quads.containsKey(safeParseInt(req.getPathInfo().replace("/", "")))) {
            final int id = safeParseInt(req.getPathInfo().replace("/", ""));
            final String xPos = req.getParameter("x");
            final String yPos = req.getParameter("y");
            final String size = req.getParameter("size");
            final String color = req.getParameter("color");
            if (xPos != null) {
                quads.get(id).setX(xPos);
            }
            if (yPos != null) {
                quads.get(id).setY(yPos);
            }
            if (size != null) {
                quads.get(id).setSize(size);
            }
            if (color != null && isColor(color)) {
                quads.get(id).setColor(color);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        printQuads();
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


    /* ---------------------------
    * Some auxiliary methods. */

    private void printQuads() {
        System.out.println("-------------------");
        for (Map.Entry<Integer, Quad> integerQuadEntry : quads.entrySet()) {
            System.out.println(integerQuadEntry.getKey() + " : " + integerQuadEntry.getValue());
        }
    }

    private Integer safeParseInt(final String s) {
        Integer res;
        try {
            res = Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            return null;
        }
        return res;
    }

    private boolean isInt(final String s) {
        try {
            Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

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
