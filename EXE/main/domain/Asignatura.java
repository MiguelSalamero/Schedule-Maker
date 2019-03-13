package domain;

import java.util.*;


public class Asignatura {
    private String nombre;
    private String curso;
    private Integer alumnos;
    private Map<List<String>, Turno> turnos;
    private Map<String, Asignatura> requisitos;
    private Titulacion titulacion;
    private Integer duracionTeoria;
    private Integer duracionLabo;

    /**
     * Creadora del objeto Asignatura
     * @param nom: Nombre de la asignatura
     * @param curs: Curso o nivel en el que se imparte la asignatura
     * @param alumno: Alumnos matriculados en la asignatura
     * @param titu: Titulacion a la que la asignatura pertenece
     * @param duracionTeoria: Horas semanales de teoria
     * @param duracionLabo: Horas semanales de laboratorio
     */
    public Asignatura(String nom, String curs, Integer alumno, Titulacion titu, Integer duracionTeoria, Integer duracionLabo) {

        this.nombre = nom;
        this.curso = curs;
        this.alumnos = alumno;
        this.turnos = new HashMap<List<String>, Turno>();
        this.requisitos = new HashMap<String, Asignatura>();
        this.titulacion = titu;
        this.duracionTeoria = duracionTeoria;
        this.duracionLabo = duracionLabo;
        titu.anadirAsignatura(this);

    }

    /**
     * Metodo para anadir un turno a la asignatura
     * @param turn: Turno a anadir
     */

    public void anadirTurno(Turno turn) {
        Integer ident = 0;
        while (this.turnos.containsKey(Collections.unmodifiableList(Arrays.asList(turn.getId().toString(), turn.getAsignatura().getNombre(), ident.toString())))) {
            ++ident;
        }

        this.turnos.put(Collections.unmodifiableList(Arrays.asList(turn.getId().toString(), turn.getAsignatura().getNombre(), ident.toString())), turn);
    }

    /**
     * Metodo para eliminar un turno de una asignatura
     * @param turno: Turno a eliminar
     */

    public void eliminarTurno(Turno turno) {
        this.turnos.remove(Collections.unmodifiableList(Arrays.asList(turno.getId().toString(), turno.getAsignatura().getNombre(), turno.getIdent().toString())));

    }

    /**
     * Metodo para anadir como requisito la asignatura asig y anadir la asignatura
     * como requisito de asig
     * @param asig: Asignatura a la que se le va a anadir un requisito y que va a anadir
     *              esta asignatura como requisito
     */

    public void anadirRequisitoBidireccional(Asignatura asig) {
        this.requisitos.put(asig.getNombre(), asig);
        asig.requisitos.put(this.getNombre(), this);
    }

    /**
     * Metodo para anadir como requisito la asignatura asig
     * @param asig: Asignatura que se va a anadir como requisito
     */

    public void anadirRequisito(Asignatura asig) {
        asig.requisitos.put(this.getNombre(), this);
    }

    /**
     * Metodo para eliminar esta asignatura como requisito de la asignatura
     * con nombre "nombre" y viceversa
     * @param nombre: Nombre de la asignatura que se va a eliminar como requisito
     *                y a la que se le va a eliminar esta asignatura como requisito
     */
    public void eliminarRequisitoBidireccional(String nombre) {
        Asignatura asig = this.requisitos.get(nombre);
        this.requisitos.remove(nombre);
        asig.requisitos.remove(this.getNombre());
    }

    /**
     * Metodo para comprobar si una asignatura es requisito
     * @param asig: Asignatura que vamos a comprobar si existe como requisito
     * @return Devuelve falso si asig no es un requisito y true en caso contrario
     */

    public boolean isRequisito(String asig) {
        return (this.requisitos.containsKey(asig));
    }

    /**
     * Metodo para eliminar como requisito de asig la asignatura this
     * @param asig: Asignatura a la que vamos a eliminar la asignatura this como requisito
     */
    public void eliminarRequisito(Asignatura asig) {
        asig.requisitos.remove(this.getNombre());
    }

    /**
     * Metodo para eliminar todos los requisitos y eliminar la asignatura this de la lista
     * de requisitos de todos ellos
     */

    public void eliminarTodosRequisitos() {
        ArrayList<String> lista = new ArrayList<String>();
        for (Map.Entry<String, Asignatura> entry : this.requisitos.entrySet()) {
            lista.add(entry.getKey());
        }
        for(String elemento : lista) this.eliminarRequisitoBidireccional(elemento);
    }

    /**
     * Metodo para obtener la asignatura con nombre "nombre" de la lista de requisitos
     * @param nombre: Nombre de la asignatura a obtener de la lista de requisitos
     * @return Devuelve la asignatura con nombre "nombre" de la lista de requisitos
     */

    public Asignatura getRequisito(String nombre) { return this.requisitos.get(nombre); }

