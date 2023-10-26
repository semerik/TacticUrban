package com.example.tacticurban;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import OpenHelper.SQLite_OpenHelper;


public class SingupAdmin extends AppCompatActivity {

    //Fecha
    private ImageView imgFecha;
    private Calendar calendar;

    //Ubicacion
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView fullName,user,email,password, fechaSeleccionada,ubicacion;
    private RadioGroup groupGender;
    private RadioButton radioMale, radioFemale;
    private CheckBox terms;
    private Button btnRegister;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    SQLite_OpenHelper bdHelper = new SQLite_OpenHelper(this,null,1);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        bdHelper.crearAdminPrincipal();
        //R.finders y parametros
        fullName = findViewById(R.id.createAccount_fullName);
        user = findViewById(R.id.loginForm_userName);
        imgFecha = findViewById(R.id.editTextFecha);
        email = findViewById(R.id.createAccount_eMail);
        password = findViewById(R.id.loginForm_password);
        groupGender = findViewById(R.id.radio_group_gender);
        radioMale = findViewById(R.id.radio_male);
        radioFemale = findViewById(R.id.radio_female);
        terms = findViewById(R.id.createAccount_terms);
        fechaSeleccionada = findViewById(R.id.textViewFecha);
        ubicacion = findViewById(R.id.addressTextView);
        btnRegister = findViewById(R.id.createAccount_register);

        //base de datos registrar usuario
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bdHelper.insertUser(
                        fullName.getText().toString(),
                        user.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        saberRadioGroup(),
                        fechaSeleccionada.getText().toString(),
                        ubicacion.getText().toString(),
                        "ciudadano"
                );

                bdHelper.closeBD();

                Toast.makeText(getApplicationContext(),"Registro almacenado con éxito",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), LoginForm.class);
                startActivity(intent);
            }
        });


        //Fecha
        calendar = Calendar.getInstance();
        imgFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SingupAdmin.this,
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
                                    fechaSeleccionada.setText(selectedDate);
                                    // Aquí puedes permitir que el usuario continúe con el registro.
                                } else {
                                    // Muestra un mensaje de error o toma la acción correspondiente.
                                    fechaSeleccionada.setText("Edad insuficiente para registrarse.");

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


        Button getLocationButton = findViewById(R.id.getLocationButton);
        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SingupAdmin.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido, obtener ubicación
                    obtenerDireccion();
                } else {
                    // Solicitar permiso
                    ActivityCompat.requestPermissions(SingupAdmin.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });
    }

    //Aca termina onCreate



    private String saberRadioGroup(){

        int selectedId = groupGender.getCheckedRadioButtonId();
        String selectedOption = "";

        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            selectedOption = radioButton.getText().toString();
        }

        return selectedOption;
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
                            Geocoder geocoder = new Geocoder(SingupAdmin.this, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (!addresses.isEmpty()) {
                                    Address address = addresses.get(0);
                                    String direccion = address.getAddressLine(0);
                                    ubicacion.setText(direccion);
                                } else {
                                    ubicacion.setText("No se pudo obtener la dirección.");
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