package usr.work.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;



public class SendSms {

	static Log log = LogFactory.getLog(SendSms.class);

	public static synchronized void send(String phone, int deviceId, String alarmMsg) {

		if (!isPhone(phone))
			return;

		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "DpzfEWwPqrT8BFke",
				"euRQrpnh9NQwHJffyADOQuztfyBhVH");
		/**
		 * use STS Token DefaultProfile profile = DefaultProfile.getProfile(
		 * "<your-region-id>", // The region ID "<your-access-key-id>", // The
		 * AccessKey ID of the RAM account "<your-access-key-secret>", // The
		 * AccessKey Secret of the RAM account "<your-sts-token>"); // STS Token
		 **/
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setSysMethod(MethodType.POST);
		request.setSysDomain("dysmsapi.aliyuncs.com");
		request.setSysVersion("2017-05-25");
		request.setSysAction("SendSms");
		request.putQueryParameter("PhoneNumbers", phone);
		request.putQueryParameter("SignName", "USR设备");
		request.putQueryParameter("TemplateCode", "SMS_61045335");
		request.putQueryParameter("TemplateParam", "{'deviceId':'" + deviceId + "','infoBar':'" + alarmMsg + "'}");
		try {
			CommonResponse response = client.getCommonResponse(request);
			log.info(response.getData());

		} catch (Exception e) {
			e.printStackTrace();
		}

		// CloseableHttpClient httpClient = HttpClients.createDefault();
		// try {
		// HttpUriRequest httpGet =
		// RequestBuilder.get("http://ca.aliyuncs.com/gw/alidayu/sendSms")
		// .addHeader("X-Ca-Key", "23388495").addHeader("X-Ca-Secret",
		// "1bd13d861e7143c1d3761bf6c56a3f98")
		// .addParameter("rec_num", phone).addParameter("sms_template_code",
		// "SMS_61045335")
		// .addParameter("sms_free_sign_name", "USR设备").addParameter("sms_type",
		// "normal")
		// .addParameter("sms_param",
		// "{'deviceId':'" + deviceId + "','infoBar':'" + alarmMsg + "'}")
		// .build();
		// httpClient.execute(httpGet, new ResponseHandler<String>() {
		// @Override
		// public String handleResponse(HttpResponse httpResponse) throws
		// ClientProtocolException, IOException {
		// System.out.println("sendsms："+phone+",res:" +
		// EntityUtils.toString(httpResponse.getEntity()));
		// if (httpResponse.getStatusLine().getStatusCode() == 200) {
		// System.out.println("sendsms："+phone+",res:" +
		// EntityUtils.toString(httpResponse.getEntity()));
		// }
		// return null;
		// }
		// });
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public static boolean isPhone(String phone) {
		if (phone == null)
			return false;
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}

}
