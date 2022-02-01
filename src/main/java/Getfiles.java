import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/telechegertravail")
public class Getfiles extends HttpServlet {
	Connection connexion = null;
	private static final int BUFFER_SIZE = 14536102; 

	protected void doGet(HttpServletRequest req , HttpServletResponse res) throws IOException {
		int id = Integer.parseInt(req.getParameter("q"));
		
		try {
			Connection connexion = DriverManager.getConnection( Connexionbdd.url, Connexionbdd.user, Connexionbdd.pw );
			String query ="select travail_livrer from stage where stage.id= ? ; ";
			 PreparedStatement ps = connexion.prepareStatement(query);
	            ps.setInt(1, id);
	            ResultSet result = ps.executeQuery();
	            
	            if (result.next()) {
	            	  Blob blob = result.getBlob("travail_livrer");
	              
	                InputStream inputStream = blob.getBinaryStream();
	                int fileLength = inputStream.available();
	                 
	               
	                OutputStream outStream = res.getOutputStream();
	                 
	                byte[] buffer = new byte[BUFFER_SIZE];
	                int bytesRead = -1;
	                 
	                while ((bytesRead = inputStream.read(buffer)) != -1) {
	                    outStream.write(buffer, 0, bytesRead);
	                }
	                 
	                inputStream.close();
	                outStream.close();             
	            } else {
	                // no file found
	                res.getWriter().print("File not found for the id: " + id);  
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
