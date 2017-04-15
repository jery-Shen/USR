package usr.work.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import usr.work.bean.Host;
import usr.work.bean.Message;
import usr.work.bean.User;
import usr.work.dao.HostDao;
import usr.work.dao.UserDao;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
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
						if(user.getPrivilege()==5){
							request.getSession().setAttribute("user", user);
							message.setStatus(200);
						}else{
							message.setStatus(211);
							message.setError("用户无权限，请联系管理员");
						}
					}else{
						userDao.updateFlag(userName);
						message.setStatus(213);
						message.setError("密码错误");
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
