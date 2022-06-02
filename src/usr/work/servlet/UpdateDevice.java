package usr.work.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;

import usr.work.bean.Message;
import usr.work.server.Server;
import usr.work.utils.Hex;

/**
 * Servlet implementation class UpdateDevice
 */
@WebServlet("/UpdateDevice")
public class UpdateDevice extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(UpdateDevice.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int areaId = parseInt(request.getParameter("areaId"));
		int deviceId = parseInt(request.getParameter("deviceId"));
		int tempUpLimit = parseInt(request.getParameter("tempUpLimit"));
		int tempDownLimit = parseInt(request.getParameter("tempDownLimit"));
		int hrUpLimit = parseInt(request.getParameter("hrUpLimit"));
		int hrDownLimit = parseInt(request.getParameter("hrDownLimit"));
		int dpUpLimit = parseInt(request.getParameter("dpUpLimit"));
		int dpDownLimit = parseInt(request.getParameter("dpDownLimit"));
		int tempAlarmClose = parseInt(request.getParameter("tempAlarmClose"));
		int hrAlarmClose = parseInt(request.getParameter("hrAlarmClose"));
		int dpAlarmClose = parseInt(request.getParameter("dpAlarmClose"));
		int inWindAlarmClose = parseInt(request.getParameter("inWindAlarmClose"));
		
		Message message = new Message();
		if(areaId!=-1&&deviceId!=-1){
			List<byte[]> sendQueue = new ArrayList<byte[]>();
			if(tempUpLimit!=-1){
				byte[] pBytes = Hex.hex4toByte(tempUpLimit*10);
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x79,pBytes[0],pBytes[1]};
				sendQueue.add(bytes);
			}
			if(tempDownLimit!=-1){
				byte[] pBytes = Hex.hex4toByte(tempDownLimit*10);
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7a,pBytes[0],pBytes[1]};
				sendQueue.add(bytes);
			}
			if(hrUpLimit!=-1){
				byte[] pBytes = Hex.hex4toByte(hrUpLimit*10);
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7b,pBytes[0],pBytes[1]};
				sendQueue.add(bytes);
			}
			if(hrDownLimit!=-1){
				byte[] pBytes = Hex.hex4toByte(hrDownLimit*10);
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7c,pBytes[0],pBytes[1]};
				sendQueue.add(bytes);
			}
			if(dpUpLimit!=-1){
				byte[] pBytes = Hex.hex4toByte(dpUpLimit);
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7d,pBytes[0],pBytes[1]};
				sendQueue.add(bytes);
			}
			if(dpDownLimit!=-1){
				byte[] pBytes = Hex.hex4toByte(dpDownLimit);
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7e,pBytes[0],pBytes[1]};
				sendQueue.add(bytes);
			}
			if(tempAlarmClose!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7f,0x00,(byte) tempAlarmClose};
				sendQueue.add(bytes);
			}
			if(hrAlarmClose!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,(byte) 0x80,0x00,(byte) hrAlarmClose};
				sendQueue.add(bytes);
			}
			if(dpAlarmClose!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,(byte) 0x81,0x00,(byte) dpAlarmClose};
				sendQueue.add(bytes);
			}
			if(inWindAlarmClose!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,(byte) 0x82,0x00,(byte) inWindAlarmClose};
				sendQueue.add(bytes);
			}
			Server.getInstance().sendUpdate(areaId, deviceId, sendQueue);
			message.setStatus(200);
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
	
	private int parseInt(String str){
		int value = -1;
		try {
			value = Integer.parseInt(str);
		} catch (Exception e) {}
		return value;
	}
	

}
