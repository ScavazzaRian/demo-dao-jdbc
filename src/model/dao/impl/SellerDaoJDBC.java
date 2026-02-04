package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO {
	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
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
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT S.*, D.NAME AS DEPNAME FROM SELLER S INNER JOIN DEPARTMENT D ON D.ID = S.DEPARTMENTID WHERE S.ID = ?");
			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("DEPARTMENTID"));
				dep.setName(rs.getString("DEPNAME"));
				Seller obj = new Seller();
				obj.setId(rs.getInt("ID"));
				obj.setName(rs.getString("NAME"));
				obj.setEmail(rs.getString("EMAIL"));
				obj.setSalary(rs.getDouble("BASESALARY"));
				obj.setBirthDate(rs.getObject("BIRTHDATE", LocalDate.class));
				obj.setDepartment(dep);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
