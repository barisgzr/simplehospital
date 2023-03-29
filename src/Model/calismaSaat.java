package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class calismaSaat {
	private int id,doktor_id;
	private String doktor_name,calismasaat,durum;
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	
	public calismaSaat(int id, int doktor_id, String doktor_name, String calismasaat, String durum) {
		this.id = id;
		this.doktor_id = doktor_id;
		this.doktor_name = doktor_name;
		this.calismasaat = calismasaat;
		this.durum = durum;
	}
	
	public calismaSaat() {}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getDoktor_id() {
		return doktor_id;
	}


	public void setDoktor_id(int doktor_id) {
		this.doktor_id = doktor_id;
	}


	public String getDoktor_name() {
		return doktor_name;
	}


	public void setDoktor_name(String doktor_name) {
		this.doktor_name = doktor_name;
	}


	public String getCalismasaat() {
		return calismasaat;
	}


	public void setCalismasaat(String calismasaat) {
		this.calismasaat = calismasaat;
	}


	public String getDurum() {
		return durum;
	}


	public void setDurum(String durum) {
		this.durum = durum;
	}
	
	public ArrayList<calismaSaat> getcalismaSaatList(int doktor_id) throws SQLException {
		ArrayList<calismaSaat> list = new ArrayList<>();
		calismaSaat obj;
		try {
			Connection con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM calismasaatleri WHERE durum = 'a' AND doktor_id = " + doktor_id);
			while (rs.next()) {
				obj = new calismaSaat();
				obj.setId(rs.getInt("id"));
				obj.setDoktor_id(rs.getInt("doktor_id"));
				obj.setDoktor_name(rs.getString("doktor_name"));
				obj.setDurum(rs.getString("durum"));
				obj.setCalismasaat(rs.getString("calismasaat"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;	
	}
	
}
