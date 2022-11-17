package org.example;

import org.example.clases.Libro;

import java.sql.*;

public class Main {
    private static String url = "jdbc:mysql://localhost:6603";
    private static String user ="root";
    private static String password ="alumno";
    private Libro libro=new Libro(1,"2","3","11/12/2001","3",3,3);
    public static void main(String[] args) {

        try (Connection conexion=DriverManager.getConnection(url,user,password)) {

            if(conexion!=null){
                System.out.println("Conexion establecida");

                Statement s = conexion.createStatement();
                s.execute("DROP DATABASE IF EXISTS BIBLIOTECA");
                s.execute("CREATE DATABASE BIBLIOTECA");
                s.execute("USE BIBLIOTECA");
                createTable(s);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createTable(Statement s){
        try {
            s.execute("DROP TABLE IF EXISTS CLIENTES");
            s.execute("CREATE TABLE CLIENTES (DNI CHAR(9) NOT NULL, APELLIDOS VARCHAR(32) NOT NULL, CP CHAR(5), " +
                    "PRIMARY KEY (DNI))");
            s.execute("DROP TABLE IF EXISTS LIBRO");
            s.execute("CREATE TABLE LIBRO (CODIGO INT, TITULO VARCHAR(40) NOT NULL, AUTOR VARCHAR(40)," +
                    "EDITORIAL VARCHAR(40),AÑO DATE, ISBN VARCHAR(40) NOT NULL UNIQUE, CANT_EJEMPLARES INT(8) , " +
                    "NUM_PAG INT(4), PRIMARY KEY (CODIGO))");
            s.execute("DROP TABLE IF EXISTS SOCIO");
            s.execute("CREATE TABLE SOCIO (CODIGO INT, NOMBRE VARCHAR(40) NOT NULL, APELLIDOS VARCHAR(40) NOT NULL, " +
                    "FECHA_NAC DATE, DOMICILIO VARCHAR(40), TELEFONO VARCHAR(12), PRIMARY KEY (CODIGO))");
            s.execute("DROP TABLE IF EXISTS CLIENTES");
            s.execute("CREATE TABLE PRESTAMO (COD_LIBRO INT, COD_SOCIO INT, FECHA_INI_PRESTAMO DATE, " +
                    "FECHA_FIN_PRESTAMO DATE,  PRIMARY KEY (COD_LIBRO,COD_SOCIO),  FOREIGN KEY  (COD_LIBRO) " +
                    "REFERENCES LIBRO(CODIGO) ON DELETE CASCADE, FOREIGN KEY  (COD_SOCIO) REFERENCES SOCIO(CODIGO) ON DELETE CASCADE)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void insertTable(Connection conexion){
        try {
            PreparedStatement insertarLibro=conexion.prepareStatement("INSERT INTO LIBRO(CODIGO,TITULO, AUTOR ,EDITORIAL ,AÑO , ISBN) VALUES ("")" );
            PreparedStatement insertarSocio=conexion.prepareStatement("INSERT INTO LIBRO(CODIGO,NOMBRE, APELLIDOS ,FECHA_NAC ,DOMICILIO , TELEFONO) VALUES ("")" );
            PreparedStatement insertarPrestamo=conexion.prepareStatement("INSERT INTO LIBRO(COD_LIBRO,COD_SOCIO, FECHA_INI_PRESTAMO ,FECHA_FIN_PRESTAMO ,AÑO , ISBN) VALUES ("")" );
            insertarLibro.execute();
            insertarSocio.execute();
            insertarPrestamo.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}