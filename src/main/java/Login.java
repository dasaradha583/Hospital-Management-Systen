

import java.io.IOException;
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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userMail = request.getParameter("username");
    String password = request.getParameter("pass");

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        // Replace with your database connection info
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "Password");

        stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        stmt.setString(1, userMail);
        stmt.setString(2, password);

        rs = stmt.executeQuery();

        if (rs.next()) {
            // User exists and password is correct
            HttpSession session = request.getSession();
            session.setAttribute("username", userMail);
            response.sendRedirect("welcome.jsp");
        } else {
            // User does not exist or password is incorrect
            response.sendRedirect("error.jsp");
        }
    } catch (SQLException e) {
        throw new ServletException("Database error", e);
    } finally {
        // Close resources in reverse order of opening
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { }
        }
        if (stmt != null) {
            try { stmt.close(); } catch (SQLException e) { }
        }
        if (conn != null) {
            try { conn.close(); } catch (SQLException e) {  }
        }
    }
}
		
}
