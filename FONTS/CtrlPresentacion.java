package presentation;

import domain.domaincontrollers.CtrlDominio;
import domain.restrictions.DemasiadasAsignaturas;
import domain.restrictions.DemasiadasTitulaciones;

import java.util.ArrayList;


public class CtrlPresentacion {

    private CtrlDominio ctrlDominio;

    private VistaMenu vistaMenu = null;
    private static vistaAula vistaaula;
    private vistaAulaSecundaria vistaaulasecundaria;
    private VistaAnyadirRequisito vistaAnyadirRequisito = null;
    private VistaAsignaturas vistaAsignaturas = null;
    private VistaGestionAsignatura vistaGestionAsignatura = null;
    private vistaMenuHorario vistamenuhorario = null;
    private vistaHorario vistahorario = null;
    private vistaGenerarHorario vistagenerarhorario = null;

    private vistaTitulacion vistatitulacion;
    private vistaTitulacionSecundaria vistatitulacionsecundaria;

    public CtrlPresentacion()  {
        inicializar_CtrlPresentacion();
        inicializar_Presentacion();
    }

    private void inicializar_CtrlPresentacion() {
        ctrlDominio = new CtrlDominio();

        try {
            ctrlDominio.cargarSistema();
        }
        catch (java.io.FileNotFoundException fileExcept) {
            System.out.println("Error en el nombre de los ficheros");
            System.exit(1);
        }
        catch (java.io.IOException ioExcept) {
            System.out.println("Error de IO");
            System.exit(1);
        }
        catch (DemasiadasTitulaciones demtitu) {
            System.out.println("Demasiadas titulaciones");
            System.exit(1);
        }
    }

    private void inicializar_Presentacion() {

        if (vistaMenu == null)  // innecesario
            vistaMenu = new VistaMenu(this);
        vistaMenu.hacerVisible();


    }



    //////////////////////// Metodos de sincronizacion entre vistas


    // vistas Menú principal

    public void sincronizacionVistaMenu_a_Aulas() {
        vistaMenu.desactivar();
        vistaMenu.hacerInvisible();

        vistaaula = new vistaAula(this);
        vistaaula.hacerVisible();
    }

    public void sincronizacionVistaMenu_a_Titulaciones() {
        vistaMenu.desactivar();
        vistaMenu.hacerInvisible();

        vistatitulacion = new vistaTitulacion(this);
        vistatitulacion.hacerVisible();
    }

    public void sincronizacionVistaMenu_a_Horarios() {
        vistaMenu.desactivar();
        vistaMenu.hacerInvisible();

        vistamenuhorario = new vistaMenuHorario(this);
        vistamenuhorario.hacerVisible();
    }

    // vistas Aulas

    public void sincronizacionVistaPrincipalAula_a_Secundaria(vistaAulaSecundaria.TipoVista tipoVista) {
       vistaaula.desactivar();
        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        //if (vistaaulasecundaria == null)
            vistaaulasecundaria = new vistaAulaSecundaria(this,vistaaula, tipoVista);
        vistaaulasecundaria.setTipoVista(tipoVista);
        vistaaulasecundaria.hacerVisible();
    }


    public void sincronizacionVistaSecundariaAula_a_Principal() {
        // Se hace invisible la vista secundaria (podria anularse)
        vistaaulasecundaria.hacerInvisible();
        vistaaulasecundaria.desactivar();
        vistaaula.actualizar_ListaAulas();
        vistaaula.activar();
    }

    public void sincronizacionVistaAula_a_Menu() {
        vistaaula.desactivar();
        vistaaula.hacerInvisible();

        vistaMenu.hacerVisible();
        vistaMenu.activar();
    }

    // vistas Titulaciones


    public void sincronizacionVistaPrincipalTitulacion_a_Secundaria(vistaTitulacionSecundaria.TipoVista tipoVista) {
        vistatitulacion.desactivar();
        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        //if (vistaaulasecundaria == null)
        vistatitulacionsecundaria = new vistaTitulacionSecundaria(this,vistatitulacion, tipoVista);
        vistatitulacionsecundaria.setTipoVista(tipoVista);
        vistatitulacionsecundaria.hacerVisible();
    }


