package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import models.dao.SellerDao;
import models.entities.Department;
import models.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection connect;
	
	public SellerDaoJDBC(Connection conn) {
		this.connect = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connect.prepareStatement("SELECT seller.*, department.Name as DepName "
										+ "FROM seller INNER JOIN department "
										+ "ON seller.DepartmentId = department.Id "
										+ "WHERE seller.Id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller sel = instantiateSeller(rs, dep);
				
				return sel;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(stmt);
		}
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connect.prepareStatement("SELECT seller.*, department.Name as DepName "
										+ "FROM seller INNER JOIN department "
										+ "ON seller.DepartmentId = department.Id "
										+ "ORDER BY Id");
			rs = stmt.executeQuery();
			
			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> mapDeps = new HashMap<>();
			
			while(rs.next()) {
				Department dep = mapDeps.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instantiateDepartment(rs);
					mapDeps.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller sel = instantiateSeller(rs, dep);
				sellers.add(sel);
				}
				return sellers;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(stmt);
	}

	}

	@Override
	public List<Seller> findByDepartment(Department dept) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connect.prepareStatement("SELECT seller.*, department.Name as DepName "
										+ "FROM seller INNER JOIN department "
										+ "ON seller.DepartmentId = department.Id "
										+ "WHERE Department.Id = ? "
										+ "ORDER BY Name");
			stmt.setInt(1, dept.getId());
			rs = stmt.executeQuery();
			
			List<Seller> sellers = new ArrayList<>();
			Map<Integer, Department> mapDeps = new HashMap<>();
			
			while(rs.next()) {
				Department dep = mapDeps.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instantiateDepartment(rs);
					mapDeps.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller sel = instantiateSeller(rs, dep);
				sellers.add(sel);
				}
				return sellers;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(stmt);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sel = new Seller();
		
		sel.setId(rs.getInt("Id"));
		sel.setName(rs.getString("Name"));
		sel.setEmail(rs.getString("Email"));
		sel.setBaseSalary(rs.getDouble("BaseSalary"));
		sel.setBirthDate(rs.getDate("BirthDate"));
		sel.setDepartment(dep);
		
		return sel;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		
		return dep;
	}
}
