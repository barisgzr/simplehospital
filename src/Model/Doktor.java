package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Doktor extends User {
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public Doktor() {
		super();
	}
	public Doktor(int id, String tcno, String name, String password) {
		super(id, tcno, name, password);
	}
	
	public boolean calismaSaat(int doktor_id, String doktor_name, String calismasaat) throws SQLException{
		boolean key = false;
		int count = 0;
		String query = "INSERT INTO calismasaatleri" + "(doktor_id,doktor_name,calismasaat) VALUES" + "(?,?,?)";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM calismasaatleri WHERE durum = 'a' AND doktor_id = " + doktor_id + " AND calismasaat ='" + calismasaat + "'");
			
			while (rs.next()) {
				count++;
				break;
			}
			
			if (count == 0) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, doktor_id);
				preparedStatement.setString(2,doktor_name);
				preparedStatement.setString(3,calismasaat);
				preparedStatement.executeUpdate();
				key = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return key;
	}
	
	public ArrayList<calismaSaat> getcalismaSaatList(int doktor_id) throws SQLException {
		ArrayList<calismaSaat> list = new ArrayList<>();
		calismaSaat obj;
		try {
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
	
	public boolean tarihSil(int id) throws SQLException {
		String query = "DELETE FROM calismasaatleri WHERE id = ?";
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
	
	
}
