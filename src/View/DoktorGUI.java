package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import Model.Doktor;
import Model.Randevu;

import javax.swing.JTabbedPane;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DoktorGUI extends JFrame {

	private JPanel contentPane;
	private static Doktor doktor = new Doktor();
	private JTable table_randevu;
	private DefaultTableModel calismaSaatModel;
	private Object[] calismaSaatData = null;
	private JTable table_hastalarim;
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
					DoktorGUI frame = new DoktorGUI(doktor);
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
	public DoktorGUI(Doktor doktor) throws SQLException {

		calismaSaatModel = new DefaultTableModel();
		Object[] columnCalismaSaat = new Object[2];
		columnCalismaSaat[0] = "ID";
		columnCalismaSaat[1] = "Tarih";
		calismaSaatModel.setColumnIdentifiers(columnCalismaSaat);
		calismaSaatData = new Object[2];
		for (int i = 0; i < doktor.getcalismaSaatList(doktor.getId()).size(); i++) {
			calismaSaatData[0] = doktor.getcalismaSaatList(doktor.getId()).get(i).getId();
			calismaSaatData[1] = doktor.getcalismaSaatList(doktor.getId()).get(i).getCalismasaat();
			calismaSaatModel.addRow(calismaSaatData);
		}
		
		// Randevu Modeli
		randevuModel = new DefaultTableModel();
		Object[] columnRandevu = new Object[3];
		columnRandevu[0] = "ID";
		columnRandevu[1] = "Hasta";
		columnRandevu[2] = "Tarih";
		randevuModel.setColumnIdentifiers(columnRandevu);
		randevuData = new Object[3];
		for (int i = 0; i < randevu.getDoktorRandevuList(doktor.getId()).size(); i++) {
			randevuData[0] = randevu.getDoktorRandevuList(doktor.getId()).get(i).getId();
			randevuData[1] = randevu.getDoktorRandevuList(doktor.getId()).get(i).getHastaName();
			randevuData[2] = randevu.getDoktorRandevuList(doktor.getId()).get(i).getRandevuSaat();
			randevuModel.addRow(randevuData);

		}

		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lbl_hosgeldiniz = new JLabel("Hoşgeldiniz, Sayın " + doktor.getName());
		lbl_hosgeldiniz.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_hosgeldiniz.setBounds(12, 33, 377, 33);
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
		btn_cikisyap.setBounds(730, 36, 104, 28);
		contentPane.add(btn_cikisyap);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setBounds(12, 87, 822, 501);
		contentPane.add(tabbedPane);

		JPanel panel_calismaSaat = new JPanel();
		tabbedPane.addTab("Çalışma Saatleri", null, panel_calismaSaat, null);
		panel_calismaSaat.setLayout(null);

		JDateChooser select_date = new JDateChooser();
		select_date.setDateFormatString("d MMM, y");
		select_date.setBounds(12, 12, 175, 30);
		panel_calismaSaat.add(select_date);

		JComboBox select_time = new JComboBox();
		select_time.setFont(new Font("FreeSans", Font.BOLD, 14));
		select_time.setModel(new DefaultComboBoxModel(new String[] { "10:00", "10:30", "11:00", "11:30", "12:00",
				"12:30", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30" }));
		select_time.setBounds(199, 12, 80, 30);
		panel_calismaSaat.add(select_time);

		JButton btn_randevuEkle = new JButton("Ekle");
		btn_randevuEkle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String date = "";
				try {
					date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
				}
				if (date.length() == 0) {
					Helper.showMessage("Lütfen geçerli bir tarih giriniz!");
				} else {
					String time = " " + select_time.getSelectedItem().toString() + ":00";
					String selectedDate = date + time;
					boolean control;
					try {
						control = doktor.calismaSaat(doktor.getId(), doktor.getName(), selectedDate);
						if (control) {
							updateCalismasaatModel(doktor);
							Helper.showMessage("basarili");
						} else {
							Helper.showMessage("hata");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}

			}
		});
		btn_randevuEkle.setFont(new Font("FreeSans", Font.BOLD, 14));
		btn_randevuEkle.setBounds(291, 12, 100, 30);
		panel_calismaSaat.add(btn_randevuEkle);

		JScrollPane scroll_randevu = new JScrollPane();
		scroll_randevu.setBounds(12, 45, 793, 417);
		panel_calismaSaat.add(scroll_randevu);

		table_randevu = new JTable(calismaSaatModel);
		scroll_randevu.setViewportView(table_randevu);

		JButton btn_randevuSil = new JButton("Sil");
		btn_randevuSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_randevu.getSelectedRow();
				if (selectedRow >= 0) {
					String selectRow = table_randevu.getModel().getValueAt(selectedRow, 0).toString();
					int selectedRowID = Integer.parseInt(selectRow);
					boolean control;
					try {
						control = doktor.tarihSil(selectedRowID);
						if (control) {
							updateCalismasaatModel(doktor);
							Helper.showMessage("basarili");
						} else {
							Helper.showMessage("hata");
						}
					} catch (Exception e2) {
					}
				} else {
					Helper.showMessage("Lütfen bir tarih seçiniz!");
				}
			}
		});
		btn_randevuSil.setFont(new Font("FreeSans", Font.BOLD, 14));
		btn_randevuSil.setBounds(705, 12, 100, 30);
		panel_calismaSaat.add(btn_randevuSil);

		JPanel panel_doktorrandevu = new JPanel();
		tabbedPane.addTab("Hastalarım", null, panel_doktorrandevu, null);
		panel_doktorrandevu.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 793, 450);
		panel_doktorrandevu.add(scrollPane);

		table_hastalarim = new JTable(randevuModel);
		scrollPane.setViewportView(table_hastalarim);
	}

	public void updateCalismasaatModel(Doktor doktor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_randevu.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < doktor.getcalismaSaatList(doktor.getId()).size(); i++) {
			calismaSaatData[0] = doktor.getcalismaSaatList(doktor.getId()).get(i).getId();
			calismaSaatData[1] = doktor.getcalismaSaatList(doktor.getId()).get(i).getCalismasaat();
			calismaSaatModel.addRow(calismaSaatData);
		}
	}
}
