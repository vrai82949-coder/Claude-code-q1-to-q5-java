-- Q16 - Optional manual setup for EmployeeApp.java
--
-- EmployeeApp creates the database and table by itself on first run, so running this
-- file is optional. It is useful if you want to see or create the schema yourself,
-- e.g. in MySQL Workbench or with:   mysql -u root -p < setup.sql

CREATE DATABASE IF NOT EXISTS employee_db;
USE employee_db;

CREATE TABLE IF NOT EXISTS employees (
    id         INT AUTO_INCREMENT PRIMARY KEY,   -- MySQL assigns 1, 2, 3, ... automatically
    name       VARCHAR(100)   NOT NULL,
    department VARCHAR(50)    NOT NULL,
    salary     DECIMAL(10, 2) NOT NULL           -- exact decimal, the usual type for money
);
