package org.example.clases;

import java.util.Date;

public class Libro {
    private int codigo;
    private String titulo;
    private String autor;
    private int año;
    private String ISBN;
    private String editorial;
    private int num_Ejemplares;
    private int num_Paginas;

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Libro(int codigo, String titulo, String autor, String editorial, int año, String ISBN, int num_Ejemplares, int num_Paginas) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial=editorial;
        this.año = año;
        this.ISBN = ISBN;
        this.num_Ejemplares = num_Ejemplares;
        this.num_Paginas = num_Paginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getNum_Ejemplares() {
        return num_Ejemplares;
    }

    public void setNum_Ejemplares(int num_Ejemplares) {
        this.num_Ejemplares = num_Ejemplares;
    }

    public int getNum_Paginas() {
        return num_Paginas;
    }

    public void setNum_Paginas(int num_Paginas) {
        this.num_Paginas = num_Paginas;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
