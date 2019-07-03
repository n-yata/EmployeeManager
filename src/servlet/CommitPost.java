package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Post;
import dao.PostDAO;
import util.Validator;

/**
 * Servlet implementation class CommitPost
 */
@WebServlet("/CommitPost")
public class CommitPost extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommitPost() {
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
			String postName = request.getParameter("postName");
			Post post = new Post();
			post.setName(postName);

			boolean result = false;

			if(request.getParameter("id").equals("0")) {
				result = new PostDAO().insert(post);
			}else {
				post.setId(Integer.parseInt(request.getParameter("id")));
				result = new PostDAO().update(post);
			}

			if(result) {
				System.out.println("成功");
				request.setAttribute("succeed", "Success");
			}else {
				System.out.println("失敗");
				request.setAttribute("succeed", "Error");
			}
			request.setAttribute("action", "post");
			request.getRequestDispatcher("Result.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("PostEditor.jsp").forward(request, response);
		}
	}

	private boolean inputIsValidated(HttpServletRequest request, HttpServletResponse response) {
		boolean success = true;

		String errorName = validateName(request.getParameter("postName"));
		if(errorName != null) {
			request.setAttribute("errorName", errorName);
			success = false;
		}
		return success;
	}

	private String validateName(String input) {
		ArrayList<String> msg = new ArrayList<>();
		if(!Validator.validateRequired(input)) {
			msg.add("部署名は入力必須事項です。");
//			System.out.println("部署名は入力必須事項です。");
		}
		if(!Validator.validateStringSize(input, Post.NAME_MAX_SIZE)) {
			msg.add("部署名は" + Post.NAME_MAX_SIZE + "バイト以内で入力してください。");
		}
		if(msg.isEmpty()) {
			return null;
		}else {
			return String.join("<br/>", msg);
		}
	}
}
