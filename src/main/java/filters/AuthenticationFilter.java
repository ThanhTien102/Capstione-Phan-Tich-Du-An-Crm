package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// laay ra contextpath 
        String contextPath = req.getContextPath();
        
        // lay path 
        boolean isLoginPage = req.getServletPath().equals("/login");

		// lay session ra
        HttpSession session = req.getSession(false);
        // da dang nhap session != null va currentUser != null
        boolean isLoggedIn = session != null && session.getAttribute("currentUser") != null;
        
        if(isLoggedIn || isLoginPage) {
        	//Cho phép request đi tiếp đến Servlet
        	chain.doFilter(req, resp);
        	return;
        }
        resp.sendRedirect(contextPath + "/login");



		
	}

}
