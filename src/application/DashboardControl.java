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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DashboardControl {
    // Navbar
    @FXML
    private Label staffName;
    @FXML
    private Button btnLoan;
    @FXML
    private Button btnBook;
    @FXML
    private Button btnBorrow;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnCustomer;
    @FXML
    private Button btnCategory;
    @FXML
    private Button btnLogout;
    @FXML
    private Label sumQuantity;
    @FXML
    private Label sumBorrow;
    @FXML
    private Label sumInventory;
    @FXML
    private Label sumCustomer;

    // View
    @FXML
    private TableView<Book> tbAvailableBook;
    @FXML
    private TableColumn<Book, Integer> colBookID;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, Integer> colQuantity;

    private ObservableList<Book> availableBooks;
    public String loggedInUserName;

    @FXML
    public void initialize() {
        // Initialize columns for the available books table
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Load data into the available books table
        loadAvailableBooks();
    }

    public void setLoggedInUserName(String userName) {
        this.loggedInUserName = userName;
        staffName.setText("Staff: " + loggedInUserName); // Set the label text here
    }


    @FXML
    public void BookClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Book.fxml"));
        Parent root = loader.load();
        
        BookControl BookController = loader.getController();
        BookController.setLoggedInUserName(loggedInUserName); // Pass the logged in username to NewBookControl
        
        Stage stage = (Stage) btnBook.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("New Book");
        stage.show();
    }


    @FXML
    public void BorrowClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Borrow.fxml"));
        Parent root = loader.load();
        
        BorrowControl borrowController = loader.getController();
        borrowController.initData(availableBooks); // Pass available books data to BorrowController
        borrowController.setLoggedInUserName(loggedInUserName);
        
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
    
        ReturnControl ReturnControl = loader.getController();
        ReturnControl.setLoggedInUserName(loggedInUserName); // Pass the logged in username to NewBookControl
      
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
        
        CustomerControl CustomerControl = loader.getController();
        CustomerControl.setLoggedInUserName(loggedInUserName); 
        
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
        
        CategoryControl CategoryControl = loader.getController();
        CategoryControl.setLoggedInUserName(loggedInUserName); 
        
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

    private void loadAvailableBooks() {
        availableBooks = FXCollections.observableArrayList();
        
        String query = "SELECT bookID, title, author, quantity FROM book WHERE status = 'Available'";
        
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int bookID = rs.getInt("bookID");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");

                // Use the new Book constructor
                Book book = new Book(bookID, title, author, quantity);
                availableBooks.add(book);
            }

            tbAvailableBook.setItems(availableBooks);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error loading available books: " + e.getMessage());
        }
    }

    public ObservableList<Book> getAvailableBooks() {
        return availableBooks;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
