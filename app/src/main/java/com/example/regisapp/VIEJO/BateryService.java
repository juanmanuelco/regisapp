package com.example.regisapp.VIEJO;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class BateryService extends Service {

    DB_STATUS status;

    @Override
    public void onCreate() {
        super.onCreate();
        status = new DB_STATUS(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BatteryBroadcast broadcast = new BatteryBroadcast();
        this.registerReceiver(broadcast, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(Datos.nivelBateriActual >0) {

                    DateTime fechaHora = new DateTime();
                    if(fechaHora.getHourOfDay() == 23 && fechaHora.getMinuteOfHour() ==59){
                        enviarBase();
                        status.eliminarBase();
                    }else{
                        status.guardarRegistro(Datos.nivelBateriActual);
                    }
                }
            }
        }, 0, 60000);
        return START_STICKY;
    }

    public void enviarBase(){
        String serverURL ="http://192.168.1.5:8080/api/envio";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, serverURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resultado) {
                        Toast.makeText(BateryService.this, resultado, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                enviarBase();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                List<Bateria> listado = status.todosStatus();
                String datos = "";
                int valor =0;
                for (Bateria bat: listado ) {
                    String fecha = getDate(bat.getFechaHora(), "yyyy-MM-dd hh:mm:ss");
                    datos+=bat.getNivel()+"----"+fecha+ "%%%%%";
                }
                String[] datos_u={"datos", "dispositivo"};
                String[] data_ui={datos, getDeviceName()};
                return asignacionMAP(datos_u, data_ui);
            }
        };
        singletonDatos.getInstancia(this).addToRequest(stringRequest);


    }

    public static Map<String,String> asignacionMAP(String[] datos_u, String[] datos_usuario) {
        Map <String,String> params =new HashMap<String, String >();
        for(int i=0; i< datos_u.length; i++){
            params.put(datos_u[i],datos_usuario[i]);
        }
        return params;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) return capitalize(model);
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) return str;
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) capitalizeNext = true;
            phrase += c;
        }
        return phrase;
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}

