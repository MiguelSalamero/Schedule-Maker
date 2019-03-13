package data;

import java.io.*;
import java.util.ArrayList;


public class CtrlDatosAsignatura {

    private static CtrlDatosAsignatura instance = null;

    private CtrlDatosAsignatura(){}

    public static CtrlDatosAsignatura getInstance() {
        if (instance == null) instance = new CtrlDatosAsignatura();
        return instance;
    }


    /**
     * Metodo que devuelve un ArrayList con todas las asignaturas de "asignaturas.txt"
     * @return ArrayList con los parametros de todas las asignaturas de "asignaturas.txt"
     * @throws IOException
     */
    public ArrayList<String[]> getAll() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("asignaturas.txt"));
        String linea;
        ArrayList<String[]> datos = new ArrayList<>();
        while ((linea = in.readLine()) != null) {
            String[] parametros = linea.split(",");
            datos.add(parametros);
        }
        in.close();
        return datos;
    }

    /**
     * Metodo que guarda en "asignaturas.txt" todas las asignaturas del sistema
     * @param asignaturas: Asignaturas del sistema
     * @throws IOException
     */
    public void guardarAsignaturas(ArrayList<String[]> asignaturas) throws IOException {
        File file = new File("asignaturas.txt");
        FileWriter fr = new FileWriter(file);
        BufferedWriter br = new BufferedWriter(fr);
        for (String[] asignatura : asignaturas) {
            String dataWithNewLine = new String();
            for (int i = 0; i<asignatura.length; ++i) {
                if (i == 0) dataWithNewLine = asignatura[0];
                else dataWithNewLine = dataWithNewLine + "," + asignatura[i];
            }
            dataWithNewLine = dataWithNewLine + System.getProperty("line.separator");
            br.write(dataWithNewLine);
        }
        br.close();
        fr.close();
    }


}
