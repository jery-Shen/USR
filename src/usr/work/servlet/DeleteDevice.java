package usr.work.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import usr.work.bean.Message;
import usr.work.dao.DeviceDao;
import usr.work.server.Server;

/**
 * Servlet implementation class DeleteDevice
 */
@WebServlet("/DeleteDevice")
public class DeleteDevice extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int areaId = 0;
		int deviceId = 0;
		try {
			areaId = Integer.parseInt(request.getParameter("areaId"));
			deviceId = Integer.parseInt(request.getParameter("deviceId"));
		} catch (Exception e) {}
		Message message = new Message();
		if(areaId!=0&&deviceId!=0){
			Server.getInstance().getDevice(areaId, deviceId).setEnable(0);
			Server.getInstance().getDevice(areaId, deviceId).setDeviceId(-1);
			new DeviceDao().delete(areaId,deviceId);
			message.setStatus(200);
		}else{
			message.setStatus(210);
			message.setError("信息不完整");
		}
		response.getWriter().write(JSON.toJSONString(message));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
