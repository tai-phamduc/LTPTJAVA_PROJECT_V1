package test;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class TicketRefundSelectedGUI {

    public static void main(String[] args) {
        // Tạo frame chính
        JFrame frame = new JFrame("Ticket Refund Selected");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sử dụng MigLayout
        frame.setLayout(new MigLayout("wrap", "[grow]", "[][][grow][]"));

        // Tiêu đề danh sách các vé chọn trả
        JLabel title = new JLabel("Danh sách các vé chọn trả");
        frame.add(title, "growx, wrap");

        // Tạo panel để hiển thị vé
        JPanel ticketPanel = new JPanel(new MigLayout("wrap", "[grow]"));
        
        // Phần vé đầu tiên
        ticketPanel.add(new JLabel("Hà Nội - Sài Gòn 04/04/2020"), "wrap");
        ticketPanel.add(new JLabel(" - Họ tên: ngo thanh huong"), "wrap");
        ticketPanel.add(new JLabel(" Tàu SE7 Toa: 1 chỗ số: 24"), "wrap");
        ticketPanel.add(new JLabel(" Lệ phí trả vé: 101,000"), "wrap");
        ticketPanel.add(new JLabel(" - Số giấy tờ: 32423434"), "wrap");
        ticketPanel.add(new JLabel(" Tiền vé: 1,014,000 VND"), "wrap");
        ticketPanel.add(new JLabel(" Tiền trả: 913,000 VND"), "wrap");

        // Phần vé thứ hai
        ticketPanel.add(new JLabel("Sài Gòn - Hà Nội 09/04/2020"), "wrap");
        ticketPanel.add(new JLabel(" - Họ tên: ngo tran minh"), "wrap");
        ticketPanel.add(new JLabel(" Tàu SE8 Toa: 1 chỗ số: 44"), "wrap");
        ticketPanel.add(new JLabel(" Lệ phí trả vé: 123,000"), "wrap");
        ticketPanel.add(new JLabel(" - Số giấy tờ: 23423434"), "wrap");
        ticketPanel.add(new JLabel(" Tiền vé: 823,000 VND"), "wrap");
        ticketPanel.add(new JLabel(" Tiền trả: 700,000 VND"), "wrap");

        frame.add(ticketPanel, "growx, wrap");

        // Tạo panel chứa tổng tiền
        JPanel totalPanel = new JPanel(new MigLayout("wrap 2", "[grow, right][grow, left]"));
        totalPanel.add(new JLabel("Tổng vé: 2"));
        totalPanel.add(new JLabel("Tổng tiền vé: 1,837,000 VND"));
        totalPanel.add(new JLabel("Tổng lệ phí: 224,000 VND"));
        totalPanel.add(new JLabel("Tổng tiền trả: 1,613,000 VND"));
        frame.add(totalPanel, "growx, wrap");

        // Phương thức nhận mã xác thực trả vé
        JLabel methodLabel = new JLabel("Phương thức nhận mã xác thực trả vé");
        frame.add(methodLabel, "wrap");

        JPanel methodPanel = new JPanel(new MigLayout("wrap 2", "[grow]"));
        JCheckBox emailCheckBox = new JCheckBox("Email: email@email.com");
        JCheckBox phoneCheckBox = new JCheckBox("Số điện thoại: 0983456789");
        methodPanel.add(emailCheckBox);
        methodPanel.add(phoneCheckBox);
        frame.add(methodPanel, "growx, wrap");

        // Nút xác nhận
        JButton confirmButton = new JButton("Xác nhận");
        frame.add(confirmButton, "center, wrap");

        // Hiển thị frame
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
