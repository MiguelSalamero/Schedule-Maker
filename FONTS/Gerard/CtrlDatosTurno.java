package data;

import java.io.*;
import java.util.ArrayList;


public class CtrlDatosTurno {

    private static CtrlDatosTurno instance = null;

    private CtrlDatosTurno(){}

    public static CtrlDatosTurno getInstance() {
        if (instance == null) instance = new CtrlDatosTurno();
        return instance;
    }

    /**
     * Metodo que devuelve un ArrayList con todos los turnos de "turnos.txt"
     * @return ArrayList con los parametros de todos los turnos de "turnos.txt"
     * @throws IOException
     */
    public ArrayList<String[]> getAll() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("turnos.txt"));
        String linea;
        ArrayList<String[]> datos = new ArrayList<>();
        while ((linea = in.readLine()) != null) {
            String[] parametros = linea.split(",");
            datos.add(parametros);
        }
        in.close();
        return datos;
    }

    public void guardarTurnos(ArrayList<String[]> turnos) throws IOException {
        File file = new File("turnos.txt");
        FileWriter fr = new FileWriter(file);
        BufferedWriter br = new BufferedWriter(fr);
        for (String[] turno : turnos) {
            String dataWithNewLine = new String();
            for (int i = 0; i<turno.length; ++i) {
                if (i == 0) dataWithNewLine = turno[0];
                else dataWithNewLine = dataWithNewLine + "," + turno[i];
            }
            dataWithNewLine = dataWithNewLine + System.getProperty("line.separator");
            br.write(dataWithNewLine);
        }
        br.close();
        fr.close();
    }


}
