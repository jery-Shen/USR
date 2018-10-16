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
@WebServlet("/GetDeviceListDao")
public class GetDeviceListDao extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		for(Device device : deviceList){
			if(device.getTemp()>50){
				device.setTemp(device.getTemp()/10);
				device.setTempUpLimit(device.getTempUpLimit()/10);
				device.setTempDownLimit(device.getTempDownLimit()/10);
				device.setHr(device.getHr()/10);
				device.setHrUpLimit(device.getHrUpLimit()/10);
				device.setHrDownLimit(device.getHrDownLimit()/10);
				device.setDp(device.getDp()/10);
				device.setDpUpLimit(device.getDpUpLimit()/10);
				device.setDpDownLimit(device.getDpDownLimit()/10);
			}
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
