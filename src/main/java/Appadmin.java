import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/appadmin")
public class Appadmin extends HttpServlet {
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res ) throws ServletException, IOException {
		Admin admin = new Admin();
		admin.setusername("admin");
		admin.setcategorie("admin");
		req.getSession(false).setAttribute("user", admin);
		req.getRequestDispatcher("/page_d_acceil.jsp").forward(req, res);

	}
	protected void doPost(HttpServletRequest req,HttpServletResponse res ) throws ServletException, IOException {
		doGet(req,res);
	} 
}
