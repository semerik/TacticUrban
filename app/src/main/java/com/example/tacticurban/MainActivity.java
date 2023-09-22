package com.example.tacticurban;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Fecha
    private EditText editTextFecha;
    private Calendar calendar;

    //Ubicacion
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView addressTextView;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private TextView textViewFechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fecha

        editTextFecha = findViewById(R.id.editTextFecha);
        textViewFechaSeleccionada = findViewById(R.id.textViewFechaSeleccionada);
        calendar = Calendar.getInstance();

        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, month, day);

                                Date currentDate = Calendar.getInstance().getTime();
                                int age = calculateAge(selectedCalendar.getTime(), currentDate);

                                if (age >= 18) {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                    String selectedDate = dateFormat.format(selectedCalendar.getTime());
                                    editTextFecha.setText(selectedDate);
                                    textViewFechaSeleccionada.setText(selectedDate);
                                    // Aquí puedes permitir que el usuario continúe con el registro.
                                } else {
                                    // Muestra un mensaje de error o toma la acción correspondiente.
                                    textViewFechaSeleccionada.setText("Edad insuficiente para registrarse.");
                                    // Aquí puedes mostrar un mensaje de error o tomar otra acción.
                                }
                            }
                        },
                        year, month, dayOfMonth
                );
                datePickerDialog.show();
            }
        });



        //Ubicación
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        addressTextView = findViewById(R.id.addressTextView);

        Button getLocationButton = findViewById(R.id.getLocationButton);
        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido, obtener ubicación
                    obtenerDireccion();
                } else {
                    // Solicitar permiso
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });
    }

    private int calculateAge(Date birthDate, Date currentDate) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);
        int birthYear = birthCalendar.get(Calendar.YEAR);
        int currentYear = calendar.get(Calendar.YEAR);
        return currentYear - birthYear;
    }

    private void obtenerDireccion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (!addresses.isEmpty()) {
                                    Address address = addresses.get(0);
                                    String direccion = address.getAddressLine(0);
                                    addressTextView.setText(direccion);
                                } else {
                                    addressTextView.setText("No se pudo obtener la dirección.");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso concedido, obtener ubicación
                obtenerDireccion();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Agrega esta línea
    }

}
