package com.example.regisapp.VIEJO;

public class NEGOCIO {
    public static String Formateados(int tope){
        String respueta ="";
        for (int i=0; i < tope; i++ ){
            respueta += "%s, ";
        }
        respueta = respueta.substring(0, respueta.length()-2);
        return respueta;
    }
}