    public void sincronizacionVistaSecundariaTitulacion_a_Principal() {
        // Se hace invisible la vista secundaria (podria anularse)
        vistatitulacionsecundaria.hacerInvisible();
        vistatitulacion.actualizar_ListaTitulacinoes();
        vistatitulacion.activar();
    }

    public void sincronizacionVistaTitulaciones_a_Menu() {
        vistatitulacion.desactivar();
        vistatitulacion.hacerInvisible();

        vistaMenu.hacerVisible();
        vistaMenu.activar();
    }

    public void sincronizacionVistaTitulaciones_a_Asignatura() {
        vistatitulacion.desactivar();
        vistatitulacion.hacerInvisible();

        vistaAsignaturas = new VistaAsignaturas(this);
        vistaAsignaturas.activar();
        vistaAsignaturas.hacerVisible();

    }


    // vistas Asignaturas

    public void sincronizacionVistaAsignatura_a_Titulaciones() {
        vistaAsignaturas.desactivar();
        vistaAsignaturas.hacerInvisible();

        vistatitulacion.hacerVisible();
        vistatitulacion.activar();
    }

    public void vistaCrearAsignatura() {
        vistaAsignaturas.desactivar();
        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        vistaGestionAsignatura = new VistaGestionAsignatura(this, 0);
        vistaGestionAsignatura.hacerVisible();
    }

    public void vistaModificarAsignatura() {
        vistaAsignaturas.desactivar();
        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        vistaGestionAsignatura = new VistaGestionAsignatura(this, 1);
        vistaGestionAsignatura.hacerVisible();
    }

    public void asignaturasARequisitos() {
        vistaAsignaturas.desactivar();
        vistaAnyadirRequisito = new VistaAnyadirRequisito(this);
        vistaAnyadirRequisito.activar();
        vistaAnyadirRequisito.hacerVisible();
    }

    public void requisitosAAsignaturas() {
        vistaAnyadirRequisito.hacerInvisible();
        vistaAsignaturas.activar();
        vistaAsignaturas.hacerVisible();

    }

    public void gestionAAsignaturas() {
        // Se hace invisible la vista secundaria (podria anularse)
        vistaGestionAsignatura.hacerInvisible();
        vistaAsignaturas.inicializarLista();
        vistaAsignaturas.activar();
        vistaAsignaturas.hacerVisible();
    }

    //vistas de horario

    public void sincronizacionVistaMenuHorario_a_Menu() {
        vistamenuhorario.desactivar();
        vistamenuhorario.hacerInvisible();

        vistaMenu.hacerVisible();
        vistaMenu.activar();
    }

    public void sincronizacionVistaMenuHorario_a_Horario() {
        vistamenuhorario.desactivar();
        vistamenuhorario.hacerInvisible();
        vistahorario = new vistaHorario(this);
        vistahorario.hacerVisible();
        vistahorario.activar();
    }
    public void sincronizacionVistaMenuHorario_a_GenerarHorario() {
        vistamenuhorario.desactivar();
        vistagenerarhorario = new vistaGenerarHorario(this, vistamenuhorario);
        vistagenerarhorario.hacerVisible();
    }

    public void sincronizacionVistaHorario_a_MenuHorario() {
        vistahorario.desactivar();
        vistahorario.hacerInvisible();

        vistamenuhorario.hacerVisible();
        vistamenuhorario.activar();
    }

    public void sincronizacionVistaGenerarHorario_a_MenuHorario() {
        vistagenerarhorario.hacerInvisible();

        vistamenuhorario.actualizar_ListaHorarios();
        vistamenuhorario.activar();
        vistamenuhorario.hacerVisible();
    }




    //////////// LLamadas al controlador de dominio

    //**************** SISTEMA *************************

    public void guardarSistema() {
        try {
            ctrlDominio.guardarSistema();
        }
        catch (java.io.IOException ioExcept) {
            System.out.println("Error de IO");
            System.exit(1);
        }

    }



    //**************** AULAS ************************

    public String[] listarAulasNombre() {
        return ctrlDominio.listarAulasNombre();
    }

    public void guardarAulas() throws java.io.IOException{
        ctrlDominio.guardarAulas();
    }



    public void eliminarAula(String aula) {
        ctrlDominio.eliminarAula(aula);
    }

    public String[] getParametrosAula(String aula) {
        return ctrlDominio.consultarParametrosAula(aula);
    }

