package domain.domaincontrollers;

import data.CtrlDatosHorario;
import domain.Horario;
import domain.SesionAsignatura;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CtrlHorario {


    private Map<String, Horario> horarios;
    private String horarioSeleccionado;
    private CtrlDominio controladorDominio;

    public CtrlHorario(CtrlDominio controldorDominio) {
        this.controladorDominio = controldorDominio;
        inicializarCtrlHorario();
    }

    public void inicializarCtrlHorario() {
        horarios = new HashMap<String,Horario>();
        horarioSeleccionado = null;
    }




    /**
     * @pre cierto
     * @post carga todos los horarios del fichero horarios.txt
     */
    public void cargarHorariosDeFichero() throws FileNotFoundException, IOException {
         boolean primerHorario = true;
        CtrlDatosHorario ctrlDatosHorario = CtrlDatosHorario.getInstance();
        ArrayList<String[]> datos = ctrlDatosHorario.getAll();

        ArrayList<SesionAsignatura> sesionesHorario = new ArrayList<>();
        Horario horario = new Horario();

        for (String[] info : datos) {
            if ("Horario".equals(info[0])) {

                if (!primerHorario) {
                    horario.setSesionesAsignatura(sesionesHorario);
                    horarios.put(horario.toString(),horario);
                }
                crearHorario(info[1]);
                horario = horarios.get(info[1]);
                primerHorario = false;
                sesionesHorario = new ArrayList<>();
            }
            else if ("end".equals(info[0])) {
                horario.setSesionesAsignatura(sesionesHorario);
                horarios.put(horario.toString(),horario);
            }
            else {
                SesionAsignatura sesion = new SesionAsignatura(controladorDominio.getTurno(info[0],Integer.parseInt(info[1]),Integer.parseInt(info[2])),controladorDominio.getFecha(info[3]),controladorDominio.getAula(info[4]),Boolean.parseBoolean(info[5]));
                sesionesHorario.add(sesion);
            }
        }
    }


    /**
     * @pre cierto
     * @post guarda todos los horarios del sistema en el fichero horarios.txt
    */
    public void guardarHorarios() throws IOException {
        ArrayList<String[]> datosHorario = new ArrayList<>();
        for (Map.Entry<String, Horario> horarioEntry : this.horarios.entrySet()) {
           Horario horario = horarioEntry.getValue();
           String[] firstLine = new String[2];
           firstLine[0] = "Horario";
           firstLine[1] = horario.getNombre();
           datosHorario.add(firstLine);

           for (SesionAsignatura sesionAsignatura : horario.getSesionesAsignatura()) {
               String[] sesion = new String[6];
               sesion[0] = sesionAsignatura.getTurno().getAsignatura().toString();
               sesion[1] = String.valueOf(sesionAsignatura.getTurno().getId());
               sesion[2] = String.valueOf(sesionAsignatura.getTurno().getIdent());
               sesion[3] = sesionAsignatura.getFecha().toString();
               sesion[4] = sesionAsignatura.getAula().toString();
               sesion[5] = Boolean.toString(sesionAsignatura.getRestriccionUsuario());
               datosHorario.add(sesion);
           }
        }

        CtrlDatosHorario ctrlDatosHorario = CtrlDatosHorario.getInstance();
        ctrlDatosHorario.saveHorarios(datosHorario);
    }


    /**
     * @pre cierto
     * @post lista el nombre de todos los horarios en el sitema  */
    public ArrayList<String> listarHorarios () {
        return new ArrayList<>(horarios.keySet());
    }

    /**
     * @pre cierto
     * @post si no existía, crea el horario y devuelve cierto  */
    public boolean crearHorario(String nombre) {
        Horario horario;
        if (!existeHorario(nombre)) {
            horario = horarios.put(nombre, new Horario(nombre));
            return true;
        }
        else return false;
    }

    /**
     * @pre cierto
     * @post si existe, elimina el horario y devuelve cierto  */
    public boolean eliminarHorario(String id) {
        if (existeHorario(id)) {
            horarios.remove(id);
            return true;
        }
        return false;
    }

    /**
     * @pre cierto
     * @post devuelve cierto si existe el horario identificado con nombre  */
    public boolean existeHorario(String nombre) {
        return horarios.containsKey(nombre);
    }

    /**
     * @pre existe el horario identificado con nombre
     * @post devuelve la instancia horario identificada con nombre  */
    public Horario getHorario(String nombre) {
        return horarios.get(nombre);
    }

    /**
     * @pre existe el hoario
     * @post devuelve un array de las sesiones del horario *//*
    public ArrayList<String[]> getSesionesHorario(String nombre) {
        ArrayList<String[]> Lista = new ArrayList<String[]>();
        ArrayList<SesionAsignatura> sesiones= new ArrayList<SesionAsignatura>();
        if (existeHorario(nombre)) {
            for (Sesion)
            Lista = horarios.get(nombre).getSesionesAsignatura();
        }
        return Lista;
    }
*/
    /**
     * @pre existe el horario identificado con nombre
     * @post rellena el horario con el set de sesiones  */
    public void setSesionesHorario(String nombre, ArrayList<SesionAsignatura> sesiones) {
        horarios.get(nombre).setSesionesAsignatura(sesiones);
    }

    /**
     * @pre existe el horario identificado con oldNombre
     * @post devuelve cierto si existe el horario identificado con nombre  */
    public void modificarNombre(String oldNombre, String newNombre) {
        Horario horario = horarios.remove(oldNombre);
        horario.setNombre(newNombre);
        horarios.put(newNombre,horario);

    }

    /**
     * @pre cierto
     * @post se devuelve el identificador del horario. Null si no había ninguna seleccionada.
     */
    public String getHorarioSeleccionado() {
        return horarioSeleccionado;
    }

    /**
     * @pre cierto
     * @post se modifica el horario seleccionada.
     */
    public void setHorarioSeleccionado(String nombre) {
        this.horarioSeleccionado = nombre;
    }

    public void anadirHorario(Horario horario) {
        this.horarios.put(horario.getNombre(), horario);
    }

    public String[] listarnomHorarios() {
        String[] nomhoraris = new String[horarios.size()];
        ArrayList<String> lista = new ArrayList<>(horarios.keySet());
        Collections.sort(lista);
        nomhoraris = lista.toArray(nomhoraris);
        return nomhoraris;
    }

    public void eliminarTodosHorarios() {
        horarioSeleccionado = null;
        horarios = new HashMap<String, Horario>();
    }


}
