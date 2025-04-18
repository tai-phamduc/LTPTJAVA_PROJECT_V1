package gui.other;//package gui.other;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
//
//import entity.Movie;
//
//public class MovieCSVReader {
//
//	public static List<Movie> readMoviesFromCSV(String csvFile) {
//		List<Movie> movies = new ArrayList<>();
//
//		try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
//			String[] nextLine;
//
//			try {
//				reader.readNext();
//				while ((nextLine = reader.readNext()) != null) {
//					String name = nextLine[0];
//					String description = nextLine[1];
//					String genre = nextLine[2];
//					String director = nextLine[3];
//					int duration = Integer.parseInt(nextLine[4]);
//					LocalDate releasedDate = LocalDate.parse(nextLine[5], DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//					String language = nextLine[6];
//					String country = nextLine[7];
//					String trailer = nextLine[8];
//					LocalDate startDate = LocalDate.parse(nextLine[9], DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//					String status = nextLine[10];
//					double importPrice = Double.parseDouble(nextLine[11]);
//					String imagePath = nextLine[12];
//
//					Movie newMovie = new Movie(name, description, genre, director, duration, releasedDate, language,
//							country, trailer, startDate, status, importPrice, imagePath);
//
//					movies.add(newMovie);
//				}
//			} catch (CsvValidationException | NumberFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return movies;
//	}
//}
