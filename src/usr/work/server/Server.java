package usr.work.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import usr.work.bean.DeviceSocket;
import usr.work.utils.CRC;

public class Server {

	public static List<DeviceSocket> dsockets;

	private ServerSocket serverSocket;
	private Timer timer;

	public Server() {
		dsockets = new ArrayList<>();
	}

	private void makeServe(final int port) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(port);
					//System.out.println(serverSocket.getInetAddress().getLocalHost() + ":" + serverSocket.getLocalPort());
					while (true) {
						Socket socket = serverSocket.accept();
						DeviceSocket deviceSocket = new DeviceSocket();
						deviceSocket.setSocket(socket);
						deviceSocket.setDataOut(new DataOutputStream(socket.getOutputStream()));
						dsockets.add(deviceSocket);
						ServerThread serverThread = new ServerThread(deviceSocket, dsockets);
						serverThread.start();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
	}

	private void scanClient(int scanNum) {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				synchronized (dsockets) {
					//System.out.println("current connects:"+dsockets.size());
					if (dsockets.size() > 0) {
						for (DeviceSocket deviceSocket : dsockets) {						
							int deviceId = deviceSocket.getDeviceId();
							if(deviceId!=0&&!deviceSocket.isSending()){
								byte[] bytes = new byte[] { (byte) deviceId, 0x03, 0x02, 0x58, 0x00, 0x64 };
								byte[] crcBytes = CRC.getCRC(bytes);
								sendOne(crcBytes, deviceSocket);
								//System.out.println(Hex.printHexString(crcBytes));
							}
						}
					}
				}
			}
		}, 2000, 1000);
	}
	
	private void sendOne(byte[] bytes, DeviceSocket deviceSocket) {
		try {
			deviceSocket.getDataOut().write(bytes);
			deviceSocket.getDataOut().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void serveStart(int port,int scanNum) {
		makeServe(port);
		scanClient(scanNum);
	}

	public void serveStop(){
		timer.cancel();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			serverSocket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
