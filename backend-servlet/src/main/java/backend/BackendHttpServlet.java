package backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BackendHttpServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		String msg = req.getParameter("msg");
		ResponseDTO responseDTO = new ResponseDTO();
		
		responseDTO.setMsg(msg);
		responseDTO.setTime(System.currentTimeMillis());
		responseDTO.setIp(getIp());
		
		PrintWriter writer = resp.getWriter();
		mapper.writerWithDefaultPrettyPrinter().writeValue(writer, responseDTO);
		
	
	}

	private String getIp() {
		
		String hostname;
        
		try {
            hostname = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostname = "unknown";
        }
        return hostname;
	}


}
