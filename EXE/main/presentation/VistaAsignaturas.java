package presentation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

////////////////////////

public class VistaAsignaturas{

    private int selectedIndex = -1;
    // Controlador de presentacion
    private CtrlPresentacion ctrlPresentacion;

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame("Asignaturas");
    // Paneles
    private JPanel panelContenidos = new JPanel();
    private JPanel panelInformacion = new JPanel();
    private JPanel panelInformacionA = new JPanel();
    private JPanel panelInformacion1 = new JPanel();
    private JPanel panelInformacion2 = new JPanel();
    private JPanel panelBotones = new JPanel();
    // Botones
    private JButton buttonAnyadirRequisito = new JButton("Gestionar Requisitos");
    private JButton buttonCrearAsignatura = new JButton("Crear Asignatura");
    private JButton buttonModificarAsignatura = new JButton("Modificar Asignatura");
    private JButton buttonEliminarAsignatura = new JButton("Eliminar Asignatura");
    private JButton buttonSalir = new JButton("Volver");
    private JButton buttonHelp = new JButton("?");
    // Texto
    private JLabel labelTituloLista = new JLabel("Asignaturas");
    private JLabel labelNombre = new JLabel("Nombre:");
    private JLabel labelCurso = new JLabel("Curso:");
    private JLabel labelAlumnos = new JLabel("Alumnos:");
    private JLabel labelTitulacion = new JLabel("Titulación:");
    private JLabel labelHorasTeoria = new JLabel("Horas semanales de teoría:");
    private JLabel labelHorasLabo = new JLabel("Horas semanales de laboratorio:");

    private JList<String> list = new JList<String>();


    // Menus
    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("Opciones");
    private JMenuItem menuitemGuardar = new JMenuItem("Guardar");
    // Resto de atributos
    private int iPanelActivo = 0;


//////////////////////// Constructor y metodos publicos


    public VistaAsignaturas(CtrlPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        inicializarComponentes();
    }

    public void hacerVisible() {
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public void hacerInvisible() {
        frameVista.setVisible(false);
    }

    public void activar() {
        frameVista.setEnabled(true);
    }

    public void desactivar() {
        frameVista.setEnabled(false);
    }

    public void actionPerformed_guardarAsignaturas(ActionEvent event) {
        ctrlPresentacion.guardarSistema();
    }


//////////////////////// Asignacion de listeners


    private void asignar_listenersComponentes() {

        // Listeners para los botones

        buttonSalir.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        ctrlPresentacion.sincronizacionVistaAsignatura_a_Titulaciones();
                    }
                });

        buttonEliminarAsignatura.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        if (selectedIndex != -1) {

                            ctrlPresentacion.eliminarAsignatura();
                            DefaultListModel model = (DefaultListModel) list.getModel();
                            model.remove(selectedIndex);
                            selectedIndex= -1;
                            buttonEliminarAsignatura.setEnabled(false);
                            buttonModificarAsignatura.setEnabled(false);
                            buttonAnyadirRequisito.setEnabled(false);
                        }
                    }
                });

        buttonModificarAsignatura.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {

                        ctrlPresentacion.vistaModificarAsignatura();
                        inicializarLista();

                        buttonEliminarAsignatura.setEnabled(false);
                        buttonModificarAsignatura.setEnabled(false);
                        buttonAnyadirRequisito.setEnabled(false);
                    }
                });

        buttonCrearAsignatura.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {

                        ctrlPresentacion.vistaCrearAsignatura();
                        inicializarLista();
                    }
                });

        buttonAnyadirRequisito.addActionListener
                (new ActionListener() {
                     public void actionPerformed(ActionEvent e) {

                         ctrlPresentacion.asignaturasARequisitos();
                     }
                });

        buttonHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Desde este menú puedes:\n" +
                        "Dar de alta una nueva asignatura (sin repetir nombres)\n" +
                        "Modificar una asignatura existente\n" +
                        "Eliminar una asignatura existente\n" +
                        "Gestionar los requisitos de una asignatura existente\n"+
                        "Salir\n" +
                        "\n" +
                        "NOTA: Entendemos por requisito aquella asignatura que se puede cursar\n" +
                        "a la vez que la seleccionada a pesar de ser de otro curso\n"+
                        "\n"+
                        "IMPORTANTE: Cualquier cambio en el sistema invalidará y destruirá\n" +
                        "todos los horarios previamente guardados");
            }

        });

        // Listeners para el resto de componentes

        list.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            JList l = (JList) e.getSource();
                            if (l.getSelectedValue() != null) {
                                buttonEliminarAsignatura.setEnabled(true);
                                buttonModificarAsignatura.setEnabled(true);
                                buttonAnyadirRequisito.setEnabled(true);
                                ctrlPresentacion.setAsignaturaSeleccionada(l.getSelectedValue().toString());
                                String[] atrb = ctrlPresentacion.consultarParametrosAsignatura(l.getSelectedValue().toString());
                                labelNombre.setText("Nombre: " + atrb[0]);
                                labelCurso.setText("Curso: " + atrb[1]);
                                labelAlumnos.setText("Alumnos: " + atrb[2]);
                                labelTitulacion.setText("Titulación: " + atrb[3]);
                                labelHorasTeoria.setText("Horas semanales de teoría: " + atrb[4]);
                                labelHorasLabo.setText("Horas semanales de laboratorío: " + atrb[5]);
                                selectedIndex = l.getSelectedIndex();
                            }
                        }
                    }
                }
        );

        // Listeners para menuBar


        menuitemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent event) {
                actionPerformed_guardarAsignaturas(event);
            }
        });
    }



