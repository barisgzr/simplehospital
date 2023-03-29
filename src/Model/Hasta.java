package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.text.StyledEditorKit.BoldAction;

import Helper.Helper;

public class Hasta extends User {
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Hasta() {
	}

	public Hasta(int id, String tcno, String name, String password) {
		super(id, tcno, name, password);
	}

	public boolean kayit(String tcno, String password, String name) throws SQLException {
		boolean key = false;
		boolean kontrol = false;
		String query = "INSERT INTO hasta" + "(tcno,password,name) VALUES" + "(?,?,?)";

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM hasta WHERE tcno = '" + tcno + "'");

			while (rs.next()) {
				kontrol = true;
				Helper.showMessage("Bu TC Numarasına ait daha önceden yapılmış bir kayıt bulunmaktadır!");
				break;
			}

			if (kontrol == false) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setString(1, tcno);
				preparedStatement.setString(2, password);
				preparedStatement.setString(3, name);
				preparedStatement.executeUpdate();
				key = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return key;
	}

	public boolean randevuAl(int doktor_id, int hasta_id, String doktor_name, String hasta_name, String tarih)
			throws SQLException {
		boolean key = false;

		String query = "INSERT INTO randevu" + "(doktor_id,doktor_name,hasta_id,hasta_name,randevu_saat) VALUES" + "(?,?,?,?,?)";

		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, doktor_id);
			preparedStatement.setString(2, doktor_name);
			preparedStatement.setInt(3, hasta_id);
			preparedStatement.setString(4, hasta_name);
			preparedStatement.setString(5, tarih);
			preparedStatement.executeUpdate();
			key = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return key;
	}
	
	public boolean durumDegis(int doktor_id, String tarih)
			throws SQLException {
		boolean key = false;

		String query = "UPDATE calismasaatleri SET durum = ? WHERE doktor_id = ? AND calismasaat = ?";

		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "p");
			preparedStatement.setInt(2, doktor_id);
			preparedStatement.setString(3, tarih);
			preparedStatement.executeUpdate();
			key = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return key;
	}
	

}
