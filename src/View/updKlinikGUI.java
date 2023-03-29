package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Klinik;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class updKlinikGUI extends JFrame {

	private JPanel contentPane;
	private JTextField text_poliklinik;
	private static Klinik klinik;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					updKlinikGUI frame = new updKlinikGUI(klinik);
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
	public updKlinikGUI(Klinik klinik) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 225, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_poliklinikadi = new JLabel("Poliklinik Adı");
		lbl_poliklinikadi.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_poliklinikadi.setBounds(53, 23, 135, 29);
		contentPane.add(lbl_poliklinikadi);
		
		text_poliklinik = new JTextField();
		text_poliklinik.setFont(new Font("FreeSans", Font.PLAIN, 17));
		text_poliklinik.setColumns(10);
		text_poliklinik.setBounds(12, 52, 197, 29);
		text_poliklinik.setText(klinik.getPkname());
		contentPane.add(text_poliklinik);
		
		JButton btn_duzenleklinik = new JButton("Düzenle");
		btn_duzenleklinik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Helper.gabul("eminmisen")) {
					try {
						klinik.klinikGuncelle(klinik.getId(), text_poliklinik.getText());
						Helper.showMessage("basarili");
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_duzenleklinik.setFont(new Font("FreeSans", Font.BOLD, 17));
		btn_duzenleklinik.setBounds(12, 93, 197, 40);
		contentPane.add(btn_duzenleklinik);
	}
}
