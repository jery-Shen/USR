package usr.work.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import usr.work.bean.Device;
import usr.work.bean.DeviceSocket;
import usr.work.bean.User;
import usr.work.dao.DeviceDao;
import usr.work.dao.UserDao;
import usr.work.listener.DeviceListener;
import usr.work.utils.CRC;
import usr.work.utils.Hex;
import usr.work.utils.SendSms;
import usr.work.utils.Util;

public class Server implements DeviceListener {

	Log log = LogFactory.getLog(Server.class);

	private static Server instance = null;

	public static synchronized Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	public List<Device> deviceList = new ArrayList<Device>();
	private List<DeviceSocket> dsockets = new ArrayList<DeviceSocket>();

	private ServerSocket serverSocket;
	private Timer timer;
	private Timer updateTimer;

	private Server() {

	}

	private void makeServe(final int port) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(port);
					// System.out.println(serverSocket.getInetAddress().getLocalHost()
					// + ":" + serverSocket.getLocalPort());
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
			if (dsockets.size() > 0) {
				for (DeviceSocket deviceSocket : dsockets) {
					int deviceId = deviceSocket.getDeviceId();
					if (deviceId != 0) {
						//log.info("UnReceiveTime:"+deviceSocket.getUnReceiveTime());
						deviceSocket.setUnReceiveTime(deviceSocket.getUnReceiveTime() - 1);
						if (!deviceSocket.isSending()) {
							byte[] bytes = new byte[] { (byte) deviceId, 0x03, 0x02, 0x58, 0x00, 0x64 };
							byte[] crcBytes = CRC.getCRC(bytes);
							sendOne(crcBytes, deviceSocket);
							log.debug(Hex.printHexString(crcBytes));
						}
						if (deviceSocket.getUnReceiveTime() == -10) {
							Device device = getDevice(deviceSocket.getAreaId(), deviceSocket.getDeviceId());
							if (device != null && device.getOnline()==1) {
								device.setOnline(0);
								DeviceDao deviceDao = new DeviceDao();
								deviceDao.update(device);
								log.info("deviceClose:device:" + deviceSocket.getAreaId() + " "
										+ deviceSocket.getDeviceId());
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
	
	private void updateDeviceRepeat() {
		updateTimer = new Timer();
		updateTimer.schedule(new TimerTask() {
			public void run() {
				DeviceDao deviceDao = new DeviceDao();
				synchronized (dsockets) {
					for (Device device : deviceList) {
						if (device.getEnable() == 1 && device.getOnline() == 1) {
							DeviceSocket dsocket = getDeviceSocket(device.getAreaId(), device.getDeviceId());
							if(dsocket==null || dsocket.getDevice()==null){
								device.setOnline(0);
							}
							deviceDao.update(device);
							log.info("devicUpdate:device:" + device.getAreaId() + " "
									+ device.getDeviceId());
						}
					}
				}
			} 
		}, 120000, 120000); //2分钟
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

	public void serveStart(int port, int scanNum) {
		makeServe(port);
		deviceList = new DeviceDao().getList();
		for (Device device : deviceList) {
			device.setDeviceListener(this);
		}
		scanClientRepeat(scanNum);
		updateDeviceRepeat();
	}

	public void serveStop() {
		timer.cancel();
		updateTimer.cancel();
		DeviceDao deviceDao = new DeviceDao();
		for (Device device : deviceList) {
			if (device.getEnable() == 1 && device.getOnline() == 1) {
				device.setOnline(0);
				deviceDao.update(device);
			}
		}
		deviceList.clear();
		deviceList = null;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendUpdate(int areaId, int deviceId, List<byte[]> sendQueue) {
		DeviceSocket deviceSocket = getDeviceSocket(areaId, deviceId);
		if (deviceSocket != null) {
			deviceSocket.setSending(true);
			sleep();
			for (byte[] bytes : sendQueue) {
				byte[] crcBytes = CRC.getCRC(bytes);
				log.info(Util.formatDate(new Date()) + " areaId:" + areaId + ",deviceId:" + deviceId + ",send:"
						+ Hex.printHexString(crcBytes));
				deviceSocket = getDeviceSocket(areaId, deviceId);
				sendOne(crcBytes, deviceSocket);
				sleep();
			}
			sleep();
			deviceSocket.setSending(false);
		}
	}

	public DeviceSocket getDeviceSocket(int areaId, int deviceId) {
		synchronized (dsockets) {
			if (dsockets.size() > 0) {
				for (DeviceSocket deviceSocket : dsockets) {
					if (deviceSocket.getAreaId() == areaId && deviceSocket.getDeviceId() == deviceId) {
						return deviceSocket;
					}
				}
			}
		}
		return null;
	}

	public Device getDevice(int areaId, int deviceId) {
		if (deviceList.size() > 0) {
			for (Device device : deviceList) {
				if (device.getAreaId() == areaId && device.getDeviceId() == deviceId) {
					return device;
				}
			}
		}
		return null;
	}

	public List<Device> getDeviceList() {
		List<Device> devices = new ArrayList<Device>();
		if (deviceList.size() > 0) {
			for (Device device : deviceList) {
				if (device.getEnable() == 1) {
					devices.add(device);
				}
			}
		}
		return devices;
	}

	public List<Device> getDeviceList(int areaId) {
		List<Device> devices = new ArrayList<Device>();
		if (deviceList.size() > 0) {
			for (Device device : deviceList) {
				if (device.getEnable() == 1 && device.getAreaId() == areaId) {
					devices.add(device);
				}
			}
		}
		return devices;
	}

	private void sleep() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void listChange(int areaId, int flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void objectChange(Device device, String field, Object oldValue, Object newValue) {
		//log.info("objectChange:" + device.getAreaId() + " " + device.getDeviceId() + " field:" + field + "  oldValue:"
		//		+ oldValue + " newValue:" + newValue);
		if (field.endsWith("tempUpLimit") && ((float) newValue) == 81) {
			SendSms.send("13358018613", device.getDeviceId(), "测试报警");
		}
		if (device.getOnline() == 1 && field.endsWith("infoBar") && (int) newValue > 1) {
			String alarmMsg = Util.stringOfInfoBar((int) newValue);
			if(alarmMsg!=""){
				switch ((int) newValue) {
				case 4:
					alarmMsg += "，当前" + device.getTemp() + "大于上限" + device.getTempUpLimit();
					break;
				case 5:
					alarmMsg += "，当前" + device.getTemp() + "小于下限" + device.getTempDownLimit();
					break;
				case 6:
					alarmMsg += "，当前" + device.getHr() + "大于上限" + device.getHrUpLimit();
					break;
				case 7:
					alarmMsg += "，当前" + device.getHr() + "小于下限" + device.getHrDownLimit();
					break;
				case 8:
					alarmMsg += "，当前" + device.getDp() + "大于上限" + device.getDpUpLimit();
					break;
				case 9:
					alarmMsg += "，当前" + device.getDp() + "小于下限" + device.getDpDownLimit();
					break;
				default:
					break;
				}
	
				UserDao userDao = new UserDao();
				List<User> userList = userDao.getList(device.getAreaId());
				for (User user : userList) {
					if (user.getPhone() != null && !user.getPhone().equals("-")) {
						SendSms.send(user.getPhone(), device.getDeviceId(), alarmMsg);
					}
				}
				recordAlarm(device.getAreaId(), device.getDeviceId(), alarmMsg);
			}
		}

	}

	private void recordAlarm(int areaId, int deviceId, String alarmMsg) {
		Device device = getDevice(areaId, deviceId);
		String alarmHistory = device.getAlarmHistory();
		JSONArray alarmJsonArray = JSON.parseArray(alarmHistory);
		if (alarmJsonArray.size() >= 8) {
			alarmJsonArray.remove(0);
		}
		JSONObject alarmJson = new JSONObject();
		alarmJson.put("time", Util.formatDate(new Date()));
		alarmJson.put("msg", alarmMsg);
		alarmJsonArray.add(alarmJson);
		device.setAlarmHistory(alarmJsonArray.toJSONString());
	}

}
