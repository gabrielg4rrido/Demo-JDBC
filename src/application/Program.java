package application;

import java.util.Date;

import models.entities.Department;
import models.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Department d1 = new Department(1, "Computers");
		Seller s1 = new Seller(1, "Gabriel Garrido",
								"gabrielgarridoag@gmail.com",
								new Date(),
								2000.0,
								d1);
		System.out.println(s1);
	}

}