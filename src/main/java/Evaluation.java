import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
@WebServlet("/evaluer")
@MultipartConfig(maxFileSize = 161772150) 
public class Evaluation extends HttpServlet {

	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		Admin user = (Admin) req.getSession(false).getAttribute("user");
		
		try {
			Connection connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query ="select stage.id, stage.titre,categorie.nom from stage inner join categorie on stage.idcat = categorie.id "
					+ "where categorie.nom = ? and stage.etat='effectué' ;";
			java.sql.PreparedStatement ps = connexion.prepareStatement(query);
			ps.setString(1, user.getcategorie());
			ResultSet rs = ps.executeQuery();
			ArrayList<Stage> list = new ArrayList<Stage>();
			while(rs.next()) {
				Stage s = new Stage();
				s.setid(rs.getInt("stage.id"));
				s.setitret(rs.getString("stage.titre"));
				s.setcategorie(rs.getString("categorie.nom"));
				list.add(s);
			}
			req.setAttribute("stages", list);
			req.getRequestDispatcher("/evaluation.jsp").forward(req, res);
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
			String[] ids = req.getParameterValues("stagiares");
			
			String[] idstage = req.getParameterValues("stage");
			
			
			String evaluation = req.getParameter("evaluation");
			Part ficher = req.getPart("travail");
    
			InputStream  input = ficher.getInputStream();
	        
			try {
				Connection connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
				String query="update stage set etat= 'Terminer', travail_livrer = ? where stage.id = ? ;";
				PreparedStatement ps=connexion.prepareStatement(query);
		        ps.setBlob(1, input);
		         ps.setString(2,idstage[0]);
		         ps.executeUpdate();
		         query ="select idencad from stagiare where id = ? ;"; 
		         ps=connexion.prepareStatement(query);
		         ps.setString(1, ids[0]);
		         ResultSet rs= ps.executeQuery();
		         if(rs.next()) {
		         String id = rs.getString("idencad");
		         query ="select suivi from encadreur  where encadreur.id = ? ;";
		         ps=connexion.prepareStatement(query);
				 ps.setString(1, id);
				ResultSet rs2= ps.executeQuery();
				if(rs2.next()) {
					int suivi = rs2.getInt("suivi");
					suivi--;
		         query ="update encadreur set suivi = ?  where encadreur.id = ? ;";
		         ps=connexion.prepareStatement(query);
		         ps.setInt(1, suivi);
		         ps.setString(2, id);
		         ps.executeUpdate();
		         }
		         }
		         
		         query ="update stagiare set etat ='fini le travail' ,evaluation = ?, idencad = null where stagiare.id = ? ;";
		         ps=connexion.prepareStatement(query);
		         ps.setString(1, evaluation);
		         for(String id : ids) {
		        	 ps.setString(2,id);
		        	 ps.executeUpdate();
		         }
		        
		         
			
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

			input.close();
			doGet(req,res);
			
	}

}
