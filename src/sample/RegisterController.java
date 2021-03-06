package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DatabaseConnection;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private ImageView shieldImageView;
    @FXML
    private Button closeButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;

    public void initialize(URL url, ResourceBundle resourceBundle){
        File shieldFile = new File("images/shield.png");
        Image shieldImage = new Image(shieldFile.toURI().toString());
        shieldImageView.setImage(shieldImage);
    }

    public void registerButtonOnAction(ActionEvent event){
        if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();
            confirmPasswordLabel.setText("");
        } else {
            confirmPasswordLabel.setText("Password does not match");
        }
    }

    public void goBackButtonOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("createOptions.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 567));
            registerStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void closeButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void registerUser(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();

        @SuppressWarnings("SqlResolve") String insertFields = "insert into user_account(lastname, firstname, username, password) VALUES ('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registrationMessageLabel.setText("User has been registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

}
