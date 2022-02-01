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

@WebServlet("/afficherencadreurprofile")
public class Afficherencadreur extends HttpServlet {

	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	int id = Integer.parseInt(req.getParameter("q"));
	
	
	try {
		Class.forName( "com.mysql.jdbc.Driver" );
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
	try {
		connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
		String query = "select * from encadreur inner join categorie on categorie.id = encadreur.idcat inner join direction on direction.id = encadreur.iddir where encadreur.id = ?;";
		PreparedStatement ps = connexion.prepareStatement(query);
		ps.setInt(1, id);
		ResultSet rs =ps.executeQuery();
		Encadreur e = new Encadreur();
		while(rs.next()) {
			e.setid(rs.getInt("encadreur.id"));
			e.setnom(rs.getString("encadreur.nom"));
			e.setprenom(rs.getString("encadreur.prenom"));
			e.setcategorie(rs.getString("categorie.nom"));
			e.setemail(rs.getString("encadreur.email"));
			e.setdirection(rs.getString("direction.nom"));
			if(rs.getInt("encadreur.suivi")==1) {
				req.setAttribute("suivi", rs.getInt("encadreur.suivi"));
			}
		}
		
		req.setAttribute("encadreur", e);
		req.getRequestDispatcher("/encadreur_profil.jsp").forward(req, res);
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
	    if ( connexion != null )
	        try {
	           
	            connexion.close();
	        } catch ( SQLException ignore ) {
	        	
	        }
	}

	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
		
	}
}
