import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/affecter")
public class Affecter extends HttpServlet {

	Connection connexion = null;
	
protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
	Admin user = (Admin) req.getSession(false).getAttribute("user");
	
		   try {
				Class.forName( "com.mysql.jdbc.Driver" );
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query="select stage.id,stage.titre,stage.etat,categorie.nom,niveaustage.nom from stage inner join categorie on stage.idcat = categorie.id "
					+ "inner join niveaustage on niveaustage.id = stage.idniv"
					+ " where categorie.nom = ? and stage.etat = 'Valide' ;";
			java.sql.PreparedStatement prstmt = connexion.prepareStatement(query);
			prstmt.setString(1, user.getcategorie());
			
			ResultSet rs = prstmt.executeQuery();
			ArrayList<Stage> liststage = new ArrayList<Stage>();
			ArrayList<Stagiare> liststagiare = new ArrayList<Stagiare>();
			ArrayList<Encadreur> listencadreur = new ArrayList<Encadreur> ();

			while(rs.next()) {
				Stage s =new Stage();
				s.setid(rs.getInt("stage.id"));
				s.setitret(rs.getString("titre"));
				s.setniveau(rs.getString("niveaustage.nom"));
				s.setcategorie(rs.getString("categorie.nom"));
				s.setetat(rs.getString("stage.etat"));
				liststage.add(s);
			}
			query ="select stagiare.id, stagiare.nom, stagiare.prenom, stagiare.etat, categorie.nom,niveaustage.nom from stagiare inner join categorie on stagiare.idcat = categorie.id "
					+ " inner join niveaustage on niveaustage.id = stagiare.idniv where categorie.nom = ? and stagiare.etat='attendre l affectation';";
			prstmt = connexion.prepareStatement(query);
			prstmt.setString(1, user.getcategorie());
			rs = prstmt.executeQuery();
			while(rs.next()) {
				Stagiare s = new Stagiare();
				s.setid(rs.getInt("stagiare.id"));
				s.setnom(rs.getString("stagiare.nom"));
				s.setprenom(rs.getString("stagiare.prenom"));
				s.setniveau(rs.getString("niveaustage.nom"));
				s.setcategorie(rs.getString("categorie.nom"));
				s.setetat(rs.getString("stagiare.etat"));
				
				liststagiare.add(s);
			}
			query ="select encadreur.id, encadreur.nom, encadreur.prenom, categorie.nom from encadreur inner join categorie on encadreur.idcat = categorie.id "
					+ " where categorie.nom = ? ;";
			prstmt = connexion.prepareStatement(query);
			prstmt.setString(1, user.getcategorie());
			rs = prstmt.executeQuery();
			while(rs.next()) {
				Encadreur s = new Encadreur();
				s.setid(rs.getInt("encadreur.id"));
				s.setnom(rs.getString("encadreur.nom"));
				s.setprenom(rs.getString("encadreur.prenom"));
				s.setcategorie(rs.getString("categorie.nom"));
				listencadreur.add(s);
			}
			
			req.setAttribute("stagelist", liststage);
			req.setAttribute("list_satgiare", liststagiare);
			req.setAttribute("list_encadreur", listencadreur);
			req.getRequestDispatcher("/affecter_stage.jsp").forward(req, res);
			
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
		
		int stageid = Integer.parseInt(req.getParameter("stage"));
		String[] stagiare =req.getParameterValues("stagiares");
		int encadid = Integer.parseInt(req.getParameter("encadreur"));
		String dated = req.getParameter("debut");
		String datef = req.getParameter("fin");
		
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query ="update stage set etat ='effectué', date_debut= ? , date_fin = ? where stage.id = ? ;";
			PreparedStatement ps = connexion.prepareStatement(query);
			ps.setString(1, dated);
			ps.setString(2, datef);
			ps.setInt(3, stageid);
			ps.executeUpdate();
			
			query ="update stagiare set etat ='travailler en stage', travail_demander = ? , idencad = ? where stagiare.id = ? ;";
			ps=connexion.prepareStatement(query);
			for(String s : stagiare) {
				ps.setInt(1, stageid);
				ps.setInt(2, encadid);
				ps.setString(3, s);
				
				ps.executeUpdate();
			}
			
			query = "select suivi from encadreur  where encadreur.id = ? ;";
			ps=connexion.prepareStatement(query);
			ps.setInt(1, encadid);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
			int suivi = rs.getInt("suivi");
			suivi++;
			query = "update encadreur set suivi = ? where encadreur.id = ? ;";
			ps=connexion.prepareStatement(query);
			ps.setInt(1, suivi);
			ps.setInt(2, encadid);
			ps.executeUpdate();
			req.setAttribute("done", true);
			req.getRequestDispatcher("/affecter_stage.jsp").forward(req, res);
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

		
	}
	

}
