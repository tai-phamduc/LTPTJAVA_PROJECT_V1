package test;//package test;
//
//import java.io.File;
//import java.nio.file.FileSystems;
//import java.nio.file.Path;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//
//public class InvoiceQRCodeGenerator {
//
//	// Phương thức đọc file XML và lấy mã hóa đơn (SHDon)
//	public static String getInvoiceNumber(String filePath) {
//		try {
//			File xmlFile = new File(filePath);
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = dBuilder.parse(xmlFile);
//			doc.getDocumentElement().normalize();
//
//			NodeList nodeList = doc.getElementsByTagName("SHDon");
//
//			if (nodeList.getLength() > 0) {
//				Node node = nodeList.item(0);
//				if (node.getNodeType() == Node.ELEMENT_NODE) {
//					Element element = (Element) node;
//					return element.getTextContent().trim();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	// Phương thức băm mã hóa đơn bằng SHA-256
//	public static String hashInvoice(String invoiceNumber) {
//		try {
//			MessageDigest digest = MessageDigest.getInstance("SHA-256");
//			byte[] hashBytes = digest.digest(invoiceNumber.getBytes());
//
//			StringBuilder hexString = new StringBuilder();
//			for (byte b : hashBytes) {
//				String hex = Integer.toHexString(0xff & b);
//				if (hex.length() == 1)
//					hexString.append('0');
//				hexString.append(hex);
//			}
//			return hexString.toString();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	// Phương thức tạo mã QR từ chuỗi đã băm
//	public static void generateQRCode(String data, String filePath, int width, int height) {
//		try {
//			BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
//			Path path = FileSystems.getDefault().getPath(filePath);
//			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	// Main method: kết hợp các bước đọc, băm và tạo mã QR
//	public static void main(String[] args) {
//		// Bước 1: Đọc file XML và lấy mã hóa đơn
//		String filePath = "xml/invoice.xml"; // Thay thế bằng đường dẫn thật của file XML
//		String invoiceNumber = getInvoiceNumber(filePath);
//
//		if (invoiceNumber != null) {
//			// Bước 2: Băm mã hóa đơn
//			String hashedInvoice = hashInvoice(invoiceNumber);
//			System.out.println("Hashed Invoice: " + hashedInvoice);
//
//			// Bước 3: Tạo mã QR từ chuỗi đã băm
//			String qrFilePath = "path_to_save_qr_code.png"; // Thay thế bằng đường dẫn để lưu file QR
//			generateQRCode(hashedInvoice, qrFilePath, 300, 300);
//
//			System.out.println("QR code generated at: " + qrFilePath);
//		} else {
//			System.out.println("Invoice number not found in XML file.");
//		}
//	}
//}
