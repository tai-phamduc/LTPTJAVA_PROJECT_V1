package gui.other;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CreateTrainTickets {

	public static void main(String[] args) {
		createTicket("1236848", "SÀI GÒN", "HÀ NỘI", "SE4", "12/12/2015", "22:00", "11", "7", "Toàn vé",
				"Ngồi mềm điều hòa", "1019000", "John Doe", "12345678", "Odr000001");
	}

	public static void createTicket(String ticketID, String gaDi, String gaDen, String tau, String ngayDi, String gioDi,
			String toa, String cho, String loaiVe, String loaiCho, String gia, String hoTen, String giayTo,
			String orderid) {
		String companyName = "Tổng công ty Đường Sắt Việt Nam";
		String title = "Vé Tàu Hỏa";
		String note = "Để tra cứu và nhận hóa dơn diện tử xin vui lòng truy cập địa chỉ web http://hoadon.vantaiduongsatvietnam.vn";
		String orderID = "Mã tra cứu hóa đơn: " + orderid;
		String defaultFolderPath = "data/";

		Document document = new Document(new Rectangle(400, 1000));
		String fileName = ticketID + "_ticket_" + ticketID + ".pdf";

		try {
			PdfWriter.getInstance(document, new FileOutputStream(defaultFolderPath + fileName));
			document.open();

			// Font Settings
			BaseFont baseFont = BaseFont.createFont("data/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			Font fontTitle = new Font(baseFont, 16, Font.BOLD);
			Font fontInfo = new Font(baseFont, 12);
			Font fontBold = new Font(baseFont, 14, Font.BOLD);

			// Company and Title
			Paragraph firstParagraph = new Paragraph(companyName, fontInfo);
			firstParagraph.setAlignment(Element.ALIGN_CENTER);
			document.add(firstParagraph);

			Paragraph titleParagraph = new Paragraph(title, fontTitle);
			titleParagraph.setAlignment(Element.ALIGN_CENTER);
			document.add(titleParagraph);

			Paragraph passengerInfo = new Paragraph("Họ tên/Name: " + hoTen, fontInfo);
			passengerInfo.setSpacingBefore(5f);
			document.add(passengerInfo);

			Paragraph idInfo = new Paragraph("Giấy tờ/Passport: " + giayTo, fontInfo);
			document.add(idInfo);

			Image qrCodeImageTicket = createQRCode(ticketID);
			qrCodeImageTicket.setAlignment(Element.ALIGN_CENTER);
			qrCodeImageTicket.setSpacingBefore(20f);
			document.add(qrCodeImageTicket);

			Paragraph ticketIDParagraph = new Paragraph("Mã vé: " + ticketID, fontInfo);
			ticketIDParagraph.setAlignment(Element.ALIGN_CENTER);
			document.add(ticketIDParagraph);

			// Station Info Table
			PdfPTable stationTable = new PdfPTable(2);
			stationTable.setWidthPercentage(100);
			stationTable.setSpacingBefore(10f);

			// Sử dụng font in đậm cho tên ga
			PdfPCell gaDiCell = new PdfPCell(new Phrase("Ga đi: " + gaDi, fontBold));
			gaDiCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell gaDenCell = new PdfPCell(new Phrase("Ga đến: " + gaDen, fontBold));
			gaDenCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			gaDiCell.setBorder(Rectangle.NO_BORDER);
			gaDenCell.setBorder(Rectangle.NO_BORDER);

			stationTable.addCell(gaDiCell);
			stationTable.addCell(gaDenCell);
			document.add(stationTable);

			// Train Info Table
			PdfPTable trainInfoTable = new PdfPTable(2);
			trainInfoTable.setWidthPercentage(100);
			trainInfoTable.setSpacingBefore(5f);

			trainInfoTable.addCell(createCell("Tàu/Train: " + tau, fontInfo));
			trainInfoTable.addCell(createCell("Ngày đi/Date: " + ngayDi, fontInfo));
			trainInfoTable.addCell(createCell("Giờ đi/Time: " + gioDi, fontInfo));
			trainInfoTable.addCell(createCell("Toa/Coach: " + toa, fontInfo));
			trainInfoTable.addCell(createCell("Chỗ/Seat: " + cho, fontInfo));
			trainInfoTable.addCell(createCell("Loại vé/Ticket: " + loaiVe, fontInfo));
			trainInfoTable.addCell(createCell("Loại chỗ/Class: " + loaiCho, fontInfo));
			document.add(trainInfoTable);

			// Price Info
			Paragraph priceParagraph = new Paragraph("Giá/Price: " + gia + " VND", fontBold);
			priceParagraph.setSpacingBefore(10f);
			document.add(priceParagraph);

			// QR Code
			Image qrCodeImage = createQRCode(orderid);
			qrCodeImage.setAlignment(Element.ALIGN_CENTER);
			qrCodeImage.setSpacingBefore(20f);
			document.add(qrCodeImage);

			Paragraph orderIDParagraph = new Paragraph(orderID, fontInfo);
			orderIDParagraph.setSpacingBefore(10f);
			document.add(orderIDParagraph);

			Paragraph noteParagraph = new Paragraph(note, fontInfo);
			noteParagraph.setSpacingBefore(10f);
			document.add(noteParagraph);

			// Footer
			DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
			Paragraph footerParagraph = new Paragraph("Ngày in/Printed date: " + LocalDateTime.now().format(df),
					fontInfo);
			footerParagraph.setSpacingBefore(20f);
			footerParagraph.setAlignment(Element.ALIGN_RIGHT);
			document.add(footerParagraph);

			document.close();
			System.out.println("Ticket PDF generated successfully!");

			// Auto-open PDF
			File pdfFile = new File(defaultFolderPath + fileName);
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(pdfFile);
			}
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	private static PdfPCell createCell(String content, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(content, font));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		return cell;
	}

	private static Image createQRCode(String id) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Map<EncodeHintType, Object> hintMap = new HashMap<>();
		hintMap.put(EncodeHintType.MARGIN, 1);
		BitMatrix qrCodeMatrix = null;
		Image qrCodeImage = null;
		try {
			qrCodeMatrix = new MultiFormatWriter().encode(id, BarcodeFormat.QR_CODE, 200, 200, hintMap);
			MatrixToImageWriter.writeToStream(qrCodeMatrix, "PNG", baos);
			qrCodeImage = Image.getInstance(baos.toByteArray());
		} catch (WriterException | IOException | BadElementException e) {
			e.printStackTrace();
		}
		return qrCodeImage;
	}

}
