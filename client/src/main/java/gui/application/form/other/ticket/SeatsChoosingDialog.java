package gui.application.form.other.ticket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGIcon.ColorFilter;

import dao.SeatDAO;
import dao.TrainJourneyDAO;
import entity.*;
import net.miginfocom.swing.MigLayout;
import utils.ServerFetcher;

public class SeatsChoosingDialog extends JDialog {

	private JPanel container;
	private JPanel chonGheContainer;
	private JPanel gioVeContainer;
	private JPanel container1;
	private JPanel container2;
	private JPanel container3;
	private JPanel container4;
	private JPanel container5;
	private JLabel chonGheLabel;
	private JPanel toaItem;
	private JPanel dauTauContainer;
	private JLabel dauTauLabel;
	private JLabel dauTauValue;
	private Train train;
	private List<Coach> coachList;
	private Coach selectedCoach;
	private SeatDAO seatDAO;
	private List<Seat> seatsOfselectedCoach;
	private JPanel toaConVeContainer;
	private JLabel toaConVeLabel;
	private JLabel tauConVeValue;
	private JPanel toaDangChonContainer;
	private JLabel toaDangChonLabel;
	private JLabel toaDangChonValue;
	private JPanel toaHetVeContainer;
	private JLabel toaHetVeLabel;
	private JLabel toaHetVeValue;
	private JPanel choTrongContainer;
	private JButton choTrongButton;
	private JLabel choTrongValue;
	private JPanel choDangChonContainer;
	private JButton choDangChonButton;
	private JLabel choDangChonValue;
	private JPanel choDaBanContainer;
	private JButton choDaBanButton;
	private JLabel choDaBanValue;
	private JPanel container2boSung;
	private JLabel toaSoLabel;
	private JPanel wrapper;
	private JPanel wrapper1;
	private JLabel gioVeLabel;
	private JPanel wrapper2;
	private JButton tiepTucButton;
	private JPanel wrapper4;
	private JButton goBackButton;
	private TrainJourneyDAO trainJourneyDAO;
	private TrainJourneyOptionItem trainJourneyOptionItem;
	private JPanel wrapper3;
	private ArrayList<Ticket> chosenTicketList;
	private PassengerInfoAddingDialog passengerInfoAddingDialog;
	private List<Seat> unavailableSeatList;
	private TrainJourneyChoosingDialog trainJourneyChoosingDialog;
	private Seat selectedSeat = null;
	private Customer cus;

