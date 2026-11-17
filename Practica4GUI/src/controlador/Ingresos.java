package controlador;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import modelo.Aportador;
import modelo.RegistroIngresos;
import modelo.Validador;
import vista.Ventana;

public class Ingresos {

    private RegistroIngresos registro = new RegistroIngresos();
    private Ventana vista = new Ventana();
    private Validador validador = new Validador();

    public void iniciar() {

        vista.getBtnCargar().addActionListener(e -> cargarRegistros());

        vista.getBtnMostrar().addActionListener(e -> mostrarRegistros());

        vista.getBtnResumen().addActionListener(e -> mostrarResumen());

        vista.getBtnSalir().addActionListener(e -> System.exit(0));

        vista.setVisible(true);
    }

    public void cargarRegistros() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar archivo de ingresos");
        selector.setFileFilter(
            new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos de texto", "txt"
            )
        );

        int resultado = selector.showOpenDialog(vista);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();

            try {
                registro.leerArchivo(archivo.getAbsolutePath());

                for (Aportador a : registro.getAportadores()) {
                    a.setCedulaValida(
                        validador.validarCedula(a.getCedula()));
                    a.setCorreoValido(
                        validador.validarCorreo(a.getCorreo()));
                    a.setMontoValido(
                        validador.validarMonto(a.getMonto()));
                }

                vista.mostrarAportadores(registro.getAportadores());
                vista.mostrarMensaje("Archivo cargado: "
                    + registro.getAportadores().size() + " registros.");

                JOptionPane.showMessageDialog(vista,
                    "Archivo cargado correctamente.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista,
                    "Error al leer el archivo: " + ex.getMessage());
            }
        }
    }

    public void mostrarRegistros() {
        vista.mostrarAportadores(registro.getAportadores());
    }

    public void mostrarResumen() {
        double total = 0;
        int[] errores = new int[3];

        for (Aportador a : registro.getAportadores()) {
            if (!a.isCedulaValida()) errores[0]++;
            if (!a.isCorreoValido()) errores[1]++;
            if (!a.isMontoValido()) errores[2]++;

            if (a.esValido()) {
                total += a.getMonto();
            }
        }

        vista.mostrarResumen(total, errores);
    }
}