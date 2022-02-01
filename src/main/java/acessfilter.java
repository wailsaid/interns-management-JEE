import java.io.IOException; 

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebFilter(urlPatterns = {"/*"})
//"/affecter","/afficherencadreurprofile","/affichlisteencadreur","/affichlistestage","/afficherprofile"
public class acessfilter implements Filter{
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) arg1;
		HttpServletRequest req =(HttpServletRequest) arg0;
	     res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
	     res.setHeader("Pragma", "no-cache");
	     res.setDateHeader("Expires", 0); 
	     
	     String path= req.getRequestURI();
	     if(path.endsWith(".css") || path.endsWith(".png")){
	    	 arg2.doFilter(arg0,arg1);
	         return;
	       }

	    /* HttpSession session=request.getSession(false);

	     if(session != null && !session.isNew()) {
	    	 arg2.doFilter(request, response);
	        } 
	     else {
	            response.sendRedirect("Authentifier");
	        }

*/
	
		//Admin ad = (Admin) req.getSession(false).getAttribute("user");
		//System.out.println(ad.getusername());
	     
	     HttpSession session = req.getSession(false);    
	        boolean connecter = (session != null && session.getAttribute("user") != null);
	        String acessadmin = req.getContextPath() + "/Authentifier"; 
	        String acessempl = req.getContextPath() + "/employee"; 
	        String acess = req.getContextPath() + "/"; 
	        boolean demanderConnecter = (req.getRequestURI().equals(acessadmin) || req.getRequestURI().equals(acessempl) || req.getRequestURI().equals(acess))                      ;
	        boolean pageauthn = req.getRequestURI().endsWith("/Authentifier");
	        if(connecter && pageauthn) {
	        	req.getRequestDispatcher("/page_d_acceil.jsp").forward(arg0, arg1);
	        }else if(connecter || demanderConnecter) {
	        	arg2.doFilter(arg0, arg1);
	        }else {
	        	//req.getRequestDispatcher("/Authentification.jsp").forward(req, arg1);
				req.getRequestDispatcher("/Authentifier").forward(req, arg1);

	        }
	 
		
	}
	@Override
    public void destroy() {
         
    }

}
