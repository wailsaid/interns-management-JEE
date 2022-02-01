import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns={"/supdir","/supcat"})
public class supDirCat extends HttpServlet {
	Connection connexion = null;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("q"));
		ArrayList<String> sql= new ArrayList<String>();
		String nom = req.getParameter("nom");
	//	System.out.println(nom);
		boolean i=false; 
		if(nom.equals("direction")) {
		 sql.add("delete from direction where id = ? ;");
		 sql.add("delete from admin where iddir = ? ;");
		 sql.add("delete from stagiare where iddir = ? ;");
		 sql.add("delete from encadreur where iddir = ? ;");
		 i=true;
		 }else {
		 sql.add("delete from admin where idcat = ? ;");
		 sql.add("delete from stagiare where idcat = ? ;");
		 sql.add("delete from stage where idcat = ? ;");
		 sql.add("delete from encadreur where idcat = ? ;");
		 sql.add("delete from categorie where id = ? ;");
		 }
//System.out.println(sql);
			try {
				connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
				for(String s : sql) {
				//	System.out.println(s);
				PreparedStatement ps=connexion.prepareStatement(s);
				ps.setInt(1, id);
				ps.executeUpdate();
				//System.out.println("ok");
				}
				if(i) {
					req.getRequestDispatcher("/listdirections").forward(req, res);
				}else {
					req.getRequestDispatcher("/listcategoories").forward(req, res);
				}
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
	