	public SeatsChoosingDialog(TrainJourneyOptionItem trainJourneyOptionItem, Employee employee,
			TrainJourneyChoosingDialog trainJourneyChoosingDialog, TicketInfo ticket) {

		this.trainJourneyChoosingDialog = trainJourneyChoosingDialog;
		if (ticket != null) {
			this.cus = ticket.getTicket().getOrder().getCustomer();
		}

		seatDAO = new SeatDAO();
		trainJourneyDAO = new TrainJourneyDAO();
		this.trainJourneyOptionItem = trainJourneyOptionItem;

		train = trainJourneyOptionItem.getTrain();
		System.out.println(train.getTrainID());

		HashMap<String, String> payload = new HashMap<>();
		payload.put("trainID", train.getTrainID());
		List<Coach> coachList = (List<Coach>) ServerFetcher.fetch("coach", "getByTrainID", payload);

		if (!coachList.isEmpty()) {
			selectedCoach = coachList.get(0);
		} else {
			System.out.println("Không có toa nào trong danh sách.");
		}

		unavailableSeatList = trainJourneyDAO.getUnavailableSeats(trainJourneyOptionItem.getTrainJourneyID(),
				trainJourneyOptionItem.getDepartureStation(), trainJourneyOptionItem.getArrivalStation());

		// state
		selectedCoach = coachList.getFirst();
		chosenTicketList = new ArrayList<Ticket>();
		// state

		this.setLayout(new BorderLayout());
		container = new JPanel(new MigLayout("wrap, fill, insets 40 40", "[fill, grow][350px]", "[fill]"));

		chonGheContainer = new JPanel(new MigLayout("flowy", "[]", "[][][][]push[][]"));
		gioVeContainer = new JPanel(new MigLayout("wrap, fill", "[fill]", "[fill]"));

		// chon ghe o phia ben trai
		container1 = new JPanel(new MigLayout("wrap, fill", "[]16[]", ""));
		container2 = new JPanel(new MigLayout("gapx 0"));
		
		container2boSung = new JPanel(new MigLayout("wrap, fill, insets 16 8 0 8", "[fill]"));
		container3 = new JPanel(new MigLayout("", "", ""));
		container4 = new JPanel(new MigLayout("", "", ""));
		container5 = new JPanel(new MigLayout("", "", ""));

		goBackButton = new JButton(new FlatSVGIcon("gui/icon/svg/gobackicon.svg", 0.15f));
		goBackButton.putClientProperty(FlatClientProperties.STYLE,
				"background: #fafafa; borderWidth: 0; focusWidth: 0");
		goBackButton.addActionListener(e -> {
			this.dispose();
		});
		chonGheLabel = new JLabel("CHỌN GHẾ");
		container1.add(goBackButton);
		container1.add(chonGheLabel);

		dauTauContainer = new JPanel(new MigLayout("flowy, insets 0, gap 0"));
		dauTauLabel = new JLabel(new FlatSVGIcon("gui/icon/svg/dauTau.svg", 0.4f));
		dauTauValue = new JLabel(train.getTrainNumber());
		dauTauContainer.add(dauTauLabel);
		dauTauContainer.add(dauTauValue, "align 50% 50%");
		container2.add(dauTauContainer);
		container3 = new JPanel(new MigLayout());

		setSelectedCoach(selectedCoach);
		container4 = new JPanel(new MigLayout("wrap, fill", "[][][]", "[]"));
		toaConVeContainer = new JPanel(new MigLayout());
		FlatSVGIcon toaConVeIcon = new FlatSVGIcon("gui/icon/svg/toatau.svg", 0.45f);
		toaConVeIcon.setColorFilter(new ColorFilter().add(Color.BLACK, Color.decode("#24689B")));
		toaConVeLabel = new JLabel(toaConVeIcon);
		tauConVeValue = new JLabel("Tàu còn vé");
		toaConVeContainer.add(toaConVeLabel);
		toaConVeContainer.add(tauConVeValue);
		container4.add(toaConVeContainer);

		toaDangChonContainer = new JPanel(new MigLayout());
		FlatSVGIcon toaDangChonIcon = new FlatSVGIcon("gui/icon/svg/toatau.svg", 0.45f);
		toaDangChonIcon.setColorFilter(new ColorFilter().add(Color.BLACK, Color.decode("#24A94A")));
		toaDangChonLabel = new JLabel(toaDangChonIcon);
		toaDangChonValue = new JLabel("Toa đang chọn");
		toaDangChonContainer.add(toaDangChonLabel);
		toaDangChonContainer.add(toaDangChonValue);
		container4.add(toaDangChonContainer);

		toaHetVeContainer = new JPanel(new MigLayout());
		FlatSVGIcon toaHetVeIcon = new FlatSVGIcon("gui/icon/svg/toatau.svg", 0.45f);
		toaHetVeIcon.setColorFilter(new ColorFilter().add(Color.BLACK, Color.decode("#E21A1A")));
		toaHetVeLabel = new JLabel(toaHetVeIcon);
		toaHetVeValue = new JLabel("Toa hết vé");
		toaHetVeContainer.add(toaHetVeLabel);
		toaHetVeContainer.add(toaHetVeValue);
		container4.add(toaHetVeContainer);

		container5 = new JPanel(new MigLayout("wrap, fill", "[][][]", "[]"));

		choTrongContainer = new JPanel(new MigLayout());
		choTrongButton = new JButton();
		choTrongButton.setPreferredSize(new Dimension(40, 40));
		choTrongButton.setMargin(new Insets(0, 0, 0, 0));
		choTrongValue = new JLabel("Chỗ trống");
		choTrongContainer.add(choTrongButton);
		choTrongContainer.add(choTrongValue);
		container5.add(choTrongContainer);

		choDangChonContainer = new JPanel(new MigLayout());
		choDangChonButton = new JButton();
		choDangChonButton.putClientProperty(FlatClientProperties.STYLE, "background: #24A94A");
		choDangChonButton.setPreferredSize(new Dimension(40, 40));
		choDangChonButton.setMargin(new Insets(0, 0, 0, 0));
		choDangChonValue = new JLabel("Chỗ đang chọn");
		choDangChonContainer.add(choDangChonButton);
		choDangChonContainer.add(choDangChonValue);
		container5.add(choDangChonContainer);

		choDaBanContainer = new JPanel(new MigLayout());
		choDaBanButton = new JButton();
		choDaBanButton.putClientProperty(FlatClientProperties.STYLE, "background: #E21A1A");
		choDaBanButton.setPreferredSize(new Dimension(40, 40));
		choDaBanButton.setMargin(new Insets(0, 0, 0, 0));
		choDaBanValue = new JLabel("Chỗ đã bán");
		choDaBanContainer.add(choDaBanButton);
		choDaBanContainer.add(choDaBanValue);
		container5.add(choDaBanContainer);

		chonGheContainer.add(container1);
		chonGheContainer.add(container2);
		chonGheContainer.add(container2boSung);
		chonGheContainer.add(container3);
		chonGheContainer.add(container4);
		chonGheContainer.add(container5);

		// Gio ve o ben phai
		wrapper = new JPanel(new MigLayout("flowy, wrap, fill, insets 16", "[fill]", "[][]push[][]"));
		wrapper1 = new JPanel(new MigLayout("insets 0"));
		gioVeLabel = new JLabel("Giỏ vé");
		gioVeLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");
		wrapper1.add(gioVeLabel);
		wrapper.add(wrapper1);

		wrapper2 = new JPanel(new MigLayout("wrap", "[fill]"));
		wrapper.add(new JScrollPane(wrapper2));
		wrapper3 = new JPanel(new MigLayout("wrap, fill", "[]push[]", "[fill]"));
		reactToChosenListChanged();
		wrapper.add(wrapper3);

		wrapper4 = new JPanel(new MigLayout("wrap, fill", "[fill, center]", "[fill]"));
		tiepTucButton = new JButton("Tiếp tục");
		tiepTucButton.putClientProperty(FlatClientProperties.STYLE, "background:$primary; foreground:$clr-white");
		wrapper4.add(tiepTucButton, "align 50% 50%");
		wrapper.add(wrapper4);

		gioVeContainer.add(wrapper, "growx");

		container.add(chonGheContainer, "growx");
		container.add(gioVeContainer, "growx");

		this.add(container);
		this.setUndecorated(true);
		this.setSize(1400, 770);
		this.setLocationRelativeTo(null);

		// style
		chonGheLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +20");
		JScrollPane scroll = (JScrollPane) wrapper2.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		tiepTucButton.addActionListener(e -> {
			if (chosenTicketList.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Chưa chọn vé vào");
				return;
			}
			passengerInfoAddingDialog = new PassengerInfoAddingDialog(trainJourneyOptionItem, chosenTicketList, this,
					employee, ticket);
			passengerInfoAddingDialog.setModal(true);
			passengerInfoAddingDialog.setVisible(true);
		});

	}

