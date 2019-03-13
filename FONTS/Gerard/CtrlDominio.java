package domain.domaincontrollers;

import domain.*;
import domain.restrictions.DemasiadasAsignaturas;
import domain.restrictions.DemasiadasTitulaciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CtrlDominio {

    private CtrlAsignatura ctrlAsignatura;
    private CtrlTurno ctrlTurno;
    private CtrlTitulacion ctrlTitulacion;
    private CtrlAula ctrlAula;
    private CtrlFecha ctrlFecha;
    private CtrlHorario ctrlHorario;
    private GeneticAlgorithm algorithm;

    private Asignatura asignaturaSeleccionada = null;


    /**
     * Creadora por defecto de CtrlDominio. Inicializa una instancia de cada controlador y le pasa a si misma como parametro
     * de controlador de dominio
     */
    public CtrlDominio() {
        this.ctrlAsignatura = new CtrlAsignatura(this);
        this.ctrlTurno = new CtrlTurno(this);
        this.ctrlTitulacion = new CtrlTitulacion(this);
        this.ctrlAula = new CtrlAula(this);
        this.ctrlFecha = new CtrlFecha(this);
        this.ctrlHorario = new CtrlHorario(this);
    }






    // ****************** Titulaciones *************************

    /**
     * Metodo que permite crear una titulacion
     * @param nombre: Nombre de la titulacion
     * @param AlumnosGrupoTeoria: Alumnos por grupo de teoria
     * @param alumnosGrupoLab: Alumnos por grupo de laboratorio
     * @return Devuelve cierto si se ha podido crear la titulacion y falso de lo contrario
     * @throws DemasiadasTitulaciones
     */
    public boolean crearTitulacion(String nombre, String AlumnosGrupoTeoria, String alumnosGrupoLab) throws DemasiadasTitulaciones {
        return ctrlTitulacion.crearTitulacion(nombre, Integer.parseInt(AlumnosGrupoTeoria), Integer.parseInt(alumnosGrupoLab));
    }

    /**
     * Metodo que permite cambiar el nombre a una titulacion
     * @param oldTitulacion: Antiguo nombre
     * @param newTitulacion: Nombre nuevo
     * @return Devuelve cierto en caso de que se haya podido modificar el nombre, y falso de lo contrario
     */
    public boolean setNombreTitulacion(String oldTitulacion, String newTitulacion) {
        return ctrlTitulacion.modificarNombreTitulacion(oldTitulacion, newTitulacion);
    }

    /**
     * Devuelve el nombre de la titulacion a la que pertenece una asignatura
     * @param nombre: Nombre de la asignatura cuya titulacion se quiere consultar
     * @return Devuelve el nombre de la titulacion a la que pertenece la asignatura "nombre"
     */
    public String getTitulacionAsignatura(String nombre) {
        Asignatura asig = ctrlAsignatura.getAsignatura(nombre);
        return asig.getTitulacion().getNombre();
    }

    /**
     * Metodo que permite listar el nombre de las titulaciones del sistema
     * @return Nombre de las titulaciones del sistema
     */
    public String[] listarTitulacionesNombre() {
        return ctrlTitulacion.listarTitulacionesNombre();
    }

    /**
     * Metodo que permite listar las titulaciones del sistema
     * @return Devuelve una ArrayList de String con el nombre de las titulaciones del sistema
     */
    public ArrayList<String> listarTitulaciones() {
        return ctrlTitulacion.listarTitulaciones();
    }

    /**
     * Metodo que permite guardar las titulaciones del sistema
     * @throws java.io.IOException
     */
    public void guardarTitulaciones() throws java.io.IOException {
        ctrlTitulacion.guardarTitulacionens();
    }


    /**
     * Metodo que permite eliminar una titulacion del sistema
     * @param nombre: Nombre de la titulacion a eliminar
     */
    public void eliminarTitulacion(String nombre) {
        ctrlTitulacion.eliminarTitulacion(nombre);
    }

    /**
     * Metodo que permite consultar los parametros de una titulacion
     * @param titulacion: Nombre de la titulacion cuyos parametros se quieren consultar
     * @return: Devuelve un vector de String con los parametros de la titulacion que se quiere consultar
     */
    public String[] consultarParametrosTitulacion(String titulacion) {
        return ctrlTitulacion.consultarParametros(titulacion);
    }


    /**
     * Metodo que permite obtener una titulacion con nombre "nombre"
     * @param nombre: Nombre de la titulacion a obtener
     * @return Devuelve la titulacion con nombre "nombre"
     */
    public Titulacion getTitulacion(String nombre) {
        return ctrlTitulacion.getTitulacion(nombre);
    }

    /**
     * Metodo que establece una titulacion seleccionada
     * @param titulacionSeleccionada: Nombre de la titulacion que se quiere establecer como seleccionada
     */
    public void setTitulacionSeleccionada(String titulacionSeleccionada) {
        ctrlTitulacion.setTitulacionSeleccionada(titulacionSeleccionada);
    }

    /**
     * Metodo que permite obtener el nombre de la titulacion seleccionada
     * @return Devuelve el nombre de la titulacion seleccionada
     */
    public String getTitulacionSeleccionada() {
        return ctrlTitulacion.getTitulacionSeleccionada();
    }


    /**
     * Metodo que permite modificar los alumnos por grupo de teoria de una titulacion
     * @param nombreTitulacion: Titulacion que se va a modificar
     * @param newAlumnosTeoria: Nuevo numero de alumnos por grupo de teoria
     */
    public void setAlumnosTeoria(String nombreTitulacion, String newAlumnosTeoria) {
        ctrlTitulacion.modificarAlumnosTeoria(nombreTitulacion, newAlumnosTeoria);
        ctrlAsignatura.modificarTurnos(nombreTitulacion);
    }


    /**
     * Metodo que permite modificar los alumnos por grupo de laboratorio de una titulacion
     * @param nombreTitulacion: Titulacion que se va a modificar
     * @param newAlumnosLaboratorio: Nuevo numero de alumnos por grupo de laboratorio
     */
    public void setAlumnosLaboratorio(String nombreTitulacion, String newAlumnosLaboratorio) {
        ctrlTitulacion.modificarAlumnosLabo(nombreTitulacion, newAlumnosLaboratorio);
        ctrlAsignatura.modificarTurnos(nombreTitulacion);
    }

    // **************************** Aulas **************************************


    /**
     * Metodo que permite crear una aula nueva
     * @param nombre: Nombre de la aula
     * @param capacidad: Capacidad de la aula
     * @param conPC: "si" si consta de PC y "no" de lo contrario
     * @return Devuelve cierto si se ha podido crear el aula y falso de lo contrario
     */
    public boolean crearAula(String nombre, String capacidad, String conPC) {
        return ctrlAula.crearAula(nombre,capacidad,conPC);
    }

    /**
     * Metodo que permite modificar el nombre de una aula
     * @param oldAula: Antiguo nombre de la aula
     * @param newAula: Nuevo nombre de a aula
     * @return Devuelve cierto si se ha podido modificar el nombre de la aula
     */
    public boolean setNombreAula(String oldAula, String newAula) {
        return ctrlAula.modificarIdentificadorAula(oldAula, newAula);
    }

    /**
     * Metodo que permite obtener una lista de todas las Aulas del sistema
     * @return Devuelve una ArrayList de Aula con todas las aulas del sistema
     */
    public ArrayList<Aula> listarTodasAulas() {
        ArrayList<Aula> lista = ctrlAula.listarAulas();
        return lista;
    }

    /**
     * Metodo que permite obtener una lista del nombre de todas las Aulas del sistema
     * @return Devuelve una ArrayList de String con todas las aulas del sistema
     */
    public String[] listarAulasNombre() {
        return ctrlAula.listarAulasNombre();
    }

    /**
     * Metodo que permite guardar las aulas del sistema en el fichero "aulas.txt"
     * @throws java.io.IOException
     */
    public void guardarAulas() throws java.io.IOException {
        ctrlAula.guardarAulas();
    }



    /**
     * Metodo que permite eliminar una aula del sistema
     * @param aulaNombre: Nombre de la aula a borrar
     * @return Devuelve falso si la aula no existe. Devuelve cierto en caso contrario
     */
    public boolean eliminarAula(String aulaNombre) {
        if (ctrlAula.existeAula(aulaNombre)){
            ctrlAula.eliminarAula(aulaNombre);
            return true;
        }
        else return false;
    }

    /**
     * Metodo que permite consultar los atributos de una aula con nombre "aula"
     * @param aula: Nombre de la aula a consultar
     * @return Devuelve un vector de String con los atributos de la aula "aula" en este orden: nombre, capacidad,
     * conPC
     */
    public String[] consultarParametrosAula(String aula) {
        return ctrlAula.consultarParametros(aula);
    }

    /**
     * Metodo que devuelve el aula con el nombre id
     * @param id: Nombre de la aula
     * @return Devuelve el aula con el nombre id
     */
    public Aula getAula(String id) {
        return ctrlAula.getAula(id);
    }

    /**
     * Metodo que permite establecer el aula seleccionada
     * @param aulaSeleccionada: Nombre del aula que se quiere establecer como seleccionada
     */
    public void setAulaSeleccionada(String aulaSeleccionada) {
        ctrlAula.setAulaSeleccionada(aulaSeleccionada);
    }

    /**
     * Metodo que permite obtener el nombre del aula seleccionada
     * @return Devuelve el nombre de la aula seleccionada
     */
    public String getAulaSeleccionada() {
        return ctrlAula.getAulaSeleccionada();
    }

    /**
     * Metodo que permite establecer la capacidad de una aula
     * @param nombreAula: Nombre del aula cuya capacidad se quiere modificar
     * @param newCapacidad: Nueva capacidad
     */
    public void setCapacidadAula(String nombreAula, String newCapacidad) {
        ctrlAula.modificarCapacidadAula(nombreAula, newCapacidad);
    }

    /**
     * Metodo que permite establecer si una aula tiene o no PC
     * @param nombreAula: Nombre del aula que se quiere modificar
     * @param newConPC: Si o no
     */
    public void setConPCAula(String nombreAula, String newConPC) {
        ctrlAula.modificarConPCAula(nombreAula, newConPC);
    }

    //***********************************************




    // **************************** Horarios **************************************


    public void guardarHorarios() throws java.io.IOException {
        ctrlHorario.guardarHorarios();
    }

    public boolean eliminarHorario(String horarioNombre) {
        if (ctrlHorario.existeHorario(horarioNombre)) {
            ctrlHorario.eliminarHorario(horarioNombre);
            return true;
        }
        return false;
    }

    public ArrayList<ArrayList<String>> getSesionesPorFecha(Integer fecha) {
        Horario h = ctrlHorario.getHorario(getHorarioSeleccionado());
        return h.getSesionesPorFecha(fecha);
    }
    public ArrayList<Integer> listarIdFechas(){
        return ctrlFecha.listarIdFechas();
    }


    public String[] listarnomHorarios() {
        return ctrlHorario.listarnomHorarios();
    }

    public String getHorarioSeleccionado() {
        return ctrlHorario.getHorarioSeleccionado();
    }

    public void setHorarioSeleccionado(String nombrHorario) {
        ctrlHorario.setHorarioSeleccionado(nombrHorario);
    }


    public boolean swap(Integer id1, Integer id2, String aula1, String aula2) {
        String nombreHorario = ctrlHorario.getHorarioSeleccionado();
        Horario h = ctrlHorario.getHorario(nombreHorario);
        Fecha fid1 = ctrlFecha.getFecha(id1.toString());
        Fecha fid2 = ctrlFecha.getFecha(id2.toString());
        return h.swap(id1, id2, aula1, aula2, fid1, fid2);
    }

    public void eliminarTodosHorarios() {
        ctrlHorario.eliminarTodosHorarios();
    }

    //***********************************************




    // **************************** Asignaturas **************************************

    /**
     * Metodo que permite crear una asignatura en el controlador de asignatura
     * @param nombre: Nombre de la asignatura
     * @param curso: Curso al que pertenece la asignatura
     * @param alumnos: Alumnos matriculados en la asignatura
     * @param titu: Titulacion a la que la asignatura pertenece
     * @param duracionTeoria: Horas de teoria semanales
     * @param duracionLabo: Horas de laboratorio semanales
     * @return Devuelve falso si no existe la titulacion o si ya existe la asignatura. Devuelve cierto de lo contrario
     * @throws DemasiadasAsignaturas
     */

    public boolean crearAsignatura(String nombre, String curso, String alumnos, String titu, String duracionTeoria, String duracionLabo) throws DemasiadasAsignaturas {
        if (!ctrlTitulacion.existeTitulacion(titu)) return false;
        Titulacion titulacion = ctrlTitulacion.getTitulacion(titu);
        if (titulacion.getAsignaturas().size() > 100) throw new DemasiadasAsignaturas("Una titulacion no puede tener mas de 100 asignaturas");
        if (!ctrlAsignatura.crearAsignatura(nombre, curso, Integer.parseInt(alumnos), titulacion, Integer.parseInt(duracionTeoria), Integer.parseInt(duracionLabo))) return false;
        Asignatura asig = ctrlAsignatura.getAsignatura(nombre);
        crearTurnos(asig);

        return true;
    }

    /**
     * Metodo que permite obtener una asignatura con nombre "nombre"
     * @param nombre: Nombre de la asignatura a obtener
     * @return Devuelve la asignatura con nombre "nombre"
     */
    public Asignatura getAsignatura(String nombre) { return ctrlAsignatura.getAsignatura(nombre); }


    /**
     * Metodo que permite eliminar una asignatura del sistema, lo que produce tambien la eliminacion de todos sus turnos
     * @param nombre: Nombre de la asignatura a borrar
     * @return Devuelve falso si la asignatura no existe. Devuelve cierto en caso contrario
     */
    public boolean eliminarAsignatura(String nombre) {
        if (ctrlAsignatura.existeAsignatura(nombre)){
            ctrlTurno.eliminarTurnos(ctrlAsignatura.getAsignatura(nombre));
            ctrlAsignatura.eliminarAsignatura(nombre);

            return true;
        }
        else return false;
    }

    /**
     * Metodo que permite listar las asignaturas de una titulacion con nombre "titulacion"
     * @param titulacion: Nombre de la titulacion de la que se quieren listar las asignaturas
     * @return Devuelve una ArrayList de String con el nombre de todas las asignaturas de la titulacion "titulacion"
     */
    public ArrayList<String> listarAsignaturas(String titulacion) {

        return ctrlAsignatura.consultarAsignaturas(titulacion);
    }


    /**
     * Metodo que permite consultar los atributos de una asignatura con nombre "asig"
     * @param asig: Nombre de la asignatura a consultar
     * @return Devuelve un vector de String con los atributos de la asignatura "asig" en este orden: nombre, curso,
     * alumnos, titulacion, duracion de teoria y duracion de laboratorio
     */
    public String[] consultarParametrosAsignatura(String asig) {
        return ctrlAsignatura.consultarParametros(asig);
    }


    /**
     * Metodo que permite modificar el nombre de una asignatura
     * @param asig: Nombre de la asignatura a modificar
     * @param nombre: Nuevo nombre a dar a la asignatura
     * @return Devuelve falso si la asignatura "asig" no existe o la asignatura "nombre" ya existe. Devuelve cierto
     * en caso contrario
     */
    public boolean setNombreAsignatura(String asig, String nombre) {
        if (ctrlAsignatura.existeAsignatura(asig)) {
            ctrlAsignatura.setNombre(asig, nombre);
            return true;
        }
        else return false;
    }

    /**
     * Metodo que permite modificar el curso de una asignatura
     * @param asig: Nombre de la asignatura a modificar
     * @param curso: Nuevo curso de la asignatura
     * @return Devuelve falso si la asignatura "asig" no existe y cierto de lo contrario
     */
    public boolean setCursoAsignatura(String asig, String curso) {
        if (ctrlAsignatura.existeAsignatura(asig)) {
            ctrlAsignatura.setCurso(asig, curso);
            return true;
        }
        else return false;
    }

    /**
     * Metodo que permite modificar los alumnos matriculados en una asignatura. Esto provocara la eliminacion de los
     * turnos de la asignatura y la creacion de los turnos correspondientes
     * @param asig: Nombre de la asignatura a modificar
     * @param alumnos: Nueva cantidad de alumnos matriculados
     * @return Devuelve falso si la asignatura no existe y cierto de lo contrario
     */
    public boolean setAlumnosAsignatura(String asig, String alumnos) {
        if (ctrlAsignatura.existeAsignatura(asig)) {
            ctrlAsignatura.setAlumnos(asig, Integer.parseInt(alumnos));
            ctrlTurno.eliminarTurnos(ctrlAsignatura.getAsignatura(asig));
            crearTurnos(ctrlAsignatura.getAsignatura(asig));
            return true;
        }
        else return false;
    }

    /**
     * Metodo que permite modificar la titulacion a la que pertenece una asignatura
     * @param asig: Asignatura que se quiere modificar
     * @param titu: Nueva titulacion a la que pertenece la asignatura
     * @return Devuelve falso si la asignatura o la titulacion no existen y cierto de lo contrario
     */
    public boolean setTitulacionAsignatura(String asig, String titu) {
        if (!ctrlTitulacion.existeTitulacion(titu)) return false;
        Titulacion titulacion = ctrlTitulacion.getTitulacion(titu);
        if (ctrlAsignatura.existeAsignatura(asig)) {
            ctrlAsignatura.setTitulacion(asig, titulacion);
            ctrlTurno.eliminarTurnos(ctrlAsignatura.getAsignatura(asig));
            crearTurnos(ctrlAsignatura.getAsignatura(asig));
            return true;
        }
        else return false;
    }

    /**
     * Metodo que permite modificar las horas semanales de teoria de una asignatura. Esto provocara la eliminacion de
     * todos los turnos de la asignatura y la creacion de nuevos turnos en funcion de las horas semanales de teoria
     * @param asig: Nombre de la asignatura
     * @param duracionTeoria: Nueva duracion de teoria
     * @return Devuelve falso si no existe la asignatura, cierto de lo contrario
     */
    public boolean setDuracionTeoria(String asig, String duracionTeoria) {
        if (ctrlAsignatura.existeAsignatura(asig)) {
            ctrlAsignatura.setDuracionTeoria(asig, Integer.parseInt(duracionTeoria));
            ctrlTurno.eliminarTurnos(ctrlAsignatura.getAsignatura(asig));
            crearTurnos(ctrlAsignatura.getAsignatura(asig));
            return true;
        }
        else return false;
    }

    public boolean setDuracionLabo(String asig, String duracionLabo) {
        if (ctrlAsignatura.existeAsignatura(asig)) {
            ctrlAsignatura.setDuracionLabo(asig, Integer.parseInt(duracionLabo));
            ctrlTurno.eliminarTurnos(ctrlAsignatura.getAsignatura(asig));
            crearTurnos(ctrlAsignatura.getAsignatura(asig));
            return true;
        }
        else return false;
    }

    public boolean anadirRequisito(String asig, String requisito) {
        return ctrlAsignatura.anadirRequisito(asig, requisito);
    }

    public boolean eliminarRequisito(String asig, String requisito) {
        return ctrlAsignatura.eliminarRequisito(asig, requisito);
    }

    public ArrayList<String> listarRequisitos(String asig) {
        return ctrlAsignatura.listarRequisitos(asig);
    }


    public void seleccionarAsignatura(String asig) {

        this.asignaturaSeleccionada = ctrlAsignatura.getAsignatura(asig);
    }

    public void eliminarAsignaturaSeleccionada() {
        if (asignaturaSeleccionada != null) eliminarAsignatura(asignaturaSeleccionada.getNombre());
        asignaturaSeleccionada = null;
    }

    public boolean setNombreAsignaturaSeleccionada(String nombre) {
        return (this.ctrlAsignatura.setNombre(this.asignaturaSeleccionada.getNombre(), nombre)) || this.asignaturaSeleccionada.getNombre().equals(nombre);
    }
    public void setCursoAsignaturaSeleccionada(String curso) {
        this.asignaturaSeleccionada.setCurso(curso);
    }
    public void setAlumnosAsignaturaSeleccionada(String alumnos) {
        this.asignaturaSeleccionada.setAlumnos(Integer.parseInt(alumnos));
    }


    public boolean existeAsignatura(String nombre) {
        return ctrlAsignatura.existeAsignatura(nombre);
    }

    public boolean esRequisito(String nombre) {
        return asignaturaSeleccionada.isRequisito(nombre);
    }

    public String getNombreAsignaturaSeleccionada() {
        return asignaturaSeleccionada.getNombre();
    }



    //*********************** Turnos ***********************

    /**
     * Metodo que permite obtener un turno del sistema
     * @param nombreasig: Nombre de la asignatura a la que pertenece el turno
     * @param id: Id del turno
     * @param ident: Identificador del turno
     * @return Devuelve el turno con id "id", que pertenece a la asignatura "nombreasig" y con identificador "iden"
     */
    public Turno getTurno(String nombreasig, Integer id, Integer ident) {
        Asignatura asig = ctrlAsignatura.getAsignatura(nombreasig);

        Turno turno = ctrlTurno.getTurno(String.valueOf(id),nombreasig,String.valueOf(ident));

        return turno;
    }


    public String[] consultarParametrosTurno(String id, String asig, String iden) {
        Turno turno = ctrlTurno.getTurno(id, asig, iden);
        return ctrlTurno.consultarParametros(turno);

    }

    /**
     * Metodo que permite crear los turnos de una asignatura de forma adecuada. Esto es: Se crearan tantos turnos de teoria
     * como se pueda repartiendo los alumnos matriculados en la asignatura en grupos de tantos alumnos como permita el
     * maximo de la titulacion. Estos grupos se dividen en turnos de dos horas (con un turno de una hora si las horas
     * semanales son impares) que tienen un identificador distinto entre ellos. El mismo proceso se sigue para los turnos
     * de laboratorio
     * @param asig: Asignatura a la que se le van a crear los turnos
     */
    public void crearTurnos(Asignatura asig) {
        Integer duracionTeoriaaux = asig.getDuracionTeoria();
        Integer ident = 0;
        while (duracionTeoriaaux > 0) {
            Integer alumnosaux = asig.getAlumnos();
            Integer grupos = 1;
            Integer duracion;
            if (duracionTeoriaaux % 2 == 0) duracion = 2;
            else duracion = 1;
            while (alumnosaux > 0) {
                Integer alumnosturno;
                Integer alumnosmaximo = asig.getTitulacion().getAlumnosTeoria();
                if (alumnosaux >= alumnosmaximo) alumnosturno = alumnosmaximo;
                else alumnosturno = alumnosaux;
                ctrlTurno.crearTurno((grupos * 10), "Teoria", duracion, asig, alumnosturno, ident);

                int identl = 0;

                Integer duracionLaboaux = asig.getDuracionLabo();
                while (duracionLaboaux > 0) {
                    int alumnosauxl = alumnosturno;
                    Integer gruposl = (grupos*10)+1;
                    Integer duracionl;
                    if (duracionLaboaux % 2 == 0) duracionl = 2;
                    else duracionl = 1;
                    while (alumnosauxl > 0) {
                        Integer alumnosturnol;
                        Integer alumnosmaximol = asig.getTitulacion().getAlumnosLabo();
                        if (alumnosauxl >= alumnosmaximol) alumnosturnol = alumnosmaximol;
                        else alumnosturnol = alumnosauxl;
                        ctrlTurno.crearTurno((gruposl),"Laboratorio", duracionl, asig, alumnosturnol, identl);
                        alumnosauxl -= alumnosmaximol;
                        gruposl += 1;
                    }
                    if (duracionLaboaux%2 == 0) duracionLaboaux -= 2;
                    else duracionLaboaux-=1;
                    ++identl;
                }
                alumnosaux -= alumnosmaximo;
                ++grupos;
            }
            ++ident;
            if (duracionTeoriaaux%2 == 0) duracionTeoriaaux -= 2;
            else duracionTeoriaaux-=1;
        }
    }


    /**
     * Metodo que permite listar todos los turnos de una asignatura con nombre "nombre"
     * @param nombre: Nombre de la asignatura a la que se le listan los turnos
     * @return Devuelve una ArrayList de Integers con el Id de los turnos que tiene la asignatura "nombre"
     */
    public ArrayList<Integer> listarTurnos(String nombre) {
        ArrayList<Integer> lista = ctrlAsignatura.listarTurnos(nombre);
        Collections.sort(lista);
        return lista;
    }

    /**
     * Metodo que permite obtener una lista de todos los Turnos del sistema
     * @return Devuelve una ArrayList de Turno con todos los turnos del sistema
     */
    public ArrayList<Turno> listarTodosTurnos() {
        ArrayList<Turno> lista = ctrlTurno.listarTodosTurnos();
        return lista;
    }

    public void eliminarTurnos(Asignatura asig) {
        ctrlTurno.eliminarTurnos(asig);
    }


    // **************************** Fechas **************************************


    /**
     * Metodo que permite obtener una lista de todas las Fechas del sistema
     * @return Devuelve una ArrayList de Fecha con todas las fechas del sistema
     */
    public ArrayList<Fecha> listarTodasFechas() {
        ArrayList<Fecha> lista = ctrlFecha.listarTodasFechas();
        return lista;
    }

    public Fecha getFecha(String id) {
        return ctrlFecha.getFecha(id);
    }


    // **************************** Sistema **************************************


    /**
     * Metodo que permite que todos los controladores carguen sus objetos de sus respectivos ficheros
     * @throws java.io.FileNotFoundException
     * @throws IOException
     */
    public void cargarSistema() throws DemasiadasTitulaciones, IOException, FileNotFoundException {

            this.ctrlTitulacion.cargarTitulacionesDeFichero();

            this.ctrlAsignatura.cargarAsignaturas();
            this.ctrlTurno.cargarTurnos();
            this.ctrlAula.cargarAulasDeFichero();
            this.ctrlFecha.cargarFechas();
            this.ctrlHorario.cargarHorariosDeFichero();



    }

    public void guardarSistema() throws java.io.IOException{
        ctrlAsignatura.guardarSistema();
        ctrlTurno.guardarSistema();
        ctrlAula.guardarAulas();
        ctrlTitulacion.guardarTitulacionens();
        ctrlHorario.guardarHorarios();
    }

    // **************************** Algoritmo **************************************

    public boolean execute(String nombre) throws java.io.IOException, java.io.FileNotFoundException {
        ArrayList<Turno> turnos = listarTodosTurnos();
        ArrayList<Fecha> fechas = listarTodasFechas();
        ArrayList<Aula> aulas = listarTodasAulas();
        this.algorithm = new GeneticAlgorithm(turnos, aulas, fechas);
        if (ctrlHorario.existeHorario(nombre)) return false;
        algorithm.loadParameters();
        ArrayList<SesionAsignatura> lista = algorithm.Execute();
        if (lista.isEmpty()) return false;
        Collections.sort(lista);
        Horario horario = new Horario(nombre, lista);
        ctrlHorario.anadirHorario(horario);
        return true;
    }

    public String[] getParametrosAlgoritmo() {
        return algorithm.getParametrosAlgoritmo();
    }

    public void setParametrosAlgoritmo(String MAX_G, String POP_SIZE, String AR_SIZE, String CROSS_RATE, String MUT_RATE){
        algorithm.setParametrosAlgoritmo( MAX_G,  POP_SIZE,  AR_SIZE,  CROSS_RATE,  MUT_RATE);
    }

    public void initAlgoritmo(){
        ArrayList<Turno> turnos = listarTodosTurnos();
        ArrayList<Fecha> fechas = listarTodasFechas();
        ArrayList<Aula> aulas = listarTodasAulas();
        this.algorithm = new GeneticAlgorithm(turnos, aulas, fechas);
    }
}
