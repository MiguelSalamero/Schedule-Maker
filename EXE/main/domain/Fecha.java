package domain;

public class Fecha {
    private Integer id;
    private Integer dia;
    private Integer hora;

    /**
     * Creadora por defecto del objeto fecha
     * @param id: Identificador de la fecha, es unico para todas las instancias
     * @param dia: Dia de la semana, donde 1 = lunes, 2 = martes, etc
     * @param hora: Hora del dia
     */
    public Fecha(Integer id, Integer dia, Integer hora) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
    }

    /**
     * Metodo que permite obtener el identificador de la fecha
     * @return Devuelve el identificador de la fecha
     */
    public Integer getId() { return this.id; }

    /**
     * Metodo que permite obtener el dia de la fecha
     * @return Devuelve el dia de la fecha
     */
    public Integer getDia() { return this.dia; }

    /**
     * Metodo que permite obtener la hora de la fecha
     * @return Devuelve la hora de la feccha
     */
    public Integer getHora() { return this.hora; }


    @Override
    public String toString() { return this.id.toString(); }

}
