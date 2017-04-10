package usr.work.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import usr.work.utils.Hex;

/**
 * Servlet implementation class TestCrc
 */
@WebServlet("/TestCrc")
public class TestCrc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		byte[] bytes = new byte[] { 0x02, 0x05, 0x00, 0x03, 0x15, 0x12 };
//        System.out.println(Hex.printHexString(CRC.getCRC16(bytes)));
//        System.out.println(Hex.printHexString(CRC.getCRC(bytes)));
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
