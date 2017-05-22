package usr.work.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import usr.work.bean.Message;
import usr.work.bean.Region;
import usr.work.dao.RegionDao;

/**
 * Servlet implementation class GetRegion
 */
@WebServlet("/GetRegionList")
public class GetRegionList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Region> regionList = null;
		RegionDao regionDao = new RegionDao();
		regionList = regionDao.getList();
		String resStr = JSON.toJSONString(new Message(200,regionList));
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
