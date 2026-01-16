package com.pluralsight;

import com.pluralsight.DAO.DataManager;
import com.pluralsight.DAO.LedgerDAO;

import java.util.ArrayList;

public class LedgerService {
    public static LedgerDAO ledgerDAO;

    public LedgerService(LedgerDAO ledgerDAO) {
        this.ledgerDAO = ledgerDAO;
    }
public static ArrayList<Transaction> getLedger(){
    return ledgerDAO.getAllTransactions();
}
}
