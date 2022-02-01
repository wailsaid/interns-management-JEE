import java.io.IOException; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/affichlistestagiare")
public class Afficherstagiares extends HttpServlet {
	
	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			ArrayList<Stagiare> list = new ArrayList<Stagiare> ();
			
			String query ="select stagiare.id, stagiare.nom, stagiare.prenom, stagiare.etat,direction.nom"
					+ " ,categorie.nom from stagiare inner join categorie on stagiare.idcat = categorie.id inner join "
					+ "direction on stagiare.iddir = direction.id;";
			java.sql.PreparedStatement prstmt = connexion.prepareStatement(query);
			
			ResultSet rs = prstmt.executeQuery();
			while(rs.next()) {
				Stagiare s = new Stagiare();
				s.setid(rs.getInt("stagiare.id"));
				s.setnom(rs.getString("stagiare.nom"));
				s.setprenom(rs.getString("stagiare.prenom"));
				s.setcategorie(rs.getString("categorie.nom"));
				s.setdirection(rs.getString("direction.nom"));
				s.setetat(rs.getString("stagiare.etat"));
				
				list.add(s);
			}
			req.setAttribute("list_satgiare", list);
			req.getRequestDispatcher("/liste_des_stagiares.jsp").forward(req, res);
			
			
			
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
