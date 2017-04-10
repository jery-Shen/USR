package usr.work.servlet;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class GetDeviceList
 */
@WebServlet("/GetDeviceList")
public class GetDeviceList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int areaId = 0;
		try {
			areaId = Integer.parseInt(request.getParameter("areaId"));
		} catch (Exception e) {}
		List<Device> deviceList = null;
		
		
//		List<DeviceSocket> dsockets = Server.dsockets;
//		JSONArray deviceJa = new JSONArray();
//		synchronized (dsockets) {
//			for (DeviceSocket ds : dsockets) {
//				Device device = ds.getDevice();
//				if (device != null && device.getDeviceId() != 0) {
//					if(areaId==0){
//						JSONObject deviceJo = new JSONObject();
//						deviceJo.put("deviceId", device.getDeviceId());
//						deviceJo.put("updateTime", device.getUpdateTime());
//						deviceJo.put("temp", device.getTemp());
//						deviceJo.put("hr", device.getHr());
//						deviceJo.put("dp", device.getDp());
//						deviceJo.put("infoBar", device.getInfoBar());
//						deviceJo.put("online", device.getOnline());
//						deviceJa.add(deviceJo);
//					}else if(device.getAreaId()==areaId){
//						JSONObject deviceJo = new JSONObject();
//						deviceJo.put("deviceId", device.getDeviceId());
//						deviceJo.put("updateTime", device.getUpdateTime());
//						deviceJo.put("temp", device.getTemp());
//						deviceJo.put("hr", device.getHr());
//						deviceJo.put("dp", device.getDp());
//						deviceJo.put("infoBar", device.getInfoBar());
//						deviceJo.put("online", device.getOnline());
//						deviceJa.add(deviceJo);
//					}
//					
//				}
//			}
//		}
//		response.getWriter().println(JSON.toJSONString(new Message(200, deviceJa)));

		 
		DeviceDao deviceDao = new DeviceDao();
		if(areaId==0){
			deviceList = deviceDao.getList();
		}else{
			deviceList = deviceDao.getList(areaId);
		}
		String resStr = JSON.toJSONString(new Message(200,deviceList));
		response.getWriter().println(resStr);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
