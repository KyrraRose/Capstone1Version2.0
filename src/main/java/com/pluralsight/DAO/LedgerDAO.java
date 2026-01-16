package com.pluralsight.DAO;

import com.pluralsight.Ledger;
import com.pluralsight.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.pluralsight.DAO.DataManager.connection;
import static com.sun.imageio.plugins.jpeg.JPEG.vendor;

public class LedgerDAO {
    private final DataSource dataSource;
    //private static Ledger ledger;

    public LedgerDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> ledger = new ArrayList<>();
        System.out.println("Loading all transactions...\n\n");

        String query = "SELECT * FROM transactions";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery()) {

            while (results.next()) {

                    Transaction transaction = mapToTransaction(results);
                    ledger.add(transaction);

            }
            return ledger;
        } catch (SQLException e) {
            e.getMessage();
            throw new RuntimeException(e);

        }
    }
    public static Transaction mapToTransaction(ResultSet rs){
        try{
        Transaction transaction = new Transaction();
        transaction.setTransaction_id(rs.getInt("transaction_id"));
        transaction.setTrans_date(rs.getObject("trans_date", LocalDate.class));
        transaction.setTrans_time(rs.getObject("trans_time", LocalTime.class));
        transaction.setDescription(rs.getString("description"));
        transaction.setVendor(rs.getString("vendor"));
        transaction.setAmount(rs.getDouble("amount");
        } catch (Exception e) {
            e.getMessage();
        }

    }

}
