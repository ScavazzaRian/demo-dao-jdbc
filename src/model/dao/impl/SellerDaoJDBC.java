package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
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

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("ID"));
		obj.setName(rs.getString("NAME"));
		obj.setEmail(rs.getString("EMAIL"));
		obj.setSalary(rs.getDouble("BASESALARY"));
		obj.setBirthDate(rs.getObject("BIRTHDATE", LocalDate.class));
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DEPARTMENTID"));
		dep.setName(rs.getString("DEPNAME"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT S.*, D.NAME AS DEPNAME FROM SELLER S INNER JOIN DEPARTMENT D ON S.DEPARTMENTID = D.ID");
			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				// Checo se meu MAP department existe
				Department dep = map.get(rs.getInt("DEPARTMENTID"));

				// Se nao existir crio o obj department e adiciono no map
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DEPARTMENTID"), dep);
				}
				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		} catch (Exception e) {
			throw new DbException(e.getMessage());
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			List<Seller> list = new ArrayList<>();
			st = conn.prepareStatement(
					"SELECT S.*, D.NAME AS DEPNAME FROM SELLER S INNER JOIN DEPARTMENT D ON S.DEPARTMENTID = D.ID WHERE D.ID = ?");
			st.setInt(1, department.getId());

			rs = st.executeQuery();

			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				Department dep = map.get(rs.getInt("DEPARTMENTID"));

				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DEPARTMENTID"), dep);
				}

				Seller obj = instantiateSeller(rs, dep);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}
}
