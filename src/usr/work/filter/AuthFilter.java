package usr.work.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import usr.work.utils.Md5;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter(filterName="AuthFilter",urlPatterns={"/*"})
public class AuthFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	private boolean checkSign(String token,String timestamp,String sign){
		long reqTime = Long.parseLong(timestamp);
		long now = System.currentTimeMillis()/1000;
		
		if(now-reqTime<60){
			if(sign.equals(Md5.encrypt(token + "USR" + timestamp))){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}

	}

}
