package com.example.regisapp.VIEJO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.List;

public class DB_STATUS extends SQLiteOpenHelper {

    public static final String DB_NOMBRE = "DB_REGISAPP.db";
    Context ct;
    public DB_STATUS(Context context) {
        super(context, DB_NOMBRE, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        this.ct = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TB_BATERIA.crearTablaBateria());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TB_BATERIA.EliminarTabla());
        onCreate(db);
    }

    public void guardarRegistro(int nivel) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "";
        ContentValues cv = new ContentValues();
        cv.put("NIVEL", nivel);
        cv.put("FECHA", System.currentTimeMillis());
        db.insert(TB_BATERIA.NOMBRE,null,cv);
    }

    public void eliminarBase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TB_BATERIA.NOMBRE);
    }

    public List<Bateria> todosStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TB_BATERIA.NOMBRE, null);
        return TB_BATERIA.todos(cursor);
    }
}
