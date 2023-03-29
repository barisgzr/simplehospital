package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Helper.Helper;
import Helper.Item;
import Model.*;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.JComboBox;

public class BashekimGUI extends JFrame {

	static Bashekim bashekim = new Bashekim();
	static Klinik klinik = new Klinik();
	private JPanel bashekim_dispanel;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JTextField fld_password;
	private JTextField fld_id;
	private JTable table_doctor;
	private DefaultTableModel doctorModel = null;
	private Object[] doctorData = null;
	private JTextField fld_poliklinikName;
	private DefaultTableModel klinikModel = null;
	private Object[] klinikData = null;
	private JTable table_klinik;
	private JPopupMenu klinikMenu;
	private JTable table_calisan;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public BashekimGUI(Bashekim bashekim) throws SQLException {
		//Doktor Modeli
		doctorModel = new DefaultTableModel();
		Object[] columnDoctorName = new Object[4];
		columnDoctorName[0] = "ID";
		columnDoctorName[1] = "Ad Soyad";
		columnDoctorName[2] = "TC Numarası";
		columnDoctorName[3] = "Şifre";
		doctorModel.setColumnIdentifiers(columnDoctorName);
		doctorData = new Object[4];
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);
		}
		
		//Klinik Modeli
		klinikModel = new DefaultTableModel();
		Object[] columnKlinikName = new Object[2];
		columnKlinikName[0] = "ID";
		columnKlinikName[1] = "Poliklinik Adı";
		klinikModel.setColumnIdentifiers(columnKlinikName);
		klinikData = new Object[2];
		for (int i = 0; i < klinik.getKlinikList().size(); i++) {
			klinikData[0] = klinik.getKlinikList().get(i).getId();
			klinikData[1] = klinik.getKlinikList().get(i).getPkname();
			klinikModel.addRow(klinikData);
		}
		
		//Calisan Model
		DefaultTableModel calisanModel = new DefaultTableModel();
		Object[] columnCalisan = new Object[2];
		columnCalisan[0] = "ID";
		columnCalisan[1] = "Ad Soyad";
		calisanModel.setColumnIdentifiers(columnCalisan);
		Object[] calisanData = new Object[2];
		
		
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		bashekim_dispanel = new JPanel();
		bashekim_dispanel.setBackground(new Color(238,238,238));
		bashekim_dispanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(bashekim_dispanel);
		bashekim_dispanel.setLayout(null);
		
		JLabel lbl_hosgeldiniz = new JLabel("Hoşgeldiniz, Sayın " + bashekim.getName());
		lbl_hosgeldiniz.setBounds(12, 29, 377, 33);
		lbl_hosgeldiniz.setFont(new Font("FreeSans", Font.BOLD, 17));
		bashekim_dispanel.add(lbl_hosgeldiniz);
		
		JButton btn_cikisyap = new JButton("Çıkış Yap");
		btn_cikisyap.setBounds(730, 32, 104, 28);
		btn_cikisyap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btn_cikisyap.setFont(new Font("FreeSans", Font.BOLD, 14));
		bashekim_dispanel.add(btn_cikisyap);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(255, 255, 255));
		tabbedPane.setBounds(12, 74, 822, 510);
		bashekim_dispanel.add(tabbedPane);
		
		JPanel doctorYonetim = new JPanel();
		tabbedPane.addTab("Doktor Yönetimi", null, doctorYonetim, null);
		doctorYonetim.setLayout(null);
		
		fld_name = new JTextField();
		fld_name.setBounds(638, 53, 150, 29);
		fld_name.setFont(new Font("FreeSans", Font.PLAIN, 17));
		doctorYonetim.add(fld_name);
		fld_name.setColumns(10);
		
		JLabel lbl_name = new JLabel("Adı Soyadı");
		lbl_name.setBounds(638, 24, 135, 29);
		lbl_name.setFont(new Font("FreeSans", Font.BOLD, 17));
		doctorYonetim.add(lbl_name);
		
		fld_tcno = new JTextField();
		fld_tcno.setFont(new Font("FreeSans", Font.PLAIN, 17));
		fld_tcno.setBounds(638, 125, 150, 29);
		fld_tcno.setColumns(10);
		doctorYonetim.add(fld_tcno);
		
		JLabel lbl_tcno = new JLabel("T.C Numarası");
		lbl_tcno.setBounds(638, 94, 135, 29);
		lbl_tcno.setFont(new Font("FreeSans", Font.BOLD, 17));
		doctorYonetim.add(lbl_tcno);
		
		fld_password = new JTextField();
		fld_password.setFont(new Font("FreeSans", Font.PLAIN, 17));
		fld_password.setBounds(638, 195, 150, 29);
		fld_password.setColumns(10);
		doctorYonetim.add(fld_password);
		
		JLabel lbl_password = new JLabel("Şifre");
		lbl_password.setBounds(638, 166, 135, 29);
		lbl_password.setFont(new Font("FreeSans", Font.BOLD, 17));
		doctorYonetim.add(lbl_password);
		
		JButton btn_ekle = new JButton("Ekle");
		btn_ekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_name.getText().length()==0 || fld_password.getText().length()==0 || fld_tcno.getText().length()==0) {
					Helper.showMessage("bosluklaridoldur");
				}else {
					try {
						boolean control = bashekim.doktorEkle(fld_tcno.getText(), fld_password.getText(), fld_name.getText());
						if(control) {
							fld_name.setText(null);
							fld_tcno.setText(null);
							fld_password.setText(null);
							updateDoctorModel();
							Helper.showMessage("basarili");
						}
					} catch (SQLException e1) {				
						e1.printStackTrace();
					}
				}
			}
		});
		btn_ekle.setFont(new Font("FreeSans", Font.BOLD, 17));
		btn_ekle.setBounds(638, 251, 150, 40);
		doctorYonetim.add(btn_ekle);
		
		fld_id = new JTextField();
		fld_id.setBackground(new Color(246, 245, 244));
		fld_id.setEditable(false);
		fld_id.setFont(new Font("FreeSans", Font.PLAIN, 17));
		fld_id.setColumns(10);
		fld_id.setBounds(638, 344, 150, 29);
		doctorYonetim.add(fld_id);
		
		JLabel lbl_id = new JLabel("Kullanıcı ID");
		lbl_id.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_id.setBounds(638, 315, 135, 29);
		doctorYonetim.add(lbl_id);
		
		JButton btn_Sil = new JButton("Sil");
		btn_Sil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_id.getText().length() == 0) {
					Helper.showMessage("Lütfen bir doktor seçiniz!");
				}else {
					if (Helper.gabul("eminmisen")) {
						int selectID = Integer.parseInt(fld_id.getText());
						try {
							boolean control = bashekim.doktorCikar(selectID);
							if (control) {
								fld_id.setText(null);
								updateDoctorModel();
								Helper.showMessage("basarili");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_Sil.setFont(new Font("FreeSans", Font.BOLD, 17));
		btn_Sil.setBounds(638, 396, 150, 40);
		doctorYonetim.add(btn_Sil);
		
		JScrollPane scroll_doctor = new JScrollPane();
		scroll_doctor.setBounds(12, 12, 619, 459);
		doctorYonetim.add(scroll_doctor);
		
		table_doctor = new JTable(doctorModel);
		scroll_doctor.setViewportView(table_doctor);
		
		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_id.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
				} catch (Exception ex ) {
				}
			}
		});
		
		
		table_doctor.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String selectTcno = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String selectPass = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();
					
					try {
						boolean control = bashekim.doktorGuncelle(selectID, selectTcno, selectPass, selectName);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		JPanel klinikYonetim = new JPanel();
		tabbedPane.addTab("Poliklinik", null, klinikYonetim, null);
		klinikYonetim.setLayout(null);
		
		JScrollPane scrollklinik = new JScrollPane();
		scrollklinik.setBounds(12, 12, 310, 459);
		klinikYonetim.add(scrollklinik);
		
		klinikMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		klinikMenu.add(updateMenu);
		klinikMenu.add(deleteMenu);
		
		updateMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedKlinikID = Integer.parseInt(table_klinik.getValueAt(table_klinik.getSelectedRow(), 0).toString());
				Klinik selectedKlinik = klinik.getFetch(selectedKlinikID);
				updKlinikGUI updateGUI = new updKlinikGUI(selectedKlinik);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateKlinikModel();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});
		
		deleteMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Helper.gabul("eminmisen")) {
					int selectedKlinikID = Integer.parseInt(table_klinik.getValueAt(table_klinik.getSelectedRow(), 0).toString());
					try {
						if (klinik.klinikCikar(selectedKlinikID)) {
							Helper.showMessage("basarili");
							updateKlinikModel();
						}else {
							Helper.showMessage("hata");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		table_klinik = new JTable(klinikModel);
		table_klinik.setComponentPopupMenu(klinikMenu);
		table_klinik.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = table_klinik.rowAtPoint(point);
				table_klinik.setRowSelectionInterval(selectedRow, selectedRow);

			}
		});
		scrollklinik.setViewportView(table_klinik);
		
		JLabel lbl_poliklinikadi = new JLabel("Poliklinik Adı");
		lbl_poliklinikadi.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_poliklinikadi.setBounds(349, 12, 135, 29);
		klinikYonetim.add(lbl_poliklinikadi);
		
		fld_poliklinikName = new JTextField();
		fld_poliklinikName.setFont(new Font("FreeSans", Font.PLAIN, 17));
		fld_poliklinikName.setColumns(10);
		fld_poliklinikName.setBounds(334, 41, 150, 30);
		klinikYonetim.add(fld_poliklinikName);
		
		JButton btn_ekleklinik = new JButton("Ekle");
		btn_ekleklinik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_poliklinikName.getText().length()==0) {
					Helper.showMessage("bosluklaridoldur");
				}else {
					try {
						if (klinik.klinikEkle(fld_poliklinikName.getText())) {
							updateKlinikModel();
							Helper.showMessage("basarili");
							fld_poliklinikName.setText(null);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_ekleklinik.setFont(new Font("FreeSans", Font.BOLD, 17));
		btn_ekleklinik.setBounds(334, 82, 150, 40);
		klinikYonetim.add(btn_ekleklinik);
		
		JScrollPane scrollcalisanDoktor = new JScrollPane();
		scrollcalisanDoktor.setBounds(493, 12, 310, 459);
		klinikYonetim.add(scrollcalisanDoktor);
		
		table_calisan = new JTable();
		scrollcalisanDoktor.setViewportView(table_calisan);
		
		JComboBox doktor_sec = new JComboBox();
		doktor_sec.setBounds(334, 369, 150, 30);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			 doktor_sec.addItem(new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName()));
		}
		doktor_sec.addActionListener(e -> {
			JComboBox cBox = (JComboBox) e.getSource();
			Item item = (Item) cBox.getSelectedItem();
			System.out.println(item.getId() + " : " + item.getValueString());
		});
		klinikYonetim.add(doktor_sec);
		
		JButton btn_calisandoktorekle = new JButton("Ekle");
		btn_calisandoktorekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_klinik.getSelectedRow();
				if (selectedRow >= 0) {
					String selectedKlinik = table_klinik.getModel().getValueAt(selectedRow, 0).toString();
					int selectedKlinikID = Integer.parseInt(selectedKlinik);
					Item doktorItem = (Item) doktor_sec.getSelectedItem();
					try {
						boolean control = bashekim.calisanEkle(doktorItem.getId(), selectedKlinikID);
						if (control) {
							DefaultTableModel clearModel = (DefaultTableModel) table_calisan.getModel();
							clearModel.setRowCount(0);
							for (int i = 0; i <bashekim.getKlinikDoctorList(selectedKlinikID).size(); i++) {
								calisanData[0] = bashekim.getKlinikDoctorList(selectedKlinikID).get(i).getId();
								calisanData[1] = bashekim.getKlinikDoctorList(selectedKlinikID).get(i).getName();
								calisanModel.addRow(calisanData);
								}
							Helper.showMessage("basarili");
						}else {
							Helper.showMessage("hata");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					Helper.showMessage("Lütfen bir poliklinik seçiniz!");
				}
			}
		});
		btn_calisandoktorekle.setFont(new Font("FreeSans", Font.BOLD, 17));
		btn_calisandoktorekle.setBounds(334, 410, 150, 40);
		klinikYonetim.add(btn_calisandoktorekle);
		
		JLabel lbl_poliklinikadi_1 = new JLabel("Poliklinik Adı");
		lbl_poliklinikadi_1.setFont(new Font("FreeSans", Font.BOLD, 17));
		lbl_poliklinikadi_1.setBounds(349, 196, 135, 29);
		klinikYonetim.add(lbl_poliklinikadi_1);
		
		JButton btn_calisanDoktorListele = new JButton("Seç");
		btn_calisanDoktorListele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_klinik.getSelectedRow();
				if (selectedRow >= 0) {
					String selectedKlinik = table_klinik.getModel().getValueAt(selectedRow, 0).toString();
					int selectedKlinikID = Integer.parseInt(selectedKlinik);
					DefaultTableModel clearModel = (DefaultTableModel) table_calisan.getModel();
					clearModel.setRowCount(0);
					
					try {
						for (int i = 0; i <bashekim.getKlinikDoctorList(selectedKlinikID).size(); i++) {
							calisanData[0] = bashekim.getKlinikDoctorList(selectedKlinikID).get(i).getId();
							calisanData[1] = bashekim.getKlinikDoctorList(selectedKlinikID).get(i).getName();
							calisanModel.addRow(calisanData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_calisan.setModel(calisanModel);
				}else {
					Helper.showMessage("Lütfen bir poliklinik seçiniz!");
				}
			}
		});
		btn_calisanDoktorListele.setFont(new Font("FreeSans", Font.BOLD, 17));
		btn_calisanDoktorListele.setBounds(334, 225, 150, 40);
		klinikYonetim.add(btn_calisanDoktorListele);
	}
	
	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);
		}
	}
	
	public void updateKlinikModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_klinik.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < klinik.getKlinikList().size(); i++) {
			klinikData[0] = klinik.getKlinikList().get(i).getId();
			klinikData[1] = klinik.getKlinikList().get(i).getPkname();
			klinikModel.addRow(klinikData);
		}
	}
}