    public void setAulaSeleccionada(String aulaSeleccionada) {
        ctrlDominio.setAulaSeleccionada(aulaSeleccionada);
    }

    public String getAulaSeleccionada() {
        return ctrlDominio.getAulaSeleccionada();
    }


    public void setCapacidadAulaSeleccionada(String newCapacidad) {
        ctrlDominio.setCapacidadAula(getAulaSeleccionada(), newCapacidad);
    }

    public void setConPCAulaSeleccionada(String newConPC) {
        ctrlDominio.setConPCAula(getAulaSeleccionada(), newConPC);
    }



    //**************** TITULACION ************************

    public String[] listarTitulacionesNombre() {
        return ctrlDominio.listarTitulacionesNombre();
    }

    public void guardarTitulaciones() throws java.io.IOException{
        ctrlDominio.guardarTitulaciones();
    }

    public void eliminarTitulacion(String titulacion) {
        ctrlDominio.eliminarTitulacion(titulacion);
    }

    public String[] getParametrosTitulacion(String titulacion) {
        return ctrlDominio.consultarParametrosTitulacion(titulacion);
    }

    public void setTitulacionSeleccionada(String titulacionSeleccionada) {
        ctrlDominio.setTitulacionSeleccionada(titulacionSeleccionada);
    }

    public String getTitulacionSeleccionada() {
        return ctrlDominio.getTitulacionSeleccionada();
    }


    public void setAlumnosTeoriaSeleccionada(String newAlumnosTeoria) {
        ctrlDominio.setAlumnosTeoria(getTitulacionSeleccionada(), newAlumnosTeoria);
    }

    public void setAlumnosLaboratorioSeleccionada(String newAlumnosLaboratorio) {
        ctrlDominio.setAlumnosLaboratorio(getTitulacionSeleccionada(), newAlumnosLaboratorio);
    }

    public boolean crearTitulacion(String nombre, String alumnosGrupoTeoria, String alumnosGrupoLaboratorio) throws DemasiadasTitulaciones {
        return ctrlDominio.crearTitulacion(nombre, alumnosGrupoTeoria, alumnosGrupoLaboratorio);
    }

    public boolean setNombreTitulacionSeleccionada(String newNombreTitulacion) {
        if (getTitulacionSeleccionada().equals(newNombreTitulacion)) {
            return true;
        }
        else if (!ctrlDominio.setNombreTitulacion(getTitulacionSeleccionada(), newNombreTitulacion)) return false;
        // Cuando cambias el nombre de la titulación seleccionada hay que cambiar tambíén la titulación seleccionada
        ctrlDominio.setTitulacionSeleccionada(newNombreTitulacion);
        return true;
    }


    //**************** HORARIOS ************************

    public void guardarHorarios() throws java.io.IOException{
        ctrlDominio.guardarHorarios();
    }

    public void eliminarHorario(String nombreHorario) {
        ctrlDominio.eliminarHorario(nombreHorario);
    }

    public void eliminarTodosHorarios() {
        ctrlDominio.eliminarTodosHorarios();
    }

    //**************** ASIGNATURAS ************************

    public ArrayList<String> listarAsignaturas () {
        return ctrlDominio.listarAsignaturas(getTitulacionSeleccionada());
    }

    public String[] consultarParametrosAsignatura(String asig) {
        return ctrlDominio.consultarParametrosAsignatura(asig);
    }

    public void setAsignaturaSeleccionada(String asig) {
        ctrlDominio.seleccionarAsignatura(asig);
    }

    public void eliminarAsignatura() {
        ctrlDominio.eliminarAsignaturaSeleccionada();
    }

    public boolean setNombreAsignaturaSeleccionada(String nombre) {
        return ctrlDominio.setNombreAsignaturaSeleccionada(nombre);
    }
    public void setCursoAsignaturaSeleccionada(String curso) {
        ctrlDominio.setCursoAsignaturaSeleccionada(curso);
    }
    public void setAlumnosAsignaturaSeleccionada(String alumnos) {
        ctrlDominio.setAlumnosAsignaturaSeleccionada(alumnos);
    }
    public void setMaxTeoriaAsignaturaSeleccionada(String maxTeoria) {
        ctrlDominio.setDuracionTeoria(ctrlDominio.getNombreAsignaturaSeleccionada(), maxTeoria);
    }
    public void setMaxLaboAsignaturaSeleccionada(String maxLabo) {
        ctrlDominio.setDuracionLabo(ctrlDominio.getNombreAsignaturaSeleccionada(), maxLabo);
    }

