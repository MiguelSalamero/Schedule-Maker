package data;

import java.io.*;
import java.util.ArrayList;

public class CtrlDatosTitulacion {


    private static CtrlDatosTitulacion instance = null;

    private CtrlDatosTitulacion() {}

    public static CtrlDatosTitulacion getInstance() {
        if (instance == null) instance = new CtrlDatosTitulacion();
        return instance;
    }

    /**
     * Metodo que devuelve un ArrayList con todas las titulaciones de "titulaciones.txt"
     * @return ArrayList con los parametros de todas las titulaciones de "titulaciones.txt"
     * @throws IOException
     */
    public ArrayList<String[]> getAll() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("titulaciones.txt"));
        String linea;
        ArrayList<String[]> datos = new ArrayList<>();
        while ((linea = in.readLine()) != null) {
            String[] parametros = linea.split(",");
            datos.add(parametros);
        }
        in.close();
        return datos;
    }

    public void saveTitulaciones(ArrayList<String[]> titulaciones)  throws java.io.IOException {
        File file = new File("titulaciones.txt");
        FileWriter fr = new FileWriter(file);
        BufferedWriter br = new BufferedWriter(fr);
        for (String[] titulacion : titulaciones) {
            String dataWithNewLine = titulacion[0] + "," + titulacion[1] + "," + titulacion[2] + System.getProperty("line.separator");
            br.write(dataWithNewLine);
        }
        br.close();
        fr.close();
    }


}
