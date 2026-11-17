package modelo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroIngresos {
    private ArrayList<Aportador> aportadores = new ArrayList<>();

    public void leerArchivo(String ruta) throws FileNotFoundException {
        Scanner archivo = new Scanner(new File(ruta), "UTF-8");
        StringBuilder texto = new StringBuilder();

        while (archivo.hasNextLine()) {
            texto.append(archivo.nextLine()).append(" ");
        }
        archivo.close();
        extraerDatos(texto.toString());
    }

    public void extraerDatos(String texto) {
        aportadores.clear();

        Pattern cedula = Pattern.compile(
            "(?i)\\b(?:c[eé]dula(?:\\s+es)?|CI)\\s*:?\\s*(\\d+)"
        );

        Pattern correo = Pattern.compile(
            "(?i)\\bcorreo\\s+([^\\s,;]+)"
        );

        Pattern monto = Pattern.compile(
            "(?i)\\$\\s*(\\d+(?:\\.\\d{1,2})?)"
            + "|\\b(\\d+(?:\\.\\d{1,2})?)\\s*"
            + "(?:d[oó]lares|USD|\\$)"
        );

        Matcher mc = cedula.matcher(texto);
        ArrayList<Integer> posiciones = new ArrayList<>();
        ArrayList<String> cedulas = new ArrayList<>();

        while (mc.find()) {
            posiciones.add(mc.start());
            cedulas.add(mc.group(1));
        }

        for (int i = 0; i < posiciones.size(); i++) {
            int inicio = posiciones.get(i);
            int fin = i + 1 < posiciones.size()
                    ? posiciones.get(i + 1) : texto.length();

            String parte = texto.substring(inicio, fin);

            String datoCorreo = "";
            double datoMonto = 0;

            Matcher me = correo.matcher(parte);
            if (me.find()) {
                datoCorreo = me.group(1).replaceAll("\\.$", "");
            }

            Matcher mm = monto.matcher(parte);
            if (mm.find()) {
                String cantidad = mm.group(1) != null
                        ? mm.group(1) : mm.group(2);
                try {
                    datoMonto = Double.parseDouble(cantidad);
                } catch (NumberFormatException e) {
                    datoMonto = 0;
                }
            }

            aportadores.add(new Aportador(
                cedulas.get(i), datoCorreo, datoMonto));
        }
    }

    public ArrayList<Aportador> getAportadores() {
        return aportadores;
    }
}