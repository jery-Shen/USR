package usr.work.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import usr.work.bean.DeviceSocket;
import usr.work.dao.DeviceDao;
import usr.work.utils.CRC;

public class Server {
	
	Log log = LogFactory.getLog(Server.class);
	
	private static Server instance = null;  
    public static synchronized Server getInstance() {  
        if (instance == null) {  
            instance = new Server();  
        }  
        return instance;  
    }  

	public List<DeviceSocket> dsockets = new ArrayList<>();

	private ServerSocket serverSocket;
	private Timer timer;
	private Server() {
		
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
		synchronized (dsockets) {
			//System.out.println("current connects:"+dsockets.size());
			if (dsockets.size() > 0) {
				for (DeviceSocket deviceSocket : dsockets) {						
					int deviceId = deviceSocket.getDeviceId();
					if(deviceId!=0){
						deviceSocket.setUnReceiveTime(deviceSocket.getUnReceiveTime()-1);
						if(!deviceSocket.isSending()){
							byte[] bytes = new byte[] { (byte) deviceId, 0x03, 0x02, 0x58, 0x00, 0x64 };
							byte[] crcBytes = CRC.getCRC(bytes);
							sendOne(crcBytes, deviceSocket);
							//System.out.println(Hex.printHexString(crcBytes));
						}
						if(deviceSocket.getUnReceiveTime()==-10){
							if(deviceSocket.getDevice()!=null){
								new DeviceDao().deviceClose(deviceSocket.getAreaId(),deviceSocket.getDeviceId());
								log.info(deviceSocket.getDeviceId() + ":deviceClose1");
								deviceSocket.setDevice(null);
							}
						}
					}
				}
			}
		}
	}

	private void scanClientRepeat(int scanNum) {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				scanClient(scanNum);
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
		scanClientRepeat(scanNum);
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
	
	public void sendUpdate(int areaId,int deviceId,List<byte[]> sendQueue){
		DeviceSocket deviceSocket = getDeviceSocket(areaId, deviceId);
		if(deviceSocket!=null){
			deviceSocket.setSending(true);
			sleep();
			for(byte[] bytes:sendQueue){
				byte[] crcBytes = CRC.getCRC(bytes);
				//System.out.println(new Date().toLocaleString()+" areaId:"+areaId+",deviceId:"+deviceId+",send:"+Hex.printHexString(crcBytes));
				deviceSocket = getDeviceSocket(areaId, deviceId);
				sendOne(crcBytes, deviceSocket);
				sleep();
			}
			sleep();
			deviceSocket.setSending(false);
		}
	}
	
	private DeviceSocket getDeviceSocket(int areaId,int deviceId){
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

	


}
