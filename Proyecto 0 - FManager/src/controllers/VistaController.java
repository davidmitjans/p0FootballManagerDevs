package controllers;

import static controllers.LoginController.getMD5;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import other.DbConnection;
import other.Helper;

public class VistaController implements Initializable {

    private static final String TITULOERROR = "Error en Registro";
    @FXML
    private AnchorPane vistaPane;
    @FXML
    private AnchorPane paneLogin2;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Label btnForgot;
    @FXML
    private Button btnRegistrarse;
    @FXML
    private Label lblErrors;

    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void btnIniciarSesion(ActionEvent event) {
        try {
            if (this.txtUsuario.getText().trim().equals("") || this.txtPassword.getText().trim().equals("")) {
                Helper.tratarError(new Exception("Usuario vacío"), 
                		TITULOERROR, "El usuario/contraseña no pueden estar vacíos");
            } else {
                loginDB();
            }
        } catch (Exception e) {
            Helper.tratarError(e, TITULOERROR, "No se pudo iniciar sesión.");
        }
    }

    @FXML
    private void btnRegistrarse(ActionEvent event) {
        try {
            AnchorPane paneLogin = FXMLLoader.load(getClass().getResource("/view/loginRegistro.fxml"));
            this.vistaPane.getChildren().setAll(new Node[]{paneLogin});
        } catch (IOException e) {
        	Helper.tratarError(e, TITULOERROR, "Error en boton REGISTRARSE.");
        }
    }
    
    public void loginDB() throws Exception {
        
        String user = this.txtUsuario.getText();
        String pass = getMD5(txtPassword.getText());
        

        DbConnection database = new DbConnection();
        Connection connection = database.getConnection();

        String query = "SELECT idEquipo, userteam FROM Equipo WHERE username=? AND userpassword=?";
        PreparedStatement preparedStmt = connection.prepareStatement(query);

        preparedStmt.setString(1, user);
        preparedStmt.setString(2, pass);

        ResultSet rs = preparedStmt.executeQuery();
        int idEquipo = rs.getInt("idEquipo");
        String nameEquipo = rs.getString("userteam");
        connection.close();
        
        if(rs.next()){
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/principalControllerPane.fxml"));
			Parent vista = loader.load();
            PrincipalControllerPane controlador = (PrincipalControllerPane)loader.getController();
            controlador.setIdEquipo(idEquipo);
            controlador.setTextoMiEquipo(nameEquipo);
            this.vistaPane.getChildren().setAll(new Node[]{vista});
            controlador.loadPage("formaciones");
            
        }else{
            Helper.tratarError(new Exception("Usuario/contraseña invalido en incio de sesión.") ,
            		TITULOERROR, "No se pudo iniciar sesión. Compruebe que usuario o contraseña son correctos.");
        }
    }
}
