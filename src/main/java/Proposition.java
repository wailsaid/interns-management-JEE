import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.cj.Session;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import com.mysql.jdbc.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns ={"/proposer"})
public class Proposition extends HttpServlet {

	private static java.sql.Connection connexion = null;
	
	protected void doGet(HttpServletRequest req ,HttpServletResponse res ) throws ServletException, IOException {
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			Statement statement = connexion.createStatement();
		    ResultSet rs = statement.executeQuery("SELECT * FROM categorie;");
		    ArrayList<Categorie> clist = new ArrayList<Categorie>();
		    while(rs.next()) {
		    	Categorie c = new Categorie();
		    	c.setid(rs.getInt("id"));
		    	c.setnom(rs.getString("nom"));
		    	clist.add(c);	
		    }
		    rs = statement.executeQuery("SELECT * FROM niveaustage;");
		    ArrayList<Niveau> nlist = new ArrayList<Niveau>();
		    while(rs.next()) {
		    	Niveau n = new Niveau();
		    	n.setid(rs.getInt("id"));
		    	n.setnom(rs.getString("nom"));
		    	nlist.add(n);	
		    }
		    
			req.setAttribute("categorie", clist);
			req.setAttribute("niveau", nlist);
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
				
		req.getRequestDispatcher("proposertheme.jsp").forward(req, res);
		
	}
	protected void doPost(HttpServletRequest req ,HttpServletResponse res ) throws ServletException, IOException {
		
		String titre = req.getParameter("titre");
		int cat_id = Integer.parseInt(req.getParameter("categorie"));
		int niv_id = Integer.parseInt(req.getParameter("niveau"));
		String description = req.getParameter("Description");
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			
			String query = ("insert into stage ( titre, idcat, idniv, description ) values ( ? , ? , ? , ? );");
			
			java.sql.PreparedStatement preparedStmt = connexion.prepareStatement(query);
			preparedStmt.setString (1, titre);
		    preparedStmt.setInt (2, cat_id);
		    preparedStmt.setInt (3, niv_id);
		    preparedStmt.setString(4, description);
		    
		    int i = preparedStmt.executeUpdate();
		    //System.out.println(i);
		    req.setAttribute("proposer", true);
		    doGet(req,res);
		  //  req.getRequestDispatcher("/proposertheme.jsp").forward(req, res);

		
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