	// react to seat chosen
	void reactToChosenListChanged() {
		wrapper2.removeAll();
		if (chosenTicketList.isEmpty()) {
			JLabel rong = new JLabel("Chưa có vé");
			wrapper2.add(rong);
		}

		int tongCong = 0;
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		for (Ticket chosenTicket : chosenTicketList) {
			JPanel veDuocChonContainer = new JPanel(new MigLayout("wrap, fill, insets 8 0 8 0", "[]push[]", "[][]"));
			JLabel veDuocChonTen = new JLabel("Toa " + chosenTicket.getSeat().getCoach().getCoachNumber() + " ghế "
					+ chosenTicket.getSeat().getSeatNumber());
			JButton xoaButton = new JButton(new FlatSVGIcon("gui/icon/svg/removeicon.svg", 0.35f));
			JLabel loaiToaLabel = new JLabel();
			if (chosenTicket.getSeat().getCoach().getCoachType().equals("Ngồi mềm điều hòa")
					|| chosenTicket.getSeat().getCoach().getCoachType().equals("Ngồi mềm đều hòa")) {
				loaiToaLabel.setText("NMDH");
			} else if (chosenTicket.getSeat().getCoach().getCoachType().equals("Giường nằm khoang 6 điều hòa")) {
				loaiToaLabel.setText("GNK6DH");
			} else if (chosenTicket.getSeat().getCoach().getCoachType().equals("Giường nằm khoang 4 điều hòa")) {
				loaiToaLabel.setText("GNK4DH");
			}
			double basePrice = trainJourneyDAO.getTrainJourneyByID(trainJourneyOptionItem.getTrainJourneyID())
					.getBasePrice();
			int distance = trainJourneyDAO.getDistanceBetweenTwoStopsOfATrainJourney(
					trainJourneyOptionItem.getTrainJourneyID(), trainJourneyOptionItem.getDepartureStation(),
					trainJourneyOptionItem.getArrivalStation());
			double heSoToa = 0;
			if (chosenTicket.getSeat().getCoach().getCoachType().equals("Ngồi mềm điều hòa")
					|| chosenTicket.getSeat().getCoach().getCoachType().equals("Ngồi mềm đều hòa")) {
				heSoToa = 1;
			} else if (chosenTicket.getSeat().getCoach().getCoachType().equals("Giường nằm khoang 6 điều hòa")) {
				heSoToa = 1.2;
			} else if (chosenTicket.getSeat().getCoach().getCoachType().equals("Giường nằm khoang 4 điều hòa")) {
				heSoToa = 1.5;
			}
			double ticketPrice = basePrice * distance * heSoToa;

			int roundedPrice = (int) (Math.ceil(ticketPrice / 1000) * 1000);
			tongCong += roundedPrice;

			JLabel giaVeLabel = new JLabel(decimalFormat.format(roundedPrice) + " VND");
			veDuocChonContainer.add(veDuocChonTen);
			veDuocChonContainer.add(xoaButton, "align 200px");
			veDuocChonContainer.add(loaiToaLabel);
			veDuocChonContainer.add(giaVeLabel);
			wrapper2.add(veDuocChonContainer, "growx, growy, push");
			veDuocChonContainer.setBorder(new MatteBorder(new Insets(0, 0, 2, 0), Color.black));

			xoaButton.addActionListener(e -> {
				chosenTicketList.remove(chosenTicket);
				reactToSelectedCoachChanged();
				reactToChosenListChanged();
			});
		}

		// wrapper3
		wrapper3.removeAll();
		JLabel tongCongLabel = new JLabel("Tổng cộng");
		JLabel tongCongValue = new JLabel(decimalFormat.format(tongCong));
		tongCongValue.putClientProperty(FlatClientProperties.STYLE, "font:bold +4; foreground: $clr-red");
		wrapper3.add(tongCongLabel);
		wrapper3.add(tongCongValue);
		wrapper3.repaint();
		wrapper3.revalidate();
		// wrapper3
		wrapper2.repaint();
		wrapper2.revalidate();
	}

