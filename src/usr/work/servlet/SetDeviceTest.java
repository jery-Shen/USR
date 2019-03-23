package usr.work.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import usr.work.bean.Device;
import usr.work.bean.Message;
import usr.work.dao.DeviceDao;
import usr.work.server.Server;

/**
 * Servlet implementation class GetDeviceDetail
 */
@WebServlet("/SetDeviceTest")
public class SetDeviceTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int areaId = 0;
		int deviceId = 0;
	
		try {
			areaId = Integer.parseInt(request.getParameter("areaId"));
			deviceId = Integer.parseInt(request.getParameter("deviceId"));
		} catch (Exception e) {}
		Device device = null;
		device = Server.getInstance().getDevice(areaId,deviceId);
		
		int online = 0;
		try {
			online = Integer.parseInt(request.getParameter("online"));
		} catch (Exception e) {}
		
		device.setOnline(online);
		
        response.getWriter().println(JSON.toJSONString(new Message(200, device)));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
