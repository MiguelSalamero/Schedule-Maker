/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class vistaMenuHorario extends JFrame {
    private CtrlPresentacion ctrlPresentacion;

    private JPanel PanelRoot = new JPanel();
    private JPanel panelContenido = new JPanel();
    private JPanel panelLista = new JPanel();
    private JPanel panelInfo = new JPanel();
    private JPanel panelBotones = new JPanel();

    private JButton buttonEliminar = new JButton("Eliminar horario");
    private JButton buttonMostrar = new JButton("Mostrar horario");
    private JButton buttonGenerar = new JButton("Generar horario");
    private JButton buttonHelp = new JButton("?");
    private JButton buttonVolver = new JButton("Volver");

    private JLabel nombreHorario = new JLabel("Nombre: ");

    private JList<String> list;


    // Menus
    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("Opciones");
    private JMenuItem menuitemGuardar = new JMenuItem("Guardar");

    //////////// Constructor y metodos publicos


    public vistaMenuHorario(CtrlPresentacion ctrlPresentacion) {
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
        setEnabled(true);
    }

    public void desactivar() {
        setEnabled(false);
    }

    public void actualizar_ListaHorarios() {
        panelLista.removeAll();
        inicializar_PanelLista();
        //asignar_listenersComponetnes();

        // Listener para la lista

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                actionPerformed_seleccionarHorario(e);
            }
        });
    }

    //////////// Metodos de las interficies Listener

    public void actionPerformed_buttonEliminarHorario(ActionEvent event) {
        // crear dialog: ¿Está seguro que desea eliminar la aula seleccionada?
        String nomhorario = list.getSelectedValue();
        if (nomhorario != null) {
            ctrlPresentacion.eliminarHorario(nomhorario);
            actualizar_ListaHorarios();
            hacerVisible();
            activar();
            ctrlPresentacion.setHorarioSeleccionado(null);
        }
    }

    public void actionPerformed_buttonGenerar(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaMenuHorario_a_GenerarHorario();
    }
    public void actionPerformed_buttonMostrar(ActionEvent event) {
        //ctrlPresentacion.setHorarioSeleccionado(list.getSelectedValue());

        if (ctrlPresentacion.getHorarioSeleccionadado() != null) ctrlPresentacion.sincronizacionVistaMenuHorario_a_Horario();
    }

    public void actionPerformed_buttonVolver(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaMenuHorario_a_Menu();
    }

    public void actionPerformed_seleccionarHorario(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {

            String Selectedhorario = list.getSelectedValue();
            if (Selectedhorario != null){
                //String[] parametrosHorarios = ctrlPresentacion.getParametrosHorarios(Selectedhorario);

                nombreHorario.setText("Nombre: " + Selectedhorario);

                ctrlPresentacion.setHorarioSeleccionado(Selectedhorario);
                buttonEliminar.setEnabled(true);
                buttonMostrar.setEnabled(true);
            }
        }
    }


    public void actionPerformed_guardarHorarios(ActionEvent event) {
        ctrlPresentacion.guardarSistema();
    }

    private void asignar_listenersComponetnes() {

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                actionPerformed_buttonEliminarHorario(event);
            }
        });

        buttonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_buttonVolver(e);
            }
        });

        buttonGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_buttonGenerar(e);

//                ctrlPresentacion.setHorarioSeleccionado(null);
//                buttonMostrar.setEnabled(false);
//
//                boolean b = false;
//                b = ctrlPresentacion.ejecutar("horariodeprueba");
//                //while (!b) {}
            }
        });
        buttonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_buttonMostrar(e);
            }
        });

        buttonHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Desde este menú puedes:\n" +
                        "Eliminar un horario guardado previamente\n" +
                        "Mostrar un horario guardado previamente\n" +
                        "Generar un nuevo horario con los datos del sistema actuales\n" +
                        "Volver\n" +
                        "\n" +
                        "IMPORTANTE: Todos los horarios que aparecen son aquellos que sirven\n" +
                        "para el sistema actual");
            }

        });


        // Listener para la lista

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                actionPerformed_seleccionarHorario(e);
            }
        });

        // Listeners para menuBar


        menuitemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent event) {
                actionPerformed_guardarHorarios(event);
            }
        });


    }

    private void inicializarComponentes() {
        inicializarVista();
        inicializar_Menu();
        inicializar_PanelContenido();
        inicializar_PanelBotones();
        inicializar_PanelLista();
        inicializar_PanelInfo();
        asignar_listenersComponetnes();
    }

    private void inicializarVista() {
        setTitle("Horarios");
        setMinimumSize(new Dimension(700,400));
        setSize(700,400);
        setResizable(false);
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

        PanelRoot.setLayout(new BorderLayout());
        PanelRoot.add(panelLista, BorderLayout.NORTH);
        PanelRoot.add(panelInfo, BorderLayout.CENTER);

        panelContenido.add(PanelRoot, BorderLayout.NORTH);
        panelContenido.add(panelBotones, BorderLayout.SOUTH);
    }

    private void inicializar_PanelLista() {
        list = new JList<>(ctrlPresentacion.listarnomHorarios());

        // solo se puede seleccionar uno a la vez
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panelLista.setLayout(new GridLayout(1,1));
        panelLista.add(new JScrollPane(list));

        list.validate();
        list.revalidate();
        list.repaint();

    }

    private void inicializar_PanelInfo() {
        panelInfo.setLayout((new BoxLayout(panelInfo, BoxLayout.Y_AXIS)));
        panelInfo.add(nombreHorario);
    }

    private void inicializar_PanelBotones() {
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(buttonEliminar);
        panelBotones.add(buttonGenerar);
        panelBotones.add(buttonMostrar);
        panelBotones.add(buttonVolver);
        panelBotones.add(buttonHelp);
        buttonEliminar.setEnabled(false);
        buttonMostrar.setEnabled(false);
    }


}
