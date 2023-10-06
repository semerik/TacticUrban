package com.example.tacticurban;

public class UsuarioActual {
    private static UsuarioActual instancia;
    private  String full_name,
            username,
            email, password,
            gender,
            birthdate,
            ubication;


    private UsuarioActual() {
        // Constructor privado para implementar Singleton
    }

    public static synchronized UsuarioActual getInstance() {
        if (instancia == null) {
            instancia = new UsuarioActual();
        }
        return instancia;
    }

    public void iniciarSesion(String full_name, String username, String email, String password, String gender, String birthdate, String ubication) {
        this.full_name = full_name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birthdate = birthdate;
        this.ubication = ubication;
    }


    public void cerrarSesion() {
        this.full_name = null;
        this.username = null;
        this.email = null;
        this.password = null;
        this.gender = null;
        this.birthdate = null;
        this.ubication = null;
        // Limpia cualquier otro dato de sesión si es necesario
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
}

