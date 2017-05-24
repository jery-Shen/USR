package usr.work.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

import usr.work.bean.Device;
import usr.work.bean.DeviceSocket;
import usr.work.bean.Message;
import usr.work.server.Server;

/**
 * Servlet implementation class OnLine
 */
@WebServlet("/OnLine")
public class OnLine extends HttpServlet {
	
	
	
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int areaId = 0;
		try {
			areaId = Integer.parseInt(request.getParameter("areaId"));
		} catch (Exception e) {}
		Message message = new Message(1);
		List<Device> deviceList = null;
		if(areaId==0){
			deviceList = Server.getInstance().getDeviceList();
		}else{
			deviceList = Server.getInstance().getDeviceList(areaId);
		}
		if(deviceList.size()>0){
			message.setSuccess(true);
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
