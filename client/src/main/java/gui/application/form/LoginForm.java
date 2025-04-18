package gui.application.form;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import entity.Employee;
import gui.application.Application;
import gui.application.form.other.train.FormTrainManagement;
import gui.other.LoginFormLayout;
import gui.other.LoginLayout;
import raven.swing.blur.BlurBackground;
import raven.swing.blur.style.StyleOverlay;
import raven.toast.Notifications;
import raven.toast.Notifications.Location;
import raven.toast.Notifications.Type;
import utils.ServerFetcher;

public class LoginForm extends BlurBackground {
	private static final long serialVersionUID = 1L;
	private JButton cmdLogin;
	private JPasswordField txtPass;
	private JLabel lbPass;
	private JLabel lbTitle;
	private JTextField txtUser;
	private JLabel lbUser;
	private JPanel login;

	public LoginForm() {
		setOverlay(new StyleOverlay(new Color(20, 20, 20), 0.1f));
		initComponents();
		init();
		setLayout(new LoginFormLayout(this));
		login.setLayout(new LoginLayout(this));
		addEvents();
	}

	public void resetLogin() {
		txtUser.setText("");
		txtPass.setText("");
		txtUser.requestFocus();
	}

	private void init() {
		lbTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:$h1.font");
		login.putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Login.background;" + "arc:20;" + "border:30,40,50,30");

		txtPass.putClientProperty(FlatClientProperties.STYLE, "" + "showRevealButton:true;" + "showCapsLock:true");
		cmdLogin.putClientProperty(FlatClientProperties.STYLE, "" + "borderWidth:0;" + "focusWidth:0");
		txtUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên đăng nhập");
		txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");
	}

	private void addEvents() {
		cmdLogin.addActionListener(e -> {
			String username = txtUser.getText().trim();
			String password = new String(txtPass.getPassword());

			HashMap<String, String> requestParams = new HashMap<>();
			requestParams.put("username", username);
			requestParams.put("password", password);
			Employee employee = (Employee) ServerFetcher.fetch("account", "getEmployeeByAccount", requestParams);

			Application app = Application.getInstance();

			if (employee == null) {
				Notifications.getInstance().show(Type.ERROR, Location.TOP_CENTER,
						"Tài khoản hoặc mật khẩu không chính xác!");
				app.getLoginForm().resetLogin();
			} else {
				app.createMainForm(employee);
				FlatAnimatedLafChange.showSnapshot();
				app.setContentPane(app.getMainForm());
				app.getMainForm().applyComponentOrientation(app.getComponentOrientation());
				Application.showMainForm(new FormTrainManagement());
				app.getMainForm().hideMenu();
				SwingUtilities.updateComponentTreeUI(app.getMainForm());
				FlatAnimatedLafChange.hideSnapshotWithAnimation();
			}
		});

		KeyListener keyEnterListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cmdLogin.doClick();
				}
			}
		};

		txtUser.addKeyListener(keyEnterListener);
		txtPass.addKeyListener(keyEnterListener);
	}

	private void initComponents() {

		login = new JPanel();
		cmdLogin = new JButton();
		lbTitle = new JLabel();
		lbUser = new JLabel();
		txtUser = new JTextField();
		lbPass = new JLabel();
		txtPass = new JPasswordField();

		cmdLogin.setText("Đăng nhập");

		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setText("Đăng nhập");

		lbUser.setText("Tên đăng nhập");

		lbPass.setText("Mật khẩu");

		GroupLayout loginLayout = new GroupLayout(login);
		login.setLayout(loginLayout);
		loginLayout.setHorizontalGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(loginLayout.createSequentialGroup().addGroup(loginLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(loginLayout.createSequentialGroup().addGroup(loginLayout
								.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(loginLayout.createSequentialGroup().addGap(15, 15, 15).addComponent(lbUser))
								.addGroup(loginLayout.createSequentialGroup().addContainerGap().addComponent(txtUser,
										GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)))
								.addGap(0, 36, Short.MAX_VALUE))
						.addGroup(loginLayout.createSequentialGroup().addContainerGap().addGroup(loginLayout
								.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(lbTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGroup(loginLayout.createSequentialGroup()
										.addGroup(loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(lbPass).addComponent(txtPass, GroupLayout.PREFERRED_SIZE,
														100, GroupLayout.PREFERRED_SIZE))
										.addGap(0, 0, Short.MAX_VALUE)))))
						.addContainerGap())
				.addGroup(loginLayout.createSequentialGroup().addContainerGap().addComponent(cmdLogin)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		loginLayout.setVerticalGroup(
				loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
						loginLayout.createSequentialGroup().addContainerGap().addComponent(lbTitle).addGap(10, 10, 10)
								.addComponent(lbUser).addGap(5, 5, 5)
								.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(10, 10, 10).addComponent(lbPass).addGap(5, 5, 5)
								.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(50, 50, 50).addComponent(cmdLogin).addContainerGap()));

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout
						.createSequentialGroup().addGap(204, 204, 204).addComponent(login, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(319, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout
						.createSequentialGroup().addGap(68, 68, 68).addComponent(login, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(179, Short.MAX_VALUE)));
	}

	public JButton getCmdLogin() {
		return cmdLogin;
	}

	public JPasswordField getTxtPass() {
		return txtPass;
	}

	public JLabel getLbPass() {
		return lbPass;
	}

	public JLabel getLbTitle() {
		return lbTitle;
	}

	public JTextField getTxtUser() {
		return txtUser;
	}

	public JLabel getLbUser() {
		return lbUser;
	}

	public JPanel getLogin() {
		return login;
	}

}
