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
 * Servlet implementation class AddRegion
 */
@WebServlet("/AddRegion")
public class AddRegion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String regionName = request.getParameter("regionName");
		String chargeName = request.getParameter("chargeName");
		String chargePhone = request.getParameter("chargePhone");
		Message message = new Message();
		Region region = null;
		if(regionName!=null){
			region = new Region();
			region.setRegionName(regionName);
			if(chargeName==null) chargeName="-";
			if(chargePhone==null) chargePhone="-";
			region.setChargeName(chargeName);
			region.setChargePhone(chargePhone);
			RegionDao regionDao = new RegionDao();
			if(regionDao.add(region)){
				message.setStatus(200);
			}else{
				message.setStatus(215);
				message.setError("区域已存在");
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
