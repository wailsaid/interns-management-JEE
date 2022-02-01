import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/affichlistestage")
public class Afficherliste extends HttpServlet {
	
	private String user ="java";
	private String pw="&Aqwxdrtgb1";
	String url = "jdbc:mysql://localhost:3306/l3_pfe";
	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		
		//System.out.println(1);

		
		   try {
				Class.forName( "com.mysql.jdbc.Driver" );
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   try {
			connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query="select stage.id,stage.titre,stage.description,stage.etat,categorie.nom,niveaustage.nom "
					+ "from stage inner join categorie on stage.idcat = categorie.id "
					+ "inner join niveaustage on stage.idniv=niveaustage.id;";
			java.sql.PreparedStatement prstmt = connexion.prepareStatement(query);
			
			ResultSet rs = prstmt.executeQuery();
			ArrayList<Stage> list = new ArrayList<Stage>();
			while(rs.next()) {
				Stage s =new Stage();
				s.setid(rs.getInt("stage.id"));
				s.setitret(rs.getString("titre"));
				s.setcategorie(rs.getString("categorie.nom"));
				s.setniveau(rs.getString("niveaustage.nom"));
				s.setdescription(rs.getString("stage.description"));
				s.setetat(rs.getString("stage.etat"));
				list.add(s);
			}
			
			req.setAttribute("stagelist", list);
			req.getRequestDispatcher("/liste_des_stages.jsp").forward(req, res);
			
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
		doGet(req,res);

	}

}
