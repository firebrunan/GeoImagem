package com.facens.exercicio;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class GPStracker implements LocationListener {

    Context context;
    //
    public GPStracker(Context c){
        context = c;
    }
    //função para conseguir o gps do aparelho
    public Location getLocation() {
// pedir permissão para acessar gps
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
//caso gps seja negado, mostrar uma mensagem de negação
            Toast.makeText(context, "Não foi permitir", Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        //Verificar se GPS está ligado e coletar coordenadas
        if (isGPSEnabled){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000,  10, this);
            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return l;
        } else {
//Pedir ao usuário para acessar gps
            Toast.makeText(context, "Por favor, habitar o GPS!", Toast.LENGTH_LONG).show();
        }
//Encerrar a função com um valor nulo
        return null;
    }
    //
    @Override
    public void onProviderDisabled(@NonNull String provider) {    }
    //
    @Override
    public void onLocationChanged(@NonNull Location location) {    }
    //
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {    }
}
