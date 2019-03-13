package domain;

public class Aula {

    private String id_aula;
    private int maxCapacity_aula;
    private boolean conPC;


    public Aula(String id_aula, int maxCapacity, boolean conPC) {
        this.id_aula = id_aula;
        this.maxCapacity_aula = maxCapacity;
        this.conPC = conPC;
    }

    public Aula(String id_aula) {
        this.id_aula = id_aula;
    }

    public Aula(String id_aula, int maxCapacity) {
        this.id_aula = id_aula;
        this.maxCapacity_aula = maxCapacity;
    }


    /**
     * @pre cierto
     * @post devuelve el identificador del aula.
     */
    public String getId() {
        return id_aula;
    }

    /**
     * @pre cierto
     * @post devuelve la capacidad máxima del aula.
     */
    public int getMaxCapacity() {
        return maxCapacity_aula;
    }

    /**
     * @pre cierto
     * @post devuelve cierto si la aula tiene PCs.
     */
    public boolean getAulaConPC() {return conPC; }

    /**
     * @pre cierto
     * @post modifica el id del aula. Devuelve cierto si no ha habido ningún error.
     */
    public boolean setId(String id_aula) {
        this.id_aula = id_aula;
        return true;
    }

    /**
     * @pre el entero pasado por parámetro tiene que ser mayor que 0.
     * @post modifica la capacidad máxima del aula. Devuelve cierto si no ha habido ningún error.
     */
    public boolean setMaxCapacity(int maxCapacity) {
        if (maxCapacity>=1) {
            this.maxCapacity_aula = maxCapacity;
            return true;
        }
        else return false;
    }

    /**
     * @pre cierto
     * @post modifica el valor de conPC. Devuelve cierto si no ha habido ningún error.
     */
    public boolean setAulaConPC(boolean conPC) {
        this.conPC = conPC;
        return true;
    }

    /** Métodos redefinidos **/
    @Override
    public String toString() {
        return id_aula;
    }

}
