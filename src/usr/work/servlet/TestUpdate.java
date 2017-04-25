package usr.work.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usr.work.bean.DeviceSocket;
import usr.work.server.Server;
import usr.work.utils.CRC;
import usr.work.utils.Hex;

/**
 * Servlet implementation class TestUpdate
 */
@WebServlet("/TestUpdate")
public class TestUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//"020603790023"
		String cmd = request.getParameter("cmd");
		if(cmd==null){
			response.getWriter().print("empty param");
			return;
		}
		byte[] bytes = Hex.hexStringToBytes(cmd);
		byte[] crcBytes = CRC.getCRC(bytes);
		System.out.println(Hex.printHexString(crcBytes));
		
		int deviceId = bytes[0];
		List<DeviceSocket> dsockets = Server.getInstance().dsockets;
		synchronized (dsockets) {
			if (dsockets.size() > 0) {
				for (DeviceSocket deviceSocket : dsockets) {	
					if(deviceSocket.getDeviceId()==deviceId){
						sendOne(crcBytes, deviceSocket);
						System.out.println("send out success");
					}
				}
			}
		}
		response.getWriter().print("success "+crcBytes);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
