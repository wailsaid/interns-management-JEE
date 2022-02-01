import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/valider")

public class Valider extends HttpServlet {

	Connection connexion = null;
	
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = req.getParameter("q")	;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query = "UPDATE stage SET etat ='Valide' WHERE id = ? ;";
			java.sql.PreparedStatement prstmt = connexion.prepareStatement(query);
			prstmt.setString(1, id);
			prstmt.executeUpdate();
		//	stage.setetat("Valide");
			//req.getSession(true).setAttribute("profile_stage", stage);	
			req.getRequestDispatcher("/afficherprofile").forward(req, res);
			
						
			
			
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

}


