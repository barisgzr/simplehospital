package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Hasta;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel register_dispanel;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JPasswordField fld_pass;
	private Hasta hasta = new Hasta();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 350);
		register_dispanel = new JPanel();
		register_dispanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(register_dispanel);
		register_dispanel.setLayout(null);
		
		fld_name = new JTextField();
		fld_name.setFont(new Font("FreeSans", Font.PLAIN, 17));
		fld_name.setColumns(10);
		fld_name.setBounds(12, 58, 322, 29);
		register_dispanel.add(fld_name);
		
		JLabel lbl_name = new JLabel("Adı Soyadı");
		lbl_name.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_name.setBounds(12, 29, 135, 29);
		register_dispanel.add(lbl_name);
		
		fld_tcno = new JTextField();
		fld_tcno.setFont(new Font("FreeSans", Font.PLAIN, 17));
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(12, 128, 322, 29);
		register_dispanel.add(fld_tcno);
		
		JLabel lbl_tcno = new JLabel("T.C Numarası");
		lbl_tcno.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_tcno.setBounds(12, 99, 135, 29);
		register_dispanel.add(lbl_tcno);
		
		JLabel lbl_sifre = new JLabel("Şifre");
		lbl_sifre.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_sifre.setBounds(12, 163, 135, 29);
		register_dispanel.add(lbl_sifre);
		
		fld_pass = new JPasswordField();
		fld_pass.setFont(new Font("FreeSans", Font.PLAIN, 17));
		fld_pass.setBounds(12, 193, 322, 29);
		register_dispanel.add(fld_pass);
		
		JButton btn_kayitol = new JButton("Kayıt Ol");
		btn_kayitol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_tcno.getText().length()==0 || fld_pass.getText().length()==0 || fld_name.getText().length()==0) {
					Helper.showMessage("bosluklaridoldur");
			}else {
				boolean control;
				try {
					control = hasta.kayit(fld_tcno.getText(),fld_pass.getText(), fld_name.getText());
					if (control) {
						Helper.showMessage("basarili");
						LoginGUI loginGUI = new LoginGUI();
						loginGUI.setVisible(true);
						dispose();
					}else {
						Helper.showMessage("hata");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	});
		btn_kayitol.setFont(new Font("FreeSans", Font.BOLD, 17));
		btn_kayitol.setBounds(12, 234, 322, 40);
		register_dispanel.add(btn_kayitol);
		
		JButton btn_geridon = new JButton("Geri Dön");
		btn_geridon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI loginGUI = new LoginGUI();
				loginGUI.setVisible(true);
				dispose();
			}
		});
		btn_geridon.setFont(new Font("FreeSans", Font.BOLD, 17));
		btn_geridon.setBounds(12, 290, 322, 40);
		register_dispanel.add(btn_geridon);
	}
}
