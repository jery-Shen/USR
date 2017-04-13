package usr.work.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SendSms {

	public static synchronized void send(String phone, int deviceId, int infoBar) {
		if (!isPhone(phone))
			return;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpUriRequest httpGet = RequestBuilder.get("https://ca.aliyuncs.com/gw/alidayu/sendSms")
					.addHeader("X-Ca-Key", "23388495").addHeader("X-Ca-Secret", "1bd13d861e7143c1d3761bf6c56a3f98")
					.addParameter("rec_num", phone).addParameter("sms_template_code", "SMS_61045335")
					.addParameter("sms_free_sign_name", "USR设备").addParameter("sms_type", "normal")
					.addParameter("sms_param",
							"{'deviceId':'" + deviceId + "','infoBar':'" + stringOfInfoBar(infoBar) + "'}")
					.build();
			httpClient.execute(httpGet, new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						System.out.println("sendsms："+phone+",res:" + EntityUtils.toString(httpResponse.getEntity()));
					}
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isPhone(String phone) {
		if(phone==null) return false;
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	private static String stringOfInfoBar(int infoBar) {
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
