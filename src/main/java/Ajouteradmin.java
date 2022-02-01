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
@WebServlet("/ajouteradmin")
public class Ajouteradmin extends HttpServlet {
	Connection connexion = null;

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
				ResultSet rs = statement.executeQuery("select * from categorie ;");
				
				ArrayList<Categorie> clist = new ArrayList<Categorie>();
				  while(rs.next()) {
					  Categorie c = new Categorie();
					  c.setid(rs.getInt("id"));
					  c.setnom(rs.getString("nom"));
				    	clist.add(c);	
				    }
				  rs = statement.executeQuery("select * from direction ;");
				  ArrayList<Direction> dlist = new ArrayList<Direction>();
				  while(rs.next()) {
					  Direction c = new Direction();
					  c.setid(rs.getInt("id"));
					  c.setnom(rs.getString("nom"));
				    	dlist.add(c);	
				    }
				req.setAttribute("categorie", clist);
				req.setAttribute("direction", dlist);
				req.getRequestDispatcher("/ajouteradmin.jsp").forward(req, res);
				

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
	
	Admin admin = new Admin();
	admin.setnom(req.getParameter("nom"));
	admin.setprenom(req.getParameter("prenom"));
	admin.setusername(req.getParameter("username"));
	admin.setpassword(req.getParameter("password"));
	admin.setdirection(req.getParameter("direction"));
	admin.setcategorie(req.getParameter("categorie"));
	
	try {
		connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
		String sql = "insert into admin(nom,prenom,username,password,idcat,iddir) values( ? , ? , ? , ? , ? , ? );";
		PreparedStatement ps = connexion.prepareStatement(sql);
		ps.setString(1, admin.getnom());
		ps.setString(2, admin.getprenom());
		ps.setString(3, admin.getusername());
		ps.setString(4, admin.getpassword());
		ps.setString(5, admin.getcategorie());
		ps.setString(6, admin.getdirection());
		ps.executeUpdate();
		req.getRequestDispatcher("/listadmins").forward(req, res);




		
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