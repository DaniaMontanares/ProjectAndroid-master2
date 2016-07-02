package com.example.bastian.prueba1.controllers.Gets;

import android.os.AsyncTask;
import android.util.Log;

import com.example.bastian.prueba1.MainActivity;
import com.example.bastian.prueba1.models.Lugar;
import com.example.bastian.prueba1.views.eventoNuevo;
import com.example.bastian.prueba1.views.perfilUsuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by bastian on 28-05-16.
 */
public class LugarGet extends AsyncTask<String, Void, String> {

    private MainActivity activity;
    private eventoNuevo eventoActivity;
    private Lugar lugar[];
    private int idActivity;

    public LugarGet(MainActivity activity,int id){
        this.activity = activity;
        this.idActivity = id;
    }

    public LugarGet(eventoNuevo activity,int id) {
        this.eventoActivity = activity;
        this.idActivity = id;}



    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... url) {
        try {
            URL u = new URL(url[0]);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            return new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (MalformedURLException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (ProtocolException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        } catch (IOException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        }
        return null;
    }

    public void getLugar(String json){
        try {
            JSONArray ja = new JSONArray(json);
            lugar = new Lugar[ja.length()];
            int largo = ja.length()-1;
            for (int i = 0; i < ja.length(); i++) {
                JSONObject row = ja.getJSONObject(largo);
                Lugar aux = new Lugar();
                aux.setId(row.getInt("idLugar"));
                aux.setLatitud(row.getDouble("latitud"));
                aux.setLongitud(row.getDouble("longitud"));
                aux.setNombre(row.getString("nombreLugar"));
                lugar[i] = aux;
                largo--;
            }
        } catch (JSONException e) {
            Log.e("ERROR", this.getClass().toString() + " " + e.toString());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(idActivity == 1) {
            getLugar(result);
            activity.obtenerLugarEvento(lugar);
        }
        if(idActivity == 2){
            getLugar(result);
            eventoActivity.obteneridLugar(lugar);
        }

    }
}
