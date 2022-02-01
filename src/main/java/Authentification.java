import java.io.IOException;

import java.sql.*;
import java.util.Properties;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Authentifier")
public class Authentification extends HttpServlet {
	
	private String user ="java";
	private String pw="&Aqwxdrtgb1";
	private String url = "jdbc:mysql://localhost:3306/l3_pfe";
	Connection connexion = null;
	
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("Authentification.jsp").forward(req, res);
		}
	
	
	
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("username");
		String password = req.getParameter("password");
		
		
	   try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   if(name.equals("admin") && password.equals("admin")) {
			 req.getRequestDispatcher("/appadmin").forward(req, res);
		}else {
	   try {
		    connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
		    String query ="SELECT admin.username,categorie.nom ,direction.nom FROM admin INNER JOIN categorie ON admin.idcat = categorie.id inner join "
		    		+ "direction on direction.id = admin.iddir WHERE username = ? and password = ? ;";
		    
		    java.sql.PreparedStatement prStmt = connexion.prepareStatement(query);

		    prStmt.setString(1, name);
		    prStmt.setString(2, password);

		    ResultSet rs =prStmt.executeQuery();
		    if(rs.next()) {
				 Admin admin = new Admin();
				 admin.setusername(name);
				 admin.setcategorie(rs.getString("categorie.nom"));
				 admin.setdirection(rs.getString("direction.nom"));
				 //System.out.print(admin.getdirection());
				 HttpSession session = req.getSession();
				 session.setAttribute("user", admin);
				 req.getRequestDispatcher("/page_d_acceil.jsp").forward(req, res);
			    }
		    
		    else{
		    	
		    	 req.setAttribute("erreur", true);
		    	req.getRequestDispatcher("Authentification.jsp").forward(req, res);
		    }
		    
		   
		    

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
		    if ( connexion != null )
		        try {
		           
		            connexion.close();
		        } catch ( SQLException ignore ) {
		        	
		        }
		}
				    
		
				    
		}	   
		
		
    }
		
	}
	


