package View;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import Helper.*;
import Model.Bashekim;
import Model.Doktor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private JPanel login_dispanel;
	private JTextField field_hastatc;
	private JPasswordField field_hastapass;
	private JTextField field_bashekimtc;
	private JPasswordField field_bashekimpass;
	private JPasswordField field_doktorpass;
	private DBConnection conn = new DBConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		login_dispanel = new JPanel();
		login_dispanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(login_dispanel);
		login_dispanel.setLayout(null);
		
		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("logo.png")));
		lbl_logo.setBounds(214, 25, 64, 56);
		login_dispanel.add(lbl_logo);
		
		JLabel lblNewLabel = new JLabel("Hastane Yönetim Sistemi");
		lblNewLabel.setFont(new Font("FreeSans", Font.BOLD, 20));
		lblNewLabel.setBounds(124, 79, 254, 37);
		login_dispanel.add(lblNewLabel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 128, 472, 256);
		login_dispanel.add(tabbedPane);
		
		JPanel hastaGiris = new JPanel();
		hastaGiris.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Hasta Girişi", null, hastaGiris, null);
		hastaGiris.setLayout(null);
		
		JLabel lbl_hastatc = new JLabel("T.C Numaranız ");
		lbl_hastatc.setBounds(12, 28, 152, 24);
		lbl_hastatc.setFont(new Font("FreeSans", Font.BOLD, 20));
		hastaGiris.add(lbl_hastatc);
		
		JLabel lbl_hastasifre =new JLabel("Şifre");
		lbl_hastasifre.setFont(new Font("FreeSans", Font.BOLD, 20));
		lbl_hastasifre.setBounds(12, 103, 159, 24);
		hastaGiris.add(lbl_hastasifre);
		
		field_hastatc = new JTextField();
		field_hastatc.setFont(new Font("FreeSans", Font.PLAIN, 20));
		field_hastatc.setBounds(182, 26, 234, 34);
		hastaGiris.add(field_hastatc);
		field_hastatc.setColumns(10);
		
		field_hastapass = new JPasswordField();
		field_hastapass.setBounds(182, 101, 234, 34);
		hastaGiris.add(field_hastapass);
		
		JButton btn_hastaKayit = new JButton("Kayıt Ol");
		btn_hastaKayit.setFont(new Font("FreeSans", Font.BOLD, 15));
		btn_hastaKayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI registerGUI = new RegisterGUI();
				registerGUI.setVisible(true);
				dispose();
			}
		});
		btn_hastaKayit.setBounds(12, 158, 215, 59);
		hastaGiris.add(btn_hastaKayit);
		
		JButton btn_hastaGiris = new JButton("Giriş Yap");
		btn_hastaGiris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field_hastatc.getText().length()==0 || field_hastapass.getText().length()==0) {
					Helper.showMessage("bosluklaridoldur");
				}else {
					boolean kontrol = true;
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM hasta");
						while (rs.next()) {
							if(field_hastatc.getText().equals(rs.getString("tcno")) && field_hastapass.getText().equals(rs.getString("password"))) {
								Hasta hasta = new Hasta();
								hasta.setId(rs.getInt("id"));
								hasta.setPassword(rs.getString("password"));
								hasta.setTcno(rs.getString("tcno"));
								hasta.setName(rs.getString("name"));
								HastaGUI hastaGUI = new HastaGUI(hasta);
								hastaGUI.setVisible(true);
								dispose();
								kontrol = false;
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					if (kontrol) {
						Helper.showMessage("Böyle bir hasta bulunmamaktadır lütfen kayıt olunuz !");
					}
				}
			}
		});
		btn_hastaGiris.setFont(new Font("FreeSans", Font.BOLD, 15));
		btn_hastaGiris.setBounds(240, 158, 215, 59);
		hastaGiris.add(btn_hastaGiris);
		
		JPanel bashekimGiris = new JPanel();
		bashekimGiris.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Başhekim Giris", null, bashekimGiris, null);
		bashekimGiris.setLayout(null);
		
		JLabel lbl_bashekimtc = new JLabel("T.C Numaranız ");
		lbl_bashekimtc.setFont(new Font("FreeSans", Font.BOLD, 20));
		lbl_bashekimtc.setBounds(12, 28, 152, 24);
		bashekimGiris.add(lbl_bashekimtc);
		
		JLabel lbl_bashekimsifre = new JLabel("Şifre");
		lbl_bashekimsifre.setFont(new Font("FreeSans", Font.BOLD, 20));
		lbl_bashekimsifre.setBounds(12, 103, 159, 24);
		bashekimGiris.add(lbl_bashekimsifre);
		
		field_bashekimtc = new JTextField();
		field_bashekimtc.setFont(new Font("FreeSans", Font.PLAIN, 20));
		field_bashekimtc.setColumns(10);
		field_bashekimtc.setBounds(182, 26, 234, 34);
		bashekimGiris.add(field_bashekimtc);
		
		field_bashekimpass = new JPasswordField();
		field_bashekimpass.setBounds(182, 101, 234, 34);
		bashekimGiris.add(field_bashekimpass);
		
		JButton btn_bashekimGiris = new JButton("Giriş Yap");
		btn_bashekimGiris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field_bashekimtc.getText().length()==0 || field_bashekimpass.getText().length()==0) {
					Helper.showMessage("bosluklaridoldur");
				}else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM bashekim");
						while (rs.next()) {
							if(field_bashekimtc.getText().equals(rs.getString("tcno")) && field_bashekimpass.getText().equals(rs.getString("password"))) {
								Bashekim bhekim = new Bashekim();
								bhekim.setId(rs.getInt("id"));
								bhekim.setPassword(rs.getString("password"));
								bhekim.setTcno(rs.getString("tcno"));
								bhekim.setName(rs.getString("name"));
								BashekimGUI bhekimGUI = new BashekimGUI(bhekim);
								bhekimGUI.setVisible(true);
								dispose();
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_bashekimGiris.setFont(new Font("FreeSans", Font.BOLD, 15));
		btn_bashekimGiris.setBounds(12, 158, 443, 59);
		bashekimGiris.add(btn_bashekimGiris);
		
		JPanel doktorGiris = new JPanel();
		doktorGiris.setLayout(null);
		doktorGiris.setBackground(Color.WHITE);
		tabbedPane.addTab("Doktor Giriş", null, doktorGiris, null);
		
		JLabel lbl_doktortc = new JLabel("T.C Numaranız ");
		lbl_doktortc.setFont(new Font("FreeSans", Font.BOLD, 20));
		lbl_doktortc.setBounds(12, 28, 152, 24);
		doktorGiris.add(lbl_doktortc);
		
		JLabel lbl_doktorsifre = new JLabel("Şifre");
		lbl_doktorsifre.setFont(new Font("FreeSans", Font.BOLD, 20));
		lbl_doktorsifre.setBounds(12, 103, 159, 24);
		doktorGiris.add(lbl_doktorsifre);
		
		JTextField field_doktortc = new JTextField();
		field_doktortc.setFont(new Font("FreeSans", Font.PLAIN, 20));
		field_doktortc.setColumns(10);
		field_doktortc.setBounds(182, 26, 234, 34);
		doktorGiris.add(field_doktortc);
		
		field_doktorpass = new JPasswordField();
		field_doktorpass.setBounds(182, 101, 234, 34);
		doktorGiris.add(field_doktorpass);
		
		JButton btn_doktorGiris_1 = new JButton("Giriş Yap");
		btn_doktorGiris_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (field_doktortc.getText().length()==0 || field_doktorpass.getText().length()==0) {
				Helper.showMessage("bosluklaridoldur");
			}else {
				try {
					Connection con = conn.connDb();
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM doktor");
					while (rs.next()) {
						if(field_doktortc.getText().equals(rs.getString("tcno")) && field_doktorpass.getText().equals(rs.getString("password"))) {
							Doktor doktor = new Doktor();
							doktor.setId(rs.getInt("id"));
							doktor.setPassword(rs.getString("password"));
							doktor.setTcno(rs.getString("tcno"));
							doktor.setName(rs.getString("name"));
							DoktorGUI doktorGUI = new DoktorGUI(doktor);
							doktorGUI.setVisible(true);
							dispose();
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	});
		btn_doktorGiris_1.setFont(new Font("FreeSans", Font.BOLD, 15));
		btn_doktorGiris_1.setBounds(12, 158, 443, 59);
		doktorGiris.add(btn_doktorGiris_1);
	}
}