//////////////////////// Resto de metodos privados

    public void inicializarLista() {

        DefaultListModel dlm = (DefaultListModel) list.getModel();
        dlm.removeAllElements();
        ArrayList<String> asignaturas = ctrlPresentacion.listarAsignaturas();
        int i = 0;
        for (String a : asignaturas) {
            dlm.add(i, a);
        }

        list.validate();
        list.revalidate();
        list.repaint();
    }

    public void inicializarComponentes() {
        list.setModel(new DefaultListModel());
        inicializarLista();

        inicializar_Menu();
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelInformacion();
        inicializar_panelInformacion1();
        inicializar_panelInformacion2();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }

    private void inicializar_frameVista() {
        // Tamanyo
        frameVista.setMinimumSize(new Dimension(1000,400));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);
        // Posicion y operaciones por defecto
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContenidos);
    }

    private void inicializar_panelContenidos() {
        // Layout
        panelContenidos.setLayout(new BorderLayout());
        // Paneles
        panelContenidos.add(panelInformacion,BorderLayout.NORTH);
        panelContenidos.add(panelBotones,BorderLayout.SOUTH);
    }

    private void inicializar_panelInformacion() {
        panelInformacion.setLayout(new BorderLayout());
        panelInformacion.add(panelInformacion1, BorderLayout.NORTH);
        panelInformacion.add(panelInformacion2, BorderLayout.SOUTH);
    }

    private void inicializar_panelInformacion1() {
        // Layout
        panelInformacion1.setLayout(new BorderLayout());
        // Componentes
        panelInformacion1.add(labelTituloLista,BorderLayout.NORTH);
        panelInformacion1.add(list,BorderLayout.SOUTH);

        panelInformacion1.add(new JScrollPane(list),BorderLayout.SOUTH);
    }

    private void inicializar_panelInformacion2() {
        // Layout
        panelInformacion2.setLayout(new BoxLayout(panelInformacion2, BoxLayout.PAGE_AXIS));
        // Componentes
        panelInformacion2.add(labelNombre);
        panelInformacion2.add(labelCurso);
        panelInformacion2.add(labelAlumnos);
        panelInformacion2.add(labelTitulacion);
        panelInformacion2.add(labelHorasTeoria);
        panelInformacion2.add(labelHorasLabo);
    }

    public void inicializar_Menu() {
        menuFile.add(menuitemGuardar);
        menubarVista.add(menuFile);
        frameVista.setJMenuBar(menubarVista);
    }

    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());
        // Componentes
        panelBotones.add(buttonCrearAsignatura);
        panelBotones.add(buttonModificarAsignatura);
        panelBotones.add(buttonEliminarAsignatura);
        panelBotones.add(buttonAnyadirRequisito);
        panelBotones.add(buttonSalir);
        panelBotones.add(buttonHelp);

        buttonModificarAsignatura.setEnabled(false);
        buttonEliminarAsignatura.setEnabled(false);
        buttonAnyadirRequisito.setEnabled(false);
        // Tooltips
    }

}

////////////////////////
