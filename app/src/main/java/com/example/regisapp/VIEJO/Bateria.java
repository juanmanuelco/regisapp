package com.example.regisapp.VIEJO;

import org.joda.time.DateTime;

public class Bateria {
    private int nivel;
    private long fechaHora;

    public Bateria( long fechaHora, int nivel) {
        this.nivel = nivel;
        this.fechaHora = fechaHora;
    }

    public int getNivel(){
        return nivel;
    }
    public long getFechaHora(){
        return fechaHora;
    }
}
