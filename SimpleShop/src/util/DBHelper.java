package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {
//	���ݿ�����
	private static final String driver = "com.mysql.jdbc.Driver";
//	�������ݿ��URL��ַ������ʹ�õ���mysql��Ĭ�϶˿�3306
//	����ַ�������ֹ����д������
	private static final String url = "jdbc:mysql://localhost:3306/simpleshop?useUnicode=true&characterEncoding=UTF-8";
//	���ݿ��½��Ϣ�������ﲻʹ��root�û���һ�����һ����Ŀ�����ǿ��Դ���һ�������ľ��в���Ȩ���û�������root�û�һ�㶼ֻ�����ڹ������ݿ�
	private static final String username = "superboy";
	private static final String password = "iamsuperboy";
	
	private static Connection conn = null;
	
//	��̬����鸺���������
//	һ�������,�����Щ�����������Ŀ������ʱ���ִ�е�ʱ��,��Ҫʹ�þ�̬�����,���ִ���������ִ�еģ�����������������
	static{
		try{
			Class.forName(driver);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws Exception{
		if(conn == null){
			conn = DriverManager.getConnection(url,username,password);
			return conn;
		}
		return conn;
	}
	
	public static void main(String[] args) {
		
		try
		{
		   Connection conn = DBHelper.getConnection();
		   if(conn!=null)
		   {
			   System.out.println("���ݿ�����������");
		   }
		   else
		   {
			   System.out.println("���ݿ������쳣��");
		   }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
}
