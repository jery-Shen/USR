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
import usr.work.bean.DeviceSocket;
import usr.work.bean.Message;
import usr.work.server.Server;

/**
 * Servlet implementation class GetDeviceListSyn
 */
@WebServlet("/GetDeviceListSyn")
public class GetDeviceListSyn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int areaId = 0;
		try {
			areaId = Integer.parseInt(request.getParameter("areaId"));
		} catch (Exception e) {}
		List<DeviceSocket> dsockets = Server.getInstance().dsockets;
		JSONArray deviceJa = new JSONArray();
		synchronized (dsockets) {
			for (DeviceSocket ds : dsockets) {
				Device device = ds.getDevice();
				if (device != null && device.getDeviceId() != 0) {
					JSONObject deviceJo = new JSONObject();
					deviceJo.put("deviceId", device.getDeviceId());
					deviceJo.put("updateTime", device.getUpdateTime());
					deviceJo.put("infoBar", device.getInfoBar());
					deviceJo.put("online", device.getOnline());
					deviceJo.put("temp", device.getTemp());
					deviceJo.put("tempUpLimit", device.getTempUpLimit());
					deviceJo.put("tempDownLimit", device.getTempDownLimit());
					deviceJo.put("hr", device.getHr());
					deviceJo.put("hrUpLimit", device.getHrUpLimit());
					deviceJo.put("hrDownLimit", device.getHrDownLimit());
					deviceJo.put("dp", device.getDp());
					deviceJo.put("dpUpLimit", device.getDpUpLimit());
					deviceJo.put("dpDownLimit", device.getDpDownLimit());
					deviceJo.put("inWindSpeed", device.getInWindSpeed());
					deviceJo.put("outWindSpeed", device.getOutWindSpeed());
					deviceJo.put("airCount", device.getAirCount());
					deviceJo.put("dpTarget", device.getDpTarget());
					deviceJo.put("workMode", device.getWorkMode());
					deviceJo.put("workHour", device.getWorkHour());
					deviceJo.put("workSecond", device.getWorkSecond());
					deviceJo.put("tempAlarmClose", device.getTempAlarmClose());
					deviceJo.put("hrAlarmClose", device.getHrAlarmClose());
					deviceJo.put("dpAlarmClose", device.getDpAlarmClose());
					deviceJo.put("inWindAlarmClose", device.getInWindAlarmClose());
					if(areaId==0){
						deviceJa.add(deviceJo);
					}else if(device.getAreaId()==areaId){
						deviceJa.add(deviceJo);
					}
				}
			}
		}
		response.getWriter().println(JSON.toJSONString(new Message(200, deviceJa)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
