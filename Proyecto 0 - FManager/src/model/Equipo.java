/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class Equipo {
    
    private List <Jugador> jugadores;
    private String nombreEquipo;
    private int totalGolesAnotados;
    private int totalGolesEncajados;
    private String division;
    private String tactica;
    private String entrenamiento;
    
    public Equipo(){
        jugadores = new ArrayList<>();
    }
    
    /**
     * Devuelve la lista de jugadores.
     * @return 
     */
    public List <Jugador> getJugadores(){
        return jugadores;
    }
    
    /**
     * Recogemos la lista de jugadores y añadimos un nuevo jugador con add.
     * @param jugador 
     */
    public void addJugador (Jugador jugador){
        this.getJugadores().add(jugador);
    }
    
    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public int getTotalGolesAnotados() {
        return totalGolesAnotados;
    }

    public void setTotalGolesAnotados(int totalGolesAnotados) {
        this.totalGolesAnotados = totalGolesAnotados;
    }

    public int getTotalGolesEncajados() {
        return totalGolesEncajados;
    }

    public void setTotalGolesEncajados(int totalGolesEncajados) {
        this.totalGolesEncajados = totalGolesEncajados;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getTactica() {
        return tactica;
    }

    public void setTactica(String tactica) {
        this.tactica = tactica;
    }

    public String getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(String entrenamiento) {
        this.entrenamiento = entrenamiento;
    }
    
    
    
}
