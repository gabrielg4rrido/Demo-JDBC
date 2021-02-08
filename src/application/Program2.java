package application;

import java.util.ArrayList;
import java.util.List;

import models.dao.DaoFactory;
import models.dao.DepartmentDao;
import models.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		System.out.println("=== TEST 1 - Depto, Insert");
		DepartmentDao deptoDao = DaoFactory.createDeptoDAO();
		
		Department dept1 = new Department(null, "Science");
		Department dept2 = new Department(null, "Architecture");
		//deptoDao.insert(dept1);
		//deptoDao.insert(dept2);

		System.out.println("\n=== TEST 2 - Depto, Delete");
		//deptoDao.deleteById(6);
		
		System.out.println("\n=== TEST 3 - Depto, Find By Id");
		Department dept3 = deptoDao.findById(1);
		System.out.println(dept3);
		
		System.out.println("\n=== TEST 4 - Depto, Find All");
		List<Department> dept4 = deptoDao.findAll();
		
		for (Department department : dept4) {
			System.out.println(department);
		}
		
		System.out.println("\n=== TEST 5 - Depto, Update");
		Department dept5 = deptoDao.findById(5);
		dept5.setName("Science, bitch!");
		deptoDao.update(dept5);
		System.out.println(dept5);
		
	}

}
