package usr.work.servlet;

import java.io.IOException;
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
 * Servlet implementation class UpdateRegion
 */
@WebServlet("/UpdateRegion")
public class UpdateRegion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String iD = request.getParameter("iD");
		String regionName = request.getParameter("regionName");
		String chargeName = request.getParameter("chargeName");
		String chargePhone = request.getParameter("chargePhone");
		Message message = new Message();
		Region region = null;
		if(regionName!=null){
			region = new Region();
			region.setID(Integer.parseInt(iD));
			region.setRegionName(regionName);
			region.setChargeName(chargeName);
			region.setChargePhone(chargePhone);
			RegionDao regionDao = new RegionDao();
			if(regionDao.update(region)){
				message.setStatus(200);
			}else{
				message.setStatus(215);
				message.setError("更新失败");
			}
		}else{
			message.setStatus(212);
			message.setError("信息不完整");
		}
		response.getWriter().write(JSON.toJSONString(message));
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