    public boolean crearAsignatura(String nombre, String curso, String alumnos, String titu, String duracionTeoria, String duracionLabo) {
        boolean b = false;
        try {
            b = ctrlDominio.crearAsignatura(nombre, curso, alumnos, titu, duracionTeoria, duracionLabo);
        }
        catch (DemasiadasAsignaturas e) {
            System.out.println("Demasiadas asignaturas");
        }
        finally {
            return b;
        }

    }

    public String getTitulacionAsignatura(String nombre) {
        return ctrlDominio.getTitulacionAsignatura(nombre);
    }

    public boolean existeAsignatura(String nombre) {
        return ctrlDominio.existeAsignatura(nombre);
    }

    public boolean esRequisito(String nombre) {
        return ctrlDominio.esRequisito(nombre);
    }

    public void anyadirRequisito(String nombre) {
        ctrlDominio.anadirRequisito(ctrlDominio.getNombreAsignaturaSeleccionada(), nombre);
    }

    public ArrayList<String> listarRequisitos() {
        return ctrlDominio.listarRequisitos(ctrlDominio.getNombreAsignaturaSeleccionada());
    }

    public void eliminarRequisito(String nombre) {
        ctrlDominio.eliminarRequisito(ctrlDominio.getNombreAsignaturaSeleccionada(), nombre);
    }

    public String getNombreAsignaturaSeleccionada() {
        return ctrlDominio.getNombreAsignaturaSeleccionada();
    }

    //**************** ALGORITMO ************************


    public boolean ejecutar(String nombrehorario) {
        boolean b = false;
        try {
            b = ctrlDominio.execute(nombrehorario);
        }
        catch (java.io.FileNotFoundException fileExcept) {
            System.out.println("Error en el nombre de los ficheros");
            System.exit(1);
        }
        catch (java.io.IOException ioExcept) {
            System.out.println("Error de IO");
            System.exit(1);
        }
        finally{
            return b;
        }
    }

    public ArrayList<ArrayList<String>> getSesionesPorFecha(int fecha) {
        return ctrlDominio.getSesionesPorFecha(fecha);
    }

    public boolean swap(Integer id1, Integer id2, String aula1, String aula2) {
        return ctrlDominio.swap(id1, id2, aula1, aula2);
    }
    public String[] getParametrosAlgoritmo() {
        return ctrlDominio.getParametrosAlgoritmo();
    }

    public void setParametrosAlgoritmo(String MAX_G, String POP_SIZE, String AR_SIZE, String CROSS_RATE, String MUT_RATE){
        ctrlDominio.setParametrosAlgoritmo( MAX_G,  POP_SIZE,  AR_SIZE,  CROSS_RATE,  MUT_RATE);
    }

    public void inicializarAlgoritmo(){
        ctrlDominio.initAlgoritmo();
    }


    //**************** FECHAS *************************


    public ArrayList<Integer> listarIdFechas(){
        return ctrlDominio.listarIdFechas();
    }


    //**************** HORARIO *************************

    public String getHorarioSeleccionadado() {
        return ctrlDominio.getHorarioSeleccionado();
    }

    public void setHorarioSeleccionado(String nombreHorario) {
        ctrlDominio.setHorarioSeleccionado(nombreHorario);
    }

    public String[] listarnomHorarios() {
        return ctrlDominio.listarnomHorarios();
    }

    public boolean crearAula(String nombre, String capacidad, String conPc) {
        return ctrlDominio.crearAula(nombre,capacidad,conPc);
    }

    //**************** AULA *************************

    public boolean setNombreAulaSeleccionada(String newNombreAula) {
        if (getAulaSeleccionada().equals(newNombreAula)) {
            return true;
        }
        else if (!ctrlDominio.setNombreAula(getAulaSeleccionada(), newNombreAula)) return false;
        // Cuando cambias el nombre de la aula seleccionada hay que cambiar tambíén la aula seleccionada
        ctrlDominio.setAulaSeleccionada(newNombreAula);
        return true;
    }
}

