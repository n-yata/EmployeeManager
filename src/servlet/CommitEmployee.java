package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import beans.Employee;
import beans.Photo;
import dao.EmployeeDAO;
import dao.PhotoDAO;
import util.Validator;

/**
 * Servlet implementation class CommitEmployee
 */
@WebServlet("/CommitEmployee")
@MultipartConfig //partとformデータ両方とも受け取れるようになる　忘れずに
public class CommitEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommitEmployee() {
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
			String empId = request.getParameter("empId");
			String name = request.getParameter("name");
			//空文字の場合、0をセット
			int age = 0;
			if(!request.getParameter("age").equals("")) {
				age = Integer.parseInt(request.getParameter("age"));
			}
			String gender = request.getParameter("gender");
			int photoId = processPhoto(request);
			String zip = request.getParameter("zip");
			int pref = Integer.parseInt(request.getParameter("pref"));
			String address = request.getParameter("address");
			int postId = Integer.parseInt(request.getParameter("postId"));
			//空文字の場合、nullをセット
			Date entDate = null;
			if(!request.getParameter("entDate").equals("")) {
				entDate = Date.valueOf(request.getParameter("entDate"));
			}
			//空文字の場合、nullをセット
			Date retDate = null;
			if(!request.getParameter("retDate").equals("")) {
				retDate = Date.valueOf(request.getParameter("retDate"));
			}


			Employee emp = new Employee();
			emp.setEmpId(empId);
			emp.setName(name);
			emp.setAge(age);
			emp.setGender(gender);
			emp.setPhotoId(photoId);
			emp.setZip(zip);
			emp.setPref(pref);
			emp.setAddress(address);
			emp.setPostId(postId);
			emp.setEntDate(entDate);
			emp.setRetDate(retDate);

			boolean result = false;

			if(request.getParameter("id").equals("0")) {
				result = new EmployeeDAO().insert(emp);
			}else {
				emp.setId(Integer.parseInt(request.getParameter("id")));
				result = new EmployeeDAO().update(emp);
			}

			if(result) {
//				System.out.println("成功");
				request.setAttribute("succeed", "Success");
			}else {
//				System.out.println("失敗");
				request.setAttribute("succeed", "Error");
			}
			request.setAttribute("action", "emp");
			request.getRequestDispatcher("Result.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("EmployeeEditor.jsp").forward(request, response);
		}
	}

	//inputの制限事項に引っかかった場合のエラーメッセージ
	private boolean inputIsValidated(HttpServletRequest request, HttpServletResponse response) {
		boolean success = true;

		String errorPhoto;
		try {
			errorPhoto = validatePhoto(request.getPart("photo"));
			if(errorPhoto != null) {
				request.setAttribute("errorPhoto", errorPhoto);
				success = false;
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
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
		String errorAge = validateAge(request.getParameter("age"));
		if(errorAge != null) {
			request.setAttribute("errorAge", errorAge);
			success = false;
		}
		String errorZip = validateZip(request.getParameter("zip"));
		if(errorZip != null) {
			request.setAttribute("errorZip", errorZip);
			success = false;
		}
		String errorAddress = validateAddress(request.getParameter("address"));
		if(errorAddress != null) {
			request.setAttribute("errorAddress", errorAddress);
			success = false;
		}
		String errorEntDate = validateDate(request.getParameter("entDate"));
		if(errorEntDate != null) {
			request.setAttribute("errorEntDate", errorEntDate);
			success = false;
		}
		String errorRetDate = validateDate(request.getParameter("retDate"));
		if(errorRetDate != null) {
			request.setAttribute("errorRetDate", errorRetDate);
			success = false;
		}
		return success;
	}

	//各inputの制限事項
	private String validatePhoto(Part part) {
		ArrayList<String> msg = new ArrayList<>();
		if(!Validator.validateFileSize(part.getSize(), Photo.MAX_FILE_SIZE)) {
			msg.add("ファイルサイズは2Mまでです。");
		}
		if(msg.isEmpty()) {
			return null;
		}else {
			return String.join("</br>", msg);
		}
	}

	private String validateEmpId(String input) {
		ArrayList<String> msg = new ArrayList<>();
		if(!Validator.validateRequired(input)) {
			msg.add("従業員IDは入力必須事項です。");
		}
		if(!Validator.validateStringSize(input, Employee.EMPID_MAX_SIZE)) {
			msg.add("従業員IDは" + Employee.EMPID_MAX_SIZE + "バイト以内で入力してください。");
		}
		if(!Validator.validateRegex(input, "^emp[0-9]{4}$")) {
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
		if(!Validator.validateRequired(input)) {
			msg.add("名前は入力必須事項です。");
		}
		if(!Validator.validateStringSize(input, Employee.NAME_MAX_SIZE)) {
			msg.add("名前は" + Employee.NAME_MAX_SIZE + "バイト以内で入力してください。");
		}
		if(msg.isEmpty()) {
			return null;
		}else {
			return String.join("</br>", msg);
		}
	}

	private String validateAge(String input) {
		//数字以外の入力の時
		ArrayList<String> msg = new ArrayList<>();
		if(!Validator.validateNumberAllowsBlank(input)) {
			msg.add("年齢は半角数字で入力してください。");
		}
		if(msg.isEmpty()) {
			return null;
		}else {
			return String.join("</br>", msg);
		}
	}

	private String validateZip(String input) {
		ArrayList<String> msg = new ArrayList<>();
		if(!Validator.validateZipAllowsBlank(input)) {
			msg.add("郵便番号は「000-0000」の形式で入力してください。");
		}
		if(msg.isEmpty()) {
			return null;
		}else {
			return String.join("</br>", msg);
		}
	}

	private String validateAddress(String input) {
		ArrayList<String> msg = new ArrayList<>();
		if(!Validator.validateStringSize(input, Employee.ADDRESS_MAX_SIZE)) {
			msg.add("住所は" + Employee.ADDRESS_MAX_SIZE + "バイト以内で入力してください。");
		}
		if(msg.isEmpty()) {
			return null;
		}else {
			return String.join("</br>", msg);
		}
	}

	private String validateDate(String input) {
		ArrayList<String> msg = new ArrayList<>();
		if(!Validator.validateDateAllowsBlank(input)) {
			msg.add("日付は「0000-00-00」の形式で入力してください。");
		}
		if(msg.isEmpty()) {
			return null;
		}else {
			return String.join("</br>", msg);
		}
	}

	private int processPhoto(HttpServletRequest request) {
		Part part;
//		empのphotoIdの初期値は0
		int id = Integer.parseInt(request.getParameter("photoId"));
		try {
			part = request.getPart("photo");
			Photo photo = new Photo();
			photo.setId(id);
			photo.setContentType(part.getContentType());
			photo.setPhoto(part.getInputStream());
			if(id == 0) {
				return new PhotoDAO().insert(photo);
			}else {
				new PhotoDAO().update(photo);
				return id;
			}
		} catch (IOException | ServletException | SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}