package usr.work.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import usr.work.bean.Message;
import usr.work.dao.UserDao;

/**
 * Servlet implementation class UpdateUserPwd
 */
@WebServlet("/UpdateUserPwd")
public class UpdateUserPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		String newPwd = request.getParameter("newPwd");
		Message message = new Message();
		if(userName!=null && userPwd!=null){
			UserDao userDao = new UserDao();
			if(userDao.updatePwd(userName, userPwd, newPwd)){
				message.setStatus(200);
			}else{
				message.setStatus(212);
				message.setError("旧密码错误");
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
