package test;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

	public static void sendVerificationCode(String toEmail, String verificationCode) {
		// Cấu hình thông tin SMTP cho Gmail
		final String fromEmail = "hoangvy170204@gmail.com"; // Địa chỉ Gmail của bạn
		final String password = "falb lips vuyv tsol"; // Mật khẩu ứng dụng của bạn

		// Thiết lập các thuộc tính cho mail
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP server của Gmail
		props.put("mail.smtp.port", "587"); // SMTP port
		props.put("mail.smtp.auth", "true"); // Bật xác thực SMTP
		props.put("mail.smtp.starttls.enable", "true"); // Bật mã hóa TLS

		// Xác thực email
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});

		try {
			// Tạo email
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject("Verification Code");
			message.setText("Your verification code is: " + verificationCode);

			// Gửi email
			Transport.send(message);
			System.out.println("Verification code sent successfully to " + toEmail);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Thử gửi email
		String toEmail = "vanhhoang6978@gmail.com";
		String verificationCode = "123456"; // Mã xác thực giả định
		for (int i = 1; i <= 10; i++) {
			sendVerificationCode(toEmail, verificationCode);
		}
	}
}