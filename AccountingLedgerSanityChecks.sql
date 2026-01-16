USE accountingledger;

-- ------------------------------------------------------------
-- 1) BASIC SANITY CHECKS
-- ------------------------------------------------------------

-- Confirm correct database
SELECT DATABASE() AS current_database;

-- Confirm total number of transactions
SELECT COUNT(*) AS total_transactions
FROM transactions;

-- Verify 2-year date range
SELECT
  MIN(trans_date) AS earliest_transaction,
  MAX(trans_date) AS latest_transaction
FROM transactions;

-- Count deposits vs payments
SELECT
  SUM(amount > 0) AS deposit_count,
  SUM(amount < 0) AS payment_count
FROM transactions;

-- ------------------------------------------------------------
-- 2) FINANCIAL INTEGRITY CHECKS
-- ------------------------------------------------------------

-- Current balance (should match Java getBalance())
SELECT
  ROUND(SUM(amount), 2) AS current_balance
FROM transactions;

-- Largest deposit and largest payment
SELECT
  MAX(amount) AS largest_deposit,
  MIN(amount) AS largest_payment
FROM transactions;

-- Average transaction amount
SELECT
  ROUND(AVG(amount), 2) AS average_transaction
FROM transactions;

-- ------------------------------------------------------------
-- 3) LEDGER VIEW QUERIES (Matches Ledger screens)
-- ------------------------------------------------------------

-- All transactions (newest first)
SELECT *
FROM transactions
ORDER BY trans_date DESC, trans_time DESC;

-- All deposits only
SELECT *
FROM transactions
WHERE amount > 0
ORDER BY trans_date DESC, trans_time DESC;

-- All payments only
SELECT *
FROM transactions
WHERE amount < 0
ORDER BY trans_date DESC, trans_time DESC;

-- ------------------------------------------------------------
-- 4) REPORT QUERIES (Matches Reports Menu)
-- ------------------------------------------------------------

-- Month-to-Date
SELECT *
FROM transactions
WHERE trans_date BETWEEN
      DATE_FORMAT(CURDATE(), '%Y-%m-01')
  AND CURDATE()
ORDER BY trans_date DESC, trans_time DESC;

-- Previous Month
SELECT *
FROM transactions
WHERE trans_date BETWEEN
      DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01')
  AND LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))
ORDER BY trans_date DESC, trans_time DESC;

-- Year-to-Date
SELECT *
FROM transactions
WHERE trans_date BETWEEN
      DATE_FORMAT(CURDATE(), '%Y-01-01')
  AND CURDATE()
ORDER BY trans_date DESC, trans_time DESC;

-- Previous Year
SELECT *
FROM transactions
WHERE YEAR(trans_date) = YEAR(CURDATE()) - 1
ORDER BY trans_date DESC, trans_time DESC;

-- ------------------------------------------------------------
-- 5) SEARCH & FILTER QUERIES
-- ------------------------------------------------------------

-- Search by vendor (case-insensitive)
SELECT *
FROM transactions
WHERE LOWER(vendor) LIKE '%amazon%'
ORDER BY trans_date DESC, trans_time DESC;

-- ------------------------------------------------------------
-- 6) AGGREGATE / ANALYTICS (Bonus / Portfolio Level)
-- ------------------------------------------------------------

-- Total amount by vendor
SELECT
  vendor,
  ROUND(SUM(amount), 2) AS total_amount
FROM transactions
GROUP BY vendor
ORDER BY total_amount ASC;

-- Monthly net totals
SELECT
  DATE_FORMAT(trans_date, '%Y-%m') AS month,
  ROUND(SUM(amount), 2) AS net_amount
FROM transactions
GROUP BY month
ORDER BY month;

-- ------------------------------------------------------------
-- 7) PERFORMANCE / INDEX CHECKS
-- ------------------------------------------------------------

-- Verify vendor index usage
EXPLAIN
SELECT *
FROM transactions
WHERE vendor = 'Amazon';

-- Verify date index usage
EXPLAIN
SELECT *
FROM transactions
WHERE trans_date BETWEEN '2025-01-01' AND '2025-12-31';