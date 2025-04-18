package gui.other;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.util.UIScale;

import gui.application.form.LoginForm;
import raven.swing.blur.BlurChild;
import raven.swing.blur.style.GradientColor;
import raven.swing.blur.style.Style;
import raven.swing.blur.style.StyleBorder;
import raven.swing.blur.style.StyleOverlay;

public class LoginLayout extends BlurChild implements LayoutManager {
	private static final long serialVersionUID = 1L;
	private final int titleGap = 10;
	private final int textGap = 10;
	private final int labelGap = 5;
	private final int buttonGap = 50;
	private JLabel lbTitle;
	private JLabel lbUser;
	private JTextField txtUser;
	private JLabel lbPass;
	private JPasswordField txtPass;
	private JButton cmdLogin;

	public LoginLayout(LoginForm loginForm) {
		super(new Style().setBlur(30)
				.setBorder(new StyleBorder(10).setOpacity(0.15f).setBorderWidth(1.2f)
						.setBorderColor(new GradientColor(new Color(200, 200, 200), new Color(150, 150, 150),
								new Point2D.Float(0, 0), new Point2D.Float(1, 0))))
				.setOverlay(new StyleOverlay(new Color(0, 0, 0), 0.2f)));
		this.lbTitle = loginForm.getLbTitle();
		this.lbUser = loginForm.getLbUser();
		this.lbPass = loginForm.getLbPass();
		this.txtUser = loginForm.getTxtUser();
		this.cmdLogin = loginForm.getCmdLogin();
		this.txtPass = loginForm.getTxtPass();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int height = insets.top + insets.bottom;

			height += lbTitle.getPreferredSize().height;
			height += UIScale.scale(titleGap);
			height += lbUser.getPreferredSize().height;
			height += UIScale.scale(labelGap);
			height += txtUser.getPreferredSize().height;
			height += UIScale.scale(textGap);

			height += lbPass.getPreferredSize().height;
			height += UIScale.scale(labelGap);
			height += txtPass.getPreferredSize().height;
			height += UIScale.scale(buttonGap);
			height += cmdLogin.getPreferredSize().height;
			return new Dimension(0, height);
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			return new Dimension(0, 0);
		}
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int x = insets.left;
			int y = insets.top;
			int width = parent.getWidth() - (insets.left + insets.right);

			lbTitle.setBounds(x, y, width, lbTitle.getPreferredSize().height);
			y += lbTitle.getPreferredSize().height + UIScale.scale(titleGap);

			lbUser.setBounds(x, y, width, lbUser.getPreferredSize().height);
			y += lbUser.getPreferredSize().height + UIScale.scale(labelGap);
			txtUser.setBounds(x, y, width, txtUser.getPreferredSize().height);
			y += txtUser.getPreferredSize().height + UIScale.scale(textGap);

			lbPass.setBounds(x, y, width, lbPass.getPreferredSize().height);
			y += lbPass.getPreferredSize().height + UIScale.scale(labelGap);
			txtPass.setBounds(x, y, width, txtPass.getPreferredSize().height);
			y += txtPass.getPreferredSize().height + UIScale.scale(buttonGap);

			cmdLogin.setBounds(x, y, width, cmdLogin.getPreferredSize().height);
		}
	}

}
