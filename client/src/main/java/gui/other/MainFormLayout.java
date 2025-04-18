package gui.other;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.formdev.flatlaf.util.UIScale;

import gui.application.form.MainForm;
import gui.menu.other.Menu;

public class MainFormLayout implements LayoutManager {
	private Menu menu;
	private JPanel panelBody;

	public MainFormLayout(MainForm mainForm) {
		menu = mainForm.getMenu();
		panelBody = mainForm.getPanelBody();
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
			return new Dimension(5, 5);
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
			boolean ltr = parent.getComponentOrientation().isLeftToRight();
			Insets insets = UIScale.scale(parent.getInsets());
			int x = insets.left;
			int y = insets.top;
			int width = parent.getWidth() - (insets.left + insets.right);
			int height = parent.getHeight() - (insets.top + insets.bottom);
			int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
			int menuX = ltr ? x : x + width - menuWidth;
			menu.setBounds(menuX, y, menuWidth, height);
			int gap = UIScale.scale(5);
			int bodyWidth = width - menuWidth - gap;
			int bodyHeight = height;
			int bodyx = ltr ? (x + menuWidth + gap) : x;
			int bodyy = y;
			panelBody.setBounds(bodyx, bodyy, bodyWidth, bodyHeight);
		}
	}
}
