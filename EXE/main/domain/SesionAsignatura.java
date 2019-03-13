package domain;

import java.util.ArrayList;

public class SesionAsignatura implements Comparable<SesionAsignatura> {
    private Turno turno;
    private Fecha fecha;
    private Aula aula;
    private boolean restriccionUsuario;

    /**
     * Creadora de SesionAsignatura
     * @param turno: Turno de la sesion
     * @param fecha: Fecha de la sesion
     * @param aula: Aula de la sesion
     * @param restriccionUsuario: Cierto si se trata de una sesion fijada por el usuario, falso si se trata de una
     *                          sesion generada por el algoritmo del sistema.
     */
    public SesionAsignatura(Turno turno, Fecha fecha, Aula aula, boolean restriccionUsuario) {
        this.turno = turno;
        this.fecha = fecha;
        this.aula = aula;
        this.restriccionUsuario = restriccionUsuario;
    }

    public ArrayList<String> getParametros() {
        ArrayList<String> l = new ArrayList<String>();
        l.add(this.fecha.getId().toString());
        l.add(this.aula.getId().toString());
        l.add(this.turno.getId().toString());
        l.add(this.turno.getAsignatura().getNombre());
        l.add(this.turno.getAsignatura().getTitulacion().getNombre());
        return l;
    }

    /**
     * Metodo que permite obtener el turno de la sesion
     * @return Devuelve el turno de la sesion
     */
    public Turno getTurno() { return this.turno; }

    /**
     * Metodo que permite obtener la fecha de la sesion
     * @return Devuelve la fecha de la sesion
     */
    public Fecha getFecha() { return this.fecha; }

    /**
     * Metodo que permite obtener el aula de la sesion
     * @return: Devuelve el aula de la sesion
     */
    public Aula getAula() { return this.aula; }

    /**
     * Metodo que permite obtener si la sesion esta fijada por el usuario en el horario o no
     * @return Devuelve cierto si la sesion esta fijada por el usuario en el horario, falso de lo contrario
     */
    public boolean getRestriccionUsuario() { return this.restriccionUsuario; }


    /**
     * Metodo que permite modificar el turno de la sesion por "turno"
     * @param turno: Turno que se va a establecer
     */
    public void setTurno(Turno turno) { this.turno = turno; }

    /**
     * Metodo que permite modificar la fecha de la sesion por "fecha"
     * @param fecha: Fecha que se va a establecer
     */
    public void setFecha(Fecha fecha) { this.fecha = fecha; }

    /**
     * Metodo que permite modificar el aula de la sesion por "aula"
     * @param aula: Aula que se va a establecer
     */
    public void setAula(Aula aula) { this.aula = aula; }

    /**
     * Metodo que permite modificar si la sesion ha sido fijada por el usuario en el horario o no
     * @param restriccionUsuario: Cierto si esta fijada por el usuario, falso de lo contrario
     */
    public void setRestriccionUsuario(boolean restriccionUsuario) { this.restriccionUsuario = restriccionUsuario; }

    @Override

    public int compareTo(SesionAsignatura sesion) {
        int compareFecha = ((SesionAsignatura)sesion).getFecha().getId();
        return this.getFecha().getId()-compareFecha;
    }

}
