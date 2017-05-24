package usr.work.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import usr.work.bean.Device;
import usr.work.bean.Message;
import usr.work.dao.DeviceDao;

/**
 * Servlet implementation class GetHostList
 */
@WebServlet("/GetHostList")
public class GetHostList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int areaId = 0;
		try {
			areaId = Integer.parseInt(request.getParameter("areaId"));
		} catch (Exception e) {}
		List<Device> deviceList = null;
		DeviceDao deviceDao = new DeviceDao();
		if(areaId==0){
			deviceList = deviceDao.getList();
		}else{
			deviceList = deviceDao.getList(areaId);
		}
		
		JSONArray hostList = new JSONArray();
		for(Device device : deviceList){
			JSONObject host = new JSONObject();
			host.put("areaId", device.getAreaId());
			host.put("deviceId", device.getDeviceId());
			host.put("mac", device.getMac());
			host.put("des", device.getDes());
			host.put("enable", device.getEnable());
			host.put("online", device.getOnline());
			hostList.add(host);
		}
		String resStr = JSON.toJSONString(new Message(200,hostList));
		response.getWriter().println(resStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
