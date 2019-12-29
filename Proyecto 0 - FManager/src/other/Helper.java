/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

/**
 *
 * @author David
 */
public class Helper {
    
    //Exception ex, String mensajeError, String titulo, String cabecera, String contenido
    public static void tratarError(Exception ex, String titulo, String contenido){
        
        ex.printStackTrace();
        System.out.println(titulo + " " + contenido);
        Alert ErrorSignUp = new Alert(Alert.AlertType.ERROR);
            ErrorSignUp.setTitle(titulo);
            ErrorSignUp.setHeaderText(" Disculpe, hubo un error.");
            ErrorSignUp.setContentText(contenido);
            
            DialogPane dialogPane = ErrorSignUp.getDialogPane();
            dialogPane.getStylesheets().add("/view/vista.css");
            
            ErrorSignUp.showAndWait();
    }
    
    public static void tratarInformacion(Alert.AlertType tipoAlert,String cabecera, String titulo, String contenido){
        
        System.out.println(titulo + " " + contenido);
        Alert ErrorSignUp = new Alert(tipoAlert);
            ErrorSignUp.setTitle(titulo);
            ErrorSignUp.setHeaderText(cabecera);
            ErrorSignUp.setContentText(contenido);
            
            DialogPane dialogPane = ErrorSignUp.getDialogPane();
            dialogPane.getStylesheets().add("/view/vista.css");
            
            ErrorSignUp.showAndWait();
    }
    
    public static void imprimirTraza(String mensaje){
        System.out.println(mensaje);
    }
    

	public static boolean isNumeric(String texto) {

		try {
			Integer.parseInt(texto);
			return true;
		} catch (NumberFormatException ex) {
		}
		return false;

	}
}
