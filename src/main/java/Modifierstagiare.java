import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/modiferStagire")
public class Modifierstagiare extends HttpServlet {

	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("q"));
		
		
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query = "select stagiare.id, stagiare.nom, stagiare.prenom, stagiare.email,stagiare.etabalissement, "
					+ "categorie.nom, niveaustage.nom,direction.nom  from stagiare inner join categorie on categorie.id = stagiare.idcat"
					+ " inner join niveaustage on niveaustage.id = stagiare.idniv inner join direction on direction.id=stagiare.iddir where stagiare.id = ? ;";
			java.sql.PreparedStatement ps = connexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Stagiare s = new Stagiare();
			while(rs.next()) {
				s.setid(rs.getInt("stagiare.id"));
				s.setnom(rs.getString("stagiare.nom"));
				s.setprenom(rs.getString("stagiare.prenom"));
				s.setemail(rs.getString("stagiare.email"));
				s.setdirection(rs.getString("direction.nom"));
				s.setcategorie(rs.getString("categorie.nom"));
				s.setniveau(rs.getString("niveaustage.nom"));
				s.setetabalissement(rs.getString("stagiare.etabalissement"));
			}
			query="SELECT * FROM categorie inner join admin on categorie.nom = ? ;";
			ps = connexion.prepareStatement(query);
			Admin admin = (Admin) req.getSession(false).getAttribute("user");
			ps.setString(1,admin.getcategorie());
			 rs = ps.executeQuery();
		   // ResultSet rs = statement.executeQuery("SELECT * FROM categorie inner join admin on categorie.id = ? ;");
		   Categorie clist = new Categorie();
		  if(rs.next()) {	    	
			  clist.setid(rs.getInt("id"));
			  clist.setnom(rs.getString("nom"));    		
		    }
		  Statement statement = connexion.createStatement();
		  rs = statement.executeQuery("SELECT * FROM niveaustage;");
		    ArrayList<Niveau> nlist = new ArrayList<Niveau>();
		    while(rs.next()) {
		    	Niveau n = new Niveau();
		    	n.setid(rs.getInt("id"));
		    	n.setnom(rs.getString("nom"));
		    	nlist.add(n);	
		    }
		    query="SELECT * FROM direction inner join admin on direction.nom = ? ;";
			ps = connexion.prepareStatement(query);
			ps.setString(1,admin.getdirection());
			 rs = ps.executeQuery();
			 Direction d = new Direction();
			if(rs.next()) {
				d.setid(rs.getInt("id"));
				d.setnom(rs.getString("nom"));
			}
			req.setAttribute("direction", d);
		    req.setAttribute("categorie", clist);
		    req.setAttribute("niveau", nlist);
			req.setAttribute("stagiare", s);
			req.getRequestDispatcher("/Modifier_stagiare.jsp").forward(req, res);
			
		} catch (SQLException e) {
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
		Stagiare stagiare = new Stagiare();
		stagiare.setid(Integer.parseInt(req.getParameter("q")));
		stagiare.setnom(req.getParameter("nom"));
		stagiare.setprenom(req.getParameter("prenom"));
		stagiare.setemail(req.getParameter("email"));
		int idniv = Integer.parseInt(req.getParameter("niveau"));
		stagiare.setetabalissement(req.getParameter("etabalissement"));
		stagiare.setdirection(req.getParameter("direction"));

		try {
			Class.forName( "com.mysql.jdbc.Driver" );
	
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query ="update stagiare set nom = ? , prenom = ? , email = ? ,iddir= ? , idniv = ? , etabalissement = ? where id = ? ;";
			java.sql.PreparedStatement ps = connexion.prepareStatement(query);
			ps.setString(1, stagiare.getnom());
			ps.setString(2, stagiare.getprenom());
			ps.setString(3, stagiare.getemail());
			ps.setString(4, stagiare.getdirection());
			ps.setInt(5, idniv);
			ps.setString(6,stagiare.getetabalissement() );
			ps.setInt(7, stagiare.getid());
			
			ps.executeUpdate();
			
			req.getRequestDispatcher("/afficherstagiareprofile?q="+stagiare.getid()).forward(req, res);
			
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
