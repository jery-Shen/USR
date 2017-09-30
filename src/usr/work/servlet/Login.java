package usr.work.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import usr.work.bean.Device;
import usr.work.bean.Message;
import usr.work.bean.User;
import usr.work.dao.DeviceDao;
import usr.work.dao.UserDao;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		Message message = new Message();
		if(userName!=null && userPwd!=null){
			UserDao userDao = new UserDao();
			User user = userDao.get(userName);
			if(user!=null){
				if(user.getLoginFlag()<10){
					if(user.getUserPwd().equals(userPwd)){
						DeviceDao deviceDao = new DeviceDao();
						int areaId = user.getAreaId();
						List<Device> deviceList = areaId==-1?deviceDao.getList():deviceDao.getList(areaId);
						JSONArray hostList = new JSONArray();
						for(Device device : deviceList){
							JSONObject host = new JSONObject();
							host.put("areaId", device.getAreaId());
							host.put("deviceId", device.getDeviceId());
							host.put("mac", device.getMac());
							host.put("des", device.getDes());
							host.put("enable", device.getEnable());
							host.put("online", device.getOnline());
							hostList.add(host);
						}
						JSONObject res = new JSONObject();
						res.put("user", user);
						res.put("hostList", hostList);
						message.setStatus(200);
						message.setResult(res);
						userDao.clearFlag(userName);
					}else{
						message.setStatus(213);
						message.setError("密码错误");
						userDao.updateFlag(userName);
					}
				}else{
					message.setStatus(215);
					message.setError("密码连续错误，请"+(user.getLoginFlag()-9)+"个小时后再试");
				}
			}else{
				message.setStatus(212);
				message.setError("用户不存在");
			}
		}else{
			message.setStatus(210);
			message.setError("信息不完整");
		}
		
		response.getWriter().write(JSON.toJSONString(message));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
