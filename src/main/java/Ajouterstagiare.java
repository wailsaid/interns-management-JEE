import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ajouterstagire")
public class Ajouterstagiare extends HttpServlet {

	Connection connexion = null;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
		Stagiare stagiare = new Stagiare();

		stagiare.setnom(req.getParameter("nom"));
		stagiare.setprenom(req.getParameter("prenom"));
		int i = Integer.parseInt(req.getParameter("categorie"));
		int j = Integer.parseInt(req.getParameter("niveau"));
		int k = Integer.parseInt(req.getParameter("direction"));
		stagiare.setetabalissement(req.getParameter("etabalissement"));
		stagiare.setemail(req.getParameter("email"));
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query ="insert into stagiare(nom,prenom,email,idcat,idniv,iddir,etabalissement) values( ? , ? , ? , ? , ? , ? , ? );";
			java.sql.PreparedStatement ps = connexion.prepareStatement(query);
			ps.setString(1, stagiare.getnom());
			ps.setString(2, stagiare.getprenom());
			ps.setString(3, stagiare.getemail());
			ps.setInt(4, i);
			ps.setInt(5, j);
			ps.setInt(6, k);
			ps.setString(7, stagiare.getetabalissement());
			 ps.executeUpdate();
			 req.getRequestDispatcher("/affichlistestagiare").forward(req, res);
			
			
			
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
	
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			Statement statement = connexion.createStatement();
			String query= "SELECT * FROM categorie where categorie.nom = ?;";
			PreparedStatement prstmt = connexion.prepareStatement(query);
			Admin admin = (Admin) req.getSession(false).getAttribute("user");
			
			prstmt.setString(1,admin.getcategorie());
			ResultSet rs = prstmt.executeQuery();
		   Categorie clist = new Categorie();
		   
		  if(rs.next()) {	    	
			  clist.setid(rs.getInt("categorie.id"));
			  clist.setnom(rs.getString("categorie.nom"));
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
		    rs = statement.executeQuery("SELECT * FROM niveaustage;");
		    ArrayList<Niveau> nlist = new ArrayList<Niveau>();
		    while(rs.next()) {
		    	Niveau n = new Niveau();
		    	n.setid(rs.getInt("id"));
		    	n.setnom(rs.getString("nom"));
		    	nlist.add(n);	
		    }
		    req.setAttribute("direction", dir);
		    req.setAttribute("categorie", clist);
			req.setAttribute("niveau", nlist);
			req.getRequestDispatcher("/ajouter_stagiare.jsp").forward(req, res);
			
			
			
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
