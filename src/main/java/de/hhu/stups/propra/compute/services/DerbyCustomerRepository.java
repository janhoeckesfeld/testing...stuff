package de.hhu.stups.propra.compute.services;

import de.hhu.stups.propra.compute.oldPJ.Customers;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DerbyCustomerRepository implements Customers {
	Connection conn;

	public DerbyCustomerRepository() {
		connectToDerby();
	}

	public void connectToDerby() {
		String dbUrl = "jdbc:derby:kunden;create=true";
		try {
			conn = DriverManager.getConnection(dbUrl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<String> getMailAddresses() {
		ArrayList<String> res = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users");
			while (rs.next()) {
				String mail = rs.getString("mail");
				res.add(mail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void addMailAddress(String adr) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (mail) VALUES ?");
			stmt.setString(1, adr);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeMailAddress(String adr) {
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE mail=?");
			stmt.setString(1, adr);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initDatabase() {
		try {
			Statement stmt = conn.createStatement();
			System.out.println("Creating table");
			stmt.execute("CREATE TABLE users(mail VARCHAR(200))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
