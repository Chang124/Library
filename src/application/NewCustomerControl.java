package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NewCustomerControl {

    @FXML
    private TextField txtCustomerID;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtPhone;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnCancel;

    @FXML
    public void CreateClick(MouseEvent event) {
        int customerID;
        try {
            customerID = Integer.parseInt(txtCustomerID.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Validation Error", "Customer ID must be a number.");
            return;
        }
        String customerName = txtCustomerName.getText().trim();
        String phone = txtPhone.getText().trim();

        if (customerName.isEmpty() || phone.isEmpty()) {
            showAlert(AlertType.WARNING, "Validation Error", "Please fill in all fields.");
            return;
        }

        String query = "INSERT INTO customer (cusID, cusName, phone) VALUES (?, ?, ?)";
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerID);
            pstmt.setString(2, customerName);
            pstmt.setString(3, phone);

            pstmt.executeUpdate();

            showAlert(AlertType.INFORMATION, "Success", "Customer added successfully.");

            // Close the current stage
            Stage stage = (Stage) btnCreate.getScene().getWindow();
            stage.close();

            // Refresh the customer list in the main view
            CustomerControl controller = new CustomerControl();
            controller.loadCustomers();

        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Error adding customer: " + e.getMessage());
        }
    }

    @FXML
    public void CancelClick(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
