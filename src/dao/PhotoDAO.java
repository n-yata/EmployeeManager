package dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import beans.Photo;

public class PhotoDAO {
	private final String dbDriver = "org.h2.Driver";
	private final String dbUrl = "jdbc:h2:~/employeemanager";
	private final String dbUser = "sa";
	private final String dbPassword = "sa";

	public Photo selectById(int id) {
		String sql = "SELECT * FROM PHOTO WHERE ID = " + id;
		Photo photo = new Photo();

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
				photo.setId(rs.getInt("ID"));
				photo.setContentType(rs.getString("CONTENTTYPE"));
				photo.setPhoto(rs.getBinaryStream("PHOTO"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return photo;
	}

	public int insert(Photo photo) throws SQLException{
		String sql = "INSERT INTO PHOTO(CONTENTTYPE, PHOTO) VALUES(?, ?)";

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, photo.getContentType());
			pstmt.setBinaryStream(2, new ByteArrayInputStream(photo.getPhoto()));
			int count = pstmt.executeUpdate();
			if(count == 1) {
				ResultSet rs = pstmt.getGeneratedKeys();
				while(rs.next()) {
					int photoId = rs.getInt(1);
					System.out.println(photoId);
					return rs.getInt(1);
				}
			}
		throw new SQLException();
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM PHOTO WHERE ID = ?";

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

	public boolean update(Photo photo) {
		String sql = "UPDATE PHOTO SET CONTENTTYPE = ?, PHOTO = ? WHERE ID = ?";

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (
				Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setString(1, photo.getContentType());
			pstmt.setBinaryStream(2, new ByteArrayInputStream(photo.getPhoto()));
			pstmt.setInt(3, photo.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
