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
@WebServlet("/listadmins")
public class Listadmins extends HttpServlet {
	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query ="select * from admin inner join categorie on admin.idcat=categorie.id inner join direction "
					+ "on admin.iddir=direction.id";
			Statement s = connexion.createStatement();
			ResultSet rs = s.executeQuery(query);
			ArrayList<Admin> list = new ArrayList<Admin>();
			while(rs.next()) {
				Admin a = new Admin();
				a.setid(rs.getInt("admin.id"));
				a.setnom(rs.getString("admin.nom"));
				a.setprenom(rs.getString("admin.prenom"));
				a.setcategorie(rs.getString("categorie.nom"));
				a.setdirection(rs.getString("direction.nom"));
				a.setusername(rs.getString("admin.username"));
				//a.setpassword(rs.getString("admin.password"));
				list.add(a);
			}
			req.setAttribute("listadmin", list);
			req.getRequestDispatcher("/listadmin.jsp").forward(req, res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
