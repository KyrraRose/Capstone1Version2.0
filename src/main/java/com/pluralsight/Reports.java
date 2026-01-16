package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.pluralsight.Utility.*;
import static java.lang.Double.parseDouble;

public class Reports {
   public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
   public static LocalDate now = LocalDate.now();

    public static void showReports(List<Transaction> transactions, Scanner scanner) {
        systemDialogue("\t--- Reports ---"+
                        "\n\t\t1) Month To Date"+
                        "\n\t\t2) Previous Month"+
                        "\n\t\t3) Year To Date"+
                        "\n\t\t4) Previous Year"+
                        "\n\t\t5) Search by Vendor"+
                        "\n\t\t6) Custom Search"+
                        "\n\t\t0) Back");

        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();



        switch (choice) {
            case "1" -> filterByDate(transactions, now.withDayOfMonth(1), now);
            case "2" -> filterByDate(transactions,
                    now.minusMonths(1).withDayOfMonth(1),
                    now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth()));
            case "3" -> filterByDate(transactions, now.withDayOfYear(1), now);
            case "4" -> filterByDate(transactions,
                    now.minusYears(1).withDayOfYear(1),
                    now.minusYears(1).withDayOfYear(365));
            case "5" -> {
                System.out.print("Enter vendor: ");
                String vendor = scanner.nextLine();
                transactions.stream()
                        .filter(t -> t.getVendor().equalsIgnoreCase(vendor))
                        .forEach(System.out::println);
            }
            case "6" -> {
                customSearch(transactions);
            }
        }
    }

    private static void filterByDate(List<Transaction> list, LocalDate start, LocalDate end) {
        list.stream()
                .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
                .forEach(System.out::println);
    }
    public static LocalDate pickDate(String compare,String message){
        String userStartDate = getUserString(message);
        LocalDate date = LocalDate.parse(compare, FORMAT);
        if (!userStartDate.isEmpty()) {
            date = LocalDate.parse(userStartDate, FORMAT);
        }
        return date;
    }

    public static double pickAmount(String message){
       String userAmount = getUserString(message);
       double amount = 0;
        if (!userAmount.isEmpty()) {
            amount = parseDouble(userAmount);
        }
       return amount;
    }

    public static void customSearch(List<Transaction> list){
        System.out.println("Please enter the following filters:");
        LocalDate userStartDate = pickDate("01/01/1900","Enter Starting Date (MM/dd/yyyy):");
        LocalDate userEndDate = pickDate("12/31/4000","End Date (MM/dd/yyyy): ");

        String userDesc = getUserString("Description: ");

        String userVendor = getUserString("Vendor: ");

        double amount = pickAmount("Amount: $");

        list.stream()
                .filter(t -> !t.getDate().isBefore(userStartDate)
                        && !t.getDate().isAfter(userEndDate))

                .filter(t -> t.getDescription().toLowerCase().contains(userDesc)
                || t.getVendor().toLowerCase().contains(userVendor)
                || t.getAmount() == amount)
                .forEach(System.out::println);
    }
}

