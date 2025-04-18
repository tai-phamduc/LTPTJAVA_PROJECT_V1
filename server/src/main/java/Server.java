import dao.AccountDAO;
import dao.CoachDAO;
import dao.TrainDAO;

import entity.Account;
import entity.Coach;
import entity.Employee;
import entity.Train;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 4631;
    private static final int MAX_THREADS = 100;
    private static final AccountDAO accountDAO = new AccountDAO();
    private static final CoachDAO coachDAO = new CoachDAO();
    private static final TrainDAO trainDAO = new TrainDAO();
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server gracefully...");
            threadPool.shutdown();
        }));

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("✅ Server started on port " + PORT);

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("\n[CLIENT CONNECTED] " + socket.getRemoteSocketAddress());
                    threadPool.execute(() -> handleClient(socket));
                } catch (IOException e) {
                    if (!serverSocket.isClosed()) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            threadPool.shutdown();
            System.out.println("Server shutdown complete");
        }
    }

    private static void handleClient(Socket socket) {
        try (socket;
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            Object request = in.readObject();

            // Log the incoming request
            System.out.println("\n[REQUEST FROM " + socket.getRemoteSocketAddress() + "]");
            System.out.println("Request Data: " + request);

            if (!(request instanceof HashMap<?, ?> map)) {
                String errorMsg = "Invalid request format";
                out.writeObject(errorMsg);
                System.out.println("[RESPONSE] " + errorMsg);
                return;
            }

            String type = (String) map.get("type");
            String action = (String) map.get("action");
            HashMap<String, String> payload = (HashMap<String, String>) map.get("payload");

            System.out.println("Type: " + type);
            System.out.println("Action: " + action);
            System.out.println("Payload: " + payload);

            Object result;
            try {
                result = switch (type) {
                    case "account" -> handleAccount(action, payload);
                    case "coach" -> handleCoach(action, payload);
                    case "train" -> handleTrain(action, payload);
                    default -> "Unknown type: " + type;
                };
            } catch (Exception e) {
                result = "Error processing request: " + e.getMessage();
                System.err.println("[ERROR] Handling client request from " +
                        socket.getRemoteSocketAddress() + ": " + e.getMessage());
            }

            // Log the response before sending
            System.out.println("[RESPONSE TO " + socket.getRemoteSocketAddress() + "]");
            System.out.println("Response Data: " + result);

            out.writeObject(result);
            out.flush();
        } catch (IOException e) {
            System.err.println("[CLIENT ERROR] " + socket.getRemoteSocketAddress() + ": " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("[PROTOCOL ERROR] " + socket.getRemoteSocketAddress() + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[UNEXPECTED ERROR] " + socket.getRemoteSocketAddress() + ": " + e.getMessage());
        } finally {
            System.out.println("\n[CLIENT DISCONNECTED] " + socket.getRemoteSocketAddress());
        }
    }

    private static Object handleAccount(String action, HashMap<String, String> payload) {
        try {
            System.out.println("[ACCOUNT ACTION] " + action + " with payload: " + payload);
            Object result = switch (action) {
                case "getByUsername" -> accountDAO.getAccountByUsername(payload.get("username"));
                case "checkAvailability" -> accountDAO.checkAvalibility(payload.get("username"));
                case "updatePassword" ->
                        accountDAO.updatePassword(payload.get("employeeID"), payload.get("newPassword"));
                case "checkCredentials" ->
                        accountDAO.checkCredentials(payload.get("username"), payload.get("password"));
                case "getEmployeeByAccount" ->
                        accountDAO.getEmployeeByAccount(payload.get("username"), payload.get("password"));
                case "createAccount" -> {
                    Account account = new Account();
                    account.setUsername(payload.get("username"));
                    account.setPassword(payload.get("password"));
                    Employee employee = new Employee(payload.get("employeeID"));
                    account.setEmployee(employee);
                    yield accountDAO.createAccount(account);
                }
                case "getEmployeeByUsername" ->
                        accountDAO.getEmployeeByUsername(payload.get("username"),
                                Boolean.parseBoolean(payload.get("authentication")));
                case "getUserByEmployeeID" ->
                        accountDAO.getUserByEmployeeID(payload.get("employeeID"));
                case "getAccountByEmployeeID" ->
                        accountDAO.getAccountByEmployeeID(payload.get("employeeID"));
                case "updateAccount" ->
                        accountDAO.updateAccount(
                                payload.get("employeeID"),
                                payload.get("username"),
                                payload.get("password")
                        );
                default -> "Unknown account action: " + action;
            };
            System.out.println("[ACCOUNT RESULT] " + result);
            return result;
        } catch (Exception e) {
            System.err.println("[ACCOUNT ERROR] " + e.getMessage());
            return "Error processing account request: " + e.getMessage();
        }
    }

    private static Object handleCoach(String action, HashMap<String, String> payload) {
        try {
            System.out.println("[COACH ACTION] " + action + " with payload: " + payload);
            Object result = switch (action) {
                case "addCoach" -> {
                    int coachNumber = Integer.parseInt(payload.get("coachNumber"));
                    String coachType = payload.get("coachType");
                    int capacity = Integer.parseInt(payload.get("capacity"));
                    String trainID = payload.get("trainID");
                    Coach coach = new Coach(coachNumber, coachType, capacity, new Train(trainID));
                    yield coachDAO.addCoach(coach);
                }
                case "getCoaches" -> {
                    Train train = new Train(payload.get("trainID"));
                    List<Coach> coaches = coachDAO.getCoaches(train);
                    yield coaches != null ? coaches : new ArrayList<Coach>();
                }
                case "removeCoaches" ->
                        coachDAO.removeCoaches(new Train(payload.get("trainID")));
                case "getCoachByID" ->
                        coachDAO.getCoachByID(Integer.parseInt(payload.get("coachID")));
                default -> "Unknown coach action: " + action;
            };
            System.out.println("[COACH RESULT] " + result);
            return result;
        } catch (Exception e) {
            System.err.println("[COACH ERROR] " + e.getMessage());
            return "Error processing coach request: " + e.getMessage();
        }
    }

    private static Object handleTrain(String action, HashMap<String, String> payload) {
        try {
            System.out.println("[TRAIN ACTION] " + action + " with payload: " + payload);
            Object result = switch (action) {
                case "getAllTrainDetails" -> trainDAO.getAllTrainDetails();
                case "addNewTrain" -> {
                    Train train = new Train(payload.get("trainNumber"));
                    train.setStatus(payload.get("status"));
                    yield trainDAO.addNewTrain(train);
                }
                case "deleteTrainByID" -> trainDAO.deleteTrainByID(payload.get("trainID"));
                case "getTrainByID" -> trainDAO.getTrainByID(payload.get("trainID"));
                case "getNumberOfCoaches" ->
                        trainDAO.getNumberOfCoaches(new Train(payload.get("trainID")));
                case "updateTrain" -> {
                    Train train = new Train(
                            payload.get("trainID"),
                            payload.get("trainNumber"),
                            payload.get("status")
                    );
                    yield trainDAO.updateTrain(train);
                }
                case "getTrainDetailsByTrainNumber" ->
                        trainDAO.getTrainDetailsByTrainNumber(payload.get("trainNumber"));
                case "getAllTrain" -> trainDAO.getAllTrain();
                default -> "Unknown train action: " + action;
            };
            System.out.println("[TRAIN RESULT] " + result);
            return result;
        } catch (Exception e) {
            System.err.println("[TRAIN ERROR] " + e.getMessage());
            return "Error processing train request: " + e.getMessage();
        }
    }
}