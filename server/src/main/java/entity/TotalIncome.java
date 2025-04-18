package entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TotalIncome implements Serializable {

	private BigDecimal sumOfTicket;
	private BigDecimal sumOfSevice;
	private BigDecimal sumOfTicketAndService;

	public TotalIncome(BigDecimal sumOfTicket, BigDecimal sumOfSevice, BigDecimal sumOfTicketAndService) {
		super();
		this.sumOfTicket = sumOfTicket;
		this.sumOfSevice = sumOfSevice;
		this.sumOfTicketAndService = sumOfTicketAndService;
	}

	public BigDecimal getSumOfTicket() {
		return sumOfTicket;
	}

	public void setSumOfTicket(BigDecimal sumOfTicket) {
		this.sumOfTicket = sumOfTicket;
	}

	public BigDecimal getSumOfSevice() {
		return sumOfSevice;
	}

	public void setSumOfSevice(BigDecimal sumOfSevice) {
		this.sumOfSevice = sumOfSevice;
	}

	public BigDecimal getSumOfTicketAndService() {
		return sumOfTicketAndService;
	}

	public void setSumOfTicketAndService(BigDecimal sumOfTicketAndService) {
		this.sumOfTicketAndService = sumOfTicketAndService;
	}

	@Override
	public String toString() {
		return "TotalIncome [sumOfTicket=" + sumOfTicket + ", sumOfSevice=" + sumOfSevice + ", sumOfTicketAndService="
				+ sumOfTicketAndService + "]";
	}

}
