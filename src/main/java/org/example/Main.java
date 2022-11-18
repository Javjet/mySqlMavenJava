package org.example;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.clases.Leer;
import org.example.clases.Libro;
import org.example.clases.Socio;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    private static String url = "jdbc:mysql://localhost:6603";
    private static String user ="root";
    private static String password ="alumno";


    //datos de los socios
    private static Date bornDate1 = new Date(978307200000L); //01/01/2001
    private static Date bornDate2 = new Date(315532800000L); //01/01/1980
    private static Date bornDate3 = new Date(631152000000L); //01/01/1990
    private static Date bornDate4 = new Date(788918400000L); //01/01/1995
    private static Date bornDate5 = new Date(1104537600000L); //01/01/2005
    private static List<Date> membersBornDates = Arrays.asList(bornDate1, bornDate2, bornDate3, bornDate4, bornDate5);


    //datos préstamos


    public static void main(String[] args) {

        /*try (Connection conexion=DriverManager.getConnection(url,user,password)) {

            //conexionConectar.setNetworkTimeout();

            if(conexion!=null){

                System.out.println("Conexion establecida");

                Statement s = conexion.createStatement();
                s.execute("DROP DATABASE IF EXISTS BIBLIOTECA");
                s.execute("CREATE DATABASE BIBLIOTECA");
                s.execute("USE BIBLIOTECA");
                createTable(s);

                insertLibro(conexion);

                insertSocio(conexion);
                /*if(conexionConectar!=null)

                userInsertLibro(conexionConectar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        try(Connection conexionConectar=obtenerFuenteDeDatos().getConnection()){
            String nombreDB=createDB(conexionConectar);
            deleteDB(conexionConectar);
            conexionConectar.createStatement().execute("USE BIBLIOTECA");
            userInsertLibro(conexionConectar);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String createDB(Connection conexion) throws SQLException {
        String dataBaseCreation="CREATE DATABASE IF NOT EXISTS ";
        String nombreDB= Leer.pedirCadena("Introduzca el nombre de la base de datos, que quiere crear: ");
        PreparedStatement preparedStatement=conexion.prepareStatement( dataBaseCreation+nombreDB);
        preparedStatement.execute();
        return nombreDB;
    }
    public static void deleteDB(Connection conexion) throws SQLException {
        String dataBaseDrop="DROP DATABASE IF EXISTS ";
        String nombreDB= Leer.pedirCadena("Introduzca el nombre de la base de datos, que quiere eliminar: ");
        PreparedStatement preparedStatement=conexion.prepareStatement( dataBaseDrop+nombreDB);
        preparedStatement.execute();
    }
    public static void createTable(Statement s){
        try {
            s.execute("DROP TABLE IF EXISTS CLIENTES");
            s.execute("CREATE TABLE CLIENTES (DNI CHAR(9) NOT NULL, APELLIDOS VARCHAR(32) NOT NULL, CP CHAR(5), " +
                    "PRIMARY KEY (DNI))");
            s.execute("DROP TABLE IF EXISTS LIBRO");
            s.execute("CREATE TABLE LIBRO (CODIGO INT, TITULO VARCHAR(40) NOT NULL, AUTOR VARCHAR(40)," +
                    "EDITORIAL VARCHAR(40),AÑO INT(4), ISBN VARCHAR(40) NOT NULL UNIQUE, CANT_EJEMPLARES INT(8) , " +
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


    public static void userInsertsLibro(Connection conexionConectar){
        try {
            Libro libro_Usr=new Libro(99990,"2","3","Editorial",1963,"3",3,3);
            conexionConectar.createStatement().execute("USE BIBLIOTECA");
            Statement statement=conexionConectar.createStatement();
            ResultSet resultado=statement.executeQuery("SELECT CODIGO FROM LIBRO");
            PreparedStatement preparedStatement= conexionConectar.prepareStatement("INSERT INTO LIBRO VALUES(?,?,?,?,?,?,?,?)");
            List<String> lista = new ArrayList<>();
            while (resultado.next()){
                lista.add(resultado.getString("CODIGO"));
            }
            if (!lista.contains(libro_Usr.getCodigo())){
                preparedStatement.setInt(1,libro_Usr.getCodigo());
                preparedStatement.setInt(5,libro_Usr.getAño());
                preparedStatement.setInt(7,libro_Usr.getNum_Ejemplares());
                preparedStatement.setInt(8,libro_Usr.getNum_Paginas());
                preparedStatement.setString(2,libro_Usr.getTitulo());
                preparedStatement.setString(3,libro_Usr.getAutor());
                preparedStatement.setString(4,libro_Usr.getEditorial());
                preparedStatement.setString(6,libro_Usr.getISBN());
                preparedStatement.execute();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void insertLibro(Connection conexion){

        try {
            PreparedStatement pS=conexion.prepareStatement("INSERT INTO LIBRO VALUES(?,?,?,?,?,?,?,?)");
            List<Libro> libros=new ArrayList<>();
            List<Integer> booksCodes = Arrays.asList(1, 2, 3, 4, 5);
            List<String> booksTitles = Arrays.asList("El Perfume", "El médico", "Tuareg", "IT", "Momo");
            List<String> booksAuthors = Arrays.asList("Patrick Suskind", "Noah Gordon", "Vazquez Figueroa", "Stephen King", "Michael Ende");
            List<String> booksEditorials = Arrays.asList("Edelvives", "Planeta", "Marcombo", "Alfaguara", "Sintesis");
            List<Integer> booksYears = Arrays.asList(1985, 1986, 1980, 1986, 1973);
            List<String> booksIsbns = Arrays.asList("ISBN1", "ISBN2", "ISBN3", "ISBN4", "ISBN5");
            List<Integer> booksCopies = Arrays.asList(10, 20, 30, 40, 50);
            List<Integer> booksPages = Arrays.asList(180, 450, 675, 785, 350);
            for (int i = 0; i < booksCodes.size(); i++) {
                libros.add(new Libro(booksCodes.get(i),booksTitles.get(i),booksAuthors.get(i),booksEditorials.get(i),
                        booksYears.get(i),booksIsbns.get(i), booksCopies.get(i),booksPages.get(i)));
            }
            for (Libro libro : libros) {
                pS.setInt(1,libro.getCodigo());
                pS.setInt(5,libro.getAño());
                pS.setInt(7,libro.getNum_Ejemplares());
                pS.setInt(8,libro.getNum_Paginas());
                pS.setString(2,libro.getTitulo());
                pS.setString(3,libro.getAutor());
                pS.setString(4,libro.getEditorial());
                pS.setString(6,libro.getISBN());
                pS.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource obtenerFuenteDeDatos(){
        BasicDataSource datos = new BasicDataSource();
        datos.setUrl(url); // url de la base de datos
        datos.setUsername(user); // usuario de la base de datos
        datos.setPassword(password); // contraseña de la base de datos
        datos.setInitialSize(50); //tamaño de conexiones a la base de datos
        return datos;
    }
    public static void insertSocio(Connection conexion){

        try {
            PreparedStatement pS=conexion.prepareStatement("INSERT INTO SOCIO VALUES(?,?,?,?,?,?)");
            List<Socio> socios = new ArrayList<>();
            List<Integer> membersCodes = Arrays.asList(1, 2, 3, 4, 5);
            List<String> membersNames = Arrays.asList("Pepe", "Pepa", "Juan", "Juana", "Jose");
            List<String> membersSurnames = Arrays.asList("Sanchez", "Butler", "Gaviria", "Dessy", "Otin");
            java.sql.Date sqlDate;
            List<String> membersAddresses = Arrays.asList("Sabi", "Jaca", "Gavín", "Tramacastilla", "Badaguás");
            List<String> membersPhoneNumbers = Arrays.asList("55555555", "2345123", "3456789", "55555555", "666666");
            for (int i = 0; i < membersBornDates.size(); i++) {
                socios.add(new Socio(membersCodes.get(i),membersNames.get(i),membersSurnames.get(i),membersBornDates.get(i),membersAddresses.get(i),
                        membersPhoneNumbers.get(i)));
            }
            for (Socio socio : socios) {
                pS.setInt(1,socio.getCodigo());
                sqlDate = new java.sql.Date(socio.getFecha_Nac().getTime());
                pS.setDate(4, sqlDate);
                pS.setString(2,socio.getNombre());
                pS.setString(3,socio.getApellidos());
                pS.setString(5,socio.getDomicilio());
                pS.setString(6,socio.getTelefono());
                pS.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static void insertPrestamo(Connection conexion){

        try {
            PreparedStatement pS=conexion.prepareStatement("INSERT INTO PRESTAMO VALUES(?,?,?,?)");

            for (int i = 0; i < membersBornDates.size(); i++) {
                socios.add(new Socio(membersCodes.get(i),membersNames.get(i),membersSurnames.get(i),membersBornDates.get(i),membersAddresses.get(i),
                        membersPhoneNumbers.get(i)));
            }
            for (Socio socio : socios) {
                pS.setInt(1,socio.getCodigo());
                sqlDate = new java.sql.Date(socio.getFecha_Nac().getTime());
                pS.setDate(4, sqlDate);
                pS.setString(2,socio.getNombre());
                pS.setString(3,socio.getApellidos());
                pS.setString(5,socio.getDomicilio());
                pS.setString(6,socio.getTelefono());
                pS.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    public static void userInsertLibro(Connection conexion){
    Libro libro_Usr=new Libro(10000,"2","3","Editorial",1963,"3",3,3);
        libro_Usr.setCodigo(Leer.pedirEntero("Introduzca el codigo del libro, con un numero entero"));
        libro_Usr.setTitulo(Leer.pedirCadena("Introduzca el titulo del libro"));
        libro_Usr.setAutor(Leer.pedirCadena("Introduzca el autor del libro"));
        libro_Usr.setEditorial(Leer.pedirCadena("Introduzca el editorial del libro"));
        libro_Usr.setAño(Leer.pedirEntero("Introduzca el año del libro, con un numero entero"));
        libro_Usr.setISBN(Leer.pedirCadena("Introduzca el ISBN del libro"));
        libro_Usr.setNum_Ejemplares(Leer.pedirEntero("Introduzca el numero de ejemplares del libro, con un numero entero"));
        libro_Usr.setNum_Paginas(Leer.pedirEntero("Introduzca el numero de paginas, con un numero entero"));
        try {
            PreparedStatement pS=conexion.prepareStatement("INSERT INTO LIBRO VALUES(?,?,?,?,?,?,?,?)");
                pS.setInt(1,libro_Usr.getCodigo());
                pS.setInt(5,libro_Usr.getAño());
                pS.setInt(7,libro_Usr.getNum_Ejemplares());
                pS.setInt(8,libro_Usr.getNum_Paginas());
                pS.setString(2,libro_Usr.getTitulo());
                pS.setString(3,libro_Usr.getAutor());
                pS.setString(4,libro_Usr.getEditorial());
                pS.setString(6,libro_Usr.getISBN());
                pS.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}