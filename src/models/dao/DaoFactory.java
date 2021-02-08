package models.dao;

import models.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDAO(){
		return new SellerDaoJDBC();
	}
}
