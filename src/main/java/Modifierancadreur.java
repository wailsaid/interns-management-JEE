import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/modiferEncadreur")
public class Modifierancadreur extends HttpServlet {

	Connection connexion = null;
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("q"));
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
			try {
				connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
				String query ="select encadreur.id,encadreur.nom, direction.nom,encadreur.prenom, encadreur.email, categorie.nom"
						+ " from encadreur inner join categorie on  categorie.id = encadreur.idcat "
						+ "inner join direction on direction.id = encadreur.iddir where encadreur.id = ? ";
				java.sql.PreparedStatement ps = connexion.prepareStatement(query);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				Encadreur e = new Encadreur();
				while(rs.next()) {
				e.setid(rs.getInt("encadreur.id"));
				e.setnom(rs.getString("encadreur.nom"));
				e.setprenom(rs.getString("encadreur.prenom"));
				e.setemail(rs.getString("encadreur.email"));
				e.setcategorie(rs.getString("categorie.nom"));
				e.setdirection(rs.getString("direction.nom"));
				}
				query="SELECT * FROM categorie inner join admin on categorie.nom = ? ;";
				ps = connexion.prepareStatement(query);
				Admin admin = (Admin) req.getSession(false).getAttribute("user");
				ps.setString(1,admin.getcategorie());
				 rs = ps.executeQuery();
			   Categorie clist = new Categorie();
			  if(rs.next()) {	    	
				  clist.setid(rs.getInt("id"));
				  clist.setnom(rs.getString("nom"));    		
			    }
			  query="SELECT * FROM direction inner join admin on direction.nom = ? ;";
				ps = connexion.prepareStatement(query);
				ps.setString(1,admin.getdirection());
				 rs = ps.executeQuery();
				 Direction d = new Direction();
				if(rs.next()) {
					d.setid(rs.getInt("id"));
					d.setnom(rs.getString("nom"));
				}
			  req.setAttribute("direction", d);
			  req.setAttribute("categorie", clist);
			  req.setAttribute("encadreur", e);
			  req.getRequestDispatcher("/Modifier_encadreur.jsp").forward(req, res);
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
		Encadreur encadreur = new Encadreur();
		encadreur.setid(Integer.parseInt(req.getParameter("q")));
		encadreur.setnom(req.getParameter("nom"));
		encadreur.setprenom(req.getParameter("prenom"));
		encadreur.setemail(req.getParameter("email"));
		
			try {
				connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			 
			
			String query ="update encadreur set nom = ? , prenom = ? , email = ?  where id = ? ;";
			java.sql.PreparedStatement ps = connexion.prepareStatement(query);
			ps.setString(1, encadreur.getnom());
			ps.setString(2, encadreur.getprenom());
			ps.setString(3, encadreur.getemail());
			ps.setInt(4, encadreur.getid());
			ps.executeUpdate();
			req.getRequestDispatcher("/afficherencadreurprofile?q="+encadreur.getid()).forward(req, res);

			}catch (SQLException e) {
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
