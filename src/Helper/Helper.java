package Helper;

import javax.swing.JOptionPane;

public class Helper {
	
	public static void showMessage(String str) {
		String msg;
		
		switch(str) {
		case "bosluklaridoldur":
			msg = "Tüm boşlukları doldurun!";
			break;
		case "basarili":
			msg="İşlem başarılı!";
			break;
		case "hata":
			msg = "Bir hata oluştu!";
			break;
		default:
			msg = str;
		}
		
		JOptionPane.showMessageDialog(null, msg,"Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean gabul(String str) {
		String msg;
		switch (str) {
		case "eminmisen":
			msg = "Bu işlemi gerçekleştirmek istiyor musun ?";
			break;
		default:
			msg = str;
			break;
		}
		
		int resp = JOptionPane.showConfirmDialog(null, msg, "Dikkat !", JOptionPane.YES_NO_OPTION);
		if (resp == 0) 
			return true;
		else
			return false;
		
	}
	
}