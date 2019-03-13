package domain.domaincontrollers;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import domain.Asignatura;
import domain.Titulacion;
import domain.Turno;

import data.CtrlDatosAsignatura;

public class CtrlAsignatura {


    private Map<String, Asignatura> asignaturas;
    private CtrlDominio ctrlDominio;

    /**
     * Creadora por defecto del controlador de asignatura
     * @param ctrlDominio: Instancia del controlador de dominio
     */
    public CtrlAsignatura(CtrlDominio ctrlDominio) {

        this.asignaturas = new HashMap<String, Asignatura>();
        this.ctrlDominio = ctrlDominio;

    }

    /**
     * Metodo que permite cargar asignaturas del sistema
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void cargarAsignaturas() throws FileNotFoundException, IOException {
        CtrlDatosAsignatura ctrlDatosAsignatura = CtrlDatosAsignatura.getInstance();
        ArrayList<String[]> list = ctrlDatosAsignatura.getAll();
        ArrayList<String[]> req = new ArrayList<String[]>();
        for (String[] parametros : list) {
            Integer alumnos = Integer.parseInt(parametros[2]);
            Titulacion titu = this.ctrlDominio.getTitulacion(parametros[3]);
            crearAsignatura(parametros[0], parametros[1], alumnos, titu, Integer.parseInt(parametros[4]), Integer.parseInt(parametros[5]));
            String[] aux = new String[parametros.length - 5];
            for (int i = 6; i<parametros.length; ++i) {

                if (i == 6) aux[0] = parametros[0];
                aux[i-5] = parametros[i];
            }

            if (parametros.length != 6) req.add(aux);
        }

        for (String[] parametros : req) {
            Asignatura asig = asignaturas.get(parametros[0]);
            for (int i = 1; i < parametros.length; ++i) {
                asig.anadirRequisitoBidireccional(asignaturas.get(parametros[i]));
            }
        }

    }

    /**
     * Metodo que permite consultar las asignaturas que pertenecen a una titulacion
     * @param titu: Nombre de la titulacion cuyas asignaturas se quieren consultar
     * @return Devuelve un ArrayList con el nombre de las asignaturas que se han consultado
     */
    public ArrayList<String> consultarAsignaturas(String titu) {
        ArrayList<String> lista_asigs = new ArrayList<String>();
        for (Map.Entry<String, Asignatura> entry : this.asignaturas.entrySet()) {

            if (entry.getValue().getTitulacion().getNombre().equals(titu)) lista_asigs.add(entry.getKey());
        }

        Collections.sort(lista_asigs);
        return lista_asigs;
    }

    /**
     * Metodo que permite crear una asignatura en el sistema.
     * @param nombre: Nombre de la asignatura
     * @param curso: Curso al que pertenece
     * @param alumnos: Alumnos matriculados en la asignatura
     * @param titu: Titulacion a la que pertenece la asignatura
     * @param duracionTeoria: Horas semanales de teoria
     * @param duracionLabo: Horas semanales de laboratorio
     * @return
     */
    public boolean crearAsignatura(String nombre, String curso, Integer alumnos, Titulacion titu, Integer duracionTeoria, Integer duracionLabo) {
        if (asignaturas.containsKey(nombre))
            return false;
        asignaturas.put(nombre, new Asignatura(nombre, curso, alumnos, titu, duracionTeoria, duracionLabo));
        return true;
    }

    /**
     * Metodo que permite eliminar una asignatura del sistema. Esto provocara que sus requisitos la eliminen de su
     * lista de requisitos
     * @param nombre: Nombre de la asignatura
     */
    public void eliminarAsignatura(String nombre) {
        Asignatura asig = asignaturas.get(nombre);
        Titulacion titu = asig.getTitulacion();
        titu.eliminarAsignatura(asig);
        asig.eliminarTodosRequisitos();
        asignaturas.remove(nombre);
    }

