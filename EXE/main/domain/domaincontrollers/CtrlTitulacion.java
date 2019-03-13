package domain.domaincontrollers;

import data.CtrlDatosTitulacion;
import domain.restrictions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import domain.Titulacion;

public class CtrlTitulacion {


    private Map<String, Titulacion> titulaciones;
    private String titulacionSeleccionada;
    private CtrlAsignatura ctrlAsignatura;
    private CtrlDominio ctrlDominio;

    public CtrlTitulacion(CtrlDominio ctrlDominio) {
        inicializarCtrlTitulacion(ctrlDominio);
    }

    private void inicializarCtrlTitulacion(CtrlDominio ctrlDominio) {
        titulaciones = new HashMap<String, Titulacion>();
        titulacionSeleccionada = null;
        ctrlDominio = ctrlDominio;
    }


    /**
     * @pre cierto
     * @post carga todas las aulas del fichero aulas.txt
     */
    public void cargarTitulacionesDeFichero() throws FileNotFoundException, IOException, DemasiadasTitulaciones{

        /*
        BufferedReader in = new BufferedReader(new FileReader("titulaciones.txt"));
        String linea;
        while ((linea = in.readLine()) != null) {
            String[] parametros = linea.split(",");
            crearTitulacion(parametros[0],Integer.parseInt(parametros[1]), Integer.parseInt(parametros[2]));
        }
        in.close();
        */
        CtrlDatosTitulacion ctrlDatosTitulacion = CtrlDatosTitulacion.getInstance();
        ArrayList<String[]> datos = ctrlDatosTitulacion.getAll();
        for (String[] parametros : datos) {
            crearTitulacion(parametros[0], Integer.parseInt(parametros[1]), Integer.parseInt(parametros[2]));
        }
    }


    /**
     * @pre cierto
     * @post guarda los cambios hechos en las titulaciones del sistema en un fichero.
     */

    // al cargar el sistema que las asignaturas llamen a su titulacion y se añadan
    // guardar titulaciones llama a guardarAsignaturas

    public void guardarTitulacionens() throws IOException  {
        ArrayList<String[]> datosTitulaciones = new ArrayList<>();
        for (Map.Entry<String, Titulacion> titulacionEntry : this.titulaciones.entrySet()) {
            Titulacion titulacion = titulacionEntry.getValue();
            String[] datoTitulacion = new String[3];
            datoTitulacion[0] = titulacion.toString();
            datoTitulacion[1] = Integer.toString(titulacion.getAlumnosTeoria());
            datoTitulacion[2] = Integer.toString(titulacion.getAlumnosLabo());
            datosTitulaciones.add(datoTitulacion);
        }
        CtrlDatosTitulacion ctrldatosaula = CtrlDatosTitulacion.getInstance();
        ctrldatosaula.saveTitulaciones(datosTitulaciones);
    }

    /**
     * @pre cierto
     * @post se devuelve una lista con el nombre de todas las titulaciones en el sistema.
     */
    public ArrayList<String> listarTitulaciones() {
        ArrayList<String> lista = new ArrayList<>(titulaciones.keySet());
        Collections.sort(lista);
        return lista;
    }


    public String[] listarTitulacionesNombre() {
        String[] titulacionesNombre = new String[titulaciones.size()];
        ArrayList<String> lista = new ArrayList<>(titulaciones.keySet());
        Collections.sort(lista);
        titulacionesNombre = lista.toArray(titulacionesNombre);
        return titulacionesNombre;
    }



    /**
     * @pre cierto
     * @post se crea una instancia nueva de titulación con los atributos.
     *      Se devuelve cierto si no ha habido ningún error.
     */
    public boolean crearTitulacion(String nombreTitulacion, Integer alumnosTeoria, Integer alumnosLaboratorio) throws DemasiadasTitulaciones{
        if (titulaciones.containsKey(nombreTitulacion)) return false;
        if (titulaciones.size() >= 100) throw new DemasiadasTitulaciones ("No puede haber mas de 100 titulaciones");
        titulaciones.put(nombreTitulacion, new Titulacion(nombreTitulacion, alumnosTeoria, alumnosLaboratorio));
        return true;
    }

    /**
     * @pre cierto
     * @post se eliminará del sistema la aula con nombre nombreTitulacion.
     *      Si no existía el aula se deveolverá falso.
     */
    public boolean eliminarTitulacion(String nombreTitulacion) {
        if (nombreTitulacion.equals(titulacionSeleccionada)) titulacionSeleccionada = null;
        //eliminarTodasAsignaturas(nombreTitulacion);
        titulaciones.remove(nombreTitulacion);
        return true;
    }


