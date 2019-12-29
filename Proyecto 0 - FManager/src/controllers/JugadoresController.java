/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import other.DbConnection;
import other.Helper;
import other.ValorCombo;

/**
 * FXML Controller class
 *
 * @author David
 */
public class JugadoresController implements Initializable {

	@FXML
	private Button actualizarJugador;
	@FXML
	private Button borrarJugador;
	@FXML
	private Button cancelarJugador;
	@FXML
	private Button guardarJugador;
	@FXML
	private Button limpiarCamposFXML;
	@FXML
	private ComboBox<ValorCombo> cBoxSeleccionJ;
	@FXML
	private ObservableList<ValorCombo> cBoxSeleccionJItems;
	@FXML
	private TextField txtNombreJ;
	@FXML
	private TextField txtApellidosJ;
	@FXML
	private TextField txtApodoJ;
	@FXML
	private TextField txtNacionalidadJ;
	@FXML
	private TextField txtLocalidadJ;
	@FXML
	private TextField txtEdadJ;
	@FXML
	private TextField txtPosicionJ;
	@FXML
	private TextField txtPesoJ;
	@FXML
	private TextField txtAlturaJ;
	@FXML
	private TextField txtDorsalJ;
	@FXML
	private TextField txtGolesJ;
	@FXML
	private TextField txtLesionesJ;
	@FXML
	private TextField txtTAJ;
	@FXML
	private TextField txtTRJ;

	private static final String TITULOERROR = "Error en Config";

	private int idEquipo;

