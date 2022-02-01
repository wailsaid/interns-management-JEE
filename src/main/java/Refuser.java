import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/refuser")
public class Refuser extends HttpServlet {

	Connection connexion = null;
	
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Stage stage = (Stage) req.getSession(false).getAttribute("profile_stage");	
		String id = req.getParameter("q");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query = "UPDATE stage SET etat ='Refuser' WHERE id = ? ;";
			 java.sql.PreparedStatement prstmt = connexion.prepareStatement(query);
			 prstmt.setString(1, id);
			prstmt.executeUpdate();
			//stage.setetat("Refuser");
			//req.getSession(true).setAttribute("profile_stage", stage);
			req.getRequestDispatcher("/afficherprofile").forward(req, res);

			//req.getRequestDispatcher("stage_profil.jsp").forward(req, res);

			
			
			
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
