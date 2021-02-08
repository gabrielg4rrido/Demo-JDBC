package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import models.dao.DaoFactory;
import models.dao.SellerDao;
import models.entities.Department;
import models.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		SellerDao sellerDao = DaoFactory.createSellerDAO();
		
		System.out.println("=== TEST 1 - Seller, Find By Id ===");
		Seller seller1 = sellerDao.findById(3);
		System.out.println(seller1);
		
		System.out.println("");
		
		System.out.println("=== TEST 2 - Seller, Find By Department ===");
		Department dept = new Department(2, null);
		List<Seller> sellerList = sellerDao.findByDepartment(dept);
		
		for (Seller sel : sellerList) {
			System.out.println(sel);
		}
		
		System.out.println(" ");
		
		System.out.println("=== TEST 3 - Seller, Find All ===");
		sellerList = sellerDao.findAll();
		
		for (Seller sel : sellerList) {
			System.out.println(sel);
		}
		
		System.out.println(" ");

		System.out.println("=== TEST 4 - Seller, Insert ===");
		Seller seller2 = new Seller(null, "Giulia de Sales", "gboechat@gmail.com", new Date(), 3000.0, dept);
		//sellerDao.insert(seller2);
		System.out.println("Inserted ! New id: " + seller2.getId());

		System.out.println(" ");
		
		System.out.println("=== TEST 5 - Seller, Update ===");
		Seller seller3 = sellerDao.findById(1);
		seller3.setName("Martha Wayne");
		sellerDao.update(seller3);
		System.out.println("Update completed!");
		
		System.out.println(" ");
		
		System.out.println("=== TEST 6 - Seller, Delete ===");
		System.out.println("Which seller do you want to delete?");
		int seller4 = sc.nextInt();
		sellerDao.deleteById(seller4);
		System.out.println("Seller deleted!");

		sc.close();
	}

}
