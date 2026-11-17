package controlador;
import javax.swing.SwingUtilities;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ingresos ingresos = new Ingresos();
            ingresos.iniciar();
        });
    }
}