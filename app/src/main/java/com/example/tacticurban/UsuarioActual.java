package com.example.tacticurban;

import android.widget.Toast;

public class UsuarioActual {
    private static UsuarioActual instancia;
    private  String full_name,
            username,
            email, password,
            gender,
            birthdate,
            ubication,
            rol;


    private UsuarioActual() {
        // Constructor privado para implementar Singleton
    }

    public static synchronized UsuarioActual getInstance() {
        if (instancia == null) {
            instancia = new UsuarioActual();
        }
        return instancia;
    }

    public void iniciarSesion(String full_name, String username, String email, String password, String gender, String birthdate, String ubication,String rol) {
        this.full_name = full_name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthdate = birthdate;
        this.ubication = ubication;
        this.rol = rol;
    }


    public void cerrarSesion() {
        instancia = null;
    }

    public static UsuarioActual getInstancia() {
        return instancia;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getUbication() {
        return ubication;
    }
    public String getRol() {
        return rol;
    }
}

