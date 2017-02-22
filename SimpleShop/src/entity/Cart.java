package entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Cart {
//	������кܶ����Ʒ���������ǿ�����һ��Map�������
//	��Ʒ����
	private HashMap<Book, Integer> books;
	
//	�ܷ���
	private double totalCost;
	
	public Cart(){}

	public HashMap<Book, Integer> getBooks() {
		return books;
	}

	public void setBooks(HashMap<Book, Integer> books) {
		this.books = books;
	}
	
	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public double getOneTypeBookCostInCart(Book book){
		return book.getPrice()* books.get(book);
	}
}
