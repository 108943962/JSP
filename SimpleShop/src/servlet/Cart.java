package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CartDAO;
import entity.User;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
//		ȷ�����ص����ݲ�����
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(user == null){
//			��δ��¼
			out.print("{\"err\":\"error\",\"message\":\"����δ��¼\"}");  
	        out.flush();  
	        out.close();
			return;
		}
		String isbn = request.getParameter("isbn");
		String action = request.getParameter("action");
		
		String uid = user.getUid();
		
		if(action.equals("add")){
			CartDAO cartdao = new CartDAO();
			if(cartdao.addOneBook(uid, isbn)){
				out.print("{\"message\":\"��ӳɹ�\"}");
			}else{
				out.print("{\"message\":\"���ʧ��\"}");
			}
		}else if(action.equals("delete")){
			CartDAO cartdao = new CartDAO();
			if(cartdao.deleteBook(uid, isbn)){
				out.print("{\"err\":\"success\",\"message\":\"ɾ���ɹ�\"}");
			}else{
				out.print("{\"err\":\"fail\",\"message\":\"ɾ��ʧ��\"}");
			}
		}
		out.flush();  
        out.close();
//		�����ڲ���
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
