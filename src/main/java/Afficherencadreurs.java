import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/affichlisteencadreur")
public class Afficherencadreurs extends HttpServlet {

	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			ArrayList<Encadreur> list = new ArrayList<Encadreur> ();
			String query ="select encadreur.id, encadreur.nom, encadreur.prenom, categorie.nom, direction.nom"
					+ " from encadreur inner join categorie on encadreur.idcat = categorie.id inner join "
					+ "direction on encadreur.iddir = direction.id ;";
			java.sql.PreparedStatement prstmt = connexion.prepareStatement(query);
			
			ResultSet rs = prstmt.executeQuery();
			while(rs.next()) {
				Encadreur s = new Encadreur();
				s.setid(rs.getInt("encadreur.id"));
				s.setnom(rs.getString("encadreur.nom"));
				s.setprenom(rs.getString("encadreur.prenom"));
				s.setcategorie(rs.getString("categorie.nom"));
				s.setdirection(rs.getString("direction.nom"));
				list.add(s);
			}
			req.setAttribute("list_encadreur", list);
			req.getRequestDispatcher("/liste_des_encadreur.jsp").forward(req, res);
			
			
			
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
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}

}
