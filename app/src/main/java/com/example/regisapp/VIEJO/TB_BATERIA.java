package com.example.regisapp.VIEJO;

import android.database.Cursor;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static com.example.regisapp.VIEJO.NEGOCIO.Formateados;

public class TB_BATERIA {
    public static final String NOMBRE   = " NIVEL_BATERIA  ";
    public static String ID             = " ID INTEGER PRIMARY KEY AUTOINCREMENT ";
    public static String FECHAHORA      = " FECHA NUMERIC ";
    public static String NIVEL          = " NIVEL INTEGER ";

    public static String crearTablaBateria(){
        return String.format("CREATE TABLE %s ("+Formateados(3)+")",
                NOMBRE, ID, FECHAHORA, NIVEL);
    }
    public static String EliminarTabla(){
        return String.format("DROP TABLE IF EXISTS %s",NOMBRE);
    }

    public static List<Bateria> todos(Cursor obtenidos) {
        List<Bateria> respuesta = new ArrayList<Bateria>();
        while (obtenidos.moveToNext()) {
            Bateria mensaje= new Bateria(
                    obtenidos.getLong(1),
                    obtenidos.getInt(2)
            );

            respuesta.add(mensaje);
        }
        return  respuesta;
    }



}
