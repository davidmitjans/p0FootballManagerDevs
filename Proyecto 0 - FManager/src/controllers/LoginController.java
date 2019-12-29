/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import org.sqlite.SQLiteException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import other.DbConnection;
import other.Helper;

/**
 * FXML Controller class
 *
 * @author David
 */
public class LoginController implements Initializable {

	private static final String TITULOERROR = "Error en Login";

	@FXML
	private AnchorPane paneLogin;
	@FXML
	private AnchorPane paneLogin2;
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Label btnForgot;
	@FXML
	private Label lblErrors;
	@FXML
	private Button btnRegistrarseRegistro;
	@FXML
	private Button btnAtrasRegistro;

	Connection connection;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	@FXML
	private void btnRegistrarse(ActionEvent event) {
		try {
			if (txtUsuario.getText().trim().equals("") || txtPassword.getText().trim().equals("")) {
				Helper.tratarError(new Exception("Usuario vacío"), TITULOERROR,
						"El usuario/contraseña no pueden estar vacíos");
			} else {
				signUpDB();
				Helper.tratarInformacion(AlertType.INFORMATION, "Registrado",
						"Bienvenido " + txtUsuario.getText() + "!",
						"Registro completado con éxito. Por favor inicie sesión.");
				AnchorPane vistaPane = FXMLLoader.load(getClass().getResource("/view/vista.fxml"));
				paneLogin.getChildren().setAll(vistaPane);
			}
		} catch (Exception e) {
			if (e instanceof SQLiteException && ((SQLiteException) e).getErrorCode() == 19) {
				Helper.tratarError(e, TITULOERROR, "Ya existe un usuario con ese nombre.");
			} else {
				Helper.tratarError(e, TITULOERROR, "No se pudo iniciar sesión. Vuelva a insertar los datos.");
			}
		}
	}

	@FXML
	private void btnAtras(ActionEvent event) {
		try {
			AnchorPane vistaPane = FXMLLoader.load(getClass().getResource("/view/vista.fxml"));
			paneLogin.getChildren().setAll(vistaPane);
		} catch (IOException e) {
			Helper.tratarError(e, TITULOERROR, "Error en boton ATRAS.");
		}
	}

	public void signUpDB() throws Exception {
		String user = txtUsuario.getText();
		String pass = getMD5(txtPassword.getText());

		DbConnection database = new DbConnection();
		Connection connection = database.getConnection();

		String query = "INSERT INTO Equipo (username, userpassword) values(?,?)";
		PreparedStatement preparedStmt = connection.prepareStatement(query);

		preparedStmt.setString(1, user);
		preparedStmt.setString(2, pass);
		preparedStmt.execute();
                
                

		connection.close();
	}
        
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
