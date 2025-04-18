package test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetTime {
    private LocalDateTime departureDateTime;

    public GetTime() {
        this.departureDateTime = LocalDateTime.of(2024, 10, 24, 0, 0);
    }

    public double getRemainingHours() {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(currentTime, departureDateTime);
        return duration.toHours() + (double) duration.toMinutesPart() / 60; // Tính giờ và phút
    }

    // Hàm tính phí hoàn vé
    public double calculateRefundFee(double ticketPrice, int ticketCount) {
        double remainingHours = getRemainingHours();
        double refundFee = 0;

        if (ticketCount == 1) {
            if (remainingHours < 24 && remainingHours >= 4) {
                refundFee = ticketPrice * 0.20; // 20% phí hoàn vé
            } else if (remainingHours >= 24) {
                refundFee = ticketPrice * 0.10; // 10% phí hoàn vé
            }
        } else if (ticketCount > 1) {
            if (remainingHours < 72 && remainingHours >= 24) {
                refundFee = ticketPrice * 0.20; // 20% phí hoàn vé
            } else if (remainingHours >= 72) {
                refundFee = ticketPrice * 0.10; // 10% phí hoàn vé
            }
        }
        return refundFee; // Trả về phí hoàn vé
    }

    // Phương thức main để thử nghiệm
    public static void main(String[] args) {
        // Tạo đối tượng GetTime
        GetTime getTime = new GetTime();

        // Tính thời gian còn lại
        System.out.println("Thời gian còn lại: " + getTime.getRemainingHours() + " giờ");

        // Tính phí hoàn vé
        double ticketPrice = 100000; // Ví dụ giá vé
        int ticketCount = 1; // Số lượng vé
        double refundFee = getTime.calculateRefundFee(ticketPrice, ticketCount);
        System.out.println("Phí hoàn vé: " + refundFee + " VNĐ");
    }
}
