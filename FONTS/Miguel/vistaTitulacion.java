package presentation;

        import javax.swing.*;
        import javax.swing.event.ListSelectionEvent;
        import javax.swing.event.ListSelectionListener;
        import javax.swing.filechooser.FileNameExtensionFilter;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

public class vistaTitulacion extends JFrame {

    private CtrlPresentacion ctrlPresentacion;


    private JPanel PanelRoot = new JPanel();
    private JPanel panelContenido = new JPanel();
    private JPanel panelLista = new JPanel();
    private JPanel panelDatosTitulacion = new JPanel();
    private JPanel panelBotones = new JPanel();

    private JButton buttonAgregar = new JButton("Crear titulación");
    private JButton buttonVolver = new JButton("Volver");
    private JButton buttonEliminar = new JButton("Eliminar titulación");
    private JButton buttonModificar = new JButton("Modificar titulación");
    private JButton buttonConsultarAsignaturas = new JButton("Consultar asignaturas");
    private JButton buttonHelp = new JButton("?");

    private JLabel nombreTitulacion = new JLabel("Nombre de la titulación: ");
    private JLabel alumnosGrupoTeoria = new JLabel("Alumnos por grupo de teoría: ");
    private JLabel alumnosGrupoLab = new JLabel("Alumnos por grupo de laboratorio: ");

    private JList<String> myLista;


    // Menus
    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("Opciones");
    private JMenuItem menuitemGuardar = new JMenuItem("Guardar");



    //////////// Constructor y metodos publicos


    public vistaTitulacion(CtrlPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        inicializarComponentes();
    }

    public void hacerVisible() {
        // check que hace pack()
        //pack();
        setVisible(true);
    }

    public void hacerInvisible() {
        setVisible(false);
    }

    public void activar() {
        hacerVisible();
        setEnabled(true);
    }

    public void desactivar() {
        setEnabled(false);
    }


