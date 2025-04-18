package entity;

import java.io.Serializable;

public class TrainJourneyRanking implements Serializable {

	private String trainJourneyName;
	private String trainNumber;
	private long totalRevenue;

	public TrainJourneyRanking(String trainJourneyName, String trainNumber, long totalRevenue) {
		super();
		this.trainJourneyName = trainJourneyName;
		this.trainNumber = trainNumber;
		this.totalRevenue = totalRevenue;
	}

	public String getTrainJourneyName() {
		return trainJourneyName;
	}

	public void setTrainJourneyName(String trainJourneyName) {
		this.trainJourneyName = trainJourneyName;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public long getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(long totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	@Override
	public String toString() {
		return "TrainJourneyRanking [trainJourneyName=" + trainJourneyName + ", trainNumber=" + trainNumber
				+ ", totalRevenue=" + totalRevenue + "]";
	}

}
