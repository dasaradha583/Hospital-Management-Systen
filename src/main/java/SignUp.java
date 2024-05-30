

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("name");
		String userMail = request.getParameter("email");
		String password = request.getParameter("password");
		String question1 = request.getParameter("question1");
		String question2 = request.getParameter("question2");
		String query = "";
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		PreparedStatement preparedstmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","Password");
			statement = connection.createStatement();
			query = "insert into signUp(name, email, password, answewr1, answer2) values(?,?,?,?,?) ";
			preparedstmt = connection.prepareStatement(query);
			preparedstmt.setString(1, userName);
			preparedstmt.setString(2, userMail);
			preparedstmt.setString(3, password);
			preparedstmt.setString(4, question1);
			preparedstmt.setString(5, question2);
			preparedstmt.executeUpdate();
			query = "insert into userData(userMail, password) values(?,?)";
			preparedstmt = connection.prepareStatement(query);
			preparedstmt.setString(1, userMail);
			preparedstmt.setString(2, password);
			preparedstmt.executeUpdate();
			response.sendRedirect("index.html");
			
		} catch (ClassNotFoundException e) {
			
		} catch(SQLException e) {
			System.out.println(e);
		}
		
	}

}
