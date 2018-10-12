package usr.work.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usr.work.bean.Device;
import usr.work.dao.DeviceDao;
import usr.work.utils.Hex;

/**
 * Servlet implementation class TestAdd
 */
@WebServlet("/TestAdd")
public class TestAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		byte[] bytes = Hex.hexStringToBytes("0303c8001100230000000000110000000000140dac07d00038006300000000003800000000000000010002007d007d0000000000bf00140000000000000000000200d600000000000100010000000000000000000000000000000000000000000000000000000005dc064006a40708076c07d00834089808fc096009c40a280af00bb80dac0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000aa55facb");
		
		DeviceDao deviceDao = new DeviceDao();
		Device device = new Device();
		
		device.setAreaId(1);
		device.setDeviceId(bytes[0]);
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
		
		device.setCommunicateFalse(Hex.parseHex4(bytes[35], bytes[36]));
		device.setCommunicateTrue(Hex.parseHex4(bytes[37], bytes[38]));
		device.setInfoBar(Hex.parseHex4(bytes[39], bytes[40]));
		device.setStateSwitch(Hex.parseHex4(bytes[41], bytes[42]));
		
		device.setDp(Hex.parseHex4(bytes[43], bytes[44])); //>125
		device.setDpUpLimit(Hex.parseHex4(bytes[45], bytes[46]));
		device.setDpDownLimit(Hex.parseHex4(bytes[47], bytes[48]));
		device.setDpOff(Hex.parseHex4(bytes[49], bytes[50]));
		device.setDpReally(Hex.parseHex4(bytes[51], bytes[52]));
		device.setDpTarget(Hex.parseHex4(bytes[53], bytes[54]));
		device.setAkpMode(Hex.parseHex4(bytes[55], bytes[56]));
		
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
		device.setAlarmHistory("[]");
		deviceDao.update(device);
		response.getWriter().print("success");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
