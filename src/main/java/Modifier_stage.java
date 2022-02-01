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
@WebServlet(urlPatterns={"/modifier","/Sauvgarder"})
public class Modifier_stage extends HttpServlet {

	Connection connexion = null;
	 
	
	protected void doGet(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("q"));

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

		    String query ="select stage.id, stage.titre, stage.description,categorie.nom, niveaustage.nom from stage inner join categorie on stage.idcat = categorie.id"
		    		+ " inner join niveaustage on stage.idniv = niveaustage.id where stage.id= ? ;";
		    PreparedStatement ps = connexion.prepareStatement(query);
		    ps.setInt(1, id);
		    rs=ps.executeQuery();
		    Stage s = new Stage();
			

		    while(rs.next()) {
		    	s.setid(rs.getInt("stage.id"));
		    	s.setitret(rs.getString("stage.titre"));
		    	s.setdescription(rs.getString("stage.description"));
		    	s.setcategorie(rs.getString("categorie.nom"));
		    	s.setniveau(rs.getString("niveaustage.nom"));

		    	
		    }

		    req.setAttribute("profile_stage", s);
			req.setAttribute("categorie", clist);
			req.setAttribute("niveau", nlist);
			req.getRequestDispatcher("modifer_stage.jsp").forward(req, res);
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
protected void doPost(HttpServletRequest req ,HttpServletResponse res ) throws ServletException, IOException {
		//Stage stage = (Stage) req.getSession(false).getAttribute("profile_stage");
		int id = Integer.parseInt(req.getParameter("q"));

		String titre = req.getParameter("titre");	
		
		//System.out.println(req.getParameter("categorie"));
		//int cat_id = Integer.parseInt(req.getParameter("categorie"));
		
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
		
			
			String query = "UPDATE stage SET titre= ? ,idniv= ? ,description= ? WHERE id = ? ;";
			java.sql.PreparedStatement prstmt = connexion.prepareStatement(query);
			prstmt.setString(1, titre);
			//prstmt.setInt(2 , cat_id );
			prstmt.setInt(2, niv_id);
			prstmt.setString(3, description);
			prstmt.setInt(4, id);			
			prstmt.executeUpdate();
						
			req.getRequestDispatcher("afficherprofile?q="+id).forward(req, res);
			
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
