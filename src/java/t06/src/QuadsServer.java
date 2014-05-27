package t06.src;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arsenykogan on 27/05/14.
 */
public class QuadsServer extends HttpServlet {

    private static final String CONTENT_TYPE = "text/html; charset=utf-8";
    private static final String HTML_TEMPLATE = "<!doctype html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Squares</title>\n" +
            "<style>\n" +
            "    .quad {\n" +
            "    /*border-radius*/\n" +
            "    -webkit-border-radius: 4px;\n" +
            "    -moz-border-radius: 4px;\n" +
            "    border-radius: 4px;\n" +
            "    position: absolute;\n" +
            "}\n" +
            "</style>" +
            "</head>\n" +
            "<body>\n" +
            "%s\n" +
            "</body>\n" +
            "</html>";

    private static final String QUADS_DIV = "<div class=\"quad\" style=\"" +
            "top:{x}px;" +
            "left:{y}px;" +
            "width:{width}px;" +
            "height:{height}px;" +
            "background-color:{color}\">{id}</div>";


    private final HashMap<Integer, Quad> quads = new HashMap<>();
    private int currentID = 0;


    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null) {
            final String xPos = req.getParameter("x");
            final String yPos = req.getParameter("y");
            final String size = req.getParameter("size");
            final String color = req.getParameter("color");
            if (isInt(xPos) && isInt(yPos) && isInt(size) && isColor(color)) {
                quads.put(currentID++, new Quad(safeParseInt(xPos), safeParseInt(yPos), safeParseInt(size), color));
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
                quads.get(id).setX(safeParseInt(xPos));
            }
            if (yPos != null) {
                quads.get(id).setY(safeParseInt(yPos));
            }
            if (size != null) {
                quads.get(id).setSize(safeParseInt(size));
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
        quads.remove(safeParseInt(path.replace("/", "")));
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.getWriter().print(getQuadsHTML());
        System.out.println(getQuadsHTML());
    }

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

    private String getQuadsHTML() {
        final StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, Quad> entry : quads.entrySet()) {
            String html = QUADS_DIV;
            html = html.replace("{x}", entry.getValue().getX() + "");
            html = html.replace("{y}", entry.getValue().getY() + "");
            html = html.replace("{width}", entry.getValue().getSize() + "");
            html = html.replace("{height}", entry.getValue().getSize() + "");
            html = html.replace("{color}", entry.getValue().getColor() + "");
            html = html.replace("{id}", entry.getKey() + "");
            builder.append(html);
            builder.append("\n");
        }
        return String.format(HTML_TEMPLATE, builder.toString());
    }
}
