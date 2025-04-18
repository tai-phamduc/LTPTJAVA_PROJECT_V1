package gui.application.form.other.profile;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entity.Employee;
import gui.application.Application;
import net.miginfocom.swing.MigLayout;
import raven.crazypanel.CrazyPanel;
import raven.toast.Notifications;
import raven.toast.Notifications.Location;
import utils.ServerFetcher;

public class ChangePasswordScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel currentPasswordLabel;
	private JLabel newPasswordLabel;
	private JLabel confirmNewPasswordLabel;
	private JTextField currentPasswordTextField;
	private JTextField newPasswordTextField;
	private JTextField confirmNewPasswordTextField;
	private CrazyPanel container;
	private JLabel imageSourceDisplay;
	private JButton changePasswordButton;
	private JLabel imforamtionLabel;
	private JLabel errorLabel;

	public ChangePasswordScreen(Employee employee) {
		initComponents(employee);
	}

	private void initComponents(Employee employee) {
		container = new CrazyPanel();
		currentPasswordLabel = new JLabel("Mật Khẩu Hiện Tại:");
		newPasswordLabel = new JLabel("Mật Khẩu Mới:");
		confirmNewPasswordLabel = new JLabel("Nhập Lại Mật Khẩu Mới:");
		currentPasswordTextField = new JPasswordField();
		newPasswordTextField = new JPasswordField();
		confirmNewPasswordTextField = new JPasswordField(55);
		changePasswordButton = new JButton("Đổi Mật Khẩu");
		imforamtionLabel = new JLabel(employee.getFullName() + " - " + employee.getRole());
		errorLabel = new JLabel();

		File file = new File(employee.getImageSource());
		boolean isValid = file.exists();
		imageSourceDisplay = new JLabel();

		if (!isValid || employee.getImageSource().trim().isEmpty()) {
			Image defaultImageIcon = new ImageIcon("images/default.png").getImage();
			imageSourceDisplay.setIcon(new ImageIcon(defaultImageIcon.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
		} else {
			Image imageIcon = new ImageIcon(employee.getImageSource()).getImage();
			imageSourceDisplay.setIcon(new ImageIcon(imageIcon.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
		}

		// Title label
		JLabel titleLabel = new JLabel("Đổi Mật Khẩu");
		titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 25));
		imforamtionLabel.setFont(new Font(imforamtionLabel.getFont().getName(), Font.ITALIC, 20));
		errorLabel.setBorder(BorderFactory.createEmptyBorder());
		errorLabel.setForeground(Color.RED);
		errorLabel.setFont(errorLabel.getFont().deriveFont(Font.ITALIC));

		container.setLayout(new MigLayout("wrap 2, fillx, insets 8 50 8 50, gap 20", "[grow 0,trail]15[fill]"));

		container.add(titleLabel, "wrap, span, al left, gapbottom 8");
		container.add(imforamtionLabel, "span 2, al left");
		container.add(imageSourceDisplay, "wrap, span, al center, gapbottom 8");
		container.add(currentPasswordLabel, "skip 6");
		container.add(currentPasswordTextField);
		container.add(newPasswordLabel);
		container.add(newPasswordTextField);
		container.add(confirmNewPasswordLabel);
		container.add(confirmNewPasswordTextField);
		container.add(new JLabel(""));
		container.add(errorLabel, "al left");
		container.add(changePasswordButton, "span 2, al right");

		add(container);

		changePasswordButton.addActionListener(e -> {
			String currentPassword = currentPasswordTextField.getText();
			String newPassword = newPasswordTextField.getText();
			String confirmNewPassword = confirmNewPasswordTextField.getText();

			if (currentPassword.isEmpty()) {
				errorLabel.setText("Vui lòng nhập mật khẩu hiện tại.");
				currentPasswordTextField.requestFocus();
				return;
			}

			if (newPassword.isEmpty()) {
				errorLabel.setText("Vui lòng nhập mật khẩu mới.");
				newPasswordTextField.requestFocus();
				return;
			}

			if (confirmNewPassword.isEmpty()) {
				errorLabel.setText("Vui lòng nhập lại mật khẩu mới.");
				confirmNewPasswordTextField.requestFocus();
				return;
			}

			if (!newPassword.equals(confirmNewPassword)) {
				errorLabel.setText("Mật khẩu mới và nhập lại mật khẩu không khớp.");
				confirmNewPasswordTextField.requestFocus();
				return;
			}

			HashMap<String, String> userPayload = new HashMap<>();
			userPayload.put("employeeID", employee.getEmployeeID());
			String username = (String) ServerFetcher.fetch("account", "getUserByEmployeeID", userPayload);

			if (username == null) {
				errorLabel.setText("Không thể lấy thông tin tài khoản.");
				return;
			}

			HashMap<String, String> authPayload = new HashMap<>();
			authPayload.put("username", username);
			authPayload.put("password", currentPassword);
			boolean isAuthenticated = (boolean) ServerFetcher.fetch("account", "checkCredentials", authPayload);

			if (!isAuthenticated) {
				errorLabel.setText("Mật khẩu hiện tại không đúng!");
				currentPasswordTextField.requestFocus();
				return;
			}

			HashMap<String, String> updatePayload = new HashMap<>();
			updatePayload.put("employeeID", employee.getEmployeeID());
			updatePayload.put("newPassword", newPassword);
			boolean isUpdated = (boolean) ServerFetcher.fetch("account", "updatePassword", updatePayload);

			if (isUpdated) {
				errorLabel.setText("");
				Notifications.getInstance().show(Notifications.Type.INFO, Location.TOP_CENTER,
						"Đổi mật khẩu thành công.");
				Application.logout();
				currentPasswordTextField.setText("");
				newPasswordTextField.setText("");
				confirmNewPasswordTextField.setText("");
			} else {
				errorLabel.setText("Đổi mật khẩu thất bại. Vui lòng thử lại.");
			}
		});

		KeyListener enterKeyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					changePasswordButton.doClick();
				}
			}
		};

		currentPasswordTextField.addKeyListener(enterKeyListener);
		newPasswordTextField.addKeyListener(enterKeyListener);
		confirmNewPasswordTextField.addKeyListener(enterKeyListener);

	}

}