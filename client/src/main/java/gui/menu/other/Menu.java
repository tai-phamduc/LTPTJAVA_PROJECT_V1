package gui.menu.other;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatUIUtils;

import gui.menu.mode.LightDarkMode;
import gui.menu.mode.ToolBarAccentColor;
import net.miginfocom.swing.MigLayout;

public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;
	private final List<MenuEvent> events = new ArrayList<>();
	private boolean menuFull = true;
	private final String headerName = "VNR |";
	private final boolean hideMenuTitleOnMinimum = true;
	private final int menuTitleLeftInset = 5;
	private final int menuTitleVgap = 5;
	private final int menuMaxWidth = 250;
	private final int menuMinWidth = 60;
	private final int headerFullHgap = 5;
	private JPanel headerContainer;
	private JLabel header;
	private JScrollPane scroll;
	private JPanel panelMenu;
	private LightDarkMode lightDarkMode;
	private ToolBarAccentColor toolBarAccentColor;
	private final String menuItems[][] = { { "~Hành Trình~" }, { "Tàu" }, { "Ga" }, { "Hành Trình", "Đường Đi", "Chuyến Tàu" }, { "Hóa Đơn" },
			{ "Vé" }, { "Dịch Vụ", "Thức Ăn", "Nước Uống" }, { "~Người Dùng~" }, { "Khách Hàng" },
			{ "Nhân Viên" }, { "~Khác~" },
			{ "Báo cáo - Thống kê", "Tổng Quan", "Dịch Vụ", "Khách Hàng", "Chuyến Tàu" },
			{ "Tài Khoản", "Thông Tin Cá Nhân", "Đổi Mật khẩu" }, { "Đăng xuất" } };

	public Menu(String role) {
		init(role);
		setLayout(new MenuLayout(this));
	}

	private void init(String role) {
		putClientProperty(FlatClientProperties.STYLE,
				"" + "border:20,2,2,2;" + "background:$Menu.background;" + "arc:10");
		headerContainer = new JPanel(new MigLayout("", "[]8[]"));
		headerContainer.putClientProperty(FlatClientProperties.STYLE, "background:$Menu.background;");

		header = new JLabel(headerName);
		header.putClientProperty(FlatClientProperties.STYLE,
				"" + "font:$Menu.header.font;" + "foreground:$Menu.foreground");

		FlatSVGIcon svgIcon = new FlatSVGIcon(getClass().getResource("/gui/icon/svg/logo.svg"));

		JLabel label = new JLabel(svgIcon);

		headerContainer.add(header);
		headerContainer.add(label);

		// Menu
		scroll = new JScrollPane();
		panelMenu = new JPanel(new MenuItemLayout(this));
		panelMenu.putClientProperty(FlatClientProperties.STYLE, "" + "border:5,5,5,5;" + "background:$Menu.background");

		scroll.setViewportView(panelMenu);
		scroll.putClientProperty(FlatClientProperties.STYLE, "" + "border:null");
		JScrollBar vscroll = scroll.getVerticalScrollBar();
		vscroll.setUnitIncrement(10);
		vscroll.putClientProperty(FlatClientProperties.STYLE,
				"" + "width:$Menu.scroll.width;" + "trackInsets:$Menu.scroll.trackInsets;"
						+ "thumbInsets:$Menu.scroll.thumbInsets;" + "background:$Menu.ScrollBar.background;"
						+ "thumb:$Menu.ScrollBar.thumb");
		createMenu(role);
		lightDarkMode = new LightDarkMode();
		toolBarAccentColor = new ToolBarAccentColor(this);
		toolBarAccentColor.setVisible(FlatUIUtils.getUIBoolean("AccentControl.show", false));
		add(headerContainer);
		add(scroll);
		add(lightDarkMode);
		add(toolBarAccentColor);

	}

	public void setMenuFull(boolean menuFull) {
		this.menuFull = menuFull;
		if (menuFull) {
			header.setText(headerName);
			header.setHorizontalAlignment(getComponentOrientation().isLeftToRight() ? JLabel.LEFT : JLabel.RIGHT);
		} else {
			header.setText("");
			header.setHorizontalAlignment(JLabel.CENTER);
		}
		for (Component com : panelMenu.getComponents()) {
			if (com instanceof MenuItem) {
				((MenuItem) com).setFull(menuFull);
			}
		}
		lightDarkMode.setMenuFull(menuFull);
		toolBarAccentColor.setMenuFull(menuFull);
	}

	private void createMenu(String role) {
		int index = 0;
		for (int i = 0; i < menuItems.length; i++) {
			String menuName = menuItems[i][0];
			if (menuName.startsWith("~") && menuName.endsWith("~")) {
				panelMenu.add(createTitle(menuName));
			} else {
				if (role.equalsIgnoreCase("Employee") && i == 3) {
					continue;
				}
				MenuItem menuItem = new MenuItem(this, menuItems[i], index++, events, role);
				panelMenu.add(menuItem);
			}
		}
	}

	private JLabel createTitle(String title) {
		String menuName = title.substring(1, title.length() - 1);
		JLabel lbTitle = new JLabel(menuName);
		lbTitle.putClientProperty(FlatClientProperties.STYLE,
				"" + "font:$Menu.label.font;" + "foreground:$Menu.title.foreground");
		return lbTitle;
	}

	public void setSelectedMenu(int index, int subIndex) {
		runEvent(index, subIndex);
	}

	protected void setSelected(int index, int subIndex) {
		int size = panelMenu.getComponentCount();
		for (int i = 0; i < size; i++) {
			Component com = panelMenu.getComponent(i);
			if (com instanceof MenuItem) {
				MenuItem item = (MenuItem) com;
				if (item.getMenuIndex() == index) {
					item.setSelectedIndex(subIndex);
				} else {
					item.setSelectedIndex(-1);
				}
			}
		}
	}

	public void runEvent(int index, int subIndex) {
		MenuAction menuAction = new MenuAction();
		for (MenuEvent event : events) {
			event.menuSelected(index, subIndex, menuAction);
		}
		if (!menuAction.isCancel()) {
			setSelected(index, subIndex);
		}
	}

	public void addMenuEvent(MenuEvent event) {
		events.add(event);
	}

	public void hideMenuItem() {
		for (Component com : panelMenu.getComponents()) {
			if (com instanceof MenuItem) {
				((MenuItem) com).hideMenuItem();
			}
		}
		revalidate();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<MenuEvent> getEvents() {
		return events;
	}

	public String getHeaderName() {
		return headerName;
	}

	public boolean isHideMenuTitleOnMinimum() {
		return hideMenuTitleOnMinimum;
	}

	public int getMenuTitleLeftInset() {
		return menuTitleLeftInset;
	}

	public int getMenuTitleVgap() {
		return menuTitleVgap;
	}

	public int getMenuMaxWidth() {
		return menuMaxWidth;
	}

	public int getMenuMinWidth() {
		return menuMinWidth;
	}

	public int getHeaderFullHgap() {
		return headerFullHgap;
	}

	public String[][] getMenuItems() {
		return menuItems;
	}

	public JLabel getHeader() {
		return header;
	}

	public JPanel getHeaderContainer() {
		return headerContainer;
	}

	public JScrollPane getScroll() {
		return scroll;
	}

	public JPanel getPanelMenu() {
		return panelMenu;
	}

	public LightDarkMode getLightDarkMode() {
		return lightDarkMode;
	}

	public ToolBarAccentColor getToolBarAccentColor() {
		return toolBarAccentColor;
	}

	public boolean isMenuFull() {
		return menuFull;
	}

}
