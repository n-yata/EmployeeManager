package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Post;

public class PostDAO {
	private final String dbDriver = "org.h2.Driver";
	private final String dbUrl = "jdbc:h2:~/employeemanager";
	private final String dbUser = "sa";
	private final String dbPassword = "sa";

	public ArrayList<Post> selectAll() {
		String sql = "SELECT * FROM POST";
		ArrayList<Post> posts = new ArrayList<>();

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
				Post p = new Post();
				p.setId(rs.getInt("ID"));
				p.setName(rs.getString("NAME"));
				posts.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return posts;
	}

	public Post selectById(int id) {
		String sql = "SELECT * FROM POST WHERE ID = " + id + "";
		Post post = new Post();

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
				post.setId(rs.getInt("ID"));
				post.setName(rs.getString("NAME"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return post;
	}

	public boolean insert(Post post) {
		String sql = "INSERT INTO POST(NAME) VALUES(?)";

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (
				Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setString(1, post.getName());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM POST WHERE ID = ?";

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

	public boolean update(Post post) {
		String sql = "UPDATE POST SET NAME = ? WHERE ID = ?";

		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (
				Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setString(1, post.getName());
			pstmt.setInt(2, post.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
