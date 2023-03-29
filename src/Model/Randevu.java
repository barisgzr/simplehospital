package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Randevu {

	private int id,doktorID,hastaID;
	private String doktorName,hastaName,randevuSaat;
	
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public Randevu(int id, int doktorID, int hastaID, String doktorName, String hastaName, String randevuSaat) {
		super();
		this.id = id;
		this.doktorID = doktorID;
		this.hastaID = hastaID;
		this.doktorName = doktorName;
		this.hastaName = hastaName;
		this.randevuSaat = randevuSaat;
	}
	
	public Randevu() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoktorID() {
		return doktorID;
	}

	public void setDoktorID(int doktorID) {
		this.doktorID = doktorID;
	}

	public int getHastaID() {
		return hastaID;
	}

	public void setHastaID(int hastaID) {
		this.hastaID = hastaID;
	}

	public String getDoktorName() {
		return doktorName;
	}

	public void setDoktorName(String doktorName) {
		this.doktorName = doktorName;
	}

	public String getHastaName() {
		return hastaName;
	}

	public void setHastaName(String hastaName) {
		this.hastaName = hastaName;
	}

	public String getRandevuSaat() {
		return randevuSaat;
	}

	public void setRandevuSaat(String randevuSaat) {
		this.randevuSaat = randevuSaat;
	}
	
	
	public ArrayList<Randevu> getHastaRandevuList(int hasta_id) throws SQLException {
		ArrayList<Randevu> list = new ArrayList<>();
		Randevu obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM randevu WHERE hasta_id = " + hasta_id);
			while (rs.next()) {
				obj = new Randevu();
				obj.setId(rs.getInt("id"));
				obj.setDoktorID(rs.getInt("doktor_id"));
				obj.setDoktorName(rs.getString("doktor_name"));
				obj.setHastaID(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("hasta_name"));
				obj.setRandevuSaat(rs.getString("randevu_saat"));
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
	
	public ArrayList<Randevu> getDoktorRandevuList(int doktor_id) throws SQLException {
		ArrayList<Randevu> list = new ArrayList<>();
		Randevu obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM randevu WHERE doktor_id = " + doktor_id);
			while (rs.next()) {
				obj = new Randevu();
				obj.setId(rs.getInt("id"));
				obj.setDoktorID(rs.getInt("doktor_id"));
				obj.setDoktorName(rs.getString("doktor_name"));
				obj.setHastaID(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("hasta_name"));
				obj.setRandevuSaat(rs.getString("randevu_saat"));
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
	
}
