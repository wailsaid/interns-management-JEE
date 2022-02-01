import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/afficherstagiareprofile")
public class Afficherstagiare extends HttpServlet {

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
String query = "select * from stagiare inner join categorie on categorie.id = stagiare.idcat"
+ " inner join niveaustage on niveaustage.id = stagiare.idniv inner join direction on direction.id =stagiare.iddir "
	+ "where stagiare.id = ? ;	";	
			java.sql.PreparedStatement ps = connexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs =ps.executeQuery();
			Stagiare s = new Stagiare();
			while(rs.next()) {
				s.setid(rs.getInt("stagiare.id"));
				s.setnom(rs.getString("stagiare.nom"));
				s.setprenom(rs.getString("stagiare.prenom"));
				s.setemail(rs.getString("stagiare.email"));
				s.setcategorie(rs.getString("categorie.nom"));
				s.setniveau(rs.getString("niveaustage.nom"));
				s.setdirection(rs.getString("direction.nom"));
				s.setetabalissement(rs.getString("stagiare.etabalissement"));	
				s.setetat(rs.getString("stagiare.etat"));
				s.setevaluation(rs.getString("stagiare.evaluation"));
				
				if(rs.getString("stagiare.travail_demander")==null ) {
					s.settravail("Rien");
					
				}else {
					String qeury2 = "select stage.titre,encadreur.nom from stage inner join stagiare on stage.id=stagiare.travail_demander inner join encadreur on stagiare.idencad = encadreur.id "
							+ "where stagiare.id = ? ;";
					java.sql.PreparedStatement ps2 = connexion.prepareStatement(qeury2);
					ps2.setInt(1, id);
					ResultSet rs2 = ps2.executeQuery();
					while(rs2.next()) {
						s.settravail(rs2.getString("stage.titre"));
						s.setencadreur(rs2.getString("encadreur.nom"));
						
					}
				}

			}
			
			req.setAttribute("stagiare", s);
			req.getRequestDispatcher("/Stagiare_profil.jsp").forward(req, res);
			
			
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
