package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UpdateCustomerControl {
	@FXML
	TextField txtCustomerID;
	@FXML
	TextField txtCustomerName;
	@FXML
	TextField txtPhone;
	@FXML
	Button btnUpdate;
	@FXML
	Button btnCancel;
	
	@FXML
	public void UpdateClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Customer.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnUpdate.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.show();        
	}
	
	@FXML
	public void CancelClick(MouseEvent event) throws IOException {
		
	}
}
