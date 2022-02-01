import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/afficheradminprofile")
public class afficheradmin extends HttpServlet {
	Connection connexion = null;
protected void doGet(HttpServletRequest req ,HttpServletResponse res ) throws ServletException, IOException {
	String q = req.getParameter("q");
	
	try {
		connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
		String sql = "select * from admin inner join direction on admin.iddir=direction.id inner join categorie on "
				+ "admin.idcat=categorie.id where admin.id = ? ;";
		PreparedStatement ps = connexion.prepareStatement(sql);
		ps.setString(1, q);
		ResultSet rs = ps.executeQuery();
		Admin ad = new Admin();
		if(rs.next()) {
			ad.setid(rs.getInt("admin.id"));
			ad.setnom(rs.getString("admin.nom"));
			ad.setprenom(rs.getString("admin.prenom"));
			ad.setcategorie(rs.getString("categorie.nom"));
			ad.setdirection(rs.getString("direction.nom"));
			ad.setusername(rs.getString("admin.username"));
			ad.setpassword(rs.getString("admin.password"));
		}
		req.setAttribute("admin", ad);
		req.getRequestDispatcher("/admin_profil.jsp").forward(req, res);
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
protected void doPost(HttpServletRequest req ,HttpServletResponse res ) throws ServletException, IOException {
	doGet(req,res);	



}
}
