package usr.work.server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

public class ServerThread extends Thread implements DeviceListener {

	Log log = LogFactory.getLog(ServerThread.class);

	Socket socket;
	DeviceSocket deviceSocket;
	List<DeviceSocket> dsockets;
	boolean isClientClose = false;
	boolean newObj = false;

	public ServerThread(DeviceSocket deviceSocket, List<DeviceSocket> dsockets) {
		this.deviceSocket = deviceSocket;
		this.dsockets = dsockets;
		this.socket = deviceSocket.getSocket();
	}

	@Override
	public void run() {
		DataInputStream dataIn;
		synchronized (dsockets) {
			log.info("connect sockets:" + dsockets.size());
		}
		try {
			dataIn = new DataInputStream(socket.getInputStream());
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			while (true) {
				byte[] bytes = new byte[64];
				int readLength = dataIn.read(bytes);
				if (readLength <= 0) {
					socket.sendUrgentData(0);
				} else {
					buffer.write(bytes, 0, readLength);
					byte[] data = buffer.toByteArray();
					// System.out.println("read
					// length:"+readLength+"pos:"+data.length+"
					// data:"+Hex.printHexString(bytes));
					if (deviceSocket.getDeviceId() == 0) {
						if (data.length == 4 && data[data.length - 2] == (byte) 0xaa
								&& data[data.length - 1] == (byte) 0x55) {
							log.info("--------------------------");
							log.info(Hex.printHexString(data));
							deviceSocket.setAreaId(data[0]);
							deviceSocket.setDeviceId(data[1]);
							buffer.reset();
						}
					} else if (data.length >= 205) {
						if (data[data.length - 4] == (byte) 0xaa && data[data.length - 3] == (byte) 0x55) {
							// log.info("--------------------------");
							// log.info(Hex.printHexString(data));
							byte[] crcData = new byte[data.length - 2];
							System.arraycopy(data, 0, crcData, 0, data.length - 2);
							if (CRC.getCRC(crcData)[data.length - 1] == data[data.length - 1]) {
								byteTransfer(data);
							}
							buffer.reset();
						}
					}
				}
			}
		} catch (Exception esx) {
			// esx.printStackTrace();
		} finally {
			if (!isClientClose) {
				clientClose();
			}
		}
	}

