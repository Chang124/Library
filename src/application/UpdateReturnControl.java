package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateReturnControl {

    @FXML
    private TextField txtBorrowID;
    @FXML
    private TextField txtQuantity;
    @FXML
    private DatePicker dtReturn;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnCancel;

    private int borrowID; // Store the borrow ID that is being updated

    private TableView<Return> tbReturn; // Reference to tbReturn from ReturnControl

    public void setTbReturn(TableView<Return> tbReturn) {
        this.tbReturn = tbReturn;
    }

    public void initData(int borrowID, int quantity, LocalDate returnDate) {
        this.borrowID = borrowID;
        txtBorrowID.setText(String.valueOf(borrowID));
        txtQuantity.setText(String.valueOf(quantity));
        dtReturn.setValue(returnDate);
    }

    @FXML
    public void UpdateClick(MouseEvent event) {
        if (tbReturn == null) {
            showAlert(Alert.AlertType.ERROR, "Internal Error", "Return table view is not properly initialized.");
            return;
        }

        Return selectedReturn = tbReturn.getSelectionModel().getSelectedItem();
        if (selectedReturn != null) {
            try {
                int quantity = Integer.parseInt(txtQuantity.getText());
                LocalDate returnDate = dtReturn.getValue();

                if (quantity <= 0 || returnDate == null) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all fields.");
                    return;
                }

                String query = "UPDATE return_record SET quantity = ?, returned_date = ? WHERE borrowID = ?";
                try (Connection conn = Connect.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(query)) {

                    pstmt.setInt(1, quantity);
                    pstmt.setDate(2, Date.valueOf(returnDate));
                    pstmt.setInt(3, selectedReturn.getBorrowID());

                    int affectedRows = pstmt.executeUpdate();

                    if (affectedRows > 0) {
                        // Show success message
                        showAlert(Alert.AlertType.INFORMATION, "Success", "Return record updated successfully.");

                        // Close the current stage
                        Stage stage = (Stage) btnUpdate.getScene().getWindow();
                        stage.close();

                        // Refresh the return list in the main view
                        if (tbReturn != null) {
                            ReturnControl controller = new ReturnControl();
                            controller.loadReturns();
                        }

                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Error updating return record. Borrow ID might not exist.");
                    }

                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Error updating return record: " + e.getMessage());
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Quantity must be a valid number.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a return record to update.");
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
