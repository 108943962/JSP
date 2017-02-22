package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import entity.Book;
import entity.Cart;
import util.DBHelper;

public class CartDAO {
//	�ж��Ƿ����и���
	public String isExist(String uid, String isbn){
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet userSet = null;
		System.out.println("����鱾");
		try{
			conn = DBHelper.getConnection();
//			�ж���Ʒ�Ƿ��Ѿ�����
			String sql = "select * from cart where uid=? and isbn=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, uid);
			preStmt.setString(2, isbn);
			userSet = preStmt.executeQuery();
			
			if(userSet.next()){
				return "true";
			}
			return "false";
		}catch(Exception ex){
			ex.printStackTrace();
			return "error";
		}finally{
			if(preStmt != null){
				try {
					preStmt.close();
					preStmt = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
//	�����Ʒ
	public boolean addBook(String uid, String isbn, int num){
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet userSet = null;
		System.out.println("����鱾");
		try{
			conn = DBHelper.getConnection();
//			�ж���Ʒ�Ƿ��Ѿ�����
			String exist = isExist(uid, isbn);
			if(exist == "error"){
				return false;
			}if(exist == "false"){
				String sql = "insert into cart(uid, isbn, num) values(?, ?, ?)";
				preStmt = conn.prepareStatement(sql);
				preStmt.setInt(3, num);
			}else if(exist == "true"){
				String sql = "update cart set num=num+1 where uid=? and isbn=?";
				preStmt = conn.prepareStatement(sql);
			}
			preStmt.setString(1, uid);
			preStmt.setString(2, isbn);
			int affectedRowNum = preStmt.executeUpdate();
			if(affectedRowNum > 0){
				return true;
			}
			else{
				return false;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			if(preStmt != null){
				try {
					preStmt.close();
					preStmt = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
//	���һ����Ʒ
	public boolean addOneBook(String uid, String isbn){
		System.out.println("���һ����");
		return addBook(uid, isbn, 1);
	}
//	ɾ����Ʒ
	public boolean deleteBook(String uid, String isbn){
		Connection conn = null;
		PreparedStatement preStmt = null;
		
		try{
			conn = DBHelper.getConnection();
			String sql = "delete from cart where uid=? and isbn=?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, uid);
			preStmt.setString(2, isbn);

			int affectedRowNum = preStmt.executeUpdate();

			if(affectedRowNum < 1){
				return false;
			}
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}finally{
			if(preStmt != null){
				try{
					preStmt.close();
					preStmt = null;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
//	��ȡ�û����ﳵ
	public Cart getCart(String uid){
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet bookSet = null;
		Cart cart = null;
		try{
			conn = DBHelper.getConnection();
			String sql = "select B.name, B.isbn, B.price, C.num from book B, Cart C where B.isbn = C.isbn and C.uid = ?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, uid);
			bookSet = preStmt.executeQuery();
			HashMap<Book, Integer> books = new HashMap<Book, Integer>();
			double tolalCost = 0;
			while(bookSet.next()){
				Book book = new Book();

				book.setName(bookSet.getString("name"));
				book.setIsbn(bookSet.getString("isbn"));
				float price = bookSet.getFloat("price");
				book.setPrice(price);
				int num = Integer.parseInt(bookSet.getString("num"));
				books.put(book, num);
				tolalCost += price * num;
			}
			cart = new Cart();
			cart.setBooks(books);
			cart.setTotalCost(tolalCost);
			return cart;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			if(bookSet != null){
				try{
					bookSet.close();
					bookSet = null;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			if(preStmt != null){
				try{
					preStmt.close();
					preStmt = null;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
}
