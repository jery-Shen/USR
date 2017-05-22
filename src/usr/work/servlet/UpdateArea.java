package usr.work.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import usr.work.bean.Message;
import usr.work.bean.Area;
import usr.work.dao.AreaDao;

/**
 * Servlet implementation class UpdateArea
 */
@WebServlet("/UpdateArea")
public class UpdateArea extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String iD = request.getParameter("iD");
		String areaName = request.getParameter("areaName");
		String chargeName = request.getParameter("chargeName");
		String chargePhone = request.getParameter("chargePhone");
		Message message = new Message();
		Area area = null;
		if(areaName!=null){
			area = new Area();
			area.setID(Integer.parseInt(iD));
			area.setAreaName(areaName);
			area.setChargeName(chargeName);
			area.setChargePhone(chargePhone);
			AreaDao areaDao = new AreaDao();
			if(areaDao.update(area)){
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