    public void actualizar_ListaTitulacinoes() {
        panelLista.removeAll();
        inicializar_PanelLista();
        //asignar_listenersComponetnes();

        // Listener para la lista

        myLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                actionPerformed_seleccionarTitulacion(e);
            }
        });

    }


    //////////// Metodos de las interficies Listener

    public void actionPerformed_buttonAgregarTitulacion(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaPrincipalTitulacion_a_Secundaria(vistaTitulacionSecundaria.TipoVista.ALTA);
    }


    public void actionPerformed_buttonEliminarTitulacion(ActionEvent event) {
        // crear dialog: ¿Está seguro que desea eliminar la aula seleccionada?
        String titulacion = myLista.getSelectedValue();
        if (titulacion != null) {
            ctrlPresentacion.eliminarTitulacion(titulacion);
            actualizar_ListaTitulacinoes();
            hacerVisible();
            activar();
            ctrlPresentacion.setTitulacionSeleccionada(null);
        }
    }

    public void actionPerformed_buttonModificarTitulacion(ActionEvent event) {
        if (ctrlPresentacion.getTitulacionSeleccionada() != null) ctrlPresentacion.sincronizacionVistaPrincipalTitulacion_a_Secundaria(vistaTitulacionSecundaria.TipoVista.MODIFICAR);
    }

    public void actionPerformed_seleccionarTitulacion(ListSelectionEvent event) {
        String titulacionSeleccionada = myLista.getSelectedValue();
        String[] parametrosTitulacion = ctrlPresentacion.getParametrosTitulacion(titulacionSeleccionada);

        nombreTitulacion.setText("Nombre de la titulación: " + parametrosTitulacion[0]);
        // numero asignaturas
        alumnosGrupoTeoria.setText("Alumnos por grupo de teoría: " + parametrosTitulacion[2]);
        alumnosGrupoLab.setText("Alumnos por grupo de laboratorio: "+ parametrosTitulacion[3]);
        ctrlPresentacion.setTitulacionSeleccionada(parametrosTitulacion[0]);
        buttonConsultarAsignaturas.setEnabled(true);
        buttonModificar.setEnabled(true);
        buttonEliminar.setEnabled(true);
    }


    public void actionPerformed_guardarTitulaciones(ActionEvent event) {
        ctrlPresentacion.guardarSistema();
    }

    public void actionPerformed_buttonVolver(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaTitulaciones_a_Menu();
    }

    public void actionPerformed_buttonConsultarAsignaturas(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaTitulaciones_a_Asignatura();
    }


    //////////// Asignacion de listeners


    // Listeners para los botones

    private void asignar_listenersComponetnes() {

        buttonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String aulaSeleccionada = myLista.getSelectedValue();
                actionPerformed_buttonAgregarTitulacion(e);

            }
        });


        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String aulaSeleccionada = myLista.getSelectedValue();
                actionPerformed_buttonEliminarTitulacion(e);

            }
        });

        buttonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String aulaSeleccionada = myLista.getSelectedValue();
                actionPerformed_buttonModificarTitulacion(e);

            }
        });

        buttonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_buttonVolver(e);
            }
        });

        buttonConsultarAsignaturas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_buttonConsultarAsignaturas(e);
            }
        });

        buttonHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Desde este menú puedes:\n" +
                        "Dar de alta una nueva titulación vacía (sin repetir nombres)\n" +
                        "Modificar una titulación existente\n" +
                        "Eliminar una titulación existente (junto con sus asignaturas)\n" +
                        "Salir\n" +
                        "\n" +
                        "IMPORTANTE: Cualquier cambio en el sistema invalidará y destruirá\n" +
                        "todos los horarios previamente guardados");
            }

        });


        // Listener para la lista

        myLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                actionPerformed_seleccionarTitulacion(e);
            }
        });

        // Listeners para menuBar

        menuitemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent event) {
                actionPerformed_guardarTitulaciones(event);
            }
        });


    }


    //////////// Resto de metodos privados

    private void inicializarComponentes() {
        inicializarVista();
        inicializar_Menu();
        inicializar_PanelContenido();
        inicializar_PanelBotones();
        inicializar_PanelLista();
        inicializar_PanelDatosTitulacion();
        asignar_listenersComponetnes();
    }

    private void inicializarVista() {
        setTitle("Titulaciones");
        setSize(1000,400);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelContenido);
    }

    public void inicializar_Menu() {
        menuFile.add(menuitemGuardar);
        menubarVista.add(menuFile);
        setJMenuBar(menubarVista);
    }

    private void inicializar_PanelContenido() {
        panelContenido.setLayout(new BorderLayout());
        // PanelRoot.setLayout(new BoxLayout(PanelRoot, BoxLayout.Y_AXIS));
        //PanelRoot.add(panelLista);
        //.add(panelDatosTitulacion);

        //panelContenido.add(panelLista, BorderLayout.CENTER);
        // panelContenido.add(panelDatosAula, BorderLayout.EAST);

        PanelRoot.setLayout(new BorderLayout());
        PanelRoot.add(panelLista, BorderLayout.NORTH);
        PanelRoot.add(panelDatosTitulacion, BorderLayout.CENTER);

        panelContenido.add(PanelRoot, BorderLayout.NORTH);
        panelContenido.add(panelBotones, BorderLayout.SOUTH);
    }

    private void inicializar_PanelBotones() {
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(buttonAgregar);
        buttonEliminar.setEnabled(false);
        panelBotones.add(buttonEliminar);
        buttonModificar.setEnabled(false);
        panelBotones.add(buttonModificar);
        buttonConsultarAsignaturas.setEnabled(false);
        panelBotones.add(buttonConsultarAsignaturas);
        panelBotones.add(buttonVolver);
        panelBotones.add(buttonHelp);
    }


    private void inicializar_PanelLista() {
        myLista = new JList<>(ctrlPresentacion.listarTitulacionesNombre());

        // solo se puede seleccionar uno a la vez
        myLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panelLista.setLayout(new GridLayout(1,1));
        panelLista.add(new JScrollPane(myLista));
    }


    private void inicializar_PanelDatosTitulacion() {
        panelDatosTitulacion.setLayout((new BoxLayout(panelDatosTitulacion, BoxLayout.Y_AXIS)));
        panelDatosTitulacion.add(nombreTitulacion);
        panelDatosTitulacion.add(alumnosGrupoTeoria);
        panelDatosTitulacion.add(alumnosGrupoLab);
    }




}





