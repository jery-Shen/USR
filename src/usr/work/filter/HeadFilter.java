package usr.work.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet Filter implementation class Filter
 */
@WebFilter(filterName="HeadFilter",urlPatterns={"/*"})
public class HeadFilter implements  Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		req.setCharacterEncoding("utf-8");
		if(req.getRequestURI().contains("/Get")||req.getRequestURI().contains("/Add")
				||req.getRequestURI().contains("/Update")||req.getRequestURI().contains("/Delete")){
			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Content-Type", "application/json; charset=utf8"); 
		}else if(!(req.getRequestURI().contains(".css") || req.getRequestURI().contains(".js"))){
			res.setHeader("Content-Type", "text/html; charset=utf8"); 
		}
		chain.doFilter(request, response);
	}

	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	
	
	
       
 
}
