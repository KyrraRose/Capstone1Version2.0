package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

import com.pluralsight.DAO.DataManager;
import com.pluralsight.DAO.LedgerDAO;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;

import static com.pluralsight.Utility.systemDialogue;

public class Main {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/accountingledger";
    private static DataManager dataManager;
    private final LedgerDAO ledgerDAO;

public Main(LedgerDAO ledgerDAO) {
    this.ledgerDAO = ledgerDAO;
}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadConnection(args[0], args[1]);
        List<Transaction> transactions = TransactionFileManager.loadTransactions();
        //List<Transaction> transactions = ledgerDAO.getAllTransactions();

        boolean running = true;

        while (running) {
            systemDialogue("\n\t=== FINANCIAL TRACKER ==="+
                    "\n\t\tD) Add Deposit"+
                    "\n\t\tP) Make Payment"+
                    "\n\t\tL) Ledger"+
                    "\n\t\tX) Exit") ;
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "D" -> addTransaction(scanner, transactions, true);
                case "P" -> addTransaction(scanner, transactions, false);
                case "L" -> Ledger.showLedger(transactions, scanner);
                case "X" -> running = false;
            }
        }
        System.out.println("Goodbye!");
    }

    public static void loadConnection(String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        try {
            Connection connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Error when loading connection. Exiting application.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void addTransaction(Scanner scanner, List<Transaction> list, boolean deposit) {
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        if (!deposit) amount *= -1;

        Transaction transaction = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount
        );

        list.add(transaction);
        TransactionFileManager.saveTransaction(transaction);
        System.out.println("Transaction saved!");
    }
}
