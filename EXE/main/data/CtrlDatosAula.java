package data;

import java.io.*;
import java.util.ArrayList;




public class CtrlDatosAula {

    private static CtrlDatosAula instance = null;

    private CtrlDatosAula(){}

    public static CtrlDatosAula getInstance() {
        if (instance == null) instance = new CtrlDatosAula();
        return instance;
    }


    /**
     * Metodo que devuelve un ArrayList con todas las aulas de "aulas.txt"
     * @return ArrayList con los parametros de todas las aulas de "aulas.txt"
     * @throws IOException
     */
    public ArrayList<String[]> getAll() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("aulas.txt"));
        String linea;
        ArrayList<String[]> datos = new ArrayList<>();
        while ((linea = in.readLine()) != null) {
            String[] parametros = linea.split(",");
            datos.add(parametros);
        }
        in.close();
        return datos;
    }

    public void saveAulas(ArrayList<String[]> aulas) throws java.io.IOException {
        File file = new File("aulas.txt");
        FileWriter fr = new FileWriter(file);
        BufferedWriter br = new BufferedWriter(fr);
        for (String[] aula : aulas) {
            String dataWithNewLine = aula[0] + "," + aula[1] + "," + aula[2] + System.getProperty("line.separator");
            br.write(dataWithNewLine);
        }
        br.close();
        fr.close();
    }


}
