package models.dao;

import db.DB;
import models.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDAO(){
		return new SellerDaoJDBC(DB.getConnection());
	}
}
