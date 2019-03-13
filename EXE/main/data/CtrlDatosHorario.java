package data;

import java.io.*;
import java.util.ArrayList;

public class CtrlDatosHorario {

    private static CtrlDatosHorario instance = null;

    private CtrlDatosHorario() {}

    public static CtrlDatosHorario getInstance() {
        if (instance == null) instance = new CtrlDatosHorario();
        return instance;
    }

    /**
     * Metodo que devuelve un ArrayList con todos los horarios de "horarios.txt"
     * @return ArrayList con los parametros de todos los horarios de "horarios.txt"
     * @throws IOException
     */
    public ArrayList<String[]> getAll() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("horarios.txt"));
        String linea;
        ArrayList<String[]> datos = new ArrayList<>();
        while ((linea = in.readLine()) != null) {
            String[] parametros = linea.split(",");
            datos.add(parametros);
        }
        in.close();
        return datos;
        // esto devuelve cada linea del fichero en un String[]
    }

    public void saveHorarios(ArrayList<String[]> horarios) throws java.io.IOException {
        File file = new File("horarios.txt");
        FileWriter fr = new FileWriter(file);
        BufferedWriter br = new BufferedWriter(fr);
        for (String[] horario : horarios) {
            if ("Horario".equals(horario[0])) {
                String dataFirstLine = horario[0] + "," + horario[1] + System.getProperty("line.separator");
                br.write(dataFirstLine);
            }
            else {
                String dataSesionLine = horario[0] + "," + horario[1] + "," + horario[2] + "," + horario[3] + "," + horario[4] + "," + horario[5] + System.getProperty("line.separator");
                br.write(dataSesionLine);
            }
        }
        br.write("end");
        br.close();
        fr.close();
    }


}
