/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package online8;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import static online8.ES.*;
import static online8.Utilidades.*;
import static online8.Cliente.*;
import static online8.Enumerados.*;
import static online8.Mercancias.*;
import static online8.Alquiler.*;

/**
 * @author juan
 */
public class AlquilerVehiculos {

    //guardar datos al salir cuando haya cambios que guardar
    public static boolean guardarDatos = true;
    
    private static final int MAX_VEHICULOS = 50;
    private static final int MAX_CLIENTES = 50;
    private static final int MAX_ALQUILERES = 50;
    private static ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Alquiler> alquileres = new ArrayList<>();

    public AlquilerVehiculos() {
    }

    public static Cliente getCliente(String dni) {

        Cliente c = null;

        if ((Utilidades.comprobarDni(dni))) {
            for (int i = 0; i < MAX_CLIENTES; i++) {
                //si la posicion array no es nula y coincide el dni existente lo devuelve
                if (clientes.get(i).getDni().equalsIgnoreCase(dni)) {
                    c = clientes.get(i);
                    break;
                }
            }
        }

        return c;

    }

    public static int buscarCliente(String dni) {
        //Devuelve la posición del array de un DNI determinado.

        int pos = -1;

        for (int i = 0; i < clientes.size(); i++) {
            if (dni.equalsIgnoreCase(clientes.get(i).getDni())) {
                pos = i;
            }
        }
        return pos;
    }

    public static Vehiculo getVehiculo(String matricula) {
        /*Crea un método getVehiculo que se le pase la matrícula de un turismo y nos
lo devuelva si este existe o null en caso contrario.*/

        Vehiculo v = null;

        if ((Utilidades.comprobarMatricula(matricula))) {
            for (int i = 0; i < vehiculos.size(); i++) {
                //for (Vehiculo vehiculo : vehiculos){
                //si la posicion array no es nula y coincide el dni existente lo devuelve        
                if (vehiculos.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                    //if (vehiculo.getMatricula().equalsIgnoreCase(matricula)){
                    //utilizar método get de arryLIst

                    v = vehiculos.get(i);

                    break;
                }
            }
        }
        return v;
    }

