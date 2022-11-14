import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StudentServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			response.getWriter().println("<h1>" + DBService.getAll() + "</h1>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String jsonstudent = HTTPUtils.getBody(request);
			DBService.addOne(jsonstudent);
			response.setContentType("text/html");
			response.getWriter().println("<h1>" + "Added Student:" + jsonstudent + "</h1>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String jsonstudent = HTTPUtils.getBody(request);
			DBService.updateOne(jsonstudent);
			response.setContentType("text/html");
			response.getWriter().println("<h1>" + "Updated Student:" + jsonstudent + "</h1>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String jsonstudent = HTTPUtils.getBody(request);
			DBService.deleteOne(jsonstudent);
			response.setContentType("text/html");
			response.getWriter().println("<h1>" + "Deleted student with id:" + jsonstudent + "</h1>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}