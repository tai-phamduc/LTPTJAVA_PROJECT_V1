package test;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class TicketRefundGUI {

	public static void main(String[] args) {
		// Tạo frame chính
		JFrame frame = new JFrame("Ticket Refund");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sử dụng MigLayout
		frame.setLayout(new MigLayout("wrap", "[grow]", "[][][grow][]"));

		// Tiêu đề
		JLabel title = new JLabel("Vui lòng chọn các vé cần trả. Tiền hoàn lại khi trả vé của quý khách...");
		frame.add(title, "growx, wrap");

		// Phần "Thông tin người đặt vé"
		JLabel infoTitle = new JLabel("Thông tin người đặt vé");
		frame.add(infoTitle, "growx, wrap");

		// Form thông tin
		frame.add(new JLabel("Họ và tên:"), "split 2");
		JTextField nameField = new JTextField(20);
		frame.add(nameField, "growx, wrap");

		frame.add(new JLabel("Số CMND/Hộ chiếu:"), "split 2");
		JTextField idField = new JTextField(20);
		frame.add(idField, "growx, wrap");

		frame.add(new JLabel("Email:"), "split 2");
		JTextField emailField = new JTextField(20);
		frame.add(emailField, "growx, wrap");

		frame.add(new JLabel("Số điện thoại:"), "split 2");
		JTextField phoneField = new JTextField(20);
		frame.add(phoneField, "growx, wrap");

		// Nút "Yêu cầu trả vé"
		JButton refundButton = new JButton("Yêu cầu trả vé");
		frame.add(refundButton, "center, wrap");

		// Hiển thị frame
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
