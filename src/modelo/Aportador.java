package modelo;

public class Aportador {

    private String cedula;
    private String correo;
    private String monto;

    public Aportador(String cedula, String correo, String monto) {
        this.cedula = cedula;
        this.correo = correo;
        this.monto = monto;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public String getMonto() {
        return monto;
    }
}