    public static int buscarVehiculo(String matricula) {
        //Devuelve la posición del array de una matrícula determinada.

        int pos = -1;

        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                pos = i;
            }
        }
        return pos;
    }

    public static void cerrarAlquiler() {
        //Crea un método cerrarAlquiler que cierre el alquiler dado un cliente y un vehiculo.

        String dni;
        String dniAux;
        String matricula;
        int posCliente;
        int posVehiculo;
        boolean value = false;
        Cliente cliente;
        Vehiculo vehiculo;
        double precio = 0.0;

        dni = (leerCadena("\nIntroduce Dni/Nie del cliente: ")).toUpperCase();
        dniAux = dni;

        if (comprobarDni(dniAux)) {

            //Comprobamos si es un NIE, y en caso de serlo lo convertimos a DNI para posteriormente
            //comprobar la letra final.
            if (dniAux.substring(0, 1).equalsIgnoreCase("X")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Y")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Z")) {
                dniAux = pasarNieADni(dniAux);
            }

            //Comprobamos la letra final del dni para validarlo
            if (dniAux.substring(8, 9).equalsIgnoreCase(calcularLetraDni(dniAux.substring(0, 8)))) {

                posCliente = buscarCliente(dni);

                if (posCliente != -1) {

                    cliente = clientes.get(posCliente);

                    matricula = leerCadena("\nIntroduce matricula del vehículo: ").toUpperCase();

                    if (comprobarMatricula(matricula)) {

                        posVehiculo = buscarVehiculo(matricula);

                        if (posVehiculo != -1) {

                            vehiculo = vehiculos.get(posVehiculo);

                            if (!vehiculo.getDisponible()) {

                                for (int i = 0; i < alquileres.size() && !value; i++) {

                                    if (alquileres.get(i).getCliente().equals(cliente) && alquileres.get(i).getVehiculo().equals(vehiculo)) {

                                        alquileres.get(i).cerrar(vehiculos);
                                        value = true;

                                        System.out.println("\nPrecio alquiler: " + alquileres.get(i).precioAlquiler() + "€ ");
                                        break;
                                    }

                                }
                                if (!value) {
                                    escribirLn("\n********************ATENCION********************");
                                    escribirLn("No hay alquileres que contengan el cliente y el vehiculo indicado.");
                                    escribirLn("------------------------------------------------\n");

                                } else {
                                    escribirLn("\nAlquiler cerrado correctamente");
                                    escribirLn("------------------------------------------------\n");
                                }//FIN

                            } else {
                                escribirLn("\n********************ATENCION********************");
                                escribirLn("        El vehiculo no está en alquiler.");
                                escribirLn("------------------------------------------------\n");
                            }

                        } else {
                            escribirLn("\n********************ATENCION********************");
                            escribirLn("        El vehículo no está registrado.");
                            escribirLn("------------------------------------------------\n");
                        }

                    } else {
                        escribirLn("\n********************ATENCION********************");
                        escribirLn("      Formato de matrícula incorrecto.");
                        escribirLn("------------------------------------------------\n");
                    }

                } else {
                    escribirLn("\n********************ATENCION********************");
                    escribirLn("No hay ningún cliente registro con el Dni/Nie proporciado");
                    escribirLn("------------------------------------------------\n");
                }

            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("        Letra del Dni/Nie incorrecto.");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("        Formato de Dni/Nie incorrecto.");
            escribirLn("------------------------------------------------\n");
        }

    }

    public static void leerDatos() {

        int tipoVehiculo = 0; //Esta variable la utilizaremos para posteriormente saber qué tipo de vehiculo almacenamos en el array de alquileres

        //CLIENTES
        String clientesTxt = leerArchivo("clientes.txt");

        if (!clientesTxt.isEmpty()) {

            String[] datosClientes = clientesTxt.split("\n");

            for (int i = 0; i < datosClientes.length; i++) {

                String[] datos = datosClientes[i].split("#");

                Cliente nuevoCliente = new Cliente(datos[0], datos[1], datos[2], datos[3], datos[4]);

                clientes.add(nuevoCliente);

            }

        }

        //VEHICULOS
        String vehiculosTxt = leerArchivo("vehiculos.txt");

        if (!vehiculosTxt.isEmpty()) {

            String[] datosVehiculos = vehiculosTxt.split("\n");

            for (int i = 0; i < datosVehiculos.length; i++) {

                String[] datos = datosVehiculos[i].split("#");

                //Vehiculo vehiculo = null;
                String matricula = datos[1];
                String marca = datos[2];
                String modelo = datos[3];
                int cilindrada = Integer.parseInt(datos[4]);
                boolean disponible = (datos[5].equalsIgnoreCase("true")) ? true : false;

                switch (datos[0]) {

                    case "Furgoneta":
                        tipoVehiculo = 1;
                        //int pma, int volumen, boolean refrigerado, Tamanio tamanio
                        int pma = Integer.parseInt(datos[6]);
                        int volumen = Integer.parseInt(datos[7]);
                        boolean refrigerado = (datos[8].equalsIgnoreCase("true")) ? true : false;
                        Tamanio tamanio = null;

                        if (datos[9].equalsIgnoreCase("GRANDE")) {
                            tamanio = Enumerados.Tamanio.GRANDE;
                        } else if (datos[9].equalsIgnoreCase("MEDIANO")) {
                            tamanio = Enumerados.Tamanio.MEDIANO;
                        } else {
                            tamanio = Enumerados.Tamanio.PEQUENIO;
                        }

                        vehiculos.add(new Furgoneta(matricula, marca, modelo, cilindrada,
                                pma, volumen, refrigerado, tamanio));
                        vehiculos.get(i).setDisponible(disponible);
                        break;

                    case "Deportivo":

                        tipoVehiculo = 2;

                        //protected int numPuertas;
                        // protected Combustible combustible;
                        // private CajaCambios cambio;
                        //  private boolean descapotable;
                        int numPuertas = Integer.parseInt(datos[6]);
                        Combustible combustible = null;

                        if (datos[7].equalsIgnoreCase("GASOLINA")) {
                            combustible = Enumerados.Combustible.GASOLINA;
                        } else if (datos[7].equalsIgnoreCase("DIESEL")) {
                            combustible = Enumerados.Combustible.DIESEL;
                        } else if (datos[7].equalsIgnoreCase("HIBRIDO")) {
                            combustible = Enumerados.Combustible.HIBRIDO;
                        } else {
                            combustible = Enumerados.Combustible.ELECTRICO;
                        }

                        CajaCambios cambio = null;

                        if (datos[8].equalsIgnoreCase("AUTOMATICO")) {
                            cambio = Enumerados.CajaCambios.AUTOMATICO;
                        } else {
                            cambio = Enumerados.CajaCambios.MANUAL;
                        }

                        boolean descapotable = (datos[9].equalsIgnoreCase("true")) ? true : false;

                        vehiculos.add(new Deportivo(matricula, marca, modelo, cilindrada, numPuertas,
                                combustible, cambio, descapotable));
                        vehiculos.get(i).setDisponible(disponible);
                        break;

                    case "Familiar":

                        tipoVehiculo = 3;

                        int numPuertasFami = Integer.parseInt(datos[6]);
                        Combustible combustibleFami = null;

                        if (datos[7].equalsIgnoreCase("GASOLINA")) {
                            combustibleFami = Enumerados.Combustible.GASOLINA;
                        } else if (datos[7].equalsIgnoreCase("DIESEL")) {
                            combustibleFami = Enumerados.Combustible.DIESEL;
                        } else if (datos[7].equalsIgnoreCase("HIBRIDO")) {
                            combustibleFami = Enumerados.Combustible.HIBRIDO;
                        } else {
                            combustibleFami = Enumerados.Combustible.ELECTRICO;
                        }

                        //int numPlazas, boolean sillaBebe
                        int numPlazas = Integer.parseInt(datos[8]);
                        boolean sillaBebe = (datos[9].equalsIgnoreCase("true")) ? true : false;

                        vehiculos.add(new Familiar(matricula, marca, modelo, cilindrada, numPuertasFami,
                                combustibleFami, numPlazas, sillaBebe));
                        vehiculos.get(i).setDisponible(disponible);
                        break;
                }

            }

        }
        //ALQUILERES

        String alquileresTxt = leerArchivo("alquileres.txt");

        if (!alquileresTxt.isEmpty()) {

            String[] datosAlquileres = alquileresTxt.split("\n");

            for (int i = 0; i < datosAlquileres.length; i++) {

                String[] datos = datosAlquileres[i].split("#");

                String dni = datos[0];
                String matricula = datos[1];
                String fecha = datos[2];
                int dias = Integer.parseInt(datos[3]);

                Cliente nuevoCliente = clientes.get(buscarCliente(dni));

                Vehiculo nuevoVehiculo = vehiculos.get(buscarVehiculo(matricula));

                String[] datosFecha = fecha.split("[/ :]+");

                int day = Integer.parseInt(datosFecha[0]);
                int month = Integer.parseInt(datosFecha[1]);
                int year = Integer.parseInt(datosFecha[2]);
                int hour = Integer.parseInt(datosFecha[3]);
                int minute = Integer.parseInt(datosFecha[4]);

                Calendar fechaAlquiler = new GregorianCalendar(year, month - 1, day, hour, minute);

                Alquiler nuevoAlquiler = new Alquiler(nuevoCliente, nuevoVehiculo);
                nuevoAlquiler.setFecha(fechaAlquiler);
                nuevoAlquiler.setDias(dias);

                alquileres.add(nuevoAlquiler);

            }

        }
        System.out.println("\nDatos cargados desde los archivos correctamente.");
    }

    //
    //
