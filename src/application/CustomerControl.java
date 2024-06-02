package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CustomerControl {
	// navbar
	@FXML
	Label staffName;
	@FXML
	Button btnLoan;
	@FXML
	Button btnBook;
	@FXML
	Button btnBorrow;
	@FXML
	Button btnReturn;
	@FXML
	Button btnDashboard;
	@FXML
	Button btnCategory;
	@FXML
	Button btnLogout;
	
	// func
	@FXML
	TextField txtSearch;
	@FXML
	Button btnSearch;
	@FXML
	Button btnAddCustomer;
	@FXML
	Button btnUpdateCustomer;
	@FXML
	Button btnDeleteCustomer;
	
	//view
	@FXML
	TableView tbCustomer;
	
	@FXML
	public void DashboardClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Dashboard.fxml"));
        Parent root = loader.load();	
        Stage stage = (Stage) btnBorrow.getScene().getWindow(); //Get the current window
	    Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
	}
		
	@FXML
	public void LoanClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Loan.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnLoan.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Loan Information");
        stage.show();
	}
		
	@FXML
	public void BookClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Book.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnBook.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Book Information");
        stage.show();
	}
		
	@FXML
	public void BorrowClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Borrow.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnReturn.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Borrow Information");
        stage.show();
	}
		
	@FXML
	public void ReturnClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Return.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnReturn.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Return Information");
        stage.show();
	}
		
	@FXML
	public void CategoryClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Category.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) btnCategory.getScene().getWindow(); //Get the current window
        Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Category Information");
	    stage.show();
	}
		
	@FXML
	public void LogoutClick(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Login.fxml"));
        Parent root = loader.load();	
        Stage stage = (Stage) btnLogout.getScene().getWindow(); //Get the current window
	    Scene scene = new Scene(root); // Set the new scene to the window
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
	}
		
	//display tbCustomer
		
	// SearchClick
	public void SearchClick(MouseEvent event) throws IOException {
			
	}

	// AddCustomerClick
	public void AddCustomerClick(MouseEvent event) throws IOException {
		Parent root =FXMLLoader.load(getClass().getResource("/ui/NewCustomer.fxml"));
		Scene scene = new Scene(root);
		
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Add New Customer Information");

		primaryStage.show();
	}
		
	// UpdateCustomerClick
	public void UpdateCustomerClick(MouseEvent event) throws IOException {
		Parent root =FXMLLoader.load(getClass().getResource("/ui/UpdateCustomer.fxml"));
		Scene scene = new Scene(root);
		
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Update Customer Information");

		primaryStage.show();
	}

	// DeleteCustomerClick
	public void DeleteCustomerClick(MouseEvent event) throws IOException {
		
	}
}
