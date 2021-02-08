package application;

import java.util.List;

import models.dao.DaoFactory;
import models.dao.SellerDao;
import models.entities.Department;
import models.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDAO();
		
		System.out.println("=== TEST 1 - Seller, Find By Id ===");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("");
		
		System.out.println("=== TEST 2 - Seller, Find By Department");
		Department dept = new Department(2, null);
		List<Seller> sellerList = sellerDao.findByDepartment(dept);
		
		for (Seller sel : sellerList) {
			System.out.println(sel);
		}
		
		System.out.println(" ");
		
		System.out.println("=== TEST 3 - Seller, Find All");
		sellerList = sellerDao.findAll();
		
		for (Seller sel : sellerList) {
			System.out.println(sel);
		}
	}

}
