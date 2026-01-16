package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction {
    private int transaction_id;
    private String trans_date, trans_time, description, vendor, amountString;
    private double amount;
    private LocalDate date;
    private LocalTime time;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    public Transaction(){
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.description = "";
        this.vendor = "";
        this.amount = 0;
    }

   /** public Transaction(int transaction_id, String trans_date, String trans_time,
                       String description, String vendor, String amountString) {
        this.transaction_id = transaction_id;
        this.trans_date = trans_date;
        this.trans_time = trans_time;
        this.description = description;
        this.vendor = vendor;
        this.amountString = amountString;
    }**/

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getAmountString() {
        return amountString;
    }


    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public double getAmount() { return amount; }

    public String toCSV() {
        return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
    }

    @Override
    public String toString() {
        return date + " | " + time + " | " + description + " | " + vendor + " | " + amount;
    }
}
