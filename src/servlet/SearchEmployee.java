package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Employee;
import dao.EmployeeDAO;
import util.Validator;

/**
 * Servlet implementation class SearchEmployee
 */
@WebServlet("/SearchEmployee")
public class SearchEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchEmployee() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		if(inputIsValidated(request, response)) {
			int postId = Integer.parseInt(request.getParameter("postId"));
			String empId = request.getParameter("empId");
			String name = request.getParameter("name");

			ArrayList<Employee> employees = new ArrayList<>();
			employees = new EmployeeDAO().searchEmp(postId, empId, name);

			request.setAttribute("searchedEmployees", employees);
			request.getRequestDispatcher("EmployeeList.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("EmployeeSearch.jsp").forward(request, response);
		}
	}

	private boolean inputIsValidated(HttpServletRequest request, HttpServletResponse responce) {
		boolean success = true;

		String errorEmpId = validateEmpId(request.getParameter("empId"));
		if(errorEmpId != null) {
			request.setAttribute("errorEmpId", errorEmpId);
			success = false;
		}
		String errorName = validateName(request.getParameter("name"));
		if(errorName != null) {
			request.setAttribute("errorName", errorName);
			success = false;
		}

		String errorAllEmpty = validateAllEmpty(Integer.parseInt(request.getParameter("postId")),
											request.getParameter("empId"),
											request.getParameter("name"));
			if(errorAllEmpty != null) {
				request.setAttribute("errorAllEmpty", errorAllEmpty);
				success = false;
		}
		return success;
	}

	private String validateEmpId(String input) {
		ArrayList<String> msg = new ArrayList<>();
		if(!Validator.validateRegexAllowsBlank(input, "^emp[0-9]{4}$")) {
			msg.add("従業員IDは「emp0000」の形式で入力してください。");
		}
		if(msg.isEmpty()) {
			return null;
		}else {
			return String.join("</br>", msg);
		}
	}
	private String validateName(String input) {
		ArrayList<String> msg = new ArrayList<>();
		if(!Validator.validateStringSize(input, Employee.NAME_MAX_SIZE)) {
			msg.add("名前は" + Employee.NAME_MAX_SIZE + "バイト以内で入力してください。");
		}
		if(msg.isEmpty()) {
			return null;
		}else {
			return String.join("</br>", msg);
		}
	}
	private String validateAllEmpty(int input1, String input2, String input3) {
		ArrayList<String> msg = new ArrayList<>();
		if(input1 == 0 && input2.equals("") && input3.equals("")) {
			msg.add("最低でも１つの項目を入力してください。");
		}
		if(msg.isEmpty()) {
			return null;
		}else {
			return String.join("</br>", msg);
		}
	}
}
