package usr.work.servlet;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usr.work.bean.DeviceSocket;
import usr.work.utils.CRC;
import usr.work.utils.Hex;

/**
 * Servlet implementation class Sc
 */
@WebServlet("/TestClient")
public class TestClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String flag = request.getParameter("flag");
		
		connect(request);
		response.getWriter().write("success");
	}
	private void connect(HttpServletRequest request){
		try {
			Socket socket = new Socket("172.16.142.2",8090);
			System.out.println(socket.getInetAddress());
			DataOutputStream outData  = new DataOutputStream(socket.getOutputStream());
			request.getSession().setAttribute("socket",socket);
			request.getSession().setAttribute("outData",outData);
			new MagThread(socket,outData).start();
			//new SendThread(outData).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class SendThread extends Thread {
		DataOutputStream outData;
		SendThread(DataOutputStream outData){
			this.outData = outData;
		}
		
		@Override
		public void run() {
			while(true){
				byte[] bytes = new byte[] { 0x02, 0x03, 0x02, 0x58, 0x00, 0x64 };
				byte[] crcBytes = CRC.getCRC(bytes);
				sendOne(crcBytes,outData);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		void sendOne(byte[] bytes,DataOutputStream outData){
			try {
				outData.write(bytes);
				outData.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	 
	
	class MagThread extends Thread {
		Socket socket;

		DataInputStream dataIn;
		DataOutputStream dataOut;
		DeviceSocket deviceSocket;

		public MagThread(Socket socket,DataOutputStream outData) {
			this.socket = socket;
			this.dataOut = outData;
			deviceSocket = new DeviceSocket();
			deviceSocket.setSocket(socket);
			deviceSocket.setDataOut(outData);
			deviceSocket.setDeviceId(2);
			try {
				dataIn = new DataInputStream(socket.getInputStream());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			try {
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				while (true) {
					byte[] bytes = new byte[64];
					int readLength = dataIn.read(bytes);
					if (readLength <= 0) {
						socket.sendUrgentData(0);
					} else {
						buffer.write(bytes,0,readLength);
						byte[] data = buffer.toByteArray();
						//System.out.println("read length:"+readLength+"pos:"+data.length+" data:"+Hex.printHexString(bytes));
						if(deviceSocket.getDeviceId()==0){
							if(data.length==4&&data[data.length-2]==(byte)0xaa&&data[data.length-1]==(byte)0x55){
								System.out.println("-----------22---------------");
								System.out.println(Hex.printHexString(data));
								deviceSocket.setDeviceId(data[1]);
								buffer.reset();
							}
						}else if(data.length>=205){
							
							if(data[data.length-4]==(byte)0xaa&&data[data.length-3]==(byte)0x55){
								System.out.println("-----------22---------------");
								System.out.println(Hex.printHexString(data));
								byte[] crcData = new byte[data.length-2];
								System.arraycopy(data, 0, crcData, 0, data.length-2);
								
								buffer.reset();
							}
						}
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {

				}

			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	String resStr = "0203c80012001a000000000012140dac07d0002d006300000000002dbd00146c07d00834089808fc096009c40a280af00bb80dac";

}
