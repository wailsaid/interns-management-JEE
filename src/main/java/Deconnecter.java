import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deconnecter")
public class Deconnecter extends HttpServlet {
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		//Admin ad =(Admin) req.getSession(false).getAttribute("user");
		//System.out.println(ad.getusername());
		req.getSession(false).removeAttribute("user");
		req.getSession(false).invalidate();
		res.sendRedirect("Authentifier");
		//req.getRequestDispatcher("/Authentifier").forward(req, res);
	}
 
}
