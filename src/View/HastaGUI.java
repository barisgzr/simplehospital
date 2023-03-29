package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.Hasta;
import Model.Klinik;
import Model.Randevu;
import Model.calismaSaat;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JTabbedPane;



import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class HastaGUI extends JFrame {

	private JPanel contentPane;
	private static Hasta hasta = new Hasta();
	private Klinik klinik = new Klinik();
	private JTable table_doktor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_calismasaat;
	private calismaSaat calismaSaat = new calismaSaat();
	private DefaultTableModel calismasaatModel;
	private Object[] calismasaatData = null;
	private int selectedDoktorID = 0;
	private String selectedDoktorName = null;
	private JTable table_randevulist;
	private DefaultTableModel randevuModel;
	private Object[] randevuData = null;
	private Randevu randevu = new Randevu();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public HastaGUI(Hasta hasta) throws SQLException {

		// Doktor Modeli
		doctorModel = new DefaultTableModel();
		Object[] columnDoctorName = new Object[2];
		columnDoctorName[0] = "ID";
		columnDoctorName[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(columnDoctorName);
		doctorData = new Object[2];

		// Çalışma Saat Modeli
		calismasaatModel = new DefaultTableModel();
		Object[] columnCalismaSaat = new Object[2];
		columnCalismaSaat[0] = "ID";
		columnCalismaSaat[1] = "Tarih";
		calismasaatModel.setColumnIdentifiers(columnCalismaSaat);
		calismasaatData = new Object[2];

		// Randevu Modeli
		randevuModel = new DefaultTableModel();
		Object[] columnRandevu = new Object[3];
		columnRandevu[0] = "ID";
		columnRandevu[1] = "Doktor";
		columnRandevu[2] = "Tarih";
		randevuModel.setColumnIdentifiers(columnRandevu);
		randevuData = new Object[3];
		for (int i = 0; i < randevu.getHastaRandevuList(hasta.getId()).size(); i++) {
			randevuData[0] = randevu.getHastaRandevuList(hasta.getId()).get(i).getId();
			randevuData[1] = randevu.getHastaRandevuList(hasta.getId()).get(i).getDoktorName();
			randevuData[2] = randevu.getHastaRandevuList(hasta.getId()).get(i).getRandevuSaat();
			randevuModel.addRow(randevuData);

		}

		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_hosgeldiniz = new JLabel("Hoşgeldiniz, Sayın " + hasta.getName());
		lbl_hosgeldiniz.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_hosgeldiniz.setBounds(12, 38, 377, 33);
		contentPane.add(lbl_hosgeldiniz);

		JButton btn_cikisyap = new JButton("Çıkış Yap");
		btn_cikisyap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_cikisyap.setFont(new Font("FreeSans", Font.BOLD, 14));
		btn_cikisyap.setBounds(730, 41, 104, 28);
		contentPane.add(btn_cikisyap);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(12, 83, 822, 501);
		contentPane.add(tabbedPane);

		JPanel panel_hastarandevu = new JPanel();
		tabbedPane.addTab("Randevu Sistemi", null, panel_hastarandevu, null);
		panel_hastarandevu.setLayout(null);

		JScrollPane scroll_doktor = new JScrollPane();
		scroll_doktor.setBounds(12, 31, 310, 431);
		panel_hastarandevu.add(scroll_doktor);

		table_doktor = new JTable(doctorModel);
		scroll_doktor.setViewportView(table_doktor);

		JLabel lblNewLabel = new JLabel("Doktor Listesi");
		lblNewLabel.setFont(new Font("FreeSans", Font.BOLD, 17));
		lblNewLabel.setBounds(12, 5, 135, 22);
		panel_hastarandevu.add(lblNewLabel);

		JLabel lbl_poliklinikadi = new JLabel("Poliklinik Adı");
		lbl_poliklinikadi.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_poliklinikadi.setBounds(334, 2, 135, 29);
		panel_hastarandevu.add(lbl_poliklinikadi);

		JComboBox select_klinik = new JComboBox();
		select_klinik.setFont(new Font("FreeSans", Font.BOLD, 15));
		select_klinik.setBounds(334, 31, 150, 40);
		select_klinik.addItem("--Poliklinik Seç--");
		for (int i = 0; i < klinik.getKlinikList().size(); i++) {
			select_klinik.addItem(new Item(klinik.getKlinikList().get(i).getId(), klinik.getKlinikList().get(i).getPkname()));
		}
		select_klinik.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_klinik.getSelectedIndex() != 0) {
					JComboBox cBox = (JComboBox) e.getSource();
					Item item = (Item) cBox.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) table_doktor.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < klinik.getKlinikDoctorList(item.getId()).size(); i++) {
							doctorData[0] = klinik.getKlinikDoctorList(item.getId()).get(i).getId();
							doctorData[1] = klinik.getKlinikDoctorList(item.getId()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doktor.getModel();
					clearModel.setRowCount(0);
				}
			}
		});
		panel_hastarandevu.add(select_klinik);

		JLabel lbl_doktorsec = new JLabel("Doktor Seç");
		lbl_doktorsec.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_doktorsec.setBounds(334, 104, 135, 29);
		panel_hastarandevu.add(lbl_doktorsec);

		JButton btn_doktorsec = new JButton("Seç");
		btn_doktorsec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doktor.getSelectedRow();
				if (row >= 0) {
					String valueString = table_doktor.getModel().getValueAt(row, 0).toString();
					int valueStringID = Integer.parseInt(valueString);
					DefaultTableModel clearModel = (DefaultTableModel) table_calismasaat.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < calismaSaat.getcalismaSaatList(valueStringID).size(); i++) {
							calismasaatData[0] = calismaSaat.getcalismaSaatList(valueStringID).get(i).getId();
							calismasaatData[1] = calismaSaat.getcalismaSaatList(valueStringID).get(i).getCalismasaat();
							calismasaatModel.addRow(calismasaatData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					table_calismasaat.setModel(calismasaatModel);
					selectedDoktorID = valueStringID;
					selectedDoktorName = table_doktor.getModel().getValueAt(row, 1).toString();

				} else {
					Helper.showMessage("Lütfen bir doktor seçiniz !");
				}
			}
		});
		btn_doktorsec.setFont(new Font("FreeSans", Font.BOLD, 17));
		btn_doktorsec.setBounds(334, 134, 150, 40);
		panel_hastarandevu.add(btn_doktorsec);

		JLabel lblNewLabel_1 = new JLabel("Doktor Listesi");
		lblNewLabel_1.setFont(new Font("FreeSans", Font.BOLD, 17));
		lblNewLabel_1.setBounds(495, 5, 135, 22);
		panel_hastarandevu.add(lblNewLabel_1);

		JScrollPane scroll_calismasaat = new JScrollPane();
		scroll_calismasaat.setBounds(495, 31, 310, 431);
		panel_hastarandevu.add(scroll_calismasaat);

		table_calismasaat = new JTable(calismasaatModel);
		scroll_calismasaat.setViewportView(table_calismasaat);

		JButton btn_randevual = new JButton("Randevu Al");
		btn_randevual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_calismasaat.getSelectedRow();
				if (selectedRow >= 0) {
					String dateValue = table_calismasaat.getModel().getValueAt(selectedRow, 1).toString();
					try {
						boolean kontrol = hasta.randevuAl(selectedDoktorID, hasta.getId(), selectedDoktorName,
								hasta.getName(), dateValue);
						if (kontrol) {
							hasta.durumDegis(selectedDoktorID, dateValue);
							updateCalismaSaat(selectedDoktorID);
							updateRandevuSaat(hasta.getId());
							Helper.showMessage("basarili");
						} else {
							Helper.showMessage("hata");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					Helper.showMessage("Lütfen bir tarih seçiniz!");
				}
			}
		});
		btn_randevual.setFont(new Font("FreeSans", Font.BOLD, 17));
		btn_randevual.setBounds(334, 235, 150, 40);
		panel_hastarandevu.add(btn_randevual);

		JLabel lbl_randevu = new JLabel("Randevu");
		lbl_randevu.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_randevu.setBounds(334, 205, 135, 29);
		panel_hastarandevu.add(lbl_randevu);

		JPanel panel_randevuliste = new JPanel();
		tabbedPane.addTab("Randevularım", null, panel_randevuliste, null);
		panel_randevuliste.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 793, 450);
		panel_randevuliste.add(scrollPane);

		table_randevulist = new JTable(randevuModel);
		scrollPane.setViewportView(table_randevulist);
	}

	public void updateCalismaSaat(int doktor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_calismasaat.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < klinik.getKlinikDoctorList(doktor_id).size(); i++) {
			doctorData[0] = klinik.getKlinikDoctorList(doktor_id).get(i).getId();
			doctorData[1] = klinik.getKlinikDoctorList(doktor_id).get(i).getName();
			doctorModel.addRow(doctorData);
		}
	}
	
	public void updateRandevuSaat(int hasta_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_randevulist.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < randevu.getHastaRandevuList(hasta_id).size(); i++) {
			randevuData[0] = randevu.getHastaRandevuList(hasta_id).get(i);
			randevuData[1] = randevu.getHastaRandevuList(hasta_id).get(i).getDoktorName();
			randevuData[2] = randevu.getHastaRandevuList(hasta_id).get(i).getRandevuSaat();
			randevuModel.addRow(randevuData);
		}
	}
}
