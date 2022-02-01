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
@WebServlet("/modiferAdmin")
public class modifieradmin extends HttpServlet {

	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("q"));
		
		
			try {
				connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
				String sql = "select * from admin inner join direction on admin.iddir=direction.id inner join categorie on "
						+ "admin.idcat=categorie.id where admin.id = ? ;";
				PreparedStatement ps = connexion.prepareStatement(sql);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				Admin ad = new Admin();
				if(rs.next()) {
					ad.setid(rs.getInt("admin.id"));
					ad.setnom(rs.getString("admin.nom"));
					ad.setprenom(rs.getString("admin.prenom"));
					ad.setcategorie(rs.getString("categorie.nom"));
					ad.setdirection(rs.getString("direction.nom"));
					ad.setusername(rs.getString("admin.username"));
					ad.setpassword(rs.getString("admin.password"));
				}
				Statement statement = connexion.createStatement();
				rs = statement.executeQuery("select * from categorie ;");
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
				req.setAttribute("admin", ad);
				req.getRequestDispatcher("/modifieradmin.jsp").forward(req, res);
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
		
		Admin admin = new Admin();
		admin.setid(Integer.parseInt(req.getParameter("q")));
		admin.setnom(req.getParameter("nom"));
		admin.setprenom(req.getParameter("prenom"));
		admin.setusername(req.getParameter("username"));
		admin.setpassword(req.getParameter("password"));
		admin.setdirection(req.getParameter("direction"));
		admin.setcategorie(req.getParameter("categorie"));
		
		
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String sql = "update admin set nom = ? ,prenom = ? ,username = ? , password = ? , iddir =  ? ,idcat= ?  where admin.id= ? ;";
			PreparedStatement ps = connexion.prepareStatement(sql);
			ps.setString(1, admin.getnom());
			ps.setString(2, admin.getprenom());
			ps.setString(3, admin.getusername());
			ps.setString(4, admin.getpassword());
			ps.setString(5, admin.getdirection());
			ps.setString(6, admin.getcategorie());
			ps.setInt(7, admin.getid());
			ps.executeUpdate();
			req.getRequestDispatcher("/afficheradminprofile?q="+admin.getid()).forward(req, res);
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