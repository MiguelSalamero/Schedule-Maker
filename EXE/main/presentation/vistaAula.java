package presentation;

        import javax.swing.*;
        import javax.swing.event.ListSelectionEvent;
        import javax.swing.event.ListSelectionListener;
        import javax.swing.filechooser.FileNameExtensionFilter;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

public class vistaAula extends JFrame {

    private CtrlPresentacion ctrlPresentacion;


    private JPanel PanelRoot = new JPanel();
    private JPanel panelContenido = new JPanel();
    private JPanel panelLista = new JPanel();
    private JPanel panelDatosAula = new JPanel();
    private JPanel panelBotones = new JPanel();

    private JButton buttonAgregar = new JButton("Crear aula");
    private JButton buttonVolver = new JButton("Volver");
    private JButton buttonEliminar = new JButton("Eliminar aula");
    private JButton buttonModificar = new JButton("Modificar aula");
    private JButton buttonHelp = new JButton("?");

    private JLabel nombreAula = new JLabel("Nombre de la aula: ");
    private JLabel capacidadMaxima = new JLabel("Capacidad máxima: ");
    private JLabel conPC = new JLabel("Tiene ordenadores: ");

    private JList<String> myLista;


    // Menus
    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("Opciones");
    private JMenuItem menuitemGuardar = new JMenuItem("Guardar");


    //////////// Constructor y metodos publicos


    public vistaAula(CtrlPresentacion ctrlPresentacion) {
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


    public void actualizar_ListaAulas() {
        panelLista.removeAll();
        inicializar_PanelLista();

        //panelLista.validate();
        //panelLista.revalidate();
        //panelLista.repaint();
        //asignar_listenersComponetnes();

        buttonEliminar.setEnabled(false);
        buttonModificar.setEnabled(false);

        myLista.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                actionPerformed_seleccionarAula(e);
            }
        });
    }


    //////////// Metodos de las interficies Listener

    public void actionPerformed_buttonAgregarAula(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaPrincipalAula_a_Secundaria(vistaAulaSecundaria.TipoVista.ALTA);
    }


    public void actionPerformed_buttonEliminarAula(ActionEvent event) {
        // crear dialog: ¿Está seguro que desea eliminar la aula seleccionada?
        String aula = myLista.getSelectedValue();
        if (aula != null) {
            ctrlPresentacion.eliminarAula(aula);
            actualizar_ListaAulas();
            hacerVisible();
            activar();
            ctrlPresentacion.setAulaSeleccionada(null);
        }
    }

    public void actionPerformed_buttonModificarAula(ActionEvent event) {
        if (ctrlPresentacion.getAulaSeleccionada() != null) ctrlPresentacion.sincronizacionVistaPrincipalAula_a_Secundaria(vistaAulaSecundaria.TipoVista.MODIFICAR);
    }

    public void actionPerformed_seleccionarAula(ListSelectionEvent event) {
        String aulaSeleccionada = myLista.getSelectedValue();
        String[] parametrosAula = ctrlPresentacion.getParametrosAula(aulaSeleccionada);
        nombreAula.setText("Nombre de la aula: " + parametrosAula[0]);
        capacidadMaxima.setText("Capacidad máxima: " + parametrosAula[1]);
        if (parametrosAula[2] == "true") conPC.setText("Tiene ordenadores: Sí");
        else conPC.setText("Tiene ordenadores: No");
        ctrlPresentacion.setAulaSeleccionada(parametrosAula[0]);
        buttonModificar.setEnabled(true);
        buttonEliminar.setEnabled(true);
    }

    public void actionPerformed_guardarAulas(ActionEvent event) {
        ctrlPresentacion.guardarSistema();
    }

    public void actionPerformed_buttonVolver(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaAula_a_Menu();
    }


    //////////// Asignacion de listeners


    // Listeners para los botones

    private void asignar_listenersComponetnes() {

        buttonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String aulaSeleccionada = myLista.getSelectedValue();
                actionPerformed_buttonAgregarAula(e);

            }
        });


        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String aulaSeleccionada = myLista.getSelectedValue();
                actionPerformed_buttonEliminarAula(e);

            }
        });

        buttonModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String aulaSeleccionada = myLista.getSelectedValue();
                actionPerformed_buttonModificarAula(e);

            }
        });

        buttonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_buttonVolver(e);
            }
        });

        buttonHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Desde este menú puedes:\n" +
                        "Dar de alta una nueva aula (sin repetir nombres)\n" +
                        "Modificar una aula existente\n" +
                        "Eliminar una aula existente\n" +
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
                actionPerformed_seleccionarAula(e);
            }
        });

        // Listeners para menuBar


        menuitemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent event) {
                actionPerformed_guardarAulas(event);
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
        inicializar_PanelDatosAula();
        asignar_listenersComponetnes();
    }

    private void inicializarVista() {
        setTitle("Aulas");
        setSize(700,400);
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
        PanelRoot.setLayout(new BoxLayout(PanelRoot, BoxLayout.Y_AXIS));
        PanelRoot.add(panelLista);
        PanelRoot.add(panelDatosAula);
        panelContenido.add(panelBotones, BorderLayout.SOUTH);
        //panelContenido.add(panelLista, BorderLayout.CENTER);
        // panelContenido.add(panelDatosAula, BorderLayout.EAST);
        panelContenido.add(PanelRoot, BorderLayout.CENTER);
    }

    private void inicializar_PanelBotones() {
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(buttonAgregar);
        buttonEliminar.setEnabled(false);
        panelBotones.add(buttonEliminar);
        buttonModificar.setEnabled(false);
        panelBotones.add(buttonModificar);
        panelBotones.add(buttonVolver);
        panelBotones.add(buttonHelp);
    }


    private void inicializar_PanelLista() {
        myLista = new JList<>(ctrlPresentacion.listarAulasNombre());

        // solo se puede seleccionar uno a la vez
        myLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panelLista.setLayout(new GridLayout(1,1));
        panelLista.add(new JScrollPane(myLista));
    }

    private void inicializar_PanelDatosAula() {
        panelDatosAula.setLayout((new BoxLayout(panelDatosAula, BoxLayout.Y_AXIS)));
        panelDatosAula.add(nombreAula);
        panelDatosAula.add(capacidadMaxima);
        panelDatosAula.add(conPC);
    }


}