package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BookControl {
    // Navbar
    @FXML
    Label staffName;
    @FXML
    Button btnLoan;
    @FXML
    Button btnDashboard;
    @FXML
    Button btnBorrow;
    @FXML
    Button btnReturn;
    @FXML
    Button btnCustomer;
    @FXML
    Button btnCategory;
    @FXML
    Button btnLogout;

    // Func
    @FXML
    TextField txtSearch;
    @FXML
    Button btnSearch;
    @FXML
    Button btnAddBook;
    @FXML
    Button btnUpdateBook;
    @FXML
    Button btnDeleteBook;

    // View
    @FXML
    private TableView<Book> tbBook;

    @FXML
    private TableColumn<Book, Integer> colId;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, Integer> colCateId;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, Integer> colPublicationYear;
    @FXML
    private TableColumn<Book, Integer> colQuantity;
    @FXML
    private TableColumn<Book, String> colStatus;

    @FXML
    public void initialize() {
        // Initialize columns
        colId.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCateId.setCellValueFactory(new PropertyValueFactory<>("cateID"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPublicationYear.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load data into TableView
        loadBooks();
    }


    @FXML
    public void DashboardClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnDashboard.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    public void LoanClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Loan.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnLoan.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Loan Information");
        stage.show();
    }

    @FXML
    public void BorrowClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Borrow.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBorrow.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Borrow Information");
        stage.show();
    }

    @FXML
    public void ReturnClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Return.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnReturn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Return Information");
        stage.show();
    }

    @FXML
    public void CustomerClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Customer.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCustomer.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Customer Information");
        stage.show();
    }

    @FXML
    public void CategoryClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Category.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCategory.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Category Information");
        stage.show();
    }

    @FXML
    public void LogoutClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    @FXML
    public void loadBooks() {
        ObservableList<Book> books = FXCollections.observableArrayList();

        String query = "SELECT * FROM book";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int bookID = rs.getInt("bookID");
                String title = rs.getString("title");
                int cateID = rs.getInt("cateID");
                String author = rs.getString("author");
                int publicationYear = rs.getInt("publication_year");
                int quantity = rs.getInt("quantity");
                String status = rs.getString("status");

                Book book = new Book(bookID, title, cateID, author, publicationYear, quantity, status);
                books.add(book);
            }

            tbBook.setItems(books);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Error loading books: " + e.getMessage());
        }
    }

    @FXML
    public void SearchClick(MouseEvent event) {
        String searchText = txtSearch.getText();
        ObservableList<Book> books = FXCollections.observableArrayList();

        String query = "SELECT * FROM book WHERE title LIKE ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int bookID = rs.getInt("bookID");
                String title = rs.getString("title");
                int cateID = rs.getInt("cateID");
                String author = rs.getString("author");
                int publicationYear = rs.getInt("publication_year");
                int quantity = rs.getInt("quantity");
                String status = rs.getString("status");

                Book book = new Book(bookID, title, cateID, author, publicationYear, quantity, status);
                books.add(book);
            }

            tbBook.setItems(books);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Error", "Error fetching books: " + e.getMessage());
        }
    }

    @FXML
    public void AddBookClick(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/NewBook.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add New Book Information");
        primaryStage.show();
    }

    @FXML
    public void UpdateBookClick(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/UpdateBook.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Update Book Information");
        primaryStage.show();
    }

    @FXML
    public void DeleteBookClick(MouseEvent event) {
        Book selectedBook = tbBook.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            String query = "DELETE FROM book WHERE bookID = ?";
            try (Connection conn = Connect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, selectedBook.getBookID());
                pstmt.executeUpdate();
                tbBook.getItems().remove(selectedBook);
                showAlert(AlertType.INFORMATION, "Success", "Book deleted successfully.");
                
             // Refresh the category list in the main view
    		    BookControl controller = new BookControl();
    		    controller.loadBooks();
            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Error", "Error deleting book: " + e.getMessage());
            }
        } else {
            showAlert(AlertType.WARNING, "No Selection", "Please select a book to delete.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

  
}
