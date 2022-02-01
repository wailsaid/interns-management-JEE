import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/employee")
public class EntrerEmployee extends HttpServlet {
	
	
	public void service(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession();
		Employee user = new Employee();
		user.setusername("employe");
		session.setAttribute("user",user);
		req.getRequestDispatcher("/page_d_acceil.jsp").forward(req, res);
	}

}