    /**
     * Metodo que permite consultar los atributos de una asignatura, que son su nombre, su curso, sus alumnos matriculados,
     * el nombre de la titulacion a la que pertenece, la duracion semanal de teoria y la duracion semanal de laboratorio
     * @param asig: Nombre de la asignatura
     * @return Devuelve un vector de String con los atributos en el siguiente orden: nombre, curso, alumnos, titulacion,
     * duracion de teoria y duracion de laboratorio
     */
    public String[] consultarParametros(String asig) {
        Asignatura asignatura = asignaturas.get(asig);
        String[] parametros = new String[6];
        parametros[0] = asignatura.getNombre();
        parametros[1] = asignatura.getCurso();
        parametros[2] = asignatura.getAlumnos().toString();
        Titulacion titu = asignatura.getTitulacion();
        parametros[3] = titu.getNombre();
        parametros[4] = asignatura.getDuracionTeoria().toString();
        parametros[5] = asignatura.getDuracionLabo().toString();
        return parametros;
    }

    /**
     * Metodo que permite obtener una asignatura del sistema
     * @param nombre: Nombre de la asignatura que se quiere obtener
     * @return Devuelve la asignatura con nombre "nombre"
     */
    public Asignatura getAsignatura(String nombre) { return this.asignaturas.get(nombre); }

    /**
     * Metodo que permite modificar el nombre de una asignatura
     * @param nombre: Nombre de la asignatura a modificar
     * @param nombrenuevo: Nuevo nombre para la asignatura
     * @return Devuelve falso si ya existe la asignatura "nombrenuevo", y cierto
     * de lo contrario
     */
    public boolean setNombre(String nombre, String nombrenuevo) {
        if (asignaturas.containsKey(nombrenuevo)) return false;
        else {
            Asignatura assig = asignaturas.get(nombre);
            asignaturas.remove(nombre);
            assig.setNombre(nombrenuevo);
            asignaturas.put(nombrenuevo, assig);
            return true;
        }
    }

    /**
     * Metodo que permite modificar el curso de una asignatura
     * @param nombre: Nombre de la asignatura
     * @param curso: Curso al que vamos a cambiar
     */
    public void setCurso(String nombre, String curso) {
        Asignatura assig = asignaturas.get(nombre);
        assig.setCurso(curso);
    }

    /**
     * Metodo que permite modificar el numero de alumnos de una asignatura. Esto provoca que se creen turnos de esta
     * asignatura en consecuencia con el valor introducido.
     * @param nombre: Nombre de la asignatura
     * @param alumnos: Nuevo numero de alumnos matriculados
     */
    public void setAlumnos(String nombre, Integer alumnos) {
        Asignatura asig = asignaturas.get(nombre);
        asig.setAlumnos(alumnos);
    }

    /**
     * Metodo que permite modificar la titulacion a la que pertenece una asignatura. Esto provoca que se creen turnos
     * de esta asignatura en consecuencia con la titulacion introducida.
     * @param nombre: Nombre de la asignatura
     * @param titu: Nombre de la nueva titulacion
     */
    public void setTitulacion(String nombre, Titulacion titu) {
        Asignatura asig = asignaturas.get(nombre);
        asig.setTitulacion(titu);
    }

    /**
     * Metodo que permite modificar las horas semanales de teoria de una asignatura. Esto provoca que se creen turnos en
     * consecuencia al valor introducido
     * @param nombre: Nombre de la asignatura
     * @param duracionTeoria: Nuevo numero de horas semanales de teoria
     */
    public void setDuracionTeoria(String nombre, Integer duracionTeoria) {
        asignaturas.get(nombre).setDuracionTeoria(duracionTeoria);
    }

    /**
     * Metodo que permite modificar el numero de horas semanales de laboratorio de una asignatura. Esto provoca que se
     * creen turnos en consecuencia al valor introducido
     * @param nombre: Nombre de la asignatuar
     * @param duracionLabo: Nuevo numero de horas semanales de laboratorio
     */
    public void setDuracionLabo(String nombre, Integer duracionLabo) {
        asignaturas.get(nombre).setDuracionLabo(duracionLabo);
    }

    /**
     * Metodo que permite listar todos los turnos de una asignatura
     * @param nombre: Nombre de la asignatura
     * @return Devuelve una ArrayList de Integers con el Id de cada turno
     */

    public ArrayList<Integer> listarTurnos(String nombre) {
        Asignatura asig = asignaturas.get(nombre);
        return asig.listarTurnos();
    }

