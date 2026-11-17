package vista;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Aportador;

public class Ventana extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnCargar, btnMostrar, btnResumen, btnSalir;
    private JTextArea txtResumen;
    private JLabel lblEstado;

    public Ventana() {
        setTitle("Registro de Ingresos");
        setSize(850, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        getContentPane().setLayout(null);

        JLabel titulo = new JLabel("REGISTRO DE INGRESOS");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBounds(280, 20, 350, 35);
        add(titulo);

        btnCargar = new JButton("Cargar archivo");
        btnCargar.setBounds(30, 80, 170, 35);
        add(btnCargar);

        btnMostrar = new JButton("Mostrar registros");
        btnMostrar.setBounds(220, 80, 170, 35);
        add(btnMostrar);

        btnResumen = new JButton("Mostrar resumen");
        btnResumen.setBounds(410, 80, 170, 35);
        add(btnResumen);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(600, 80, 170, 35);
        add(btnSalir);

        String[] columnas = {
            "Cedula", "Correo", "Monto",
            "Cedula valida", "Correo valido",
            "Monto valido", "Registro"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(90);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(30, 140, 770, 250);
        add(scroll);

        JLabel lblResumen = new JLabel("RESUMEN DE INGRESOS");
        lblResumen.setFont(new Font("Arial", Font.BOLD, 15));
        lblResumen.setBounds(30, 410, 300, 25);
        add(lblResumen);

        txtResumen = new JTextArea();
        txtResumen.setEditable(false);
        txtResumen.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollResumen = new JScrollPane(txtResumen);
        scrollResumen.setBounds(30, 445, 770, 90);
        add(scrollResumen);

        lblEstado = new JLabel("Seleccione un archivo para comenzar.");
        lblEstado.setBounds(30, 545, 770, 25);
        add(lblEstado);
    }

    public JButton getBtnCargar() {
        return btnCargar;
    }

    public JButton getBtnMostrar() {
        return btnMostrar;
    }

    public JButton getBtnResumen() {
        return btnResumen;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public void mostrarAportadores(ArrayList<Aportador> lista) {
        modeloTabla.setRowCount(0);

        for (Aportador a : lista) {
            modeloTabla.addRow(new Object[]{
                a.getCedula(),
                a.getCorreo(),
                String.format("%.2f", a.getMonto()),
                a.isCedulaValida() ? "SI" : "NO",
                a.isCorreoValido() ? "SI" : "NO",
                a.isMontoValido() ? "SI" : "NO",
                a.esValido() ? "VALIDO" : "INVALIDO"
            });
        }
    }

    public void mostrarResumen(double total, int[] errores) {
        txtResumen.setText(
            String.format("Total de ingresos validos: $%.2f%n", total)
            + "Cedulas incorrectas: " + errores[0] + "\n"
            + "Correos incorrectos: " + errores[1] + "\n"
            + "Montos incorrectos: " + errores[2]
        );
    }

    public void mostrarMensaje(String mensaje) {
        lblEstado.setText(mensaje);
    }
}