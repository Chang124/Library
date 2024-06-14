CREATE DATABASE library;
USE library;
-- DROP DATABASE IF EXISTS library;

-- Create Table staff
CREATE TABLE staff (
    staffID INT PRIMARY KEY IDENTITY(1,1),
    staffName VARCHAR(25),
    password VARCHAR(25),
    phone VARCHAR(25)
);

-- Create Table customer
CREATE TABLE customer (
    cusID INT PRIMARY KEY IDENTITY(1,1),
    cusName VARCHAR(25),
    phone VARCHAR(25)
);

-- Create Table category
CREATE TABLE category (
    cateID INT PRIMARY KEY IDENTITY(1,1),
    cate VARCHAR(25)
);

-- Create Table book
CREATE TABLE book (
    bookID INT PRIMARY KEY IDENTITY(1,1),
    title VARCHAR(100),
    cateID INT,
    author VARCHAR(100),
    publication_year INT,
    quantity INT,
    status VARCHAR(25) DEFAULT 'Available', -- Changed stautus to status
    FOREIGN KEY(cateID) REFERENCES category(cateID)
);

-- Create Table borrow_record
CREATE TABLE borrow_record (
    borrowID INT PRIMARY KEY IDENTITY(1,1),
    cusID INT,
    staffID INT,
    bookID INT,
    quantity INT,
    released_date DATE,
    return_date DATE,
    FOREIGN KEY(bookID) REFERENCES book(bookID),
    FOREIGN KEY(cusID) REFERENCES customer(cusID),
    FOREIGN KEY(staffID) REFERENCES staff(staffID)
);

-- Create Table return_record
CREATE TABLE return_record (
    borrowID INT PRIMARY KEY,
    staffID INT,
    returned_date DATE,
    FOREIGN KEY(borrowID) REFERENCES borrow_record(borrowID),
    FOREIGN KEY(staffID) REFERENCES staff(staffID)
);

-- Create Trigger to Update Book Quantity
CREATE TRIGGER update_book_quantity
ON borrow_record
AFTER INSERT
AS
BEGIN
    UPDATE book
    SET book.quantity = book.quantity - inserted.quantity
    FROM inserted
    WHERE book.bookID = inserted.bookID;
END;

-- Create Trigger to Update Book Quantity on Return
-- Create Trigger to Update Book Quantity on Return
CREATE TRIGGER update_book_quantity_on_return
ON return_record
AFTER INSERT
AS
BEGIN
    -- Update the book quantity based on the returned books
    UPDATE b
    SET b.quantity = b.quantity + br.quantity
    FROM book b
    INNER JOIN borrow_record br ON b.bookID = br.bookID
    INNER JOIN inserted i ON br.borrowID = i.borrowID;
END;
-- Create Trigger to Update Book Status
CREATE TRIGGER trg_update_book_status
ON book 
AFTER INSERT, UPDATE, DELETE
AS
BEGIN
    UPDATE b
    SET b.status = CASE WHEN b.quantity > 0 THEN 'Available' ELSE 'Unavailable' END
    FROM book b
    INNER JOIN (
        SELECT bookID, SUM(quantity) AS totalQuantity
        FROM (
            SELECT bookID, quantity FROM inserted
            UNION ALL
            SELECT bookID, -quantity FROM deleted
        ) AS changes
        GROUP BY bookID
    ) AS change_summary ON b.bookID = change_summary.bookID;
END;

-- Add Constraint to ensure return_date > released_date
ALTER TABLE borrow_record
ADD CONSTRAINT chk_return_date CHECK (return_date > released_date);
