package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entity.Book;
import util.DBHelper;

// �����ݿ⽻����
public class BookDAO {
	/*
	 JDBC��̹��̣�
	 1. �������ݿ�
	 2. ִ��SQL��䣬����ִ�н����ResultSet
	 3. ����ִ�н����ResultSet
	 4. ��Ҫ�Ĺر�ResultSet��Statement
	 */

	/*
	 * ��ȡ�����鼮
	 */
	public ArrayList<Book> getAllBooks(){
//		���ݿ�����
		Connection conn = null;
//		���ݿ��������SQL����Ԥ�����Statment������ִ��sql���
		PreparedStatement preStmt = null;
//		���ݿ�����ResultSet�����ݿ�ִ�н���������ݽṹ�����ݿ��еı��൱
		ResultSet bookSet = null;
//		������е��鼮���
		ArrayList<Book> books = new ArrayList<Book>();
		try{
//--------------- 1. �������ݿ�------------------
			conn = DBHelper.getConnection();
//--------------- 2. ִ��SQL��䣬����ִ�н����ResultSet ------------------
			String sql = "select * from book";
//			Ԥ����sql��䣬��ȡPreparedStament����
			preStmt = conn.prepareStatement(sql);
//			ִ�б���Ľ��
			bookSet = preStmt.executeQuery();
//			����������ķ������£�
//--------------- 3. ����ִ�н����ResultSet ------------------
			while(bookSet.next()){
				Book book = new Book();
				
				book.setAuthor(bookSet.getString("author"));
				book.setImg(bookSet.getString("img"));
				book.setIntro(bookSet.getString("intro"));
				book.setIsbn(bookSet.getString("isbn"));
				book.setName(bookSet.getString("name"));
				book.setPrice(Float.parseFloat(bookSet.getString("price")));
				book.setPrice_original(Float.parseFloat(bookSet.getString("price_original")));
//				book.setPublish_company(bookSet.getString("publish_company"));
////				ʱ���ʽ��
//				SimpleDateFormat dateformat  = new SimpleDateFormat( "yyyy��MM��dd�� ");
//				ParsePosition pos = new ParsePosition(0);
//				book.setPublish_time(dateformat.parse(bookSet.getString("name"),pos));
				
				books.add(book);
			}
			return books;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
//--------------- 4. ��Ҫ�Ĺر�ResultSet��Statement ------------------
//			Ϊ��ȷ����Դ����ռ�ã���Ҫ��fianlly�ͷ���Դ��ע�����ﲻ���Թر�Connection������ᱨ��
//			�ͷ� ResultSet
			if(bookSet != null){
				try{
					bookSet.close();
					bookSet = null;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
//			�ͷ�������PreparedStatement
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
	
	public Book getBookByISBN(String isbn){
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet bookSet = null;
		
		try{
//--------------- 1. �������ݿ�------------------
			conn = DBHelper.getConnection();
//--------------- 2. ִ��SQL��䣬����ִ�н����ResultSet ------------------
			String sql = "select * from book where isbn=?;";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, isbn);
			bookSet = preStmt.executeQuery();
//--------------- 3. ����ִ�н����ResultSet ------------------
			if(bookSet.next()){
				Book book = new Book();
				
				book.setAuthor(bookSet.getString("author"));
				book.setImg(bookSet.getString("img"));
				book.setIntro(bookSet.getString("intro"));
				book.setIsbn(bookSet.getString("isbn"));
				book.setName(bookSet.getString("name"));
				book.setPrice(Float.parseFloat(bookSet.getString("price")));
				book.setPrice_original(Float.parseFloat(bookSet.getString("price_original")));
				book.setPublish_company(bookSet.getString("publish_company"));
//				ʱ���ʽ��
				SimpleDateFormat dateformat  = new SimpleDateFormat( "yyyy��MM��dd�� ");
				ParsePosition pos = new ParsePosition(0);
				book.setPublish_time(dateformat.parse(bookSet.getString("name"),pos));
				
				return book;
			}else{
				return null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
//--------------- 4. ��Ҫ�Ĺر�ResultSet��Statement ------------------
//			Ϊ��ȷ����Դ����ռ�ã���Ҫ��fianlly�ͷ���Դ��ע�����ﲻ���Թر�Connection������ᱨ��
//			�ͷ� ResultSet
			if(bookSet != null){
				try{
					bookSet.close();
					bookSet = null;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
//						�ͷ�������PreparedStatement
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
	
	/*
	 * ͨ��һϵ��isbn��ȡ�鼮��Ϣ
	 */
	public ArrayList<Book> getViewList(String isbnStr){
		ArrayList<Book> books = new ArrayList<Book>();
//		�������4����Ϣ
		int maxRecordCount = 4;
		if(isbnStr != null && isbnStr.length() > 0){
			String[] isbns = isbnStr.split(",");
			if(isbns.length >= maxRecordCount){
//				��Ʒ��¼�����ڵ���4��
				for(int i = isbns.length - 1; i >= isbns.length-maxRecordCount; i --){
					books.add(getBookByISBN(isbns[i]));
				}
			}else{
//				��Ʒ��¼��С��4��
				for(int i = isbns.length-1; i >= 0; i --){
					books.add(getBookByISBN(isbns[i]));
				}
			}
			return books;
		}else{
			return null;
		}
		
 	}
}
