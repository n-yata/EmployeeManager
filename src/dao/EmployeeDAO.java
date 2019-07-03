package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import beans.Employee;

public class EmployeeDAO {
	private final String dbDriver = "org.h2.Driver";
	private final String dbUrl = "jdbc:h2:~/employeemanager";
	private final String dbUser = "sa";
	private final String dbPassword = "sa";

	public ArrayList<Employee> selectAll() {
		String sql = "SELECT * FROM EMPLOYEE";
		ArrayList<Employee> employees = new ArrayList<>();

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (
				Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt("ID"));
				e.setEmpId(rs.getString("EMPID"));
				e.setName(rs.getString("NAME"));
				e.setAge(rs.getInt("AGE"));
				e.setGender(rs.getString("GENDER"));
				e.setPhotoId(rs.getInt("PHOTOID"));
				e.setZip(rs.getString("ZIP"));
				e.setPref(rs.getInt("PREF"));
				e.setAddress(rs.getString("ADDRESS"));
				e.setPostId(rs.getInt("POSTID"));
				e.setEntDate(rs.getDate("ENTDATE"));
				e.setRetDate(rs.getDate("RETDATE"));
				employees.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return employees;
	}

	public Employee selectById(int id) {
		String sql = "SELECT * FROM EMPLOYEE WHERE ID = " + id + "";
		Employee employee = new Employee();

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (
				Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				employee.setId(rs.getInt("ID"));
				employee.setEmpId(rs.getString("EMPID"));
				employee.setName(rs.getString("NAME"));
				employee.setAge(rs.getInt("AGE"));
				employee.setGender(rs.getString("GENDER"));
				employee.setPhotoId(rs.getInt("PHOTOID"));
				employee.setZip(rs.getString("ZIP"));
				employee.setPref(rs.getInt("PREF"));
				employee.setAddress(rs.getString("ADDRESS"));
				//保留
				employee.setPostId(rs.getInt("POSTID"));
				employee.setEntDate(rs.getDate("ENTDATE"));
				employee.setRetDate(rs.getDate("RETDATE"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return employee;
	}

	public ArrayList<Employee> searchEmp(int postId, String empId, String name){
		String sql = "SELECT * FROM EMPLOYEE WHERE ";
		ArrayList<String> msg = new ArrayList<>();
		if(postId != 0) {
				msg.add("POSTID = " + postId);
		}
		if(!empId.equals("")) {
				msg.add("EMPID = '" + empId + "'");
		}
		if(!name.equals("")) {
				msg.add("NAME LIKE '%" + name + "%'");
		}
		sql = sql + String.join(" AND ", msg);
//		System.out.println(sql);

		ArrayList<Employee> employees = new ArrayList<>();

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (
				Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt("ID"));
				e.setEmpId(rs.getString("EMPID"));
				e.setName(rs.getString("NAME"));
				e.setAge(rs.getInt("AGE"));
				e.setGender(rs.getString("GENDER"));
				e.setPhotoId(rs.getInt("PHOTOID"));
				e.setZip(rs.getString("ZIP"));
				e.setPref(rs.getInt("PREF"));
				e.setAddress(rs.getString("ADDRESS"));
				e.setPostId(rs.getInt("POSTID"));
				e.setEntDate(rs.getDate("ENTDATE"));
				e.setRetDate(rs.getDate("RETDATE"));
				employees.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return employees;
	}

	public boolean insert(Employee employee) {
		String sql = "INSERT INTO EMPLOYEE(EMPID, NAME, AGE, GENDER, PHOTOID, ZIP, PREF, ADDRESS, POSTID,"
						+ "ENTDATE, RETDATE)"
							+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (
				Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setString(1, employee.getEmpId());
			pstmt.setString(2, employee.getName());
			pstmt.setInt(3, employee.getAge());
			pstmt.setString(4, employee.getGender());
			pstmt.setInt(5, employee.getPhotoId());
			pstmt.setString(6, employee.getZip());
			pstmt.setInt(7, employee.getPref());
			pstmt.setString(8, employee.getAddress());
			if(employee.getPostId() == 0) {
				pstmt.setNull(9, Types.NULL);
			}else {
				pstmt.setInt(9, employee.getPostId());
			}
			pstmt.setDate(10, employee.getEntDate());
			pstmt.setDate(11, employee.getRetDate());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM EMPLOYEE WHERE ID = ?";

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (
				Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean update(Employee employee) {
		String sql = "UPDATE EMPLOYEE SET EMPID = ?, NAME = ?, AGE = ?, GENDER = ?, PHOTOID = ?,"
						+ "ZIP = ?, PREF = ?, ADDRESS = ?, POSTID = ?, ENTDATE = ?, RETDATE = ?"
							+ "WHERE ID = ?";

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (
				Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setString(1, employee.getEmpId());
			pstmt.setString(2, employee.getName());
			pstmt.setInt(3, employee.getAge());
			pstmt.setString(4, employee.getGender());
			pstmt.setInt(5, employee.getPhotoId());
			pstmt.setString(6, employee.getZip());
			pstmt.setInt(7, employee.getPref());
			pstmt.setString(8, employee.getAddress());
			if(employee.getPostId() == 0) {
				pstmt.setNull(9, Types.NULL);
			}else {
				pstmt.setInt(9, employee.getPostId());
			}
			pstmt.setDate(10, employee.getEntDate());
			pstmt.setDate(11, employee.getRetDate());
			pstmt.setInt(12, employee.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
