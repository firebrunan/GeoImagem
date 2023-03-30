package com.facens.exercicio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Definindo variáveis da imagem e botão de gps
    private ImageView ImageViewFoto;
    private Button btnGeo;

    //Inicia o aplicativo e faz set da parte visual como o activity main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Quando o botão do gps for clicado, pedir permissão da localização
        btnGeo = (Button) findViewById(R.id.btn_gps);
        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        //
        btnGeo.setOnClickListener(new View.OnClickListener(){

            //Adquirir coordenadas da localização do aparelho ao clicar
            @Override
            public void onClick(View view){
                    GPStracker g = new GPStracker(getApplication());
                    Location l = g.getLocation();
            //Se não tiver dois valores para coordenada, crie a variável de cada um e coloque o texto em que aparecerão na tela
                if(l != null) {
                    double lat = l.getLatitude();
                    double lon = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "LATITUDE: " + lat +"\n LONGITUDE: " + lon, Toast.LENGTH_LONG).show();
                }
            }
        });
    //Checar se já foi dada permissão da camera para pedir essa permissão caso não tenha
    if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.CAMERA}, 0);
    }

    //receber imagem da camera e mostrar no app
    ImageViewFoto = (ImageView) findViewById(R.id.pao);
    findViewById(R.id.btn_pic).setOnClickListener(new View.OnClickListener() {

        //começar comando de tirar foto ao clicar
        @Override
        public void onClick(View view) {
            tirarFoto();
        }
    });
}
    //printar a imagem na camera do momento que o botão foi clicado
    private void tirarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    //
        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imagem = (Bitmap) extras.get("data");
            ImageViewFoto.setImageBitmap(imagem);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
