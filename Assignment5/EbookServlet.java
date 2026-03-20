import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ebooks")
public class EbookServlet extends HttpServlet {

    // Update these 3 values for your DB.
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ebookdb";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "root";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!doctype html>");
            out.println("<html><head><meta charset='utf-8'><title>Ebook Shop</title></head><body>");
            out.println("<h2>Ebook Shop - Table Contents</h2>");

            // MySQL JDBC driver (Connector/J)
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                out.println("<p style='color:red'>MySQL JDBC Driver not found. Add mysql-connector-j jar.</p>");
                out.println("</body></html>");
                return;
            }

            String sql = "SELECT book_id, book_title, book_author, book_price, quantity FROM ebookshop";

            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {

                out.println("<table border='1' cellpadding='6' cellspacing='0'>");
                out.println("<tr>"
                        + "<th>ID</th>"
                        + "<th>Title</th>"
                        + "<th>Author</th>"
                        + "<th>Price</th>"
                        + "<th>Quantity</th>"
                        + "</tr>");

                while (rs.next()) {
                    int id = rs.getInt("book_id");
                    String title = rs.getString("book_title");
                    String author = rs.getString("book_author");
                    double price = rs.getDouble("book_price");
                    int qty = rs.getInt("quantity");

                    out.println("<tr>"
                            + "<td>" + id + "</td>"
                            + "<td>" + html(title) + "</td>"
                            + "<td>" + html(author) + "</td>"
                            + "<td>" + price + "</td>"
                            + "<td>" + qty + "</td>"
                            + "</tr>");
                }

                out.println("</table>");

            } catch (SQLException e) {
                out.println("<p style='color:red'>Database error: " + html(e.getMessage()) + "</p>");
            }

            out.println("</body></html>");
        }
    }

    private static String html(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
