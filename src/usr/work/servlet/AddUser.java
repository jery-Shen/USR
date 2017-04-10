package usr.work.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import usr.work.bean.Message;
import usr.work.bean.User;
import usr.work.dao.UserDao;

/**
 * Servlet implementation class AddUser
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String userPwd = request.getParameter("userPwd");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String des = request.getParameter("des");
		Message message = new Message();
		
		int areaId = 0;
		try {
			areaId = Integer.parseInt(request.getParameter("areaId"));
		} catch (Exception e) {}
		User user = null;
		if(userName!=null&&userPwd!=null&&areaId!=0){
			
			user = new User();
			user.setUserName(userName);
			user.setUserPwd(userPwd);
			user.setAreaId(areaId);
			if(phone==null) phone="-";
			if(email==null) email="-";
			if(address==null) address="-";
			if(name==null) name="-";
			if(des==null) des="-";
			user.setPrivilege(1);
			user.setPhone(phone);
			user.setEmail(email);
			user.setAddress(address);
			user.setName(name);
			user.setDes(des);
			UserDao userDao = new UserDao();
			if(userDao.add(user)){
				message.setStatus(200);
			}else{
				message.setStatus(215);
				message.setError("用户已存在");
			}
		}else{
			message.setStatus(212);
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
