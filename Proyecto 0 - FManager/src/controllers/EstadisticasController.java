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
import javafx.util.Callback;
import other.DbConnection;
import other.Helper;


/**
 * FXML Controller class
 *
 * @author David
 */
public class EstadisticasController implements Initializable {

    private static final String TITULOERROR = "Error en Estadísticas.";

    @FXML
    private TableView tableViewGol;
    @FXML
    private TableView tableViewTarjAmar;
    @FXML
    private TableView tableViewTarjRoj;
    @FXML
    private TableView tableViewLesiones;

    private ObservableList<ObservableList> datosGoles;

    private ObservableList<ObservableList> datosAmarillas;

    private ObservableList<ObservableList> datosRojas;

    private ObservableList<ObservableList> datosLesiones;

    private int idEquipo;
    
    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
    public void cargarTablaGoles() {
        try {
            datosGoles = FXCollections.observableArrayList();
            DbConnection database = new DbConnection();
            Connection connection = database.getConnection();

            String query = "SELECT nombre || ' ' || apellidos || ' (' || apodo || ')',"
                    + "goles FROM Jugador WHERE idEquipo=" + idEquipo 
                    + " AND goles>0 ORDER BY goles DESC";

            ResultSet rs = connection.createStatement().executeQuery(query);

            //TABLE COLUMN AÑADIDA DINAMICAMENTE** 
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                System.out.println("Hay datos columnas");
                //Recorremos y asignamos cada columna de la fila:
                final int j = i;
                TableColumn col = (TableColumn) tableViewGol.getColumns().get(i);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        SimpleStringProperty valor = null;
                        if (param.getValue().get(j) != null) {
                            valor = new SimpleStringProperty(param.getValue().get(j).toString());
                        } else {
                            valor = new SimpleStringProperty("");
                        }
                        return valor;
                    }
                });
            }
            //Iteramos las filas pertenecientes a cada columna
            while (rs.next()) {
                System.out.println("Hay datos filas");
                //Creamos la variable fila de tipo ObservableList
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Itera cada columna y añade las filas
                    row.add(rs.getString(i));
                }

                //Insertamos la filas filas en la ObservableList
                datosGoles.add(row);
            }
            //Finalmente añadimos los datos del ObservableList en el TableView.
            tableViewGol.setItems(datosGoles);

            connection.close();

        } catch (Exception e) {
            Helper.tratarError(e, TITULOERROR, "Error al construir tabla.");
        }
    }

    public void cargarTablaTarAma() {
        try {
            datosAmarillas = FXCollections.observableArrayList();
            DbConnection database = new DbConnection();
            Connection connection = database.getConnection();

            String query = "SELECT nombre || ' ' || apellidos || ' (' || apodo || ')',"
                    + "tarjetaAmarilla FROM Jugador WHERE idEquipo=" + idEquipo 
                    + " AND tarjetaAmarilla>0 ORDER BY tarjetaAmarilla DESC";

            ResultSet rs = connection.createStatement().executeQuery(query);

            //TABLE COLUMN AÑADIDA DINAMICAMENTE** 
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                System.out.println("Hay datos columnas");
                //Recorremos y asignamos cada columna de la fila:
                final int j = i;
                TableColumn col = (TableColumn) tableViewTarjAmar.getColumns().get(i);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        SimpleStringProperty valor = null;
                        if (param.getValue().get(j) != null) {
                            valor = new SimpleStringProperty(param.getValue().get(j).toString());
                        } else {
                            valor = new SimpleStringProperty("");
                        }
                        return valor;
                    }
                });
            }
            //Iteramos las filas pertenecientes a cada columna
            while (rs.next()) {
                System.out.println("Hay datos filas");
                //Creamos la variable fila de tipo ObservableList
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Itera cada columna y añade las filas
                    row.add(rs.getString(i));
                }

                //Insertamos la filas filas en la ObservableList
                datosAmarillas.add(row);
            }
            //Finalmente añadimos los datos del ObservableList en el TableView.
            tableViewTarjAmar.setItems(datosAmarillas);

            connection.close();

        } catch (Exception e) {
            Helper.tratarError(e, TITULOERROR, "Error al construir tabla.");
        }
    }

    public void cargarTablaTarRoj() {
        try {
            datosRojas = FXCollections.observableArrayList();
            DbConnection database = new DbConnection();
            Connection connection = database.getConnection();

            String query = "SELECT nombre || ' ' || apellidos || ' (' || apodo || ')',"
                    + "tarjetaRoja FROM Jugador WHERE idEquipo=" + idEquipo
                    + " AND tarjetaRoja>0 ORDER BY tarjetaRoja DESC";

            ResultSet rs = connection.createStatement().executeQuery(query);

            //TABLE COLUMN AÑADIDA DINAMICAMENTE** 
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                System.out.println("Hay datos columnas");
                //Recorremos y asignamos cada columna de la fila:
                final int j = i;
                TableColumn col = (TableColumn) tableViewTarjRoj.getColumns().get(i);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        SimpleStringProperty valor = null;
                        if (param.getValue().get(j) != null) {
                            valor = new SimpleStringProperty(param.getValue().get(j).toString());
                        } else {
                            valor = new SimpleStringProperty("");
                        }
                        return valor;
                    }
                });
            }
            //Iteramos las filas pertenecientes a cada columna
            while (rs.next()) {
                System.out.println("Hay datos filas");
                //Creamos la variable fila de tipo ObservableList
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Itera cada columna y añade las filas
                    row.add(rs.getString(i));
                }

                //Insertamos la filas filas en la ObservableList
                datosRojas.add(row);
            }
            //Finalmente añadimos los datos del ObservableList en el TableView.
            tableViewTarjRoj.setItems(datosRojas);

            connection.close();

        } catch (Exception e) {
            Helper.tratarError(e, TITULOERROR, "Error al construir tabla.");
        }
    }

    public void cargarTablaLesiones() {
        try {
            datosLesiones = FXCollections.observableArrayList();
            DbConnection database = new DbConnection();
            Connection connection = database.getConnection();

            String query = "SELECT nombre || ' ' || apellidos || ' (' || apodo || ')',"
                    + "lesion FROM Jugador WHERE idEquipo=" + idEquipo 
                    + " AND lesion>0 ORDER BY lesion DESC ";

            ResultSet rs = connection.createStatement().executeQuery(query);

            //TABLE COLUMN AÑADIDA DINAMICAMENTE** 
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                System.out.println("Hay datos columnas");
                //Recorremos y asignamos cada columna de la fila:
                final int j = i;
                TableColumn col = (TableColumn) tableViewLesiones.getColumns().get(i);
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        SimpleStringProperty valor = null;
                        if (param.getValue().get(j) != null) {
                            valor = new SimpleStringProperty(param.getValue().get(j).toString());
                        } else {
                            valor = new SimpleStringProperty("");
                        }
                        return valor;
                    }
                });
            }
            //Iteramos las filas pertenecientes a cada columna
            while (rs.next()) {
                System.out.println("Hay datos filas");
                //Creamos la variable fila de tipo ObservableList
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Itera cada columna y añade las filas
                    row.add(rs.getString(i));
                }

                //Insertamos la filas filas en la ObservableList
                datosLesiones.add(row);
            }
            //Finalmente añadimos los datos del ObservableList en el TableView.
            tableViewLesiones.setItems(datosLesiones);

            connection.close();

        } catch (Exception e) {
            Helper.tratarError(e, TITULOERROR, "Error al construir tabla.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
}
    

