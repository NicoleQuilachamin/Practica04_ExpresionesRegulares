package vista;

import modelo.Aportador;
import modelo.RegistroIngresos;

public class VistaConsola {

    public void mostrarMenu() {
        System.out.println("\nMENU DE OPCIONES");
        System.out.println("1. Cargar archivo");
        System.out.println("2. Listar aportadores");
        System.out.println("3. Mostrar resumen");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    public void mostrarListado(RegistroIngresos registro) {

        for (Aportador a : registro.getAportadores()) {

            System.out.println("--------------------------");

            System.out.println("Cedula: " + a.getCedula()
                    + " - " + (registro.cedulaCorrecta(a) ? "Correcta" : "Incorrecta"));

            System.out.println("Correo: " + a.getCorreo()
                    + " - " + (registro.correoCorrecto(a) ? "Correcto" : "Incorrecto"));

            System.out.println("Monto: " + a.getMonto()
                    + " - " + (registro.montoCorrecto(a) ? "Correcto" : "Incorrecto"));

            System.out.println("Registro: "
                    + (registro.registroCorrecto(a) ? "VALIDO" : "INVALIDO"));
        }
    }

    public void mostrarResumen(RegistroIngresos registro) {

        System.out.println("\nRESUMEN DE INGRESOS");

        System.out.println("Total de registros: "
                + registro.getAportadores().size());

        System.out.println("Registros validos: "
                + registro.contarValidos());

        System.out.println("Cedulas incorrectas: "
                + registro.contarCedulasErroneas());

        System.out.println("Correos incorrectos: "
                + registro.contarCorreosErroneos());

        System.out.println("Montos incorrectos: "
                + registro.contarMontosErroneos());

        System.out.printf("Total de ingresos validos: $%.2f%n",
                registro.totalValido());
    }
}
