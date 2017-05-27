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
 * Servlet implementation class UpdateHostEnable
 */
@WebServlet("/UpdateHostEnable")
public class UpdateHostEnable extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int areaId = 0;
		int deviceId = 0;
		int enable = 0;
		try {
			areaId = Integer.parseInt(request.getParameter("areaId"));
			deviceId = Integer.parseInt(request.getParameter("deviceId"));
			enable = Integer.parseInt(request.getParameter("enable"));
		} catch (Exception e) {}
		Message message = new Message();
		Device device = null;
		if(areaId!=0&&deviceId!=0){
			device = Server.getInstance().getDevice(areaId, deviceId);
			device.setEnable(enable);
			DeviceDao deviceDao = new DeviceDao();
			if(deviceDao.update(device)){
				message.setStatus(200);
			}else{
				message.setStatus(215);
				message.setError("重复更改");
			}
		}else{
			message.setStatus(212);
			message.setError("信息不完整");
		}
		response.getWriter().write(JSON.toJSONString(message));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
