package com.example.ejericioagendapropuesto;

public class Contacto {
    private String nombre;
    private String apellidos;
    private long numTelefono;
    private String correo;

    public Contacto() {

    }

    public Contacto(String nombre, String apellidos, long numTelefono, String correo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numTelefono = numTelefono;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public long getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(long numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", numTelefono=" + numTelefono +
                ", correo='" + correo + '\'' +
                '}';
    }
}