    /**
     * @pre cierto
     * @post se eliminará del sistema todas las asignaturas que pertenecen a la titulación
     *      identificada con nombreTitulacion
     */

    private void eliminarTodasAsignaturas(String nombrTiulacion) {
        ArrayList<String> asignaturasAEliminar = titulaciones.get(nombrTiulacion).getAsignaturas();
        for (String asignatura : asignaturasAEliminar) {
            ctrlAsignatura.eliminarAsignatura(asignatura);
        }
    }

    /**
     * @pre cierto
     * @post devuelve cierto si la titulación existe en el sistema.
     */
    public boolean existeTitulacion(String nombre) {
        return titulaciones.containsKey(nombre);
    }

    /**
     * @pre debe existir la titulación identificada con nombreTitulacion en el sistema.
     * @post devuelve la instancia de titulación identificada por nombre.
     */
    public Titulacion getTitulacion(String nombreTitulacion) {
        return titulaciones.get(nombreTitulacion);
    }

    /**
     * @pre debe existir la titulación identificada con nombreTitulacion en el sistema.
     * @post se devuelve el número de asignaturas de la tiulación identificada con nombreTitulacion.
     */
    public String consultarNumeroAsignaturas(String nombreTitulacion) {
        return Integer.toString(titulaciones.get(nombreTitulacion).getNumAsignaturas());
    }

    /**
     * @pre debe existir la titulación identificada con nombreTitulacion en el sistema.
     * @post se devuelve el número de alumnos que hay en los grupos de teoría de dicha titulación.
     */
    public String getAlumnosTeoria(String nombreTitulacion) {
        return Integer.toString(titulaciones.get(nombreTitulacion).getAlumnosTeoria());
    }

    /**
     * @pre debe existir la titulación identificada con nombreTitulacion en el sistema.
     * @post se devuelve el número de alumnos que hay en los grupos de laboratorio de dicha titulación.
     */
    public String getAlumnosLabo(String nombreTitulacion) {
        return Integer.toString(titulaciones.get(nombreTitulacion).getAlumnosLabo());
    }

    /**
     * @pre debe existir la titulación identificada con nombreTitulacion en el sistema.
     * @post se devuelven todos los parámetros de la titulación.
     */
    public String[] consultarParametros(String nombreTitulacion) {
        String[] parametros = new String[4];
        parametros[0] = nombreTitulacion;
        parametros[1] = consultarNumeroAsignaturas(nombreTitulacion);
        parametros[2] = String.valueOf(getTitulacion(nombreTitulacion).getAlumnosTeoria());
        parametros[3] = String.valueOf(getTitulacion(nombreTitulacion).getAlumnosLabo());
        return parametros;
    }

    /**
     * @pre debe existir la titulación identificada con id en el sistema.
     * @post se modifica el identificador de la titulación identificada con oldTitulacion.
     *      Se devuelve cierto si no ha habido ningún error.
     */
    public boolean modificarNombreTitulacion(String oldTitulacion, String NewTitulacion) {
        if (titulaciones.containsKey(NewTitulacion)) return false;
        Titulacion titulacion = titulaciones.remove(oldTitulacion);
        titulacion.setNombre(NewTitulacion);
        titulaciones.put(NewTitulacion,titulacion);
        return true;
    }

    /**
     * @pre debe existir la titulación identificada con nombreTitulacion en el sistema.
     * @post se modifican los alumnos de Teoria.
     *      Se devuelve cierto si no ha habido ningún error.
     */
    public boolean modificarAlumnosTeoria(String nombreTitulacion, String alumnos) {
        titulaciones.get(nombreTitulacion).setAlumnosTeoria(Integer.parseInt(alumnos));
        return true;
    }

    /**
     * @pre debe existir la titulación identificada con nombreTitulacion en el sistema.
     * @post se modifican los alumnos de Laboratorio.
     *      Se devuelve cierto si no ha habido ningún error.
     */
    public boolean modificarAlumnosLabo(String nombreTitulacion, String alumnos) {
        titulaciones.get(nombreTitulacion).setAlumnosLabo(Integer.parseInt(alumnos));
        return true;
    }

    /**
     * @pre cierto
     * @post se devuelve el identificador de la titulación seleccionada. Null si no había ninguna seleccionada.
     */
    public String getTitulacionSeleccionada() {
        return titulacionSeleccionada;
    }

    /**
     * @pre cierto
     * @post se modifica la titulación seleccionada.
     */
    public boolean setTitulacionSeleccionada(String nombre) {
        if (existeTitulacion(nombre)) {
            this.titulacionSeleccionada = nombre;
            return true;
        }
        else return false;
    }
}
