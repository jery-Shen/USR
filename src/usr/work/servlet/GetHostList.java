package usr.work.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import usr.work.bean.Host;
import usr.work.bean.Message;
import usr.work.dao.HostDao;

/**
 * Servlet implementation class GetHostList
 */
@WebServlet("/GetHostList")
public class GetHostList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int areaId = 0;
		try {
			areaId = Integer.parseInt(request.getParameter("areaId"));
		} catch (Exception e) {}
		List<Host> hostList = null;
		HostDao hostDao = new HostDao();
		if(areaId==0){
			hostList = hostDao.getList();
		}else{
			hostList = hostDao.getList(areaId);
		}
		String resStr = JSON.toJSONString(new Message(200,hostList));
		response.getWriter().println(resStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
