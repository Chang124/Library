package application;

public class Book {
    private int bookID;
    private String title;
    private int cateID;
    private String author;
    private int publicationYear;
    private int quantity;
    private String status;

    public Book(int bookID, String title, int cateID, String author, int publicationYear, int quantity, String status) {
        this.bookID = bookID;
        this.title = title;
        this.cateID = cateID;
        this.author = author;
        this.publicationYear = publicationYear;
        this.quantity = quantity;
        this.status = status;
    }

    // Getter methods
    public int getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public int getCateID() {
        return cateID;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }
}
