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

/**
 * Servlet implementation class AddHost
 */
@WebServlet("/AddHost")
public class AddHost extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mac = request.getParameter("mac");
		String des = request.getParameter("des");
		Message message = new Message();
		int areaId = 0;
		int deviceId = 0;
		try {
			areaId = Integer.parseInt(request.getParameter("areaId"));
			deviceId = Integer.parseInt(request.getParameter("deviceId"));
		} catch (Exception e) {}
		Device device = null;
		if(areaId!=0&&deviceId!=0&&mac!=null){
			device = new Device();
			device.setAreaId(areaId);
			device.setDeviceId(deviceId);
			device.setMac(mac);
			if(des==null) des="-";
			device.setDes(des);
			DeviceDao hostDao = new DeviceDao();
			if(hostDao.add(device)){
				message.setStatus(200);
			}else{
				message.setStatus(215);
				message.setError("该区域下的该设备Id已经存在");
			}
		}else{
			message.setStatus(212);
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