    /**
     * Metodo que permite saber si existe una asignatura con nombre "nombre"
     * @param nombre: Nombre de la asignatura
     * @return Devuelve cierto si existe una asignatura con nombre "nombre", y falso de lo contrario
     */
    public boolean existeAsignatura(String nombre) {
        if (asignaturas.containsKey(nombre)) return true;
        else return false;
    }

    /**
     * Metodo que permite anadirle un requisito a una asignatura
     * @param nombre: Nombre de la asignatura
     * @param requisito: Nombre del requisito a anadir
     * @return Devuelve falso si no existen la asignatura o el requisito, y cierto de lo contrario
     */
    public boolean anadirRequisito(String nombre, String requisito) {
        if (!asignaturas.containsKey(nombre) || !asignaturas.containsKey(requisito) || asignaturas.get(nombre).getTitulacion().getNombre().equals(asignaturas.get(requisito).getTitulacion().getNombre())) return false;
        Asignatura asig = asignaturas.get(nombre);
        asig.anadirRequisitoBidireccional(asignaturas.get(requisito));
        return true;
    }

    /**
     * Metodo que permite eliminar un requisito de una asignatura
     * @param nombre: Nombre de la asignatura
     * @param requisito: Nombre del requisito a eliminar
     * @return Devuelve falso si no existen la asignatura o el requisito, o bien si "requisito" no es requisito de la
     * asignatura. Devuelve cierto de lo contrario
     */
    public boolean eliminarRequisito(String nombre, String requisito) {
        if (!asignaturas.containsKey(nombre) || !asignaturas.containsKey(requisito) || !asignaturas.get(nombre).isRequisito(asignaturas.get(requisito).getNombre())) return false;
        asignaturas.get(nombre).eliminarRequisitoBidireccional(requisito);
        return true;
    }

    /**
     * Metodo que premite guardar los cambios hechos en las asignaturas del sistema en un fichero "asignaturas.txt"
     * @throws java.io.IOException
     */
    public void guardarSistema() throws java.io.IOException{
        ArrayList<String[]> lista = new ArrayList<String[]>();
        for (Map.Entry<String, Asignatura> entry : this.asignaturas.entrySet()) {
            String[] parametros = consultarParametros(entry.getKey());
            ArrayList<String> reqs = entry.getValue().getRequisitos();
            String[] asignatura = new String[parametros.length + reqs.size()];
            for (int i = 0; i<parametros.length; ++i) {
                asignatura[i] = parametros[i];
            }
            int i = 6;
            for (String req : reqs) {
                asignatura[i] = req;
                ++i;
            }
            lista.add(asignatura);
        }
        CtrlDatosAsignatura ctrlDatosAsignatura = CtrlDatosAsignatura.getInstance();
        ctrlDatosAsignatura.guardarAsignaturas(lista);
    }

    /**
     * Metodo que permite listar todos los requisitos de una asignatura
     * @param asig: Nombre de la asignatura
     * @return Devuelve un ArrayList de String con el nombre de todos los requisitos de la asignatura
     */
    public ArrayList<String> listarRequisitos(String asig) {
        return asignaturas.get(asig).listarRequisitos();
    }

    /**
     * Metodo que permite anadir un turno a una asignatura
     * @param asignatura: Asignatura a la que se le va a anadir el turno
     * @param turno: Turno que se va a anadir
     */
    public void anadirTurno(Asignatura asignatura, Turno turno) {
        asignatura.anadirTurno(turno);
    }

    /**
     * Metodo que modifica todos los turnos de las asignaturas de una titulacion para mantener la coherencia
     * con los parametros modificados de la titulacion a la que pertenecen
     * @param nombreTitulacion: Nombre de la titulacion a la que pertenecen las asignaturas cuyos turnos se van a modificar
     */
    public void modificarTurnos(String nombreTitulacion) {
        for (Map.Entry<String, Asignatura> entry : this.asignaturas.entrySet()) {
            if (entry.getValue().getTitulacion().getNombre().equals(nombreTitulacion)) {
                ctrlDominio.eliminarTurnos(entry.getValue());
                ctrlDominio.crearTurnos(entry.getValue());
            }
        }
    }
}