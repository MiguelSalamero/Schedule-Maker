package domain.domaincontrollers;

import data.CtrlDatosAula;
import domain.Aula;

import java.io.*;
import java.util.*;

public class CtrlAula {

    private Map<String, Aula> aulas;
    private String aulaSeleccionada;
    private CtrlDominio ctrlDominio;


    public CtrlAula(CtrlDominio ctrlDominio) {
        inicializarCtrlAula(ctrlDominio);
    }

    private void inicializarCtrlAula(CtrlDominio ctrlDominio) {
        aulas = new HashMap<>();
        aulaSeleccionada = null;
        ctrlDominio = ctrlDominio;
    }




    /**
     * @pre cierto
     * @post carga todas las aulas del fichero aulas.txt
     */
    public void cargarAulasDeFichero() throws FileNotFoundException, IOException {

    /*
        BufferedReader in = new BufferedReader(new FileReader("aulas.txt"));
        String linea;
        while ((linea = in.readLine()) != null) {
            String[] parametros = linea.split(",");
            crearAula(parametros[0],parametros[1],parametros[2]);
        }
        in.close();

    */

        CtrlDatosAula ctrldatosaula = CtrlDatosAula.getInstance();
        ArrayList<String[]> datos = ctrldatosaula.getAll();
        for (String[] parametros : datos) {
            crearAula(parametros[0],parametros[1],parametros[2]);
        }

    }


    /**
     * @pre cierto
     * @post devuelve un array con el identificador de todas las aulas ordenado.
     */
    public ArrayList<Aula> listarAulas() {
        ArrayList<Aula> lista = new ArrayList<Aula>(aulas.values());
        return lista;
    }


    /**
     * @pre cierto
     * @post guarda los cambios hechos en las aulas del sistema en un fichero.
     */
    public void guardarAulas() throws java.io.IOException{
        ArrayList<String[]> datosAulas = new ArrayList<>();
        for (Map.Entry<String, Aula> aulaEntry : this.aulas.entrySet()) {
            Aula aula = aulaEntry.getValue();
            String[] datoAula = new String[3];
            datoAula[0] = aula.toString();
            datoAula[1] = Integer.toString(aula.getMaxCapacity());
            datoAula[2] = Boolean.toString(aula.getAulaConPC());
            datosAulas.add(datoAula);
        }
        CtrlDatosAula ctrldatosaula = CtrlDatosAula.getInstance();
        ctrldatosaula.saveAulas(datosAulas);

    }


    /**
     * @pre cierto
     * @post devuelve un array con el identificador de todas las aulas ordenado.
     */
    public String[] listarAulasNombre() {
        String[] aulasNombre = new String[aulas.size()];
        ArrayList<String> lista = new ArrayList<>(aulas.keySet());
        Collections.sort(lista);
        aulasNombre = lista.toArray(aulasNombre);
        return aulasNombre;
    }



    /**
     * @pre cierto
     * @post se crea una instancia nueva de aula.
     *      Se devuelve cierto si no ha habido ningún error.
     */
    public boolean crearAula(String idAula, String capacidad, String tipo) {
        if (aulas.containsKey(idAula)) return false;
        aulas.put(idAula, new Aula(idAula, Integer.parseInt(capacidad), Boolean.parseBoolean(tipo)));
        return true;
    }

    /**
     * @pre cierto
     * @post se eliminará del sistema la aula con identificador idAula.
     *      Si no existía el aula se deveolverá falso.
     */
    public boolean eliminarAula(String idAula) {
        if (idAula.equals(aulaSeleccionada)) aulaSeleccionada = null;
        aulas.remove(idAula);
        return true;
    }

    /**
     * @pre cierto
     * @post devuelve cierto si la aula existe en el sistema.
     */
    public boolean existeAula(String idAula) {
        return aulas.containsKey(idAula);
    }

    /**
     * @pre debe existir la aula identificada con id en el sistema.
     * @post devuelve la instancia de aula identificada por id.
     */
    public Aula getAula(String id) {
        return aulas.get(id);
    }

    /**
     * @pre debe existir la aula identificada con id en el sistema.
     * @post se devuelve la capacidad de la aula identificada con idAula.
     */
    public String consultarCapacidadAula(String id) {
         return Integer.toString(aulas.get(id).getMaxCapacity());
    }

    /**
     * @pre debe existir la aula identificada con id en el sistema.
     * @post se devuelve cierto si la aula tiene PCs.
     */
    public String consultarAulaConPC(String id) {
        return Boolean.toString(aulas.get(id).getAulaConPC());
    }

    /**
     * @pre debe existir la aula identificada con id en el sistema.
     * @post se devuelven todos los parámetros de la aula.
     */
    public String[] consultarParametros(String id) {
        String[] parametros = new String[3];
        parametros[0] = id;
        parametros[1] = consultarCapacidadAula(id);
        parametros[2] = consultarAulaConPC(id);
        return parametros;
    }

    /**
     * @pre debe existir la aula identificada con id en el sistema.
     * @post se modifica el identificador de la aula identificada con idAula.
     *      Se devuelve cierto si no ha habido ningún error.
     */
    public boolean modificarIdentificadorAula(String idAula, String nuevoIdAula) {
        if (aulas.containsKey(nuevoIdAula)) return false;
        Aula aula = aulas.remove(idAula);
        aula.setId(nuevoIdAula);
        aulas.put(nuevoIdAula,aula);
        return true;
    }

    /**
     * @pre debe existir la aula identificada con id en el sistema.
     * @post se modifica la capacidad de la aula identificada con idAula.
     *      Se devuelve cierto si no ha habido ningún error.
     */
    public boolean modificarCapacidadAula(String id_aula, String nuevaCapcidad) {
        return aulas.get(id_aula).setMaxCapacity(Integer.parseInt(nuevaCapcidad));
    }

    public boolean modificarConPCAula(String idAula, String conPCAula) {
        return aulas.get(idAula).setAulaConPC(Boolean.parseBoolean(conPCAula));
    }




    /**
     * @pre cierto
     * @post se devuelve el identificador de la aula seleccionada. Null si no había ninguna seleccionada.
     */
    public String getAulaSeleccionada() {
        return aulaSeleccionada;
    }

    /**
     * @pre cierto
     * @post se modifica la aula seleccionada.
     */
    public boolean setAulaSeleccionada(String aula) {
        if (existeAula(aula)) {
            aulaSeleccionada = aula;
            return true;
        }
        else return false;
    }

}

