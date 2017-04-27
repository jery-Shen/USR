package usr.work.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SendSms {
	
	Log log = LogFactory.getLog(SendSms.class);

	public static synchronized void send(String phone, int deviceId, String alarmMsg) {
		if (!isPhone(phone))
			return;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpUriRequest httpGet = RequestBuilder.get("https://ca.aliyuncs.com/gw/alidayu/sendSms")
					.addHeader("X-Ca-Key", "23388495").addHeader("X-Ca-Secret", "1bd13d861e7143c1d3761bf6c56a3f98")
					.addParameter("rec_num", phone).addParameter("sms_template_code", "SMS_61045335")
					.addParameter("sms_free_sign_name", "USR设备").addParameter("sms_type", "normal")
					.addParameter("sms_param",
							"{'deviceId':'" + deviceId + "','infoBar':'" + alarmMsg + "'}")
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


}