	// react to selected coach change
	void reactToSelectedCoachChanged() {
		container2.removeAll();
		dauTauContainer = new JPanel(new MigLayout("flowy, insets 0, gap 0"));
		dauTauLabel = new JLabel(new FlatSVGIcon("gui/icon/svg/dauTau.svg", 0.5f));
		dauTauValue = new JLabel(train.getTrainNumber());
		dauTauContainer.add(dauTauLabel);
		dauTauContainer.add(dauTauValue, "align 50% 50%");
		container2.add(dauTauContainer);
		for (Coach coach : coachList) {
			JPanel toaTauContainer = new JPanel(new MigLayout("flowy, insets 0, gap 0"));
			FlatSVGIcon toaTauIcon = new FlatSVGIcon("gui/icon/svg/ToaTau.svg", 0.5f);
			JButton toaTauButton = new JButton(toaTauIcon);
			toaTauButton.putClientProperty(FlatClientProperties.STYLE, "background: #fdfdfd; hoverBackground: #fdfdfd; border: null");
			if (coach.equals(selectedCoach)) {
				ColorFilter colorFilter = new ColorFilter();
				colorFilter.add(Color.BLACK, Color.decode("#24A94A"));
				toaTauIcon.setColorFilter(colorFilter);
			} else {
				toaTauIcon.setColorFilter(new ColorFilter().add(Color.BLACK, Color.decode("#24689B")));
			}
			
			
			// new code
			JLabel toaTauValue = new JLabel(coach.getCoachNumber() + "");
			toaTauContainer.add(toaTauButton);
			toaTauContainer.add(toaTauValue, "align 50% 50%");
			container2.add(toaTauContainer);
			// new code

			toaTauButton.addActionListener(e -> {
				setSelectedCoach(coach);
			});
		}

		// container2BoSung
		container2boSung.removeAll();
		toaSoLabel = new JLabel("Toa số " + selectedCoach.getCoachNumber() + ": " + selectedCoach.getCoachType());
		toaSoLabel.putClientProperty(FlatClientProperties.STYLE, "font: plain +2,");
		container2boSung.add(toaSoLabel, "align 50% 50%");

		// container3 (seats)
		container3.removeAll();
		if (selectedCoach.getCoachType().equals("Ngồi mềm điều hòa")
				|| selectedCoach.getCoachType().equals("Ngồi mềm đều hòa")) {
			container3.setLayout(new MigLayout("flowy, wrap", "[]", "[][][][]"));
		} else if (selectedCoach.getCoachType().equals("Giường nằm khoang 6 điều hòa")) {
			container3.setLayout(new MigLayout("flowy, wrap", "[]", "[][][]"));
		} else if (selectedCoach.getCoachType().equals("Giường nằm khoang 4 điều hòa")) {
			container3.setLayout(new MigLayout("flowy, wrap", "[]", "[][]"));
		}
		seatsOfselectedCoach = seatDAO.getSeats(selectedCoach);
		for (Seat seat : seatsOfselectedCoach) {
			JButton seatButton = new JButton(seat.getSeatNumber() + "");
			seatButton.setPreferredSize(new Dimension(50, 50));
			seatButton.setMargin(new Insets(0, 0, 0, 0));

			if (unavailableSeatList.contains(seat)) {
				seatButton.putClientProperty(FlatClientProperties.STYLE, "background: #f20000; foreground: $clr-white");
			} else {
				seatButton.addActionListener(e -> {
					if (cus != null && selectedSeat != null) {
						for (Ticket chosenTicket : chosenTicketList) {
							if (chosenTicket.getSeat().equals(selectedSeat)) {
								chosenTicketList.remove(chosenTicket);
								break;
							}
						}
						updateSeatButtonStyle(selectedSeat);
					}

					boolean contain = false;
					for (Ticket chosenTicket : chosenTicketList) {
						if (chosenTicket.getSeat().equals(seat)) {
							chosenTicketList.remove(chosenTicket);
							contain = true;
							selectedSeat = null;
							break;
						}
					}

					if (!contain) {
						chosenTicketList.add(new Ticket(
								trainJourneyDAO.getTrainJourneyByID(trainJourneyOptionItem.getTrainJourneyID()), seat));
						selectedSeat = seat;
					}

					updateSeatButtonStyle(seat);

					reactToSelectedCoachChanged();
					reactToChosenListChanged();
				});

				boolean contain = false;
				for (Ticket chosenTicket : chosenTicketList) {
					if (chosenTicket.getSeat().equals(seat)) {
						contain = true;
						break;
					}
				}

				// seat color
				if (contain) {
					seatButton.putClientProperty(FlatClientProperties.STYLE,
							"background: #24A94A; foreground: $clr-white");
				} else {
					seatButton.putClientProperty(FlatClientProperties.STYLE,
							"background: $clr-white; foreground: $clr-black");
				}
			}

			container3.add(seatButton);
		}
		container3.repaint();
		container3.revalidate();
		// container 3

		container2.repaint();
		container2.revalidate();
	}

	public Coach getSelectedCoach() {
		return selectedCoach;
	}

	public void setSelectedCoach(Coach selectedCoach) {
		this.selectedCoach = selectedCoach;
		reactToSelectedCoachChanged();
	}

	public void dongDayChuyen() {
		this.dispose();
		trainJourneyChoosingDialog.closeDialog();
	}

	private void updateSeatButtonStyle(Seat seat) {
		for (Component comp : container3.getComponents()) {
			if (comp instanceof JButton) {
				JButton button = (JButton) comp;
				if (button.getText().equals(seat.getSeatNumber() + "")) {
					button.putClientProperty(FlatClientProperties.STYLE,
							"background: $clr-white; foreground: $clr-black");
					break;
				}
			}
		}
	}

}