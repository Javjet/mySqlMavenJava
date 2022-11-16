package org.example.clases;

import java.util.Date;

public class Prestamo {
    private int Cod_Libro;
    private int Cod_Socio;
    private Date Fecha_Ini_Prestamo;

    public Prestamo(int cod_Libro, int cod_Socio, Date fecha_Ini_Prestamo, Date fecha_Fin_Prestamo) {
        Cod_Libro = cod_Libro;
        Cod_Socio = cod_Socio;
        Fecha_Ini_Prestamo = fecha_Ini_Prestamo;
        Fecha_Fin_Prestamo = fecha_Fin_Prestamo;
    }

    public int getCod_Libro() {
        return Cod_Libro;
    }

    public void setCod_Libro(int cod_Libro) {
        Cod_Libro = cod_Libro;
    }

    public int getCod_Socio() {
        return Cod_Socio;
    }

    public void setCod_Socio(int cod_Socio) {
        Cod_Socio = cod_Socio;
    }

    public Date getFecha_Ini_Prestamo() {
        return Fecha_Ini_Prestamo;
    }

    public void setFecha_Ini_Prestamo(Date fecha_Ini_Prestamo) {
        Fecha_Ini_Prestamo = fecha_Ini_Prestamo;
    }

    public Date getFecha_Fin_Prestamo() {
        return Fecha_Fin_Prestamo;
    }

    public void setFecha_Fin_Prestamo(Date fecha_Fin_Prestamo) {
        Fecha_Fin_Prestamo = fecha_Fin_Prestamo;
    }

    private Date Fecha_Fin_Prestamo;
}
