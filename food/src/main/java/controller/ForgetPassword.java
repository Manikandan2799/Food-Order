package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import view.DbConnection;

@WebServlet("/ForgetPassword")
public class ForgetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uname = request.getParameter("uname");
		String password = request.getParameter("password");
		session.setAttribute("password", password);
		session.setAttribute("uname", uname);
		String email = request.getParameter("email");
		String validate = ValidateOtp.otp();
		System.out.println(validate);
		session.setAttribute("validate", validate);
		SendEmail.sendOTP(email, validate);
		response.sendRedirect("Otp.jsp");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter pw1 = response.getWriter();
		String otp = request.getParameter("otp");
		System.out.println(otp);
		if (session.getAttribute("validate") == otp) {
			System.out.println("gfdgh");
			Connection con = DbConnection.init();
			PreparedStatement st;
			try {

				st = con.prepareStatement("update members set  password='"
						+ BCrypt.hashpw((String) session.getAttribute("password"), BCrypt.gensalt()) + "' where uname='"
						+ session.getAttribute("uname") + "'");
				st.executeUpdate();
				pw1.println("<html>");
				pw1.println("<script>");
				pw1.println("alert('Otp Successfully Verified..!!')");
				pw1.println("</script>");
				pw1.println("</html>");

				response.sendRedirect("index.jsp");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("dfg");
			PrintWriter pw = response.getWriter();
			pw.println("<html>");
			pw.println("<script>");
			pw.println("alert('Otp Doesn't Match..!!') ");
			pw.println("</script>");
			pw.println("</html>");
		}
	}
}