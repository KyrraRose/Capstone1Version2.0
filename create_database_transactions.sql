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

USE transactions;

INSERT INTO transactions (transaction_id, trans_date, trans_time, description, vendor, amount) VALUES
(1, '2024-01-02', '09:00:00', 'Payroll Deposit', 'Employer Payroll', 2450.00),
(2, '2024-01-03', '07:45:00', 'Card Purchase', 'Starbucks', -14.65),
(3, '2024-01-04', '14:22:00', 'Online Purchase', 'Amazon', -126.42),
(4, '2024-01-05', '16:30:00', 'Bill Payment', 'Electric Company', -212.87),
(5, '2024-01-07', '11:15:00', 'Grocery', 'Walmart', -95.34),
(6, '2024-01-10', '10:00:00', 'Deposit', 'Tax Refund', 975.00),
(7, '2024-01-12', '00:01:00', 'Subscription', 'Netflix', -15.99),
(8, '2024-01-13', '18:45:00', 'Dining', 'Target', -42.10),
(9, '2024-01-15', '08:30:00', 'Fuel', 'Shell', -48.72),
(10, '2024-01-18', '12:00:00', 'Transfer In', 'Savings Transfer', 500.00),

(11, '2024-02-01', '09:00:00', 'Payroll Deposit', 'Employer Payroll', 2450.00),
(12, '2024-02-02', '15:10:00', 'Online Purchase', 'Amazon', -89.44),
(13, '2024-02-03', '07:30:00', 'Dining', 'Starbucks', -11.75),
(14, '2024-02-05', '16:45:00', 'Bill Payment', 'Water Utility', -67.30),
(15, '2024-02-06', '13:20:00', 'Grocery', 'Costco', -138.60),
(16, '2024-02-08', '08:15:00', 'Fuel', 'ExxonMobil', -52.18),
(17, '2024-02-10', '00:01:00', 'Subscription', 'Spotify', -10.99),
(18, '2024-02-12', '11:30:00', 'Transfer Out', 'Savings Transfer', -300.00),
(19, '2024-02-14', '19:20:00', 'Dining', 'Uber Eats', -36.44),
(20, '2024-02-18', '14:00:00', 'ATM Withdrawal', 'Bank ATM', -120.00),

(21, '2024-03-01', '09:00:00', 'Payroll Deposit', 'Employer Payroll', 2450.00),
(22, '2024-03-02', '16:35:00', 'Online Purchase', 'Amazon', -64.33),
(23, '2024-03-04', '07:50:00', 'Dining', 'Starbucks', -9.85),
(24, '2024-03-05', '17:00:00', 'Bill Payment', 'Internet Provider', -89.99),
(25, '2024-03-07', '10:45:00', 'Grocery', 'Walmart', -102.11),
(26, '2024-03-09', '08:20:00', 'Fuel', 'Shell', -44.88),
(27, '2024-03-10', '00:01:00', 'Subscription', 'Netflix', -15.99),
(28, '2024-03-12', '19:10:00', 'Dining', 'Target', -58.24),
(29, '2024-03-15', '14:30:00', 'Deposit', 'Freelance Payment', 680.00),
(30, '2024-03-18', '13:45:00', 'ATM Withdrawal', 'Bank ATM', -80.00),

(31, '2024-04-01', '09:00:00', 'Payroll Deposit', 'Employer Payroll', 2450.00),
(32, '2024-04-02', '12:50:00', 'Grocery', 'Costco', -156.78),
(33, '2024-04-04', '07:40:00', 'Dining', 'Starbucks', -12.95),
(34, '2024-04-05', '16:30:00', 'Bill Payment', 'Electric Company', -198.40),
(35, '2024-04-06', '15:25:00', 'Online Purchase', 'Amazon', -143.21),
(36, '2024-04-08', '08:10:00', 'Fuel', 'Shell', -47.32),
(37, '2024-04-10', '00:01:00', 'Subscription', 'Spotify', -10.99),
(38, '2024-04-12', '11:00:00', 'Transfer Out', 'Savings Transfer', -400.00),
(39, '2024-04-15', '18:30:00', 'Dining', 'Uber', -26.90),
(40, '2024-04-18', '10:15:00', 'Deposit', 'Bonus Deposit', 1200.00),
(41, '2025-12-28', '14:55:00', 'Online Purchase', 'Amazon', -132.19),
(42, '2025-12-29', '08:00:00', 'Dining', 'Starbucks', -16.45),
(43, '2025-12-30', '15:30:00', 'ATM Withdrawal', 'Bank ATM', -100.00),
(44, '2025-12-31', '23:59:00', 'Deposit', 'Year End Adjustment', 850.00);