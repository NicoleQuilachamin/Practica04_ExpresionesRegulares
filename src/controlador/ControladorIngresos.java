package controlador;

import java.io.FileNotFoundException;
import java.util.Scanner;

import modelo.RegistroIngresos;
import vista.VistaConsola;

public class ControladorIngresos {

    private RegistroIngresos registro = new RegistroIngresos();
    private VistaConsola vista = new VistaConsola();
    private Scanner teclado = new Scanner(System.in);

    public void iniciar() {

        String opcion;

        do {
            vista.mostrarMenu();
            opcion = teclado.nextLine();

            switch (opcion) {

                case "1":
                    try {
                        int cantidad = registro.cargarArchivo("registro_ingresos.txt");
                        System.out.println("Archivo cargado correctamente");
                        System.out.println("Registros encontrados: " + cantidad);
                    } catch (FileNotFoundException e) {
                        System.out.println("No se encontro el archivo");
                    }
                    break;

                case "2":
                    if (registro.getAportadores().isEmpty()) {
                        System.out.println("Primero debe cargar el archivo");
                    } else {
                        vista.mostrarListado(registro);
                    }
                    break;

                case "3":
                    if (registro.getAportadores().isEmpty()) {
                        System.out.println("Primero debe cargar el archivo");
                    } else {
                        vista.mostrarResumen(registro);
                    }
                    break;

                case "4":
                    System.out.println("Programa finalizado");
                    break;

                default:
                    System.out.println("Opcion incorrecta");
            }

        } while (!opcion.equals("4"));
    }
}
