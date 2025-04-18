package gui.other;//package gui.other;
//
//import java.awt.Desktop;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
//public class test {
//
//	public static void createTicket(List<OrderDetail> odList, ArrayList<MovieScheduleSeat> seatList, Order order) {
//		
////		DecimalFormat df = new DecimalFormat("$#.00");
//		String companyName = "Tổng công ty Đường Sắt Việt Nam";
//		String title = "Vé Tàu Hỏa\n";
//		int padding = (80 - title.length()) / 2;
//		String centeredTitle = String.format("%" + (padding + title.length()) + "s", title);
//
//		String ticketID = "Mã vé: Tic90213292";
//		String gaDi = "Ga đi\nHÀ Nội";
//		String gaDen = "Ga đến\nSài Gòn";
//
//		String customerinfo = "Customer Infomation:\n";
//		String tau = "Tàu: ";
//		String tauvalue = "SE3";
//		String ngayDi= "22/10/2024"+ "\n";
//		String gioDi = "22:00";
//		String toa = "toa: 10" + "\n";
//		String cho = "Chỗ: 1";
//		String loaiVe = "Loại vé";
//		String loạiVeValue = "Cá nhân";
//		String loaiCho = "Loại Chỗ: ";
//		String loaiChoValue = "NMDH";
//		String hoTen = "Họ tên: Đồng";
//		String giayTo = "Giấy tờ: 1234";
//		String gia = "584,000";
//
//		String ghiChu = "Để tra cứu và nhận hóa dơn diện tử xin vui lòng truy cập địa chỉ web http://hoadon.vantaiduongsatvietnam.vn";
//		String maTraCuuHoaDon = "Mã tra cứu hóa đơn: S2NFBJDF";
//		DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
//		String ngayGioIn = "Ngày in: " + LocalDateTime.now().format(df);
//		
//		String defaultFolderPath = "data/";
//
//		Document document = new Document(new Rectangle(333, 999));
//		String fileName = "tic000000001" + "ticket.pdf";
//
//		try {
//			PdfWriter.getInstance(document, new FileOutputStream(defaultFolderPath + fileName));
//			document.open();
//
//			Font font = new Font(Font.FontFamily.COURIER, 12);
//			Font fontProduct = new Font(Font.FontFamily.COURIER, 11);
//			Font fontBold = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
//
//			Paragraph firstParagraph = new Paragraph(companyName, new Font(Font.FontFamily.COURIER, 25, Font.BOLD));
//			firstParagraph.setAlignment(Element.ALIGN_CENTER);
//			document.add(firstParagraph);
//			
//			Paragraph titleParagraph = new Paragraph(title, new Font(Font.FontFamily.COURIER, 25, Font.BOLD));
//			titleParagraph.setAlignment(Element.ALIGN_CENTER);
//			document.add(titleParagraph);
//
//			Paragraph cinemaTitle = new Paragraph(companyName, fontBold);
//			cinemaTitle.setAlignment(Element.ALIGN_LEFT);
//			document.add(cinemaTitle);
//			Paragraph cinemaParagraph = new Paragraph(ticketID, font);
//			cinemaParagraph.setAlignment(Element.ALIGN_LEFT);
//			document.add(cinemaParagraph);
//
//			Paragraph customerInfoTitle = new Paragraph("", fontBold);
//			customerInfoTitle.setAlignment(Element.ALIGN_LEFT);
//			document.add(customerInfoTitle);
//			
//			PdfPTable stationTable = new PdfPTable(new float[] { 0.75f, 0.25f });
//			stationTable.setWidthPercentage(100);
//			stationTable.addCell(new PdfPCell(new Phrase(gaDi, fontProduct)));
//			stationTable.addCell(new PdfPCell(new Phrase(gaDen, fontProduct)));
//			
//			Paragraph customerInfoParagraph = new Paragraph(name + phoneNumber + mail + line, font);
//			customerInfoParagraph.setAlignment(Element.ALIGN_LEFT);
//			document.add(customerInfoParagraph);
//
//			Paragraph movieInfoTitle = new Paragraph(movie, new Font(Font.FontFamily.COURIER, 14, Font.BOLD));
//			movieInfoTitle.setAlignment(Element.ALIGN_LEFT);
//			document.add(movieInfoTitle);
//			Paragraph movieParagraph = new Paragraph("Time: " + time + date + room + qs + seat + emp + line, font);
//			movieParagraph.setAlignment(Element.ALIGN_LEFT);
//			document.add(movieParagraph);
//
//			PdfPTable billTable = new PdfPTable(new float[] { 0.75f, 0.25f });
//			billTable.setWidthPercentage(100);
//			List<PdfPCell> cellList = new ArrayList<PdfPCell>();
//
//			cellList.add(new PdfPCell(new Phrase(ticketPrice, font)));
//			cellList.add(new PdfPCell(new Phrase(ticketPriceValue, font)));
//			int index = 0;
//			for (String productName : productNameList) {
//				cellList.add(new PdfPCell(new Phrase(productName, fontProduct)));
//				cellList.add(new PdfPCell(new Phrase(lineTotalList.get(index), fontProduct)));
//				index++;
//			}
//			cellList.add(new PdfPCell(new Phrase("Total:", fontBold)));
//			cellList.add(new PdfPCell(new Phrase(df.format(order.getTotal()), fontBold)));
//
//			boolean[] isOdd = new boolean[1];
//			cellList.forEach(cell -> {
//				if (!isOdd[0]) {
//					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				} else {
//					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				}
//				cell.setBorder(Rectangle.NO_BORDER);
//				billTable.addCell(cell);
//				isOdd[0] = !isOdd[0];
//			});
//
//			billTable.setHorizontalAlignment(Element.ALIGN_CENTER);
//
//			document.add(billTable);
//
//			Paragraph trasactionParagraph = new Paragraph(transactionTime, font);
//			trasactionParagraph.setAlignment(Element.ALIGN_RIGHT);
//			document.add(trasactionParagraph);
//			document.add(new Paragraph("\n"));
//
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			Map<EncodeHintType, Object> hintMap = new HashMap<>();
//			hintMap.put(EncodeHintType.MARGIN, 1);
//			BitMatrix qrCodeMatrix = new MultiFormatWriter().encode(qrContent, BarcodeFormat.QR_CODE, 400, 400,
//					hintMap);
//			MatrixToImageWriter.writeToStream(qrCodeMatrix, "PNG", baos);
//			Image qrCodeImage = Image.getInstance(baos.toByteArray());
//
//			PdfPTable table = new PdfPTable(1);
//			table.setWidthPercentage(50);
//			PdfPCell cell = new PdfPCell(qrCodeImage, true);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setBorder(Rectangle.NO_BORDER);
//			table.addCell(cell);
//			document.add(table);
//
//			document.close();
//			System.out.println("Ticket information and QR code have been written");
//			try {
//				if (Desktop.isDesktopSupported()) {
//					File pdfFile = new File(defaultFolderPath + fileName);
//					Desktop.getDesktop().open(pdfFile);
//				} else {
//					System.out.println("Desktop is not supported.");
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} catch (DocumentException | IOException | WriterException e) {
//			e.printStackTrace();
//		}
//	}
//}