	private void byteTransfer(byte[] bytes) {

		Device device = deviceSocket.getDevice();
		if (device == null) {
			newObj = true;
			device = new Device(this);
			device.setDeviceIp(socket.getInetAddress().getHostAddress());
			device.setAreaId(deviceSocket.getAreaId());
			device.setDeviceId(deviceSocket.getDeviceId());
			this.listChange(deviceSocket.getAreaId(), 1);
		} else {
			newObj = false;
		}

		device.setOnline(1);
		device.setTemp(Hex.parseHex4(bytes[3], bytes[4]));
		device.setTempUpLimit(Hex.parseHex4(bytes[5], bytes[6]));
		device.setTempDownLimit(Hex.parseHex4(bytes[7], bytes[8]));
		device.setTempOff(Hex.parseHex4(bytes[9], bytes[10]));
		device.setTempReally(Hex.parseHex4(bytes[11], bytes[12]));

		device.setWorkMode(Hex.parseHex4(bytes[15], bytes[16]));
		device.setAirCount(Hex.parseHex4(bytes[17], bytes[18])); //
		device.setInWindSpeed(Hex.parseHex4(bytes[19], bytes[20])); //
		device.setOutWindSpeed(Hex.parseHex4(bytes[21], bytes[22]));//

		device.setHr(Hex.parseHex4(bytes[23], bytes[24]));
		device.setHrUpLimit(Hex.parseHex4(bytes[25], bytes[26]));
		device.setHrDownLimit(Hex.parseHex4(bytes[27], bytes[28]));
		device.setHrOff(Hex.parseHex4(bytes[29], bytes[30]));
		device.setHrReally(Hex.parseHex4(bytes[31], bytes[32]));

		device.setDp(Hex.parseHex4(bytes[43], bytes[44])); // >125
		device.setDpUpLimit(Hex.parseHex4(bytes[45], bytes[46]));
		device.setDpDownLimit(Hex.parseHex4(bytes[47], bytes[48]));
		device.setDpOff(Hex.parseHex4(bytes[49], bytes[50]));
		device.setDpReally(Hex.parseHex4(bytes[51], bytes[52]));
		device.setDpTarget(Hex.parseHex4(bytes[53], bytes[54]));
		device.setAkpMode(Hex.parseHex4(bytes[55], bytes[56]));

		device.setCommunicateFalse(Hex.parseHex4(bytes[35], bytes[36]));
		device.setCommunicateTrue(Hex.parseHex4(bytes[37], bytes[38]));
		device.setInfoBar(Hex.parseHex4(bytes[39], bytes[40]));
		device.setStateSwitch(Hex.parseHex4(bytes[41], bytes[42]));

		device.setWorkHour(Hex.parseHex4(bytes[63], bytes[64])); //
		device.setWorkSecond(Hex.parseHex4(bytes[65], bytes[66]));//
		device.setConverterMax(Hex.parseHex4(bytes[67], bytes[68]));
		device.setConverterMin(Hex.parseHex4(bytes[69], bytes[70]));
		device.setConverterModel(Hex.parseHex4(bytes[71], bytes[72]));
		device.setCycleError(Hex.parseHex4(bytes[73], bytes[74]));
		device.setAlarmCycle(Hex.parseHex4(bytes[75], bytes[76]));

		device.setTempAlarmClose(Hex.parseHex4(bytes[83], bytes[84]));
		device.setHrAlarmClose(Hex.parseHex4(bytes[85], bytes[86]));
		device.setDpAlarmClose(Hex.parseHex4(bytes[87], bytes[88]));
		device.setInWindAlarmClose(Hex.parseHex4(bytes[89], bytes[90]));

		device.setAirSpeed10(Hex.parseHex4(bytes[103], bytes[104])); //
		device.setAirSpeed12(Hex.parseHex4(bytes[105], bytes[106]));
		device.setAirSpeed14(Hex.parseHex4(bytes[107], bytes[108]));
		device.setAirSpeed16(Hex.parseHex4(bytes[109], bytes[110]));
		device.setAirSpeed18(Hex.parseHex4(bytes[111], bytes[112]));
		device.setAirSpeed20(Hex.parseHex4(bytes[113], bytes[114]));
		device.setAirSpeed22(Hex.parseHex4(bytes[115], bytes[116]));
		device.setAirSpeed24(Hex.parseHex4(bytes[117], bytes[118]));
		device.setAirSpeed26(Hex.parseHex4(bytes[119], bytes[120]));
		device.setAirSpeed28(Hex.parseHex4(bytes[121], bytes[122]));
		device.setAirSpeed30(Hex.parseHex4(bytes[123], bytes[124]));
		device.setAirSpeed35(Hex.parseHex4(bytes[125], bytes[126]));
		device.setAirSpeed40(Hex.parseHex4(bytes[127], bytes[128]));
		device.setAirSpeed45(Hex.parseHex4(bytes[129], bytes[130]));
		device.setAirSpeed50(Hex.parseHex4(bytes[131], bytes[132]));

		device.setUpdateTime(formatDate(new Date()));

		if (device.getDeviceId() != 0 && device.getTemp() != 0) {
			deviceSocket.setUnReceiveTime(1);
			deviceSocket.setDevice(device);
			try {
				DeviceDao deviceDao = new DeviceDao();
				deviceDao.saveOrUpdate(device);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		return sdf.format(date);
	}

	private void clientClose() {
		synchronized (dsockets) {
			dsockets.remove(deviceSocket);
		}
		log.info("disconnect sockets:" + dsockets.size());
		try {
			socket.close();
			new DeviceDao().deviceClose(deviceSocket.getAreaId(), deviceSocket.getDeviceId());
		} catch (Exception exx) {
			exx.printStackTrace();
		}
		this.listChange(deviceSocket.getAreaId(), -1);
		isClientClose = true;
	}

	@Override
	public void listChange(int areaId, int flag) {
		// log.info("listChange:"+areaId+" "+flag);
	}

	@Override
	public void objectChange(Device device, String field, Object oldValue, Object newValue) {
		if (!newObj) {
			log.info("objectChange:" + device.getAreaId() + " " + device.getDeviceId() + " field:" + field
					+ "  oldValue:" + oldValue + " newValue:" + newValue);
			if (field.endsWith("tempUpLimit") && ((int) newValue) == 81) {
				SendSms.send("13358018613", device.getDeviceId(), "测试报警");
			}
			if (field.endsWith("infoBar") && (int) newValue > 1) {
				String alarmMsg = stringOfInfoBar((int) newValue);
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

	private void recordAlarm(int areaId,int deviceId,String alarmMsg) {
		DeviceDao deviceDao = new DeviceDao();
		Device device = deviceDao.get(areaId, deviceId);
		String alarmHistory = device.getAlarmHistory();
		JSONArray alarmJsonArray = JSON.parseArray(alarmHistory);
		if(alarmJsonArray.size()>=8){
			alarmJsonArray.remove(0);
		}
		JSONObject alarmJson =  new JSONObject();
		alarmJson.put("time", formatDate(new Date()));
		alarmJson.put("msg", alarmMsg);
		alarmJsonArray.add(alarmJson);
		deviceDao.updateAlarm(areaId,deviceId,alarmJsonArray.toJSONString());
	}

	private String stringOfInfoBar(int infoBar) {
		String infoBarStr = "";
		switch (infoBar) {
		case 0:
			infoBarStr = "待机状态，按开启键启动";
			break;
		case 1:
			infoBarStr = "工作正常，按关闭键停止";
			break;
		case 2:
			infoBarStr = "温度过低";
			break;
		case 3:
			infoBarStr = "断电报警";
			break;
		case 4:
			infoBarStr = "温度超高";
			break;
		case 5:
			infoBarStr = "温度过低";
			break;
		case 6:
			infoBarStr = "湿度超高";
			break;
		case 7:
			infoBarStr = "湿度过低";
			break;
		case 8:
			infoBarStr = "压差过高";
			break;
		case 9:
			infoBarStr = "压差过低";
			break;
		case 10:
			infoBarStr = "模拟量采集通讯故障";
			break;
		case 11:
			infoBarStr = "进风自动调节上限";
			break;
		case 12:
			infoBarStr = "进风自动调节下限";
			break;
		case 13:
			infoBarStr = "模拟量采集通讯故障";
			break;

		}
		return infoBarStr;
	}

}
