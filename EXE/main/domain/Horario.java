package domain;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

public class Horario {


    private String nombre;
    private HashMap<Integer, ArrayList<SesionAsignatura>> sesionesAsignatura;


    public Horario() {}

    public Horario(String nombre) {
        this.nombre = nombre;
        this.sesionesAsignatura = new HashMap<Integer, ArrayList<SesionAsignatura>>();
    }

    public Horario(String nombre, ArrayList<SesionAsignatura> lista) {
        this.sesionesAsignatura = new HashMap<Integer, ArrayList<SesionAsignatura>>();
        for (SesionAsignatura sa : lista) {
            Integer key = sa.getFecha().getId();
            ArrayList<SesionAsignatura> aux;
            if (!sesionesAsignatura.containsKey(key)) aux = new ArrayList<SesionAsignatura>();
            else aux = sesionesAsignatura.get(key);
            aux.add(sa);
            this.sesionesAsignatura.remove(key);
            this.sesionesAsignatura.put(key, aux);
        }
        this.nombre = nombre;
    }


    public String getNombre() {
        return nombre;
    }

    public ArrayList<ArrayList<String>> getSesionesPorFecha(Integer idFecha) {
        ArrayList<ArrayList<String>> l = new ArrayList<ArrayList<String>>();
        if (sesionesAsignatura.containsKey(idFecha)) {
            for (SesionAsignatura sesion : sesionesAsignatura.get(idFecha)) {
                ArrayList<String> aux = sesion.getParametros();
                l.add(aux);
            }
        }
        return l;
    }

    public ArrayList<SesionAsignatura> getSesionesAsignatura() {
        ArrayList<SesionAsignatura> l = new ArrayList<SesionAsignatura>();
        for (Map.Entry<Integer, ArrayList<SesionAsignatura>> entry : this.sesionesAsignatura.entrySet()) {
            ArrayList<SesionAsignatura> aux = entry.getValue();
            for (SesionAsignatura sa : aux) {
                l.add(sa);
            }
        }
        return l;
    }




    public Integer getSesionesSize(){

        Integer size = 0;
        for (Map.Entry<Integer, ArrayList<SesionAsignatura>> entry : this.sesionesAsignatura.entrySet()) {
            ++size;
        }
        return size;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSesionesAsignatura(ArrayList<SesionAsignatura> horario) {
        for (SesionAsignatura sa : horario) {
            anadirSesion(sa);
        }
    }

    public void anadirSesion(SesionAsignatura sa) {

        Integer key = sa.getFecha().getId();
        ArrayList<SesionAsignatura> aux;
        if (!sesionesAsignatura.containsKey(key)) aux = new ArrayList<SesionAsignatura>();
        else aux = sesionesAsignatura.get(key);
        aux.add(sa);
        //sesionesAsignatura.remove(key);
        sesionesAsignatura.put(key, aux);
    }

    public boolean swap (Integer id1, Integer id2, String aula1, String aula2, Fecha fid1, Fecha fid2) {
        ArrayList<SesionAsignatura> s1 = sesionesAsignatura.get(id1);
        ArrayList<SesionAsignatura> s2 = sesionesAsignatura.get(id2);
        SesionAsignatura s1seleccionada = null;
        SesionAsignatura s2seleccionada = null;
        for (SesionAsignatura saux : s1) {
            if (saux.getAula().getId().equals(aula1)) {
                s1seleccionada = saux;
            }
        }

        for (SesionAsignatura saux : s2) {
            if (saux.getAula().getId().equals(aula2)) s2seleccionada = saux;
        }
        for (SesionAsignatura saux : s2) {
            if (!saux.getAula().getId().equals(s2seleccionada.getAula().getId())) {
                if (saux.getTurno().colision(s1seleccionada.getTurno()) || saux.getAula().getId().equals(s1seleccionada.getAula().getId()))
                    return false;

            }
        }


        for (SesionAsignatura saux : s1) {
            if (!saux.getAula().getId().equals(s1seleccionada.getAula().getId())) {
                if (saux.getTurno().colision(s2seleccionada.getTurno()) || saux.getAula().getId().equals(s2seleccionada.getAula().getId()))
                    return false;

            }
        }


        s1.remove(s1seleccionada);
        s2.remove(s2seleccionada);
        s1seleccionada.setFecha(fid2);
        s2seleccionada.setFecha(fid1);
        s1.add(s2seleccionada);
        s2.add(s1seleccionada);
        return true;


    }



    /** Métodos redefinidos **/

    @Override
    public String toString() {
        return this.nombre;
    }


}
