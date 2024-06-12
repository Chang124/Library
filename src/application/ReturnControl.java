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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReturnControl {
    // Navbar
    @FXML
    Label staffName;
    @FXML
    Button btnLoan;
    @FXML
    Button btnBook;
    @FXML
    Button btnBorrow;
    @FXML
    Button btnDashboard;
    @FXML
    Button btnCustomer;
    @FXML
    Button btnCategory;
    @FXML
    Button btnLogout;

    // Functional buttons
    @FXML
    TextField txtSearch;
    @FXML
    Button btnSearch;
    @FXML
    Button btnAddReturn;
    @FXML
    Button btnUpdateReturn;
    @FXML
    Button btnDeleteReturn;

    // TableView
    @FXML
    private TableView<Return> tbReturn;
    @FXML
    private TableColumn<Return, Integer> borrowIDColumn;
    @FXML
    private TableColumn<Return, Integer> cusIDColumn;
    @FXML
    private TableColumn<Return, Integer> bookIDColumn;
    @FXML
    private TableColumn<Return, Integer> quantityColumn;
    @FXML
    private TableColumn<Return, String> borrowDateColumn;
    @FXML
    private TableColumn<Return, String> returnDateColumn;
    @FXML
    private TableColumn<Return, String> returnedDateColumn;
    @FXML
    private TableColumn<Return, String> statusColumn;

    @FXML
    public void initialize() {
        borrowIDColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("borrowID"));
        cusIDColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("cusID"));
        bookIDColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("bookID"));
        quantityColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("quantity"));
        borrowDateColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("returnDate"));
        returnedDateColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("returned_date"));
        statusColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("status"));

        // Load data into the table
        loadReturns();
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
    public void BookClick(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Book.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBook.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Book Information");
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
    public void loadReturns() {
        ObservableList<Return> returns = FXCollections.observableArrayList();

        String query = "SELECT br.borrowID, br.cusID, br.staffID, br.bookID, br.quantity, br.released_date, br.return_date, rr.returned_date, " +
                       "CASE WHEN rr.returned_date IS NOT NULL THEN 'Returned' ELSE 'Not Returned' END AS status " +
                       "FROM borrow_record br " +
                       "LEFT JOIN return_record rr ON br.borrowID = rr.borrowID";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int borrowID = rs.getInt("borrowID");
                int cusID = rs.getInt("cusID");
                int staffID = rs.getInt("staffID");
                int bookID = rs.getInt("bookID");
                int quantity = rs.getInt("quantity");
                String borrowDate = rs.getString("released_date");
                String returnDate = rs.getString("return_date");
                String returned_date = rs.getString("returned_date");
                String status = rs.getString("status");

                Return ret = new Return(borrowID, cusID, staffID, bookID, quantity, borrowDate, returnDate, returned_date, status);
                returns.add(ret);
            }

            tbReturn.setItems(returns);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading return records: " + e.getMessage());
        }
    }

    @FXML
    public void SearchClick(MouseEvent event) {
        String searchText = txtSearch.getText();
        ObservableList<Return> returns = FXCollections.observableArrayList();

        String query = "SELECT br.borrowID, br.cusID, br.staffID, br.bookID, br.quantity, br.released_date, br.return_date, rr.returned_date, " +
                       "CASE WHEN rr.returned_date IS NOT NULL THEN 'Returned' ELSE 'Not Returned' END AS status " +
                       "FROM borrow_record br " +
                       "LEFT JOIN return_record rr ON br.borrowID = rr.borrowID " +
                       "WHERE br.borrowID LIKE ? OR br.cusID LIKE ? OR br.staffID LIKE ? OR br.bookID LIKE ? OR br.quantity LIKE ? " +
                       "OR br.released_date LIKE ? OR br.return_date LIKE ? OR rr.returned_date LIKE ?";

        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + searchText + "%";
            for (int i = 1; i <= 8; i++) {
                pstmt.setString(i, searchPattern);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int borrowID = rs.getInt("borrowID");
                int cusID = rs.getInt("cusID");
                int staffID = rs.getInt("staffID");
                int bookID = rs.getInt("bookID");
                int quantity = rs.getInt("quantity");
                String borrowDate = rs.getString("released_date");
                String returnDate = rs.getString("return_date");
                String returned_date = rs.getString("returned_date");
                String status = rs.getString("status");

                Return ret = new Return(borrowID, cusID, staffID, bookID, quantity, borrowDate, returnDate, returned_date, status);
                returns.add(ret);
            }

            tbReturn.setItems(returns);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error fetching return records: " + e.getMessage());
        }
    }


    @FXML
    public void AddReturnClick(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/NewReturn.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add New Return Information");

        primaryStage.show();
    }

    @FXML
    public void UpdateReturnClick(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/UpdateReturn.fxml"));
        Scene scene = new Scene(root);

        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Update Return Information");

        primaryStage.show();
    }

    @FXML
    public void DeleteReturnClick(MouseEvent event) {
        Return selectedReturn = tbReturn.getSelectionModel().getSelectedItem();
        if (selectedReturn != null) {
            String query = "DELETE FROM return_record WHERE borrowID = ?";
            try (Connection conn = Connect.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, selectedReturn.getBorrowID());
                pstmt.executeUpdate();
                tbReturn.getItems().remove(selectedReturn);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Return record deleted successfully.");

                // Refresh the return list in the main view
                loadReturns();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting return record: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a return record to delete.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
