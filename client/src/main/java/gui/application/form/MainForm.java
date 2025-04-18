package gui.application.form;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entity.Employee;
import gui.application.Application;
import gui.application.form.other.customer.FormCustomerManagement;
import gui.application.form.other.employee.FormEmployeeManagement;
import gui.application.form.other.order.TicketRefundScreen;
import gui.application.form.other.profile.ChangePasswordScreen;
import gui.application.form.other.profile.ProfileScreen;
import gui.application.form.other.service.FormDrinkManagement;
import gui.application.form.other.service.FormFoodManagement;
import gui.application.form.other.station.StationScreen;
import gui.application.form.other.statistics.FormStatisticsCustomer;
import gui.application.form.other.statistics.FormStatisticsGeneralv2;
import gui.application.form.other.statistics.FormStatisticsService;
import gui.application.form.other.statistics.FormStatisticsTrainJourney;
import gui.application.form.other.ticket.FormSearchTrainJourney;
import gui.application.form.other.train.FormTrainManagement;
import gui.application.form.other.trainjourney.FormLineManagement;
import gui.application.form.other.trainjourney.FormTrainJourneyManagement;
import gui.menu.other.Menu;
import gui.menu.other.MenuAction;
import gui.other.MainFormLayout;

public class MainForm extends JLayeredPane {
	private static final long serialVersionUID = 1L;
	private Menu menu;
	private JPanel panelBody;

	public MainForm(Employee employee) {
		init(employee);
		setLayout(new MainFormLayout(this));

	}

	private void init(Employee employee) {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		menu = new Menu(employee.getRole());
		panelBody = new JPanel(new BorderLayout());
		initMenuEvent(employee);
		add(menu);
		add(panelBody);
	}

	@Override
	public void applyComponentOrientation(ComponentOrientation o) {
		super.applyComponentOrientation(o);
	}

	private void initMenuEvent(Employee employee) {
		menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
			if (employee.getRole().equalsIgnoreCase("Quản Lý")) {
				switch (index) {
				case 0:
					Application.showMainForm(new FormTrainManagement());
					break;
				case 1:
					Application.showMainForm(new StationScreen());
					break;
				case 2:
					switch (subIndex) {
					case 1:
						Application.showMainForm(new FormLineManagement());
						break;
					case 2:
						Application.showMainForm(new FormTrainJourneyManagement(employee));
						break;
					default:
						action.cancel();
						break;
					}
					break;
				case 3:
					Application.showMainForm(new TicketRefundScreen(employee));
					break;
				case 4:
					Application.showMainForm(new FormSearchTrainJourney(employee));
					break;
				case 5:
					switch (subIndex) {
					case 1:
						Application.showMainForm(new FormFoodManagement());
						break;
					case 2:
						Application.showMainForm(new FormDrinkManagement());
						break;
					default:
						action.cancel();
						break;
					}
					break;
				case 6:
					Application.showMainForm(new FormCustomerManagement());
					break;
				case 7:
					Application.showMainForm(new FormEmployeeManagement(employee));
					break;
				case 8:
					switch (subIndex) {
					case 1:
						Application.showMainForm(new FormStatisticsGeneralv2(employee));
						break;
					case 2:
						Application.showMainForm(new FormStatisticsService(employee));
						break;
					case 3:
						Application.showMainForm(new FormStatisticsCustomer(employee));
						break;
					case 4:
						Application.showMainForm(new FormStatisticsTrainJourney(employee));
						break;
					default:
						action.cancel();
						break;
					}
					break;
				case 9:
					switch (subIndex) {
					case 1:
						Application.showMainForm(new ProfileScreen(employee));
						break;
					case 2:
						Application.showMainForm(new ChangePasswordScreen(employee));
						break;
					default:
						action.cancel();
						break;
					}
					break;
				case 10:
					Application.logout();
					break;
				default:
					action.cancel();
					break;
				}
			} else {
				switch (index) {
				case 0:
					Application.showMainForm(new FormTrainManagement());
					break;
//				case 1:
//					Application.showMainForm(new FormScreeningManagement(employee));
//					break;
//				case 3:
//					Application.showMainForm(new FormCustomerManagement());
//					break;
//				case 4:
//					switch (subIndex) {
//					case 1:
//						Application.showMainForm(new FormFoodManagement());
//						break;
//					case 2:
//						Application.showMainForm(new FormDrinkManagement());
//						break;
//					default:
//						action.cancel();
//						break;
//					}
//					break;
//				case 5:
//					switch (subIndex) {
//					case 1:
//						Application.showMainForm(new FormStatisticsGeneral(employee));
//						break;
//					case 2:
//						Application.showMainForm(new FormStatisticsCustomer(employee));
//						break;
//					case 3:
//						Application.showMainForm(new FormStatisticsMovie(employee));
//						break;
//					case 4:
//						Application.showMainForm(new FormStatisticsProduct(employee));
//						break;
//					default:
//						action.cancel();
//						break;
//					}
//					break;
//				case 6:
//					switch (subIndex) {
//					case 1:
//						Application.showMainForm(new FormProfileInfo(employee));
//						break;
//					case 2:
//						Application.showMainForm(new FormChangePassword(employee));
//						break;
//					default:
//						action.cancel();
//						break;
//					}
//					break;
//				case 7:
//					Application.logout();
//					break;
				case 9:
					Application.logout();
					break;
				default:
					action.cancel();
					break;
				}
			}
		});

	}

	public void hideMenu() {
		menu.hideMenuItem();
	}

	public void showForm(Component component) {
		panelBody.removeAll();
		panelBody.add(component);
		panelBody.repaint();
		panelBody.revalidate();
	}

	public void setSelectedMenu(int index, int subIndex) {
		menu.setSelectedMenu(index, subIndex);
	}

	public Menu getMenu() {
		return menu;
	}

	public JPanel getPanelBody() {
		return panelBody;
	}
}
