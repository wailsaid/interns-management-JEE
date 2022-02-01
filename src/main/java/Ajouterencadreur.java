import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/ajouterencadreur")
public class Ajouterencadreur extends HttpServlet {

	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			Statement statement = connexion.createStatement();
			String query= "SELECT * FROM categorie inner join admin on categorie.nom = ? ;";
			java.sql.PreparedStatement prstmt = connexion.prepareStatement(query);
			Admin admin = (Admin) req.getSession(false).getAttribute("user");
			prstmt.setString(1,admin.getcategorie());
			ResultSet rs = prstmt.executeQuery();
		   // ResultSet rs = statement.executeQuery("SELECT * FROM categorie inner join admin on categorie.id = ? ;");
		   Categorie clist = new Categorie();
		  if(rs.next()) {	    	
			  clist.setid(rs.getInt("id"));
			  clist.setnom(rs.getString("nom"));
		    		
		    } 
		  query = " select * from direction where direction.nom = ? ;";
		  prstmt = connexion.prepareStatement(query);
		  //System.out.print(admin.getdirection());
			prstmt.setString(1, admin.getdirection());
			rs = prstmt.executeQuery();
		  Direction dir = new Direction();
		  if(rs.next()) { 
			  dir.setid(rs.getInt("direction.id"));  
			  dir.setnom(rs.getString("direction.nom"));
		    }
		  req.setAttribute("direction", dir);
		  req.setAttribute("categorie", clist);
		
		req.getRequestDispatcher("/ajouter_encadreur.jsp").forward(req, res);
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
		Encadreur encadreur = new Encadreur();
		encadreur.setnom(req.getParameter("nom"));
		encadreur.setprenom(req.getParameter("prenom"));
		int i = Integer.parseInt(req.getParameter("categorie"));
		encadreur.setemail(req.getParameter("email"));
		encadreur.setdirection(req.getParameter("direction"));
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query ="insert into encadreur(nom,prenom,email,idcat,iddir) values( ? , ? , ? ,? , ? );";
			java.sql.PreparedStatement ps = connexion.prepareStatement(query);
			ps.setString(1, encadreur.getnom());
			ps.setString(2, encadreur.getprenom());
			ps.setString(3, encadreur.getemail());
			ps.setInt(4, i);
			ps.setString(5, encadreur.getdirection());
			ps.executeUpdate();
			req.getRequestDispatcher("/affichlisteencadreur").forward(req, res);
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
