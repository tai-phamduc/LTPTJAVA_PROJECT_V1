package gui.menu.other;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.formdev.flatlaf.util.UIScale;

import gui.menu.mode.LightDarkMode;
import gui.menu.mode.ToolBarAccentColor;

public class MenuLayout implements LayoutManager {

	private final JLabel header;
	private final JPanel headerContainer;
	private final JScrollPane scroll;
	private final LightDarkMode lightDarkMode;
	private final ToolBarAccentColor toolBarAccentColor;
	private final int headerFullHgap;
	private final boolean menuFull;

	public MenuLayout(Menu menu) {
		this.header = menu.getHeader();
		this.headerContainer = menu.getHeaderContainer();
		this.scroll = menu.getScroll();
		this.lightDarkMode = menu.getLightDarkMode();
		this.toolBarAccentColor = menu.getToolBarAccentColor();
		this.headerFullHgap = menu.getHeaderFullHgap();
		this.menuFull = menu.isMenuFull();
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
			Insets insets = parent.getInsets();
			int x = insets.left;
			int y = insets.top;
			int gap = UIScale.scale(5);
			int sheaderFullHgap = UIScale.scale(headerFullHgap);
			int width = parent.getWidth() - (insets.left + insets.right);
			int height = parent.getHeight() - (insets.top + insets.bottom);
			int iconWidth = width;
			int accentColorHeight = 0;
			int iconHeight = headerContainer.getPreferredSize().height;
			int hgap = menuFull ? sheaderFullHgap : 0;

			if (toolBarAccentColor.isVisible()) {
				accentColorHeight = toolBarAccentColor.getPreferredSize().height + gap;
			}

			headerContainer.setBounds(x + hgap, y, iconWidth - (hgap * 2), iconHeight);

			int ldgap = UIScale.scale(10);
			int ldWidth = width - ldgap * 2;
			int ldHeight = lightDarkMode.getPreferredSize().height;
			int ldx = x + ldgap;
			int ldy = y + height - ldHeight - ldgap - accentColorHeight;

			int menux = x;
			int menuy = y + iconHeight + gap;
			int menuWidth = width;
			int menuHeight = height - (iconHeight + gap) - (ldHeight + ldgap * 2) - (accentColorHeight);
			scroll.setBounds(menux, menuy, menuWidth, menuHeight);

			lightDarkMode.setBounds(ldx, ldy, ldWidth, ldHeight);

			if (toolBarAccentColor.isVisible()) {
				int tbheight = toolBarAccentColor.getPreferredSize().height;
				int tbwidth = Math.min(toolBarAccentColor.getPreferredSize().width, ldWidth);
				int tby = y + height - tbheight - ldgap;
				int tbx = ldx + ((ldWidth - tbwidth) / 2);
				toolBarAccentColor.setBounds(tbx, tby, tbwidth, tbheight);
			}
		}
	}
}
