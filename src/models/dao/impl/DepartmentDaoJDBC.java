package models.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import models.dao.DepartmentDao;
import models.entities.Department;


public class DepartmentDaoJDBC implements DepartmentDao {

private Connection connect;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.connect = conn;
	}
	
	@Override
	public void insert(Department dep) {
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement("INSERT INTO department "
											+"(Name) "
											+ "VALUES "
											+ "(?)",
											Statement.RETURN_GENERATED_KEYS);
		
			stmt.setString(1, dep.getName());
			
			int rowsAffected = stmt.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					dep.setId(id);
				}
			System.out.println("New department!");
			} else {
				throw new DBException("Unexpected error! No rows affected.");
			}
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(stmt);
		}
	}

	@Override
	public void update(Department dep) {
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement("UPDATE department "
											+"SET Name = ? "
											+ "WHERE Id = ?");
		
			stmt.setString(1, dep.getName());
			stmt.setInt(2, dep.getId());
			
			stmt.executeUpdate();
			System.out.println("Department updated!");
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(stmt);
		}	
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement stmt = null;
		
		try {
			stmt = connect.prepareStatement("DELETE FROM department "
											+ "WHERE Id = ?");
		
			stmt.setInt(1, id);
			int rowsAffected = stmt.executeUpdate();
		
			if(rowsAffected == 0) {
				throw new DBException("Invalid id. No rows affected.");
			}
			
			System.out.println("Department deleted!");
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeStatement(stmt);
		}		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connect.prepareStatement("SELECT * FROM department "
										+ "WHERE department.Id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				Department dep = instantiateDepartment(rs);			
				return dep;
			}
			return null;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(stmt);
		}

	}

	@Override
	public List<Department> findAll() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connect.prepareStatement("SELECT * "
										+ "FROM department "
										+ "ORDER BY Id");
			rs = stmt.executeQuery();
			
			List<Department> deps = new ArrayList<>();
			Map<Integer, Department> mapDeps = new HashMap<>();
			
			while(rs.next()) {
				Department dep = mapDeps.get(rs.getInt("Id"));
				
				if(dep == null) {
					dep = instantiateDepartment(rs);
					mapDeps.put(rs.getInt("Id"), dep);
					deps.add(dep);
				}
				
			}
			return deps;
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(stmt);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		
		dep.setName(rs.getString("Name"));
		dep.setId(rs.getInt("Id"));
		
		return dep;
	}

}
