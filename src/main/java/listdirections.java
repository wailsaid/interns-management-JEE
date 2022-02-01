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

@WebServlet("/listdirections")

public class listdirections extends HttpServlet {
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
				Statement s = connexion.createStatement();
				ResultSet rs = s.executeQuery("select * from direction;");
				ArrayList<Direction> list = new	ArrayList<Direction>();
				 while(rs.next()) {	 
					 Direction d = new Direction();
					  d.setid(rs.getInt("id"));
					  d.setnom(rs.getString("nom"));
				    	list.add(d);	
				    }
				req.setAttribute("directions", list);
				req.getRequestDispatcher("/listdirection.jsp").forward(req, res);
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
	String nom = req.getParameter("nom");
	try {
		connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
		PreparedStatement ps = connexion.prepareStatement("insert into direction(nom) values( ? );");
		ps.setString(1, nom);
		ps.executeUpdate();
		doGet(req,res);
		
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
