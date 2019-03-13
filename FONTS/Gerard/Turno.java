package domain;

public class Turno {
    private Integer id;
    private String tipo_turno;
    private Integer duracion;
    private Asignatura asignatura;
    private Integer alumnos;
    private Integer ident;

    /**
     * Creadora del objeto Turno
     * @param id Id del turno, equivalente al numero del grupo
     * @param tipo_turno Tipo del turno, puede ser "teoria" o "laboratorio"
     * @param duracion Duracion del turno
     * @param asig Asignatura a la que pertenece el turno
     * @param alumnos Alumnos que participan en el turno
     * @param ident: Numero identificador unico para los turnos con el mismo id y asig
     *
     */
    public Turno(Integer id, String tipo_turno, Integer duracion, Asignatura asig, Integer alumnos, Integer ident) {
        this.id = id;
        this.tipo_turno = tipo_turno;
        this.duracion = duracion;
        this.asignatura = asig;
        this.alumnos = alumnos;
        this.ident = ident;
        asig.anadirTurno(this);
    }

    /**
     * Metodo que permite obtener el Id del turno
     * @return Devuelve el Id del turno
     */
    public Integer getId() { return this.id; }

    /**
     * Metodo que permite obtener el tipo del turno
     * @return Devuelve el tipo del turno
     */
    public String getTipo_turno() { return this.tipo_turno; }

    /**
     * Metodo que permite otener la duracion del turno
     * @return Devuelve la duracion del turno
     */
    public Integer getDuracion() { return this.duracion; }

    /**
     * Metodo que permite obtener la asignatura a la que pertenece el turno
     * @return Devuelve la asignatura a la que pertenece el turno
     */
    public Asignatura getAsignatura() { return this.asignatura; }

    /**
     * Metodo que permite obtener el identificador del turno
     * @return Devuelve el identificador del turno
     */
    public Integer getIdent() { return this.ident; }

    /**
     * Metodo que permite saber si hay colision entre el turno "this" y el turno "turno", es decir, si son de la misma
     * titulacion y, o bien son requisitos el uno del otro, o bien son del mismo curso y grupo. En caso de turnos de
     * laboratorio, se considera que el grupo es id/10
     * @param turno: Turno con el que se compara "this"
     * @return Devuelve cierto si hay colision, y falso de lo contrario
     */
    public boolean colision(Turno turno) {
        if (turno.getAsignatura().getTitulacion() == this.getAsignatura().getTitulacion()) {
            //misma titulacion
            if ( ( (this.getAsignatura().isRequisito(turno.getAsignatura().getNombre())) || (this.getAsignatura().getCurso().equals(turno.getAsignatura().getCurso()))) && (this.id/10 == turno.id/10)) return true;
            //(mismo curso o requisito) y mismo grupo
            else return false;
        }
        else return false;

    }

    /**
     * Metodo que permite obtener los alumnos que participan en el turno
     * @return Devuelve los alumnos que participan en el turno
     */
    public Integer getAlumnos() { return this.alumnos; }


    /**
     * Metodo que permite cambiar la asignatura a la que pertenece el turno
     * @param asig: Asignatura nueva a la que pertenece el turno
     */
    public void setAsignatura(Asignatura asig) {
        this.asignatura.eliminarTurno(this);
        this.asignatura = asig;
    }

    @Override
    public String toString() {
        return this.id.toString();
    }

}
