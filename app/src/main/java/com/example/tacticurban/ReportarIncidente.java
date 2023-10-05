package com.example.tacticurban;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReportarIncidente extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private Marker currentMarker;
    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_incidente);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        googleMap.setOnMapClickListener(this);

        // Establece una ubicación inicial y zoom
        LatLng ubicacionInicial = new LatLng(4.6097 , 74.0817 );
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionInicial, 12));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (currentMarker != null) {
            currentMarker.remove(); // Elimina el marcador existente
        }

        // Crea un nuevo marcador en la posición del clic
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Nuevo marcador");

        currentMarker = googleMap.addMarker(markerOptions); // Asigna el nuevo marcador como el marcador actual
    }

    public void onEnviarIncidenteClick() {
        if (currentMarker != null) {
            LatLng markerPosition = currentMarker.getPosition();

            // Aquí puedes hacer lo que necesites con las coordenadas (markerPosition)

            // Por ejemplo, mostrar las coordenadas en un Toast
            Toast.makeText(this, "Latitud: " + markerPosition.latitude + ", Longitud: " + markerPosition.longitude, Toast.LENGTH_SHORT).show();

            // También puedes guardar las coordenadas en una variable o base de datos según tus necesidades.
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}