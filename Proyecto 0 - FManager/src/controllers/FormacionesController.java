/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import other.DbConnection;
import other.Helper;

/**
 * FXML Controller class
 *
 * @author David
 */
public class FormacionesController implements Initializable {

	
    private static final String TITULOERROR = "Error en Formación";
    
    @FXML
    private VBox vboxFormacion;
    @FXML
    private VBox vboxMedio;
    @FXML
    private VBox vboxMedio1;
    @FXML
    private VBox vboxMedio2;
    @FXML
    private VBox vboxMedio3;
    @FXML
    private VBox vboxMedio4;

    private int idEquipo;

    @FXML
    private TableView tableViewFormacion;

    private ObservableList<ObservableList> datos;

    public void cargarTabla() {

        try {
            datos = FXCollections.observableArrayList();
            DbConnection database = new DbConnection();
            Connection connection = database.getConnection();

            //Modificar SELECT con los campos que se quieren mostrar:
            String query = "SELECT dorsal, nombre || ' ' || apellidos || ' (' || apodo || ')', "
                    + "posicion, edad, peso, altura, goles, tarjetaAmarilla, tarjetaRoja, lesion"
                    + " from Jugador where idEquipo=" + idEquipo+ " ORDER BY dorsal ASC";

            ResultSet rs = connection.createStatement().executeQuery(query);

            /**
             * TABLE COLUMN AÑADIDA DINAMICAMENTE**
             */
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                System.out.println("Hay datos");
                //Recorremos y asignamos cada columna de la fila:
                final int j = i;
                TableColumn col = (TableColumn) tableViewFormacion.getColumns().get(i);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                    	SimpleStringProperty valor = null;
                    	if (param.getValue().get(j) != null) {
                    		valor = new SimpleStringProperty(param.getValue().get(j).toString());
                    	}else {
                    		valor = new SimpleStringProperty("");
                    	}
                    	return valor;
                    }
                });
            }
            
            //Iteramos las filas pertenecientes a cada columna
            while (rs.next()) {
                //Creamos la variable fila de tipo ObservableList
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Itera cada columna y añade las filas
                    row.add(rs.getString(i));
                }
                
               //Insertamos la filas filas en la ObservableList
                datos.add(row);
            }
            
            //Finalmente añadimos los datos del ObservableList en el TableView.
            tableViewFormacion.setItems(datos);

            connection.close();
        } catch (Exception e) {
        	Helper.tratarError(e, TITULOERROR, "Error al construir tabla.");
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
}
