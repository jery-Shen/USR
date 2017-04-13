package usr.work.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import usr.work.server.Server;


/**
 * Application Lifecycle Listener implementation class InitListener
 *
 */
@WebListener
public class InitListener implements ServletContextListener {
	
	private Server server;

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		server = new Server();
		server.serveStart(8089,10);
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		server.serveStop();
//		try {
//			new DeviceDao().deviceClose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		

	}

}
