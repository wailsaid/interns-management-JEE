import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/suprimerAdmin")
public class suprimeradmin extends HttpServlet {
	Connection connexion = null;
protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
	String id = req.getParameter("q");
	
	try {
		connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
		String sql ="delete from admin where id = ? ;";
		PreparedStatement ps = connexion.prepareStatement(sql);
		ps.setString(1, id);
		ps.executeUpdate();
		req.getRequestDispatcher("/listadmins").forward(req, res);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
	    if ( connexion != null )
	        try {
	           
	            connexion.close();
	        } catch ( SQLException ignore ) {
	        	
	        }
	}

}
}
