package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import entity.User;
import util.SHA;

/**
 * Servlet implementation class Signin
 */
//��servlet��web.xml�н���������
//@WebServlet("/signin")
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �û�ͨ��url�ķ�ʽ���ʸ�servlet���ض���ע��ҳ��
		request.getRequestDispatcher("/signin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user") != null){
//			�û��Ѿ���¼����ʱ������ע��
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
//		��ֹ���з��ַ���������
		request.setCharacterEncoding("utf-8");
		String username = (String) request.getParameter("name");
		String password = (String) request.getParameter("password");
		String repassword = (String) request.getParameter("repassword");
//		�û�����������֤
		if(username == null || username.length() == 0){
			request.setAttribute("error", "�û�������Ϊ��");
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
			return;
		}
		if( username.length() > 10){
			request.setAttribute("error", "�û������Ȳ��ܴ���10");
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
			return;
		}
		if(password == null || password.length() == 0){
			request.setAttribute("error", "���벻��Ϊ��");
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
			return;
		}
		if(password.length() < 6 || password.length() > 16){
			request.setAttribute("error", "���볤��Ӧ����6��16֮��");
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
			return;
		}
		if(repassword == null || repassword.length() == 0){
			request.setAttribute("error", "�ظ����벻��Ϊ��");
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
			return;
		}
		if(!password.equals(repassword)){
			request.setAttribute("error", "�����������벻ͬ");
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
			return;
		}
		User user = new User();
		user.setName(username);
		// ͨ��SHA����֮����д�����ݿ�
		user.setPassword(SHA.encrypt(password));

		UserDAO userdao = new UserDAO();
		String uid = userdao.addUser(user);
		if(uid.equals("exist")){
			request.setAttribute("error", "�û����Ѵ���");
			request.getRequestDispatcher("/signin.jsp").forward(request, response);
			return;
		}
//		���û���Ϣд�뵽session�У���������Чʱ��Ϊ4Сʱ(14400s)
		user.setPassword("");
		request.getSession().setAttribute("user", user);
		request.getSession().setMaxInactiveInterval(14400);
//		�ض�����ҳ
		response.sendRedirect(request.getContextPath()+"/index.jsp");
		
	}

}
