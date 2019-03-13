package domain.domaincontrollers;

import java.io.*;
import java.util.*;

import domain.Asignatura;
import domain.Turno;

import data.CtrlDatosTurno;

public class CtrlTurno {
    private HashMap<List<String>, Turno> turnos;
    private CtrlDominio ctrlDominio;

    /**
     * Creadora del controlador de Turno, que contiene una instancia de CtrlDominio y un HashMap de Turnos vacío
     * @param ctrlDominio: Controlador de Dominio al que pertenece
     */

    public CtrlTurno(CtrlDominio ctrlDominio) {
        this.turnos = new HashMap<List<String>, Turno>();
        this.ctrlDominio = ctrlDominio;
    }

    /**
     * Metodo que permite cargar turnos del fichero "turnos.txt" al controlador
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void cargarTurnos() throws FileNotFoundException, IOException {

        CtrlDatosTurno ctrlDatosTurno = CtrlDatosTurno.getInstance();
        ArrayList<String[]> list = ctrlDatosTurno.getAll();
        for (String[] parametros : list) {
            Integer id = Integer.parseInt(parametros[0]);
            Integer duracion = Integer.parseInt(parametros[2]);
            Asignatura asig = this.ctrlDominio.getAsignatura(parametros[3]);
            Integer alumnos = Integer.parseInt(parametros[4]);
            Integer ident = Integer.parseInt(parametros[5]);
            crearTurno(id, parametros[1], duracion, asig, alumnos, ident);
        }
    }

    /**
     * Metodo que permite crear un nuevo Turno y anadirlo a la lista de turnos del controlador
     * @param id: Id del turno
     * @param tipo: Tipo del turno, "teoria" o "laboratorio"
     * @param duracion: Horas que dura el turno
     * @param asignatura: Asignatura a la que pertenece el turno
     * @param alumnos: Alumnos que participan en el turno
     * @param ident: Identificador del turno, unico entre aquellos turnos con mismo id y asignatura
     * @return
     */
    public boolean crearTurno(Integer id, String tipo, Integer duracion, Asignatura asignatura, Integer alumnos, Integer ident) {
        String identificador = id.toString();
        String asignombre = asignatura.getNombre();
        if (turnos.containsKey(Collections.unmodifiableList(Arrays.asList(identificador, asignombre, ident.toString()))))
            return false;
        Turno turno = new Turno(id, tipo, duracion, asignatura, alumnos, ident);
        //this.ctrlDominio.anadirTurnoAsignatura(asignatura, turno);
        turnos.put(Collections.unmodifiableList(Arrays.asList(identificador, asignombre, ident.toString())), turno);
        return true;
    }

    /**
     * Metodo que permite consultar los atributos de un Turno
     * @param turno: Turno del que se quieren consultar los atributos
     * @return: Devuelve un vector de string con los parametros del turno en este orden: Id, tipo, duracion, nombre de
     * la asignatura, alumnos e identificador
     */
    public String[] consultarParametros(Turno turno) {
        String[] parametros = new String[6];
        parametros[0] = turno.getId().toString();
        parametros[1] = turno.getTipo_turno();
        parametros[2] = turno.getDuracion().toString();
        Asignatura asig = turno.getAsignatura();
        parametros[3] = asig.getNombre();
        parametros[4] = turno.getAlumnos().toString();
        parametros[5] = turno.getIdent().toString();
        return parametros;
    }

    /**
     * Metodo que permite listar todos los turnos que el controlador tiene en la lista
     * @return Devuelve un ArrayList de Turno con todos los turnos que el controlador tiene en la lista de turnos
     */
    public ArrayList<Turno> listarTodosTurnos() {
        ArrayList<Turno> lista = new ArrayList<Turno>();
        for (Map.Entry<List<String>, Turno> entry : this.turnos.entrySet()) {
            lista.add(entry.getValue());
        }
        return lista;
    }

    /**
     * Metodo que permite obtener un turno de la lista de turnos del controlador
     * @param id: Id del turno
     * @param asig: Nombre de la asignatura a la que pertenece
     * @param iden: Identificador del turno
     * @return Devuelve el Turno que tiene el id "id", pertenece a la asignatura "asig" y tiene el identificador "iden"
     */
    public Turno getTurno(String id, String asig, String iden) {
        return turnos.get(Collections.unmodifiableList(Arrays.asList(id, asig, iden)));

    }

    /**
     * Metodo que permite guardar la lista de turnos del controlador en el fichero "turnos.txt"
     * @throws java.io.IOException
     */
    public void guardarSistema() throws java.io.IOException{
        ArrayList<String[]> lista = new ArrayList<String[]>();
        for (Map.Entry<List<String>, Turno> entry : turnos.entrySet()) {
            String[] parametros = consultarParametros(entry.getValue());
            lista.add(parametros);

        }

        CtrlDatosTurno ctrlDatosTurno = CtrlDatosTurno.getInstance();
        ctrlDatosTurno.guardarTurnos(lista);

    }

    /**
     * Metodo que permite eliminar todos los turnos que pertenecen a la asignatura asig
     * @param asig: Asignatura de la que se van a borar todos sus turnos
     */
    public void eliminarTurnos(Asignatura asig) {
        ArrayList<List<String>> eliminar = new ArrayList<List<String>>();
        for (Map.Entry<List<String>, Turno> turno : turnos.entrySet()) {
            Turno turn = turno.getValue();
            if (turn.getAsignatura().getNombre().equals(asig.getNombre())) {
                asig.eliminarTurno(turn);
                eliminar.add(turno.getKey());
            }
        }
        for (List<String> elemento : eliminar) {
            turnos.remove(elemento);
        }
    }

}