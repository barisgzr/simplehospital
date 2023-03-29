package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Klinik {

	private int id;
	private String pkname;

	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Klinik() {
	}

	public Klinik(int id, String pkname) {
		super();
		this.id = id;
		this.pkname = pkname;
	}
	
	public ArrayList<Klinik> getKlinikList() throws SQLException {
		ArrayList<Klinik> list = new ArrayList<>();
		Klinik obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM klinik");
			while (rs.next()) {
				obj = new Klinik();
				obj.setId(rs.getInt("id"));
				obj.setPkname(rs.getString("name"));
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}finally {
			st.close();
			rs.close();
			con.close();
		}
		return list;
	}
	
	public Klinik getFetch(int id) {
		Connection con = conn.connDb();
		Klinik cKlinik = new Klinik();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM klinik WHERE id =" + id);
			while (rs.next()) {
				cKlinik.setId(rs.getInt("id"));
				cKlinik.setPkname(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cKlinik;
		 
	}
	
	public boolean klinikEkle(String name) throws SQLException {
		String query = "INSERT INTO klinik" + "(name) VALUES" + "(?)";
		boolean key = false;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
	
	public boolean klinikCikar(int id) throws SQLException {
		String query = "DELETE FROM klinik WHERE id = ?";
		boolean key = false;
		Connection con = conn.connDb();
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

	public boolean klinikGuncelle(int id, String name) throws SQLException {
		String query = "UPDATE klinik SET name = ? WHERE id = ?";
		boolean key = false;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	} 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPkname() {
		return pkname;
	}

	public void setPkname(String pkname) {
		this.pkname = pkname;
	}
	
	public ArrayList<User> getKlinikDoctorList(int klinik_id) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			Connection con = conn.connDb();
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
