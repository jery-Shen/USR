package usr.work.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import usr.work.bean.DeviceSocket;
import usr.work.bean.Message;
import usr.work.server.Server;
import usr.work.utils.CRC;
import usr.work.utils.Hex;

/**
 * Servlet implementation class UpdateDevice
 */
@WebServlet("/UpdateDevice")
public class UpdateDevice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x79,0x00,(byte) tempUpLimit};
				sendQueue.add(bytes);
			}
			if(tempDownLimit!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7a,0x00,(byte) tempDownLimit};
				sendQueue.add(bytes);
			}
			if(hrUpLimit!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7b,0x00,(byte) hrUpLimit};
				sendQueue.add(bytes);
			}
			if(hrDownLimit!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7c,0x00,(byte) hrDownLimit};
				sendQueue.add(bytes);
			}
			if(dpUpLimit!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7d,0x00,(byte) dpUpLimit};
				sendQueue.add(bytes);
			}
			if(dpDownLimit!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,0x7e,0x00,(byte) dpDownLimit};
				sendQueue.add(bytes);
			}
			if(tempAlarmClose!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,(byte) 0x80,0x00,(byte) tempAlarmClose};
				sendQueue.add(bytes);
			}
			if(hrAlarmClose!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,(byte) 0x81,0x00,(byte) hrAlarmClose};
				sendQueue.add(bytes);
			}
			if(dpAlarmClose!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,(byte) 0x82,0x00,(byte) dpAlarmClose};
				sendQueue.add(bytes);
			}
			if(inWindAlarmClose!=-1){
				byte[] bytes = new byte[]{(byte) deviceId,0x06,0x03,(byte) 0x83,0x00,(byte) inWindAlarmClose};
				sendQueue.add(bytes);
			}
			DeviceSocket deviceSocket = getDeviceSocket(areaId, deviceId, Server.dsockets);
			if(deviceSocket!=null){
				deviceSocket.setSending(true);
				sleep();
				for(byte[] bytes:sendQueue){
					byte[] crcBytes = CRC.getCRC(bytes);
					System.out.println(Hex.printHexString(crcBytes));
					deviceSocket = getDeviceSocket(areaId, deviceId, Server.dsockets);
					sendOne(crcBytes, deviceSocket);
					sleep();
				}
				sleep();
				deviceSocket.setSending(false);
			}
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
	
	private DeviceSocket getDeviceSocket(int areaId,int deviceId,List<DeviceSocket> dsockets){
		synchronized (dsockets) {
			if (dsockets.size() > 0) {
				for (DeviceSocket deviceSocket : dsockets) {	
					if(deviceSocket.getAreaId()==areaId&&deviceSocket.getDeviceId()==deviceId){
						return deviceSocket;
					}
				}
			}
		}
		return null;
	}
	
	private void sleep(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendOne(byte[] bytes, DeviceSocket deviceSocket) {
		try {
			deviceSocket.getDataOut().write(bytes);
			deviceSocket.getDataOut().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
