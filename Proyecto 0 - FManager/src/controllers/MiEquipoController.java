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
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import other.DbConnection;
import other.Helper;

/**
 *
 * @author dmitjans
 */
public class MiEquipoController implements Initializable {

	private static final String TITULOERROR = "Error en Mi Equipo";
	@FXML
	private Button btnActualizar;
	@FXML
	private TextField txtNombreEquipo;
	@FXML
	private TextField txtLocalidad;
	@FXML
	private TextField txtPais;
	@FXML
	private TextField txtProvincia;
	@FXML
	private TextField txtLiga;
	@FXML
	private TextField txtGrupo;
	@FXML
	private TextField txtPosicion;
        
        @FXML
        private Label lbEquipo;
        
	private int idEquipo;

	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}

	public void initialize(URL url, ResourceBundle rb) {
	}

	@FXML
	private void btnActualizar(ActionEvent event) {

		String grupo = txtGrupo.getText().trim();
		String posicion = txtPosicion.getText().trim();
		if ((!grupo.equals("") && !Helper.isNumeric(grupo)) ||
			(!posicion.equals("") && !Helper.isNumeric(posicion))) {

			Helper.tratarError(new Exception("Tipos de dato incorrectos."), TITULOERROR,
					"Los campos grupo y posición deben ser valores númericos.");
		} else if (txtNombreEquipo.getText().trim().equals("")) {
			Helper.tratarError(new Exception("Datos obligatorios vacios."), TITULOERROR,
					"El nombre del equipo no puede estar vacío.");
		} else {
			try {
				actualizarDatos();
				Helper.tratarInformacion(Alert.AlertType.INFORMATION, "Campos de equipo insertados.", "Alta de Equipo",
						"Equipo correctamente actualizado: " + txtNombreEquipo.getText());

			} catch (Exception e) {
				Helper.tratarError(e, TITULOERROR, "Por favor, rellene todos los datos.");
			}
		}
	}

	public void cargarDatos() {

		DbConnection database = new DbConnection();
		try {
			Connection connection = database.getConnection();

			// Falta nombreLiga y grupoLiga(Tabla Liga).Preguntar si dejar tabla liga, o
			// traspasar esos campos a tabla Equipo
			String query = "SELECT userteam, localidad, provincia, pais, posicion, liga, grupo FROM Equipo WHERE idEquipo="
					+ idEquipo;

			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				txtNombreEquipo.setText(rs.getString("userteam"));
				txtLocalidad.setText(rs.getString("localidad"));
				txtProvincia.setText(rs.getString("provincia"));
				txtPais.setText(rs.getString("pais"));
				txtLiga.setText(rs.getString("liga"));
				String posicion = rs.getString("posicion");
                                lbEquipo.setText(rs.getString("userteam"));
                                
                                
				if (posicion != null) {
					txtPosicion.setText(posicion);
				}
				String grupo = rs.getString("grupo");
				if (grupo != null) {
					txtGrupo.setText(grupo);
				}
			} 
			pst.close();
			rs.close();
			connection.close();
		} catch (Exception e) {
			Helper.tratarError(e, TITULOERROR, "Error al cargar algunos datos.");
		}
	}

	private void actualizarDatos() throws ClassNotFoundException, SQLException {

		String nombreEquipo = txtNombreEquipo.getText().trim();
		String localidad = txtLocalidad.getText().trim();
		String provincia = txtProvincia.getText().trim();
		String pais = txtPais.getText().trim();
		String liga = txtLiga.getText().trim();
		Integer posicion = txtPosicion.getText().trim().equals("")?null:Integer.parseInt(txtPosicion.getText().trim());
		Integer grupo = txtGrupo.getText().trim().equals("")?null:Integer.parseInt(txtGrupo.getText().trim());

		DbConnection database = new DbConnection();
		Connection connection = database.getConnection();

		String query = "UPDATE Equipo SET userteam=?, localidad=?, provincia=?, pais=?, liga=?";
		if (posicion != null) {
			query += ", posicion=?"; 
		}else {
			query += ", posicion=null";
		}
		
		if (grupo != null) {
			query += ", grupo=?"; 
		}else {
			query += ", grupo=null";
		}
		query += " WHERE idEquipo=?";
		
		
		int orden = 1;
		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setString(orden++, nombreEquipo);
		preparedStmt.setString(orden++, localidad);
		preparedStmt.setString(orden++, provincia);
		preparedStmt.setString(orden++, pais);
		preparedStmt.setString(orden++, liga);
		if (posicion != null) {
			preparedStmt.setInt(orden++, posicion);
		}
		
		if (grupo != null) {
			preparedStmt.setInt(orden++, grupo);
		}
		preparedStmt.setInt(orden++, idEquipo);

		preparedStmt.executeUpdate();
		connection.close();
	}


}
