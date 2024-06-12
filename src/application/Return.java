package application;

public class Return {
    private int borrowID;
    private int cusID;
    private int staffID;
    private int bookID;
    private int quantity;
    private String borrowDate;
    private String returnDate;
    private String returned_date;
    private String status;

    public Return(int borrowID, int cusID, int staffID, int bookID, int quantity, String borrowDate, String returnDate, String returned_date, String status) {
        this.borrowID = borrowID;
        this.cusID = cusID;
        this.staffID = staffID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.returned_date = returned_date;
        this.status = status;
    }

    public int getBorrowID() {
        return borrowID;
    }

    public int getCusID() {
        return cusID;
    }

    public int getStaffID() {
        return staffID;
    }

    public int getBookID() {
        return bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getReturned_date() {
        return returned_date;
    }

    public String getStatus() {
        return status;
    }
}
