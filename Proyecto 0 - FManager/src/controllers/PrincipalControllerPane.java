/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import other.Helper;

/**
 * FXML Controller class
 *
 * @author David
 */
public class PrincipalControllerPane implements Initializable {

	private static final String TITULOERROR = "Error en Contenedor Principal";
	@FXML
	private VBox VBoxPrincipal;
	@FXML
	private HBox HBoxFormaciones;
	@FXML
	private HBox HBoxStats;
	@FXML
	private HBox HBoxMiEquipo;
	@FXML
	private HBox HBoxJugadores;
	@FXML
	private HBox HBoxCerrarSesion;
	@FXML
	private BorderPane borderpane;
	@FXML
	private Label lbMiEquipo;

	private int idEquipo;
	
	private String textoMiEquipo;
	

	public int getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}

	public void setTextoMiEquipo(String textoMiEquipo) {
		this.textoMiEquipo = textoMiEquipo;
	}
	
	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	@FXML
	private void HBoxFormaciones(MouseEvent event) {
		loadPage("formaciones");

	}

	@FXML
	private void HBoxStats(MouseEvent event) {
		loadPage("estadisticas");
	}

	@FXML
	private void HBoxJugadores(MouseEvent event) {
		loadPage("configs");
	}

	@FXML
	private void HBoxMiEquipo(MouseEvent event) {
		loadPage("miEquipo");

	}

	@FXML
	private void HBoxCerrarSesion(MouseEvent event)  {

		Alert cerrarVentana = new Alert(AlertType.CONFIRMATION);
		cerrarVentana.setTitle("Confirmación");
		cerrarVentana.setHeaderText("Saliendo...");
		cerrarVentana.setContentText("Realmente desea salir? Perderá todos los cambios no guardados.");

		DialogPane dialogPane = cerrarVentana.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("/view/vista.css").toExternalForm());

		Optional<ButtonType> result = cerrarVentana.showAndWait();

		if (result.get() == ButtonType.OK) {
			try {
				AnchorPane vistaPane = FXMLLoader.load(getClass().getResource("/view/vista.fxml"));
				VBoxPrincipal.getChildren().setAll(vistaPane);
			} catch (IOException e) {
				Helper.tratarError(e, TITULOERROR, "Error al cerrar sesión.");
			}
		}
	}

	public void loadPage(String page) {
		FXMLLoader loader = null;
		try {
			lbMiEquipo.setText(textoMiEquipo);
			loader = new FXMLLoader(getClass().getResource("/view/" + page + ".fxml"));
			borderpane.setCenter((Parent) loader.load());
			switch (page) {
			case "formaciones":
				FormacionesController formacionesController = loader.getController();
				formacionesController.setIdEquipo(idEquipo);
				formacionesController.cargarTabla();
				break;
			case "estadisticas":
				EstadisticasController estadisticasController = loader.getController();
				estadisticasController.setIdEquipo(idEquipo);
				estadisticasController.cargarTablaGoles();
				estadisticasController.cargarTablaTarAma();
				estadisticasController.cargarTablaTarRoj();
				estadisticasController.cargarTablaLesiones();
				break;
			case "configs":
				JugadoresController jugadoresController = loader.getController();
				jugadoresController.setIdEquipo(idEquipo);
				jugadoresController.cargarJugadoresCbox();
				break;
			case "miEquipo":
				MiEquipoController miEquipoController = loader.getController();
				miEquipoController.setIdEquipo(idEquipo);
				miEquipoController.cargarDatos();
				break;
			}
		} catch (IOException e) {
			Helper.tratarError(e, TITULOERROR, "Error en la carga de página: " + page + ".");
		}
	}

	@FXML
	public void FormacionesMouseEntered(MouseEvent e) {

		HBoxFormaciones.setStyle("-fx-background-color: cornsilk;");
	}

	@FXML
	public void StatsMouseEntered(MouseEvent e) {
		HBoxStats.setStyle("-fx-background-color: cornsilk;");

	}

	@FXML
	public void JugadoresMouseEntered(MouseEvent e) {
		HBoxJugadores.setStyle("-fx-background-color: cornsilk;");
	}

	@FXML
	public void MiEquipoMouseEntered(MouseEvent e) {
		HBoxMiEquipo.setStyle("-fx-background-color: cornsilk;");
	}

	@FXML
	public void CerrarSesionMouseEntered(MouseEvent e) {
		HBoxCerrarSesion.setStyle("-fx-background-color: cornsilk;");
	}

	@FXML
	public void FormacionesMouseExited(MouseEvent e) {

		HBoxFormaciones.setStyle("-fx-background-color: #DB7A67;");
	}

	@FXML
	public void StatsMouseExited(MouseEvent e) {
		HBoxStats.setStyle("-fx-background-color: #DB7A67;");

	}

	@FXML
	public void JugadoresMouseExited(MouseEvent e) {
		HBoxJugadores.setStyle("-fx-background-color: #DB7A67;");
	}

	@FXML
	public void MiEquipoMouseExited(MouseEvent e) {
		HBoxMiEquipo.setStyle("-fx-background-color: #DB7A67;");
	}

	@FXML
	public void CerrarSesionMouseExited(MouseEvent e) {
		HBoxCerrarSesion.setStyle("-fx-background-color: #DB7A67;");
	}

}
