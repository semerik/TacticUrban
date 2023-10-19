package com.example.tacticurban;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

import OpenHelper.IncidentesSQLiteOpenHelper;

import java.io.IOException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ReportarIncidente extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private Marker currentMarker;
    private MapView mapView;
    private GoogleMap googleMap;

    private EditText editLocalizacion;
    private Spinner spinnerIncidente;
    private EditText editDescripcion;
    private IncidentesSQLiteOpenHelper dbHelper;

    private ImageView imgFecha;
    private TextView textFecha;
    private UsuarioActual usuarioActual;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_incidente);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        editLocalizacion = findViewById(R.id.editLocalizacion);
        spinnerIncidente = findViewById(R.id.spinnerIncidente);
        editDescripcion = findViewById(R.id.editDescripcion);

        dbHelper = new IncidentesSQLiteOpenHelper(this);
        dbHelper.openBD();

        imgFecha = findViewById(R.id.editTextFecha);
        textFecha = findViewById(R.id.textViewFecha);
        usuarioActual = UsuarioActual.getInstance();

        imgFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textFecha.setText(obtenerFechaHoy());
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        googleMap.setOnMapClickListener(this);

        // Establece una ubicación inicial y zoom
        LatLng ubicacionInicial = new LatLng(4.6097 , -74.0817 );
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
        markerOptions.title("REPORT");

        currentMarker = googleMap.addMarker(markerOptions); // Asigna el nuevo marcador como el marcador actual

        // Actualiza el EditText con la ubicación del marcador
        // Utiliza geocodificación inversa para obtener la dirección
        String direccion = obtenerDireccionDesdeLatLong(latLng.latitude, latLng.longitude);
        EditText editLocalizacion = findViewById(R.id.editLocalizacion);
        editLocalizacion.setText(direccion);
    }

    // Método para obtener la dirección desde latitud y longitud
    private String obtenerDireccionDesdeLatLong(double latitud, double longitud) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitud, longitud, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Construye la dirección como una cadena de texto
                StringBuilder direccionBuilder = new StringBuilder();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    direccionBuilder.append(address.getAddressLine(i));
                    if (i < address.getMaxAddressLineIndex()) {
                        direccionBuilder.append(", ");
                    }
                }
                return direccionBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "No se pudo obtener la dirección.";
    }

    // Método para el botón "Reportar"
    public void onReportarClick(View view) {
        String localizacion = editLocalizacion.getText().toString();
        String tipoIncidente = spinnerIncidente.getSelectedItem().toString();
        String descripcion = editDescripcion.getText().toString();


        if (!localizacion.isEmpty() && !tipoIncidente.isEmpty() && !descripcion.isEmpty()) {
            // Insertar el incidente en la base de datos
            dbHelper.insertIncidente(localizacion, tipoIncidente, descripcion,(String) textFecha.getText(),(String) usuarioActual.getUsername());

            // Limpiar los campos después de la inserción
            editLocalizacion.setText("");
            spinnerIncidente.setSelection(0); // Establecer la selección inicial en el Spinner
            editDescripcion.setText("");

            // Mostrar un mensaje de éxito o realizar cualquier otra acción que necesites
            Toast.makeText(this, "Incidente reportado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }



    public String obtenerFechaHoy() {
        // Obtén una instancia del calendario con la fecha actual
        Calendar calendario = Calendar.getInstance();
        // Crea un objeto SimpleDateFormat para formatear la fecha como desees
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        // Obtiene la fecha actual formateada como una cadena de texto
        String fechaHoy = formatoFecha.format(calendario.getTime());

        return fechaHoy;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        dbHelper.closeBD(); // Cerrar la base de datos al destruir la actividad
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
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}