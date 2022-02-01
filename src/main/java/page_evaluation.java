import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/getstagiares")
public class page_evaluation extends HttpServlet {

	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("q"));
		//System.out.println(id);
		
		try {
			Connection connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query = "select * from stagiare inner join encadreur on encadreur.id=stagiare.idencad where travail_demander = ? ;";
			java.sql.PreparedStatement ps = connexion.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			ArrayList<Stagiare> list = new ArrayList<Stagiare> ();
			Encadreur e = new Encadreur();
			while(rs.next()) {
				Stagiare s = new Stagiare();
				s.setid(rs.getInt("stagiare.id"));
				s.setnom(rs.getString("Stagiare.nom"));
				s.setprenom(rs.getString("Stagiare.prenom"));
				s.settravail(""+id);
				list.add(s);
				e.setid(rs.getInt("encadreur.id"));
				e.setnom(rs.getString("encadreur.nom"));
				e.setprenom(rs.getString("encadreur.prenom"));
			}
			query ="select date_debut,date_fin from stage where stage.id = ? ;";
			ps=connexion.prepareStatement(query);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			Stage stage= new Stage();
			while(rs.next()) {
				stage.setdateD(rs.getDate("date_debut"));
				stage.setdateF(rs.getDate("date_fin"));
			}
			req.setAttribute("encadreur", e);
			req.setAttribute("date", stage);
			req.setAttribute("list", list);
			req.getRequestDispatcher("/page_evaluation.jsp").forward(req, res);
			
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
