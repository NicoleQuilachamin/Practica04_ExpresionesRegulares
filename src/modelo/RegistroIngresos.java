package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroIngresos {

    private ArrayList<Aportador> aportadores = new ArrayList<>();
    private Pattern patronCedula = Pattern.compile("(?i)\\b(?:c[eé]dula|CI)\\s*(?:es\\s*)?(\\d+)\\b");
    private Pattern patronCorreo = Pattern.compile("(?i)\\bcorreo\\s+(?!de\\b)([^\\s,;]+)");
    private Pattern patronMonto = Pattern.compile("\\$\\s*(-?\\d+(?:[.,]\\d{1,2})?)|(?<![\\d.,])(-?\\d+(?:[.,]\\d{1,2})?)\\s*(?:d[oó]lares|USD|\\$)", Pattern.CASE_INSENSITIVE);
    private Pattern validarCedula = Pattern.compile("^\\d{10}$");
    private Pattern validarCorreo = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$");
    public int cargarArchivo(String ruta) throws FileNotFoundException {

        ArrayList<Aportador> nuevos = new ArrayList<>();
        Scanner lector = new Scanner(new File(ruta), "UTF-8");
        String texto = "";

        while (lector.hasNextLine()) {
            texto += lector.nextLine() + " ";
        }

        lector.close();
        String[] registros = texto.split("(?<=[.!?])\\s+(?=[A-ZÁÉÍÓÚÑ])");
        for (String linea : registros) {
            Matcher cedula = patronCedula.matcher(linea);
            Matcher correo = patronCorreo.matcher(linea);
            Matcher monto = patronMonto.matcher(linea);

            boolean tieneCedula = cedula.find();
            boolean tieneCorreo = correo.find();
            boolean tieneMonto = monto.find();

            if (!tieneCedula && !tieneCorreo && !tieneMonto) {
                continue;
            }

            String valorCedula = tieneCedula ? cedula.group(1) : "SIN DATO";
            String valorCorreo = tieneCorreo ? correo.group(1).replaceAll("\\.$", "") : "SIN DATO";
            String valorMonto = "SIN DATO";

            if (tieneMonto) {
                valorMonto = monto.group(1) != null ? monto.group(1) : monto.group(2);
                valorMonto = valorMonto.replace(',', '.');
            }
            nuevos.add(new Aportador(valorCedula, valorCorreo, valorMonto));
        }
        aportadores = nuevos;
        return aportadores.size();
    }
    public ArrayList<Aportador> getAportadores() {
        return aportadores;
    }
    public boolean cedulaCorrecta(Aportador a) {
        return validarCedula.matcher(a.getCedula()).matches();
    }
    public boolean correoCorrecto(Aportador a) {
        return validarCorreo.matcher(a.getCorreo()).matches();
    }
    public boolean montoCorrecto(Aportador a) {
        try {
            return a.getMonto().matches("\\d+(\\.\\d{1,2})?")
                    && Double.parseDouble(a.getMonto()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean registroCorrecto(Aportador a) {
        return cedulaCorrecta(a) && correoCorrecto(a) && montoCorrecto(a);
    }
    public double totalValido() {
        double total = 0;
        for (Aportador a : aportadores) {
            if (registroCorrecto(a)) {
                total += Double.parseDouble(a.getMonto());
            }
        }
        return total;
    }
    public int contarValidos() {
        int cantidad = 0;
        for (Aportador a : aportadores) {
            if (registroCorrecto(a)) {
                cantidad++;
            }
        }
        return cantidad;
    }
    public int contarCedulasErroneas() {
        int cantidad = 0;
        for (Aportador a : aportadores) {
            if (!cedulaCorrecta(a)) {
                cantidad++;
            }
        }
        return cantidad;
    }
    public int contarCorreosErroneos() {
        int cantidad = 0;
        for (Aportador a : aportadores) {
            if (!correoCorrecto(a)) {
                cantidad++;
            }
        }
        return cantidad;
    }
    public int contarMontosErroneos() {

        int cantidad = 0;

        for (Aportador a : aportadores) {
            if (!montoCorrecto(a)) {
                cantidad++;
            }
        }

        return cantidad;
    }
}
