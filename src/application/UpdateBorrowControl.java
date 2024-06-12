package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateBorrowControl {

    @FXML
    private TextField txtCustomerID; // fx:id should be txtCustomerID
    @FXML
    private TextField txtBookID; // fx:id should be txtBookID
    @FXML
    private TextField txtQuantity; // fx:id should be txtQuantity
    @FXML
    private DatePicker dtBorrow; // fx:id should be dtBorrow
    @FXML
    private DatePicker dtReturn; // fx:id should be dtReturn
    @FXML
    private Button btnUpdate; // fx:id should be btnUpdate
    @FXML
    private Button btnCancel; // fx:id should be btnCancel

    private int borrowID; // Store the borrow ID that is being updated

    @FXML
    public void initialize() {
        // Initialization if needed
    }

    public void initData(int borrowID, int customerID, int bookID, int quantity, LocalDate borrowDate, LocalDate returnDate) {
        this.borrowID = borrowID;
        txtCustomerID.setText(String.valueOf(customerID));
        txtBookID.setText(String.valueOf(bookID));
        txtQuantity.setText(String.valueOf(quantity));
        dtBorrow.setValue(borrowDate);
        dtReturn.setValue(returnDate);
    }

    @FXML
    public void UpdateClick(MouseEvent event) {
        String customerIDText = txtCustomerID.getText();
        String bookIDText = txtBookID.getText();
        String quantityText = txtQuantity.getText();
        LocalDate borrowDate = dtBorrow.getValue();
        LocalDate returnDate = dtReturn.getValue();

        if (customerIDText.isEmpty() || bookIDText.isEmpty() || quantityText.isEmpty() || borrowDate == null || returnDate == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all fields.");
            return;
        }

        int customerID;
        int bookID;
        int quantity;

        try {
            customerID = Integer.parseInt(customerIDText);
            bookID = Integer.parseInt(bookIDText);
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Customer ID, Book ID, and Quantity must be valid numbers.");
            return;
        }

        String query = "UPDATE borrow SET customerID = ?, bookID = ?, quantity = ?, borrow_date = ?, return_date = ? WHERE borrowID = ?";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerID);
            pstmt.setInt(2, bookID);
            pstmt.setInt(3, quantity);
            pstmt.setDate(4, java.sql.Date.valueOf(borrowDate));
            pstmt.setDate(5, java.sql.Date.valueOf(returnDate));
            pstmt.setInt(6, borrowID);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Show success message
                showAlert(AlertType.INFORMATION, "Success", "Borrow record updated successfully.");

                // Close the current stage
                Stage stage = (Stage) btnUpdate.getScene().getWindow();
                stage.close();

                // Refresh the borrow list in the main view
                BorrowControl controller = new BorrowControl();
                controller.loadBorrows();

            } else {
                showAlert(AlertType.ERROR, "Error", "Error updating borrow record. Borrow ID might not exist.");
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error updating borrow record: " + e.getMessage());
        }
    }

    @FXML
    public void CancelClick(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