	private int idJugadorSeleccionado = -1;

	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		// deshabilitamos los botones queno tienen sentido si no seleccionamos un
		// jugador
		actualizarJugador.setDisable(true);
		borrarJugador.setDisable(true);
		cancelarJugador.setDisable(true);
		guardarJugador.setDisable(false);
		limpiarCamposFXML.setDisable(false);
		cBoxSeleccionJ.setDisable(false);

	}

	public void cargarJugadoresCbox() {
		try {
			// limpiamos el combo
			cBoxSeleccionJItems.clear();
			cBoxSeleccionJItems.add(new ValorCombo(-1, "Seleccione"));
			cBoxSeleccionJ.getSelectionModel().select(0);

			DbConnection database = new DbConnection();
			Connection connection = database.getConnection();
			String query = "SELECT idjugador, nombre || ' ' || apellidos || ' (' || apodo || ')' as nombre FROM Jugador WHERE idEquipo="
					+ idEquipo;

			PreparedStatement pst = connection.prepareStatement(query);

			ResultSet rs = pst.executeQuery();

			// Cargar los campos del jugador en los textfield:
			while (rs.next()) {
				cBoxSeleccionJItems.add(new ValorCombo(rs.getInt("idjugador"), rs.getString("nombre").replace(" ()", "")));
			}

			pst.close();
			rs.close();
			connection.close();

			cBoxSeleccionJ.setOnAction(e -> {
				try {
					int idSeleccionada = -1;
					if (cBoxSeleccionJ.getValue()!= null) {
						idSeleccionada = cBoxSeleccionJ.getValue().idProperty();
					}
					if (idSeleccionada> -1) {
						DbConnection database2 = new DbConnection();
						Connection connection2 = database2.getConnection();
						String query2 = "SELECT * FROM Jugador WHERE idJugador=?";
						PreparedStatement pst2 = connection2.prepareStatement(query2);
						pst2.setInt(1, cBoxSeleccionJ.getValue().idProperty());
						ResultSet rs2 = pst2.executeQuery();
	
						// Cargar los campos del jugador en los textfield:
						if (rs2.next()) {
							txtNombreJ.setText(rs2.getString("nombre"));
							txtApellidosJ.setText(rs2.getString("apellidos"));
							txtApodoJ.setText(rs2.getString("apodo"));
							txtNacionalidadJ.setText(rs2.getString("nacionalidad"));
							txtLocalidadJ.setText(rs2.getString("localidad"));
                        	txtPosicionJ.setText(rs2.getString("posicion"));
							String edad = rs2.getString("edad");
							if (edad != null) {
								txtEdadJ.setText(edad);
							}
							String peso = rs2.getString("peso");
							if (peso != null) {
								txtPesoJ.setText(peso);
							}
							String altura = rs2.getString("altura");
							if (altura != null) {
								txtAlturaJ.setText(altura);
							}
							String dorsal = rs2.getString("dorsal");
							if (dorsal != null) {
								txtDorsalJ.setText(dorsal);
							}
							String goles = rs2.getString("goles");
							if (goles != null) {
								txtGolesJ.setText(goles);
							}
							String lesion = rs2.getString("lesion");
							if (lesion != null) {
								txtLesionesJ.setText(lesion);
							}
							String tarjetaAmarilla = rs2.getString("tarjetaAmarilla");
							if (tarjetaAmarilla != null) {
								txtTAJ.setText(tarjetaAmarilla);
							}
							String tarjetaRoja = rs2.getString("tarjetaRoja");
							if (tarjetaRoja != null) {
								txtTRJ.setText(tarjetaRoja);
							}
	
							idJugadorSeleccionado = cBoxSeleccionJ.getValue().idProperty();
							cancelarJugador.setDisable(false);
							actualizarJugador.setDisable(false);
							borrarJugador.setDisable(false);
							guardarJugador.setDisable(true);
							limpiarCamposFXML.setDisable(true);
							cBoxSeleccionJ.setDisable(true);
						}
						pst2.close();
						rs2.close();
						connection2.close();
					}	
				} catch (Exception e2) {
					Helper.tratarError(e2, TITULOERROR, "Error al cargar los datos del jugador.");
				}
			});
		} catch (Exception e) {
			Helper.tratarError(e, TITULOERROR, "Error al cargar el combo.");
		}

	}

	@FXML
	private void cancelarJugador() {
		restablecerFormulario();
	}

	private void restablecerFormulario() {
		idJugadorSeleccionado = -1;
		actualizarJugador.setDisable(true);
		borrarJugador.setDisable(true);
		cancelarJugador.setDisable(true);
		guardarJugador.setDisable(false);
		limpiarCamposFXML.setDisable(false);
		cBoxSeleccionJ.setDisable(false);
		limpiarCamposFXML();
		cBoxSeleccionJ.getSelectionModel().select(0);
		cargarJugadoresCbox();
	}

	// Botón del FXML que al clickarle limpia los campos:
	@FXML
	private void limpiarCamposFXML() {
		txtNombreJ.setText("");
		txtApellidosJ.setText("");
		txtApodoJ.setText("");
		txtNacionalidadJ.setText("");
		txtLocalidadJ.setText("");
		txtEdadJ.setText("");
		txtPosicionJ.setText("");
		txtPesoJ.setText("");
		txtAlturaJ.setText("");
		txtDorsalJ.setText("");
		txtGolesJ.setText("");
		txtLesionesJ.setText("");
		txtTAJ.setText("");
		txtTRJ.setText("");
	}

	@FXML
	private void borrarJugador(ActionEvent event) throws ClassNotFoundException {
		try {
			Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
			confirmacion.setTitle("Confirmación");
			confirmacion.setHeaderText(" ¿Esta seguro de borrar el jugador?");
			confirmacion.setContentText("No podrá deshacer esta acción.");
                        
                        DialogPane dialogPane = confirmacion.getDialogPane();
                        dialogPane.getStylesheets().add("/view/vista.css");
                        
			Optional<ButtonType> result = confirmacion.showAndWait();
			if (result.get() == ButtonType.OK) {
				DbConnection database = new DbConnection();
				Connection connection = database.getConnection();

				String borrado = "DELETE FROM Jugador WHERE idJugador=" + idJugadorSeleccionado;
				PreparedStatement preparedStmt = connection.prepareStatement(borrado);
				preparedStmt.execute();

				connection.close();

				restablecerFormulario();
			}
		} catch (SQLException ex) {
			Helper.tratarError(ex, TITULOERROR, "Hubo un error en la eliminación de los datos. Pruebelo más tarde.");
		}
	}

	private boolean validarCampos() {
		String edad = txtEdadJ.getText().trim();
		String peso = txtPesoJ.getText().trim();
		String altura = txtAlturaJ.getText().trim();
		String dorsal = txtDorsalJ.getText().trim();
		String goles = txtGolesJ.getText().trim();
		String lesiones = txtLesionesJ.getText().trim();
		String tarAmarillas = txtTAJ.getText().trim();
		String tarRojas = txtTRJ.getText().trim();

		          if ((!edad.equals("") && !Helper.isNumeric(edad))
                    || (!peso.equals("") && !Helper.isNumeric(peso)) || (!altura.equals("") && !Helper.isNumeric(altura))
                    || (!dorsal.equals("") && !Helper.isNumeric(dorsal)) || (!goles.equals("") && !Helper.isNumeric(goles))
                    || (!lesiones.equals("") && !Helper.isNumeric(lesiones))
                    || (!tarAmarillas.equals("") && !Helper.isNumeric(tarAmarillas))
                    || (!tarRojas.equals("") && !Helper.isNumeric(tarRojas))) {

			Helper.tratarError(new Exception("Tipos de dato incorrectos."), TITULOERROR,
					"Los campos edad, peso, altura, dorsal, goles, lesiones, tarjetas amarillas y tarjetas rojas deben ser valores númericos.");
		} else if (txtNombreJ.getText().trim().equals("") || txtApellidosJ.getText().trim().equals("")) {
			Helper.tratarError(new Exception("Datos obligatorios vacios."), TITULOERROR,
					"El nombre y apellidos del jugador no puede estar vacío.");
		} else {
			return true;
		}
		return false;
	}

	@FXML
	private void guardarJugador(ActionEvent event) {
		if (validarCampos()) {
			try {
				guardarJugadorSQL();
				Helper.tratarInformacion(Alert.AlertType.INFORMATION, "Insertado", "Alta de jugador",
						"Jugador correctamente insertado: " + txtApodoJ.getText());
				restablecerFormulario();
				cargarJugadoresCbox();
			} catch (Exception e) {
				Helper.tratarError(e, TITULOERROR, "No se pudieron insertar los datos. Intentélo más tarde.");
			}
		}
	}

	private void guardarJugadorSQL() throws ClassNotFoundException, SQLException {

		String nombreJ = txtNombreJ.getText();
		String apellidosJ = txtApellidosJ.getText();
		String apodoJ = txtApodoJ.getText();
		String nacionalidadJ = txtNacionalidadJ.getText();
		String localidadJ = txtLocalidadJ.getText();
        String posicion = txtPosicionJ.getText();
		Integer edad = txtEdadJ.getText().trim().equals("") ? null : Integer.parseInt(txtEdadJ.getText().trim());
		Integer peso = txtPesoJ.getText().trim().equals("") ? null : Integer.parseInt(txtPesoJ.getText().trim());
		Integer altura = txtAlturaJ.getText().trim().equals("") ? null : Integer.parseInt(txtAlturaJ.getText().trim());
		Integer dorsal = txtDorsalJ.getText().trim().equals("") ? null : Integer.parseInt(txtDorsalJ.getText().trim());
		Integer goles = txtGolesJ.getText().trim().equals("") ? null : Integer.parseInt(txtGolesJ.getText().trim());
		Integer lesiones = txtLesionesJ.getText().trim().equals("") ? null
				: Integer.parseInt(txtLesionesJ.getText().trim());
		Integer tarAmarillas = txtTAJ.getText().trim().equals("") ? null : Integer.parseInt(txtTAJ.getText().trim());
		Integer tarRojas = txtTRJ.getText().trim().equals("") ? null : Integer.parseInt(txtTRJ.getText().trim());

		String query = "INSERT INTO JUGADOR (nombre,apellidos,apodo,nacionalidad,"
				+ "localidad,edad,posicion,peso,altura,dorsal,goles,lesion,tarjetaAmarilla,tarjetaRoja,idEquipo) VALUES"
				+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

		DbConnection database = new DbConnection();
		Connection connection = database.getConnection();
		PreparedStatement preparedStmt = connection.prepareStatement(query);

		preparedStmt.setString(1, nombreJ);
		preparedStmt.setString(2, apellidosJ);
		preparedStmt.setString(3, apodoJ);
		preparedStmt.setString(4, nacionalidadJ);
		preparedStmt.setString(5, localidadJ);
        preparedStmt.setString(7, posicion);

		if (edad != null) {
			preparedStmt.setInt(6, edad);
		} else {
			preparedStmt.setString(6, null);
		}
		if (peso != null) {
			preparedStmt.setInt(8, peso);
		} else {
			preparedStmt.setString(8, null);
		}
		if (altura != null) {
			preparedStmt.setInt(9, altura);
		} else {
			preparedStmt.setString(9, null);
		}
		if (dorsal != null) {
			preparedStmt.setInt(10, dorsal);
		} else {
			preparedStmt.setString(10, null);
		}
		if (goles != null) {
			preparedStmt.setInt(11, goles);
		} else {
			preparedStmt.setString(11, null);
		}
		if (lesiones != null) {
			preparedStmt.setInt(12, lesiones);
		} else {
			preparedStmt.setString(12, null);
		}
		if (tarAmarillas != null) {
			preparedStmt.setInt(13, tarAmarillas);
		} else {
			preparedStmt.setString(13, null);
		}
		if (tarRojas != null) {
			preparedStmt.setInt(14, tarRojas);
		} else {
			preparedStmt.setString(14, null);
		}
		preparedStmt.setInt(15, idEquipo);

		preparedStmt.execute();
		preparedStmt.close();
		connection.close();
	}

	@FXML
	private void actualizarJugador(ActionEvent event) {
		if (validarCampos()) {
			try {
				actualizarJugadorSQL();
				Helper.tratarInformacion(Alert.AlertType.INFORMATION, "Actualizado", "Actualización de jugador",
						"Jugador correctamente actualizado: " + txtApodoJ.getText());
				restablecerFormulario();
			} catch (Exception e) {
				Helper.tratarError(e, TITULOERROR, "No se pudieron actualizar los datos. Intentélo más tarde.");
			}
		}
	}

	private void actualizarJugadorSQL() throws ClassNotFoundException, SQLException {

		String nombreJ = txtNombreJ.getText();
		String apellidosJ = txtApellidosJ.getText();
		String apodoJ = txtApodoJ.getText();
		String nacionalidadJ = txtNacionalidadJ.getText();
		String localidadJ = txtLocalidadJ.getText();
        String posicion = txtPosicionJ.getText();
		Integer edad = txtEdadJ.getText().trim().equals("") ? null : Integer.parseInt(txtEdadJ.getText().trim());
		Integer peso = txtPesoJ.getText().trim().equals("") ? null : Integer.parseInt(txtPesoJ.getText().trim());
		Integer altura = txtAlturaJ.getText().trim().equals("") ? null : Integer.parseInt(txtAlturaJ.getText().trim());
		Integer dorsal = txtDorsalJ.getText().trim().equals("") ? null : Integer.parseInt(txtDorsalJ.getText().trim());
		Integer goles = txtGolesJ.getText().trim().equals("") ? null : Integer.parseInt(txtGolesJ.getText().trim());
		Integer lesiones = txtLesionesJ.getText().trim().equals("") ? null
				: Integer.parseInt(txtLesionesJ.getText().trim());
		Integer tarAmarillas = txtTAJ.getText().trim().equals("") ? null : Integer.parseInt(txtTAJ.getText().trim());
		Integer tarRojas = txtTRJ.getText().trim().equals("") ? null : Integer.parseInt(txtTRJ.getText().trim());

		String query = "UPDATE jugador SET nombre=?, apellidos=?, apodo=?, nacionalidad=?, localidad=?,posicion=?";

		DbConnection database = new DbConnection();
		Connection connection = database.getConnection();
                
		if (edad != null) {
			query += ", edad=?";
		} else {
			query += ", edad=null";
		}
		if (peso != null) {
			query += ", peso=?";
		} else {
			query += ", peso=null";
		}
		if (altura != null) {
			query += ", altura=?";
		} else {
			query += ", altura=null";
		}
		if (dorsal != null) {
			query += ", dorsal=?";
		} else {
			query += ", dorsal=null";
		}
		if (goles != null) {
			query += ", goles=?";
		} else {
			query += ", goles=null";
		}
		if (lesiones != null) {
			query += ", lesion=?";
		} else {
			query += ", lesion=null";
		}
		if (tarAmarillas != null) {
			query += ", tarjetaAmarilla=?";
		} else {
			query += ", tarjetaAmarilla=null";
		}
		if (tarRojas != null) {
			query += ", tarjetaRoja=?";
		} else {
			query += ", tarjetaRoja=null";
		}
		query += " WHERE idEquipo=? and idJugador=?";
		
		int orden = 1;

		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setString(orden++, nombreJ);
		preparedStmt.setString(orden++, apellidosJ);
		preparedStmt.setString(orden++, apodoJ);
		preparedStmt.setString(orden++, nacionalidadJ);
		preparedStmt.setString(orden++, localidadJ);
        preparedStmt.setString(orden++, posicion);
		
		if (edad != null) {
			preparedStmt.setInt(orden++, edad);
		}
		if (peso != null) {
			preparedStmt.setInt(orden++, peso);
		}
		if (altura != null) {
			preparedStmt.setInt(orden++, altura);
		}
		if (dorsal != null) {
			preparedStmt.setInt(orden++, dorsal);
		}
		if (goles != null) {
			preparedStmt.setInt(orden++, goles);
		}
		if (lesiones != null) {
			preparedStmt.setInt(orden++, lesiones);
		}
		if (tarAmarillas != null) {
			preparedStmt.setInt(orden++, tarAmarillas);
		}
		if (tarRojas != null) {
			preparedStmt.setInt(orden++, tarRojas);
		}
		preparedStmt.setInt(orden++, idEquipo);
		preparedStmt.setInt(orden++, idJugadorSeleccionado);

		preparedStmt.executeUpdate();
		connection.close();
	}
}
