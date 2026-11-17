package modelo;
import java.util.regex.Pattern;

public class Validador {

    public boolean validarCedula(String cedula) {
        return cedula != null &&
                Pattern.matches("\\d{10}", cedula);
    }

    public boolean validarCorreo(String correo) {
        return correo != null &&
                Pattern.matches(
                    "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}",
                    correo);
    }

    public boolean validarMonto(double monto) {
        return Double.isFinite(monto) && monto > 0;
    }
}