package Model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bashekim extends User {
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Bashekim(int id, String tcno, String name, String password) {
		super(id, tcno, name, password);
	}
	
	public Bashekim() {}
	
	public ArrayList<User> getDoctorList() throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM doktor");
			while (rs.next()) {
				obj = new User(rs.getInt("id"),rs.getString("tcno"),rs.getString("name"),rs.getString("password"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;	
	}
	
	public boolean doktorEkle(String tcno, String password, String name) throws SQLException {
		String query = "INSERT INTO doktor" + "(tcno,password,name) VALUES" + "(?,?,?)";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, tcno);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public boolean doktorCikar(int id) throws SQLException {
		String query = "DELETE FROM doktor WHERE id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public boolean doktorGuncelle(int id, String tcno, String password, String name) throws SQLException {
		String query = "UPDATE doktor SET name = ?, tcno = ?, password = ? WHERE id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, tcno);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	} 
	
	public boolean calisanEkle(int doktor_id , int klinik_id) throws SQLException {
		String query = "INSERT INTO calisan" + "(klinik_id , doktor_id) VALUES" + "(?,?)";
		boolean key = false;
		int count = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM calisan WHERE klinik_id = " + klinik_id + " AND doktor_id = " + doktor_id);
			while (rs.next()) {
				count++;
			}
			if(count == 0) {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, klinik_id);
			preparedStatement.setInt(2, doktor_id);
			preparedStatement.executeUpdate();
			key = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public ArrayList<User> getKlinikDoctorList(int klinik_id) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT d.id, d.tcno, d.name, d.password FROM calisan c LEFT JOIN doktor d ON c.doktor_id = d.id WHERE klinik_id = " + klinik_id);
			while (rs.next()) {
				obj = new User(rs.getInt("d.id"),rs.getString("d.tcno"),rs.getString("d.name"),rs.getString("d.password"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;	
	}

}
