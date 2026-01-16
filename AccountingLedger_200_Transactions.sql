
-- ============================================================
-- ACCOUNTING LEDGER – FULL DATA DUMP (200 TRANSACTIONS)
-- ============================================================

CREATE DATABASE IF NOT EXISTS AccountingLedger;
USE AccountingLedger;

DROP TABLE IF EXISTS transactions;

CREATE TABLE transactions (
  transaction_id INT AUTO_INCREMENT PRIMARY KEY,
  trans_date DATE NOT NULL,
  trans_time TIME NOT NULL,
  description VARCHAR(120) NOT NULL,
  vendor VARCHAR(80) NOT NULL,
  amount DECIMAL(10,2) NOT NULL
);

CREATE INDEX idx_transactions_vendor ON transactions(vendor);
CREATE INDEX idx_transactions_date ON transactions(trans_date);

-- ------------------------------------------------------------
-- Seed exactly 200 transactions across 2 years
-- ------------------------------------------------------------
DROP PROCEDURE IF EXISTS seed_transactions_200;

DELIMITER $$

CREATE PROCEDURE seed_transactions_200()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE start_date DATE;
  DECLARE end_date DATE;
  DECLARE span_days INT;

  SET end_date   = CURDATE();
  SET start_date = DATE_SUB(end_date, INTERVAL 2 YEAR);
  SET span_days  = DATEDIFF(end_date, start_date);

  WHILE i <= 200 DO
    SET @d := DATE_ADD(start_date, INTERVAL FLOOR((i-1) * span_days / 199) DAY);
    SET @t := MAKETIME(MOD(i*3,24), MOD(i*7,60), MOD(i*11,60));

    SET @vendor := ELT(MOD(i-1, 10) + 1,
      'Amazon','Walmart','Target','Shell','Netflix',
      'Employer Inc','Venmo','CVS','Uber','Bank ATM'
    );

    IF MOD(i,5) = 0 THEN
      SET @desc := 'Payroll Deposit';
      SET @amt  := 2500.00 + MOD(i*37, 900);
    ELSE
      SET @desc := 'General Expense';
      SET @amt  := -1 * (20.00 + MOD(i*41, 230));
    END IF;

    INSERT INTO transactions (trans_date, trans_time, description, vendor, amount)
    VALUES (@d, @t, @desc, @vendor, ROUND(@amt,2));

    SET i = i + 1;
  END WHILE;
END$$

DELIMITER ;

CALL seed_transactions_200();
DROP PROCEDURE IF EXISTS seed_transactions_200;
