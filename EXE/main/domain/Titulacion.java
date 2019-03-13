package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Titulacion {

    private String nombre;
    private Map<String, Asignatura> asignaturas;
    private Integer alumnosPorGrupoTeoria;
    private Integer alumnosPorGrupoLaboratorio;

    public Titulacion() {}

    public Titulacion(String nombre) {
        this.nombre = nombre;
        asignaturas = new HashMap<>();
    }

    public Titulacion(String nombre, Integer alumnosPorGrupoTeoria, Integer alumnosPorGrupoLaboratorio) {
        this.nombre = nombre;
        this.alumnosPorGrupoLaboratorio = alumnosPorGrupoLaboratorio;
        this.alumnosPorGrupoTeoria = alumnosPorGrupoTeoria;
        asignaturas = new HashMap<>();
    }


    public String getNombre() {
        return nombre;
    }

    public int getNumAsignaturas() {
        return asignaturas.size();
    }

    public ArrayList<String> getAsignaturas() {
        return new ArrayList<>(asignaturas.keySet());
    }

    public Integer getAlumnosTeoria() { return alumnosPorGrupoTeoria; }

    public Integer getAlumnosLabo() { return alumnosPorGrupoLaboratorio; }


    public boolean setAlumnosTeoria (Integer alumnos) {
        this.alumnosPorGrupoTeoria = alumnos;
        return true;
    }

    public boolean setAlumnosLabo(Integer alumnos) {
        this.alumnosPorGrupoLaboratorio = alumnos;
        return true;
    }

    public boolean setNombre(String nombre) {
        this.nombre = nombre;
        return true;
    }

    public void anadirAsignatura(Asignatura asignatura) {
        this.asignaturas.put(asignatura.toString(), asignatura);
    }

    public void eliminarAsignatura(Asignatura asignatura){
        this.asignaturas.remove(asignatura.toString());
    }

    /** Métodos redefinidos **/
    @Override
    public String toString() {
        return this.nombre;
    }


}
