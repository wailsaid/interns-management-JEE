import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/afficherprofile")
public class Afficherstage extends HttpServlet {
	
	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
			int id = Integer.parseInt(req.getParameter("q"));
			
			
			Stage profil = new Stage();

			try {
					Class.forName( "com.mysql.jdbc.Driver" );
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
				String query="select stage.id,stage.titre,stage.description,stage.etat,categorie.nom,niveaustage.nom, stage.date_debut,stage.date_fin from stage inner join categorie on stage.idcat = categorie.id inner join niveaustage on stage.idniv=niveaustage.id where stage.id = ? ;";
					java.sql.PreparedStatement prstmt = connexion.prepareStatement(query);
					prstmt.setInt(1, id);
					ResultSet rs = prstmt.executeQuery();
					if(rs.next()) {
						profil.setid(rs.getInt("stage.id"));
						profil.setitret(rs.getString("titre"));
						profil.setcategorie(rs.getString("categorie.nom"));
						profil.setniveau(rs.getString("niveaustage.nom"));
						profil.setdescription(rs.getString("stage.description"));
						profil.setetat(rs.getString("stage.etat"));
						profil.setdateD(rs.getDate("stage.date_debut"));
						profil.setdateF(rs.getDate("stage.date_fin"));

					}
					req.setAttribute("profile_stage", profil);
				
					req.getRequestDispatcher("/stage_profil.jsp").forward(req, res);

					
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
	protected void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {	
		doGet(req,res);
	}
	
	

}