    /**
     * Metodo para obtener el turno con id "id" de la lista de turnos
     * @param id: Id del turno de la lista de turnos que se obtendra
     * @return Devuelve el turno con id "id" de la lista de turnos
     */

    public Turno getTurno(Integer id, Integer ident) {
        return this.turnos.get(Collections.unmodifiableList(Arrays.asList(id.toString(), nombre, ident.toString())));
    }

    /**
     * Metodo para obtener el atributo "nombre"
     * @return Devuelve el atributo "nombre"
     */

    public String getNombre() { return this.nombre; }

    /**
     * Metodo para obtener el atributo "curso"
     * @return Devuelve el atributo "curso"
     */

    public String getCurso() { return this.curso; }

    /**
     * Metodo para obtener el atributo "alumnos"
     * @return Devuelve el atributo "alumnos"
     */

    public Integer getAlumnos() { return this.alumnos; }

    /**
     * Metodo para obtener el atributo "titulacion"
     * @return Devuelve el atributo "titulacion"
     */

    public Titulacion getTitulacion() { return this.titulacion; }

    /**
     * Metodo para obtener una ArrayList de String con el nombre de los requisitos de la lista de requisitos
     * @return Devuelve una ArrayList de String con el nombre de los requisitos de la lista de rquisitos
     */

    public ArrayList<String> getRequisitos() {
        Integer counter = 0;
        ArrayList<String> lista = new ArrayList<String>();
        for (Map.Entry<String, Asignatura> entry : this.requisitos.entrySet()) {
            lista.add(entry.getKey());
            counter = counter + 1;
        }
        return lista;
    }

    /**
     * Metodo para obtener el atributo "duracionTeoria"
     * @return Devuelve el atributo "duracionTeoria"
     */

    public Integer getDuracionTeoria() { return this.duracionTeoria; }

    /**
     * Metodo para obtener el atributo "duracionLabo"
     * @return Devuelve el atributo "duracionLabo"
     */

    public Integer getDuracionLabo() { return this.duracionLabo; }

    /**
     * Metodo para establecer un nuevo "nombre" a la asignatura
     * @param nombre: Nombre que queremos establecer como nuevo atributo "nombre"
     */

    public void setNombre(String nombre) {
        this.titulacion.eliminarAsignatura(this);
        for (Map.Entry<String, Asignatura> entry : this.requisitos.entrySet()) {
            entry.getValue().eliminarRequisito(this);
        }
        this.nombre = nombre;
        this.titulacion.anadirAsignatura(this);
        for (Map.Entry<List<String>, Turno> entry : this.turnos.entrySet()) {
            entry.getValue().setAsignatura(this);
        }
        for (Map.Entry<String, Asignatura> entry : this.requisitos.entrySet()) {
            entry.getValue().anadirRequisito(this);
        }
    }

    /**
     * Metodo para establecer un nuevo "curso" a la asignatura
     * @param curso: Curso que queremos establecer como nuevo atributo "curso"
     */

    public void setCurso(String curso) { this.curso = curso; }

    /**
     * Metodo para establecer un nuevo "alumnos" a la asignatura
     * @param alumnos: Curso que queremos establecer como nuevo atributo "alumnos"
     */

    public void setAlumnos(Integer alumnos) { this.alumnos = alumnos; }

    public void setDuracionTeoria(Integer duracionTeoria) { this.duracionTeoria = duracionTeoria; }

    public void setDuracionLabo(Integer duracionLabo) { this.duracionLabo = duracionLabo; }

    /**
     * Metodo para establecer una nueva "titulacion" a la asignatura
     * @param titu: Titulacion que queremos establecer como nuevo atributo "titulacion"
     */

    public void setTitulacion(Titulacion titu) {
        this.titulacion.eliminarAsignatura(this);
        this.titulacion = titu;
        titu.anadirAsignatura(this);
    }

    /**
     * Metodo para listar los el id de los turnos de la lista de turnos
     * @return Devuelve un ArrayList de enteros con el id de todos los turnos de la lista de turnos
     */

    public ArrayList<Integer> listarTurnos () {
        ArrayList<Integer> lista = new ArrayList<Integer>();
        for (Map.Entry<List<String>, Turno> entry : this.turnos.entrySet()) {
            lista.add(entry.getValue().getId());
        }
        return lista;
    }

    /**
     * Metodo para listar los nombres de las asignaturas de la lista de asignaturas
     * @return Devuelve un ArrayList de String con el nombre de todas las asignaturas de la lista de rquisitos
     */

    public ArrayList<String> listarRequisitos() {
        ArrayList<String> lista = new ArrayList<String>();
        for (Map.Entry<String, Asignatura> entry : this.requisitos.entrySet()) {
            lista.add(entry.getKey());
        }
        return lista;
    }


    @Override
    public String toString() {
        return this.nombre;
    }


}
