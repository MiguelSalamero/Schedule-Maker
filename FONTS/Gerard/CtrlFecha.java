package domain.domaincontrollers;

import domain.Fecha;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;


public class CtrlFecha {

    private HashMap<Integer, Fecha> fechas;
    private CtrlDominio ctrlDominio;

    /**
     * Creadora del controlador de Fecha, que contiene una instancia de CtrlDominio y un HashMap de Fechas vacío
     * @param ctrlDominio: Controlador de Dominio al que pertenece
     */

    public CtrlFecha(CtrlDominio ctrlDominio) {
        this.fechas = new HashMap<Integer, Fecha>();
        this.ctrlDominio = ctrlDominio;
    }

    /**
     * Metodo que permite cargar Fechas a partir del fichero "fechas.txt"
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void cargarFechas() throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader("fechas.txt"));
        String linea;
        while ((linea = in.readLine()) != null) {
            String[] parametros = linea.split(",");
            crearFecha(parametros[0], parametros[1], parametros[2]);
        }
        in.close();

    }

    /**
     * Metodo que permite crear una nueva Fecha y anadirla a la lista de Fechas del controlador
     * @param id: Id de la Fecha, unico en la lista
     * @param dia: Dia al que pertenece la fecha. 1 = Lunes, 2 = Martes, etc
     * @param hora: Hora a la que pertenece la fecha.
     */

    public void crearFecha(String id, String dia, String hora) {
        Fecha fecha = new Fecha(Integer.parseInt(id), Integer.parseInt(dia), Integer.parseInt(hora));
        fechas.put(fecha.getId(), fecha);
    }

    /**
     * Metodo que permite listar todas las fechas del controlador
     * @return Devuelve un ArrayList de Fechas con todas las fechas del controlador
     */
    public ArrayList<Fecha> listarTodasFechas() {
        ArrayList<Fecha> lista = new ArrayList<Fecha>();
        for (int i = 1; i < 14; ++i) {
            for (int j = 0; j < 5; ++j) {
                lista.add(fechas.get(i+(20*j)));
            }
        }
        return lista;
    }

    public Fecha getFecha(String id) {
        return fechas.get(Integer.parseInt(id));
    }

    public ArrayList<Integer> listarIdFechas() {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        for (int i = 1; i < 14; ++i) {
            for (int j = 0; j < 5; ++j) {
                lista.add(i+(20*j));
            }
        }
        return lista;
    }
}
