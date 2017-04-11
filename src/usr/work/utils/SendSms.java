package usr.work.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SendSms {
	public static void send(int deviceId,String infoBar){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpUriRequest httpGet = RequestBuilder.get("https://ca.aliyuncs.com/gw/alidayu/sendSms")
					.addHeader("X-Ca-Key","23388495")
					.addHeader("X-Ca-Secret","1bd13d861e7143c1d3761bf6c56a3f98")
					.addParameter("rec_num","13358018613")
					.addParameter("sms_template_code","SMS_61045335")
					.addParameter("sms_free_sign_name","USR设备")
					.addParameter("sms_type","normal")
					.addParameter("sms_param","{'deviceId':'"+deviceId+"','infoBar':'"+infoBar+"'}")
					.build();
			httpClient.execute(httpGet,new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
					if(httpResponse.getStatusLine().getStatusCode()==200){
						System.out.println("发送短信提示："+EntityUtils.toString(httpResponse.getEntity()));
					}
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