//
    //---------------------------------------MAIN------------------------------------------------------//  
    public static void main(String[] args) {

        //Cargamos los datos desde los archivos.
        leerDatos();

        int opcion = 0;
        Scanner sc = new Scanner(System.in);
        escribirLn("-------------------------------------------------------");
        escribirLn("Bienvenido al programa de gestión de Alquileres Xuanin.");
        escribirLn("-------------------------------------------------------");
        escribirLn("Información al usuario:\n- Una vez elegida una opción del menu, en el caso de haber escogido erroneamente,\n"
                + "  podrás volver al menu dando un dato con formato erroneo en el primer paso de la opción.\n"
                + "- Si va a introducir un alquiler, se recomienda listar previamente con las opciones 3,6 y 9\n"
                + "  los clientes, vehículos y alquileres ya guardados para poder consultar datos.\n"
                + "- Si la letra del Dni introducido al añadir un cliente, siento una letra válida\n"
                + "  no es la que corresponde al mismo, el programa le proporcionará la correcta.\n"
                + "- Cuando listemos los alquileres, el dato 'Disponible' nos indicará: si es Si que\n"
                + "  se trata de un alquiler cerrado; si es No que es un alquiler activo.");

        do {

            escribirLn("Opciones: ");
            escribirLn("1. Añadir cliente.\n2. Borrar cliente.\n3. Listar clientes.\n"
                    + "4. Añadir vehiculo.\n5. Borrar vehiculo.\n6. Listar vehiculos.\n"
                    + "7. Nuevo alquiler.\n8. Cerrar alquiler.\n9. Listar alquileres.\n"
                    + "10. Guardar datos.\n11. Crear copia de seguridad.\n12. Salir");

            opcion = leerEntero("\nIntroduce opción: ");

            switch (opcion) {
                case 1:
                    caseAnadirCliente();
                    guardarDatos = true;
                    break;
                case 2:
                    caseBorrarCliente();
                    guardarDatos = true;
                    break;
                case 3:
                    caseListarClientes();
                    break;
                case 4:
                    anadirVehiculo();
                    guardarDatos = true;
                    break;
                case 5:
                    caseBorrarVehiculo();
                    guardarDatos = true;
                    break;
                case 6:
                    caseListarVehiculos();
                    break;
                case 7:
                    caseNuevoAlquiler();
                    guardarDatos = true;
                    break;
                case 8:
                    cerrarAlquiler();
                    guardarDatos = true;
                    break;
                case 9:
                    caseListarAlquileres();
                    break;
                case 10:
                    caseGuardarDatos("");
                    //Al pasarle por parámetro un String vacío, los datos se guardaran en el directorio raíz del proyecto
                    guardarDatos = false;
                    break;
                case 11:
                    crearCopiaSeg();
                    break;
                case 12:
                    //guardar datos cuando haya cambios que guardar
                    if (guardarDatos){
                    caseConfirmarGuardarDatos();
                    }
                    escribirLn("\n               Fin de programa");
                    escribirLn("------------------------------------------------\n");
                    escribirLn("------------------------------------------------\n");
                    break;
                default:
                    escribirLn("********************ATENCION********************");
                    escribirLn("              Opción incorrecta.");
                    escribirLn("          Elija una opción del menu.");
                    escribirLn("------------------------------------------------");
                    break;

            }

        } while (opcion != 12);

    }
