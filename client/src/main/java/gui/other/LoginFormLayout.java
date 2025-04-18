package gui.other;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import com.formdev.flatlaf.util.UIScale;

import gui.application.form.LoginForm;
import raven.swing.blur.BlurChild;
import raven.swing.blur.style.GradientColor;
import raven.swing.blur.style.Style;
import raven.swing.blur.style.StyleBorder;
import raven.swing.blur.style.StyleOverlay;

public class LoginFormLayout extends BlurChild implements LayoutManager {
	private static final long serialVersionUID = 1L;
	private JPanel login;

	public LoginFormLayout(LoginForm loginForm) {
		super(new Style().setBlur(30)
				.setBorder(new StyleBorder(10).setOpacity(0.15f).setBorderWidth(1.2f)
						.setBorderColor(new GradientColor(new Color(200, 200, 200), new Color(150, 150, 150),
								new Point2D.Float(0, 0), new Point2D.Float(1, 0))))
				.setOverlay(new StyleOverlay(new Color(0, 0, 0), 0.2f)));
		this.login = loginForm.getLogin();
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
			return new Dimension(0, 0);
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
			int width = parent.getWidth();
			int height = parent.getHeight();
			int loginWidth = UIScale.scale(320);
			int loginHeight = login.getPreferredSize().height;
			int x = (width - loginWidth) / 2;
			int y = (height - loginHeight) / 2;
			login.setBounds(x, y, loginWidth, loginHeight);
		}
	}
}