//    
//
//---------------------------------------METODOS OPCIONES MENU-------------------------------------//
//
//
//    

    public static void anadirCliente(Cliente c) {
        /*Crea un método anadirCliente que añada un cliente al array de clientes si cabe
y si no existe ningún otro con el mismo DNI o muestre un mensaje con el error que se ha producido.*/

        
        boolean encontrado = false;

        
            for (int i = 0; i < clientes.size() && !encontrado; i++) {
                if (clientes.get(i).getDni().equalsIgnoreCase(c.getDni())) {
                    encontrado = true;
                }
            }
        

        if (encontrado) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Dni/Nie ya registrado. Cliente no añadido.");
            escribirLn("------------------------------------------------\n");
        } else {
            clientes.add(c);
            escribirLn(c.toString());
            escribirLn("\n      Cliente añadido correctamente.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void caseAnadirCliente() {

        //Utilizamos dniAux parahacer las comprobaciones en el caso de que se trate de un NIE
        String dniAux;
        String dni = leerCadena("\nIntroduce Dni/Nie de cliente: ").toUpperCase();
        boolean value = false;
        dniAux = dni;
        if (comprobarDni(dni)) {
            //nie = dni;
            if (dniAux.substring(0, 1).equalsIgnoreCase("X")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Y")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Z")) {
                dniAux = pasarNieADni(dniAux);
            }
            //Comparamos la letra del dni/nie con la letra calculada con el método calcular letra
            if (dniAux.substring(8, 9).equalsIgnoreCase(calcularLetraDni(dniAux.substring(0, 8)))) {
                //escribirLn("DNI correcto");
                String nombre = leerCadena("\nIntroduce nombre de cliente: ").toUpperCase();
                String direccion = leerCadena("\nIntroduce dirección de cliente: ").toUpperCase();
                String localidad = leerCadena("\nIntroduce localidad de cliente: ").toUpperCase();

                //utilizar un while para volver a pedir el cp si fuera erroneo
                while (!value) {
                    String cod_postal = leerCadena("\nIntroduce el código postal de cliente: ");
                    if (comprobarCodigoPostal(cod_postal)) {
                        value = true;
                        anadirCliente(new Cliente(dni, nombre, direccion, localidad, cod_postal));

                    } else {
                        escribirLn("\n********************ATENCION********************");
                        escribirLn("          Código postal incorrecto.");
                        escribirLn("------------------------------------------------");
                    }
                }

            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("Letra de Dni/Nie incorrecta.  Al documento " + dni.substring(0, 8) + " le corresponde la letra: " + calcularLetraDni(dniAux));
                escribirLn("Escoja de nuevo una opción del menu principal.");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Formato de Dni/Nie incorrecto.  Debe introducir una letra valida a continuación de:\n"
                    + "- 8 números para un Dni."
                    + "- X,Y o Z y 7 números para un Nie."
                    + "\nLetras válidas: T,R,W,A,G,M,Y,F,P,D,X,B,N,J,Z,S,Q,V,H,L,C,K o E"
                    + "\nEscoja de nuevo una opción del menu principal.");
            escribirLn("------------------------------------------------\n");
        }

    }

    public static void borrarCliente(String dni) {
        /*Crea un método borrarCliente que elimine un cliente, dado su DNI.*/

        int pos = -1;

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getDni().equalsIgnoreCase(dni)) {

                vehiculos.remove(vehiculos.get(i));

                pos = i;

            }
        }
        if (pos == -1) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("            Dni/Nie no registrado.");
            escribirLn("------------------------------------------------\n");
        } else {

            escribirLn("\n               Cliente borrado.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void caseBorrarCliente() {

        String dni = leerCadena("\nIntroduce Dni/Nie de cliente a borrar: ").toUpperCase();
        if (comprobarDni(dni)) {
            if (dni.substring(0, 1).equalsIgnoreCase("X")
                    || dni.substring(0, 1).equalsIgnoreCase("Y")
                    || dni.substring(0, 1).equalsIgnoreCase("Z")) {
                dni = pasarNieADni(dni);
            }
            //Comparamos la letra del dni con la letra calculada con el método calcular letra
            if (dni.substring(8, 9).equalsIgnoreCase(calcularLetraDni(dni.substring(0, 8)))) {
                borrarCliente(dni);

            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("           Letra de Dni/Nie incorrecta.");
                escribirLn("------------------------------------------------\n");
            }
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("          Formato de Dni/Nie incorrecto");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void caseListarClientes() {

        if (clientes.isEmpty()) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("               No existen clientes.");
            escribirLn("------------------------------------------------\n");
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                escribirLn(clientes.get(i).toString());
            }
        }
    }

    public static void anadirVehiculo() {
        /*Crea un método anadirVehiculo que añada un coche al array de vehiculos si
cabe y no existe ningún otro con la misma matrícula o muestre un mensaje con el
error que se ha producido.*/

        //pos para guardar primera posición nula(vacia)
        int pos = -1;
        boolean encontrado = false;

        //existe espacio en el array, ha dado toda la vuelta y pos no ha cambiado. Ahora comprobamos que no este ya guarda la matrícula
        //Si 0 esta vacia, pos = 0 por ser null, pero 0 aún no tine vehículo para poder comparar matrícula.(pos!=0)
        //Existe espacio vacío. Comenzamos a pedir información al usuario.
        String matricula = (leerCadena("\nIntroduce matrícula del vehículo: ")).toUpperCase();

        if (comprobarMatricula(matricula)) {

            for (int i = 0; i < vehiculos.size() && !encontrado; i++) {

                if (vehiculos.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                    // escribirLn(vehiculos[i].getMatricula());
                    encontrado = true;

                }
            }

            if (encontrado) {
                escribirLn("\n********************ATENCION********************");
                escribirLn("Matrícula ya registrada. Vehiculo no añadido.");
                escribirLn("------------------------------------------------\n");
            } else {

                //Si la matricula no está registrada, comenzamos a pedir los datos del vehiculo.
                String marca = (leerCadena("\nIntroduce marca del vehículo: ")).toUpperCase();
                String modelo = (leerCadena("\nIntroduce modelo del vehículo: ")).toUpperCase();
                int cilindrada = leerEntero("\nIntroduce cilindrada del vehículo: ");
                int seleccion = leerEntero(1, 2, "\nSelecciona tipo de vehículo.\n1.Mercancias.\n2.Turismo.");

                if (seleccion == 1) {

                    int pma = leerEntero("\nVas a registrar una furgoneta.\nIntroduce pma: ");
                    int volumen = leerEntero("\nIntroduce volumen: ");
                    boolean refrigerado = leerBoolean("\nVehículo refrigerado S/N");

                    int posicion = leerEntero(1, 3, "\nSelecciona un tamaño:\n1.Grande\n2.Mediano\n3.Pequeño");

                    Tamanio tamanio = Tamanio.values()[posicion - 1];

                    Furgoneta furgoneta = new Furgoneta(matricula, marca, modelo, cilindrada, pma, volumen, refrigerado, tamanio);

                    vehiculos.add(furgoneta);

                    escribirLn("\n" + furgoneta.toString());
                    escribirLn("\nVehiculo furgoneta añadido correctamente.");
                    escribirLn("------------------------------------------------\n");

                } else {

                    int numPuertas = leerEntero(3, 5, "\nVas a añadir un turismo.\n\nIntroduce número de puertas entre 3 y 5:");

                    int posicion = leerEntero(1, 4, "\nSelecciona tipo de combustible:\n1.Gasolina.\n2.Diesel.\n3.Híbrido.\n4.Eléctrico.");

                    Combustible combustible = Combustible.values()[posicion - 1];

                    seleccion = leerEntero(1, 2, "\nEscoja tipo de turismo:\n1.Familiar.\n2.Deportivo");

                    if (seleccion == 1) {
                        escribirLn("\nHas escogido añadir un familiar.");

                        int numPlazas = leerEntero(4, 7, "\nElija el número de plazas entre 4 y 7.");

                        boolean sillaBebe = leerBoolean("\n¿Tiene silla de bebe? S/N");

                        Familiar familiar = new Familiar(matricula, marca, modelo, cilindrada, numPuertas, combustible, numPlazas, sillaBebe);

                        vehiculos.add(familiar);
                        
                        escribirLn("\n" + familiar.toString());
                        escribirLn("\nVehículo familiar añadido correctamente.");
                        escribirLn("------------------------------------------------\n");

                    } else {
                        escribirLn("\nHas escogido añadir un deportivo");

                        boolean descapotable = leerBoolean("\n¿Deportivo descapotable? S/N");

                        int opcion = leerEntero(1, 2, "\nSelecciona tipo de caja de cambios: \n1.Automático.\n2.Manual.");

                        CajaCambios cambio = CajaCambios.values()[opcion - 1];

                        Deportivo deportivo = new Deportivo(matricula, marca, modelo, cilindrada, numPuertas, combustible, cambio, descapotable);

                        vehiculos.add(deportivo);

                        escribirLn("\n" + deportivo.toString());
                        escribirLn("\nVehículo deportivo añadido correctamente.");
                        escribirLn("------------------------------------------------\n");

                    }

                }

            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Formato de matrícula incorrecto. Formato requerido tipo '1234BCD'. Vocales no aceptadas."
                    + "\nEscoja de nuevo una opción del menu principal.");
            escribirLn("------------------------------------------------\n");

        }

    }

    public static void borrarVehiculo(String m) {
        /* Crea un método borrarVehiculo que borre un vehiculo, dada su matrícula, del
    array de vehiculos. */

        int pos = -1;
        for (int i = 0; i < vehiculos.size() && pos == -1; i++) {
            if (vehiculos.get(i).getMatricula().equalsIgnoreCase(m)) {
                //Creamos un vehículo para compararlo con el que queremos borrar del ArrayList
                Vehiculo v = vehiculos.get(i);
                vehiculos.remove(v);
                pos = i;
            }
        }
        if (pos == -1) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("           Matricula no registrada.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\nVehiculo borrado correctamente.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void caseBorrarVehiculo() {

        String matricula = (leerCadena("\nIntroduce matrícula del vehiculo a borrar: ")).toUpperCase();
        comprobarMatricula(matricula);
        if (comprobarMatricula(matricula)) {
            borrarVehiculo(matricula);

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("        Formato de matrícula incorrecto.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void caseListarVehiculos() {
        boolean vacio = vehiculos.isEmpty();

        if (vacio) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("             No existen vehículos.");
            escribirLn("------------------------------------------------\n");
        } else {
            for (Vehiculo elemento : vehiculos) {
                escribirLn(elemento.toString());
            }
        }
    }

    public static void caseNuevoAlquiler() {
        String dni;
        String dniAux;
        String matricula;
        int posCliente;
        int posVehiculo;
        boolean value = false;
        
        dni = (leerCadena("\nIntroduce Dni/Nie del cliente: ")).toUpperCase();
        dniAux = dni;

        if (comprobarDni(dniAux)) {

            //Comprobamos si es un NIE, y en caso de serlo lo convertimos a DNI para posteriormente
            //comprobar la letra final.
            if (dniAux.substring(0, 1).equalsIgnoreCase("X")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Y")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Z")) {
                dniAux = pasarNieADni(dniAux);
            }

            //Comprobamos la letra final del dni para validarlo
            if (dniAux.substring(8, 9).equalsIgnoreCase(calcularLetraDni(dniAux.substring(0, 8)))) {

                posCliente = buscarCliente(dni);

                while (!value) {
                    if (posCliente != -1) {

                        matricula = leerCadena("\nIntroduce matricula del vehículo: ").toUpperCase();

                        if (comprobarMatricula(matricula)) {

                            posVehiculo = buscarVehiculo(matricula);

                            if (posVehiculo != -1) {

                                if (vehiculos.get(posVehiculo).getDisponible() == true) {

                                    for (int i = 0; i < alquileres.size() && !value; i++) {

                                        vehiculos.get(posVehiculo).setDisponible(false);
                                        alquileres.add(new Alquiler(clientes.get(posCliente), vehiculos.get(posVehiculo)));
                                        System.out.println(alquileres.get(i));
                                        value = true;

                                    }

                                    if (!value) {
                                        escribirLn("\n********************ATENCION********************");
                                        escribirLn("No hay espacio en la memoria para nuevos alquileres.");
                                        escribirLn("------------------------------------------------\n");
                                    } else {
                                        escribirLn(new Alquiler(clientes.get(posCliente), vehiculos.get(posVehiculo)).toString());
                                        escribirLn("Alquiler registrado correctamente");
                                        escribirLn("------------------------------------------------\n");
                                    }//FIN

                                } else {
                                    escribirLn("\n********************ATENCION********************");
                                    escribirLn(" El vehiculo no está disponible en este momento.");
                                    escribirLn("------------------------------------------------\n");
                                }

                            } else {
                                escribirLn("\n********************ATENCION********************");
                                escribirLn("       El vehículo no está registrado.");
                                escribirLn("------------------------------------------------\n");
                            }

                        } else {
                            escribirLn("\n********************ATENCION********************");
                            escribirLn("       Formato de matrícula incorrecto.");
                            escribirLn("------------------------------------------------\n");
                        }

                    } else {
                        escribirLn("\n********************ATENCION********************");
                        escribirLn("No hay ningún cliente registro con el Dni/Nie proporciado");
                        escribirLn("------------------------------------------------\n");
                    }
                }

            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("        Letra del Dni/Nie incorrecto.");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("        Formato de Dni/Nie incorrecto");
            escribirLn("------------------------------------------------\n");
        }

    }

    public static void caseListarAlquileres() {
        boolean vacio = true;

        for (int i = 0; i < alquileres.size(); i++) {

            escribirLn(alquileres.get(i).toString());
            vacio = false;

        }
        if (vacio) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("            No existen alquileres.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void caseGuardarDatos(String ruta) {

        //Archivo para array clientes.
        String rutaC = (ruta == "") ? "clientes.txt" : ruta + "/clientes.txt";

        /*if(ruta == ""){
            rutaC = "clientes.txt";
        }else{
            rutaC = (ruta + "/clientes.txt");*/
        String datosCliente = "";

        //escribirArchivo(String ruta, String datos, boolean sobreescribir)
        for (int i = 0; i < clientes.size(); i++) {

            datosCliente += clientes.get(i).getDni() + "#" + clientes.get(i).getNombre()
                    + "#" + clientes.get(i).getDireccion() + "#" + clientes.get(i).getLocalidad()
                    + "#" + clientes.get(i).getCodigoPostal() + "\n";

        }

        if (escribirArchivo(rutaC, datosCliente, true)) {
            escribirLn("\nDatos de clientes guardados correctamente.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("         Error en escritura de datos.");
            escribirLn("------------------------------------------------\n");
        }

        //Archivo para array vehículos
        //String rutaV = (ruta + "/vehiculos.txt");
        //Archivo para array clientes.
        String rutaV = (ruta == "") ? "vehiculos.txt" : ruta + "/vehiculos.txt";

        String datosVehiculos = "";

        for (int i = 0; i < vehiculos.size(); i++) {

            if (vehiculos.get(i) instanceof Deportivo) {

                Deportivo aux = (Deportivo) vehiculos.get(i);

                datosVehiculos += "Deportivo#" + aux.getMatricula() + "#" + aux.getMarca() + "#"
                        + aux.getModelo() + "#" + aux.getCilindrada() + "#" + aux.getDisponible() + "#"
                        + aux.getNumeroPuertas() + "#" + aux.getCombustible() + "#" + aux.getCambio()
                        + "#" + aux.getDescapotable() + "\n";

            }

            if (vehiculos.get(i) instanceof Familiar) {

                Familiar aux = (Familiar) vehiculos.get(i);

                datosVehiculos += "Familiar#" + aux.getMatricula() + "#" + aux.getMarca() + "#"
                        + aux.getModelo() + "#" + aux.getCilindrada() + "#" + aux.getDisponible() + "#"
                        + aux.getNumeroPuertas() + "#" + aux.getCombustible() + "#" + aux.getNumPlazas()
                        + "#" + aux.getSillaBebe() + "\n";

            }

            if (vehiculos.get(i) instanceof Furgoneta) {

                Furgoneta aux = (Furgoneta) vehiculos.get(i);

                datosVehiculos += "Furgoneta#" + aux.getMatricula() + "#" + aux.getMarca() + "#"
                        + aux.getModelo() + "#" + aux.getCilindrada() + "#" + aux.getDisponible() + "#"
                        + aux.getPma() + "#" + aux.getVolumen() + "#" + aux.getRefrigerado() + "#"
                        + aux.getTamanio() + "\n";

            }

        }

        if (escribirArchivo(rutaV, datosVehiculos, true)) {
            escribirLn("\nDatos de vehículos guardados correctamente.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("         Error en escritura de datos.");
            escribirLn("------------------------------------------------\n");
        }

        //Archivo para array alquileres
        //String rutaA = (ruta + "/alquileres.txt");
        String rutaA = (ruta == "") ? "alquileres.txt" : ruta + "/alquileres.txt";

        String datosAlquileres = "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (int i = 0; i < alquileres.size(); i++) {

            datosAlquileres += alquileres.get(i).getCliente().getDni() + "#"
                    + alquileres.get(i).getVehiculo().getMatricula() + "#"
                    + sdf.format(alquileres.get(i).getFecha().getTime())
                    + "#" + alquileres.get(i).getDias() + "\n";

        }
        if (escribirArchivo(rutaA, datosAlquileres, true)) {
            escribirLn("\nDatos de alquileres guardados correctamente.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("        Error en escritura de datos.");
            escribirLn("------------------------------------------------\n");
        }

    }

    public static void caseConfirmarGuardarDatos() {

        if (leerBoolean("¿Desea guardar cambios? S/N.")) {
            caseGuardarDatos("");
        } else {
            escribirLn("\n********************ATENCION********************");
        }
        escribirLn("         No se han guardado los datos.");
        escribirLn("------------------------------------------------");
    }

    public static void crearCopiaSeg() {

        Calendar date = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        String fecha = sdf.format(date.getTime());

        String ruta = "/copias_seguridad/" + fecha;

        File directorio = new File(ruta);

        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                escribirLn("\n    Directorio creado satisfactoriamente en");
                escribirLn("            C:\\copias_seguridad");
                escribirLn("------------------------------------------------\n");
                caseGuardarDatos(ruta);
            } else {
                escribirLn("********************ATENCION********************");
                escribirLn("           Error al crear directorio");
                escribirLn("------------------------------------------------\n");
            }
        } else {
            escribirLn("\n     El directorio ya existe");
            
            borrarFicherosDeDirectorio(directorio); 
            
            if (directorio.delete()) {
                escribirLn("   El directorio existente ha sido borrado.");
                escribirLn("------------------------------------------------\n");
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado satisfactoriamente");
                    caseGuardarDatos(ruta);
                } else {
                    escribirLn("********************ATENCION********************");
                    escribirLn("           Error al crear directorio");
                    escribirLn("------------------------------------------------\n");
                }
            }else{
                escribirLn("********************ATENCION********************");
                escribirLn("Error al intentar borrar el directorio existente.\n"
                        + "         Copia de seguridad no creada. ");
                escribirLn("------------------------------------------------\n");
            }
        }
    }
    
    private static void borrarFicherosDeDirectorio(File directorio){
        //Con este método borramos todos los ficheros contenidos en el directorio
        
        File[] ficheros = directorio.listFiles();
        
        for(int i=0; i < ficheros.length; i++){
            ficheros[i].delete();
        }
    }

}
