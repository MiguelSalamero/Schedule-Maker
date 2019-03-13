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
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class vistaHorario extends JFrame {
    private CtrlPresentacion ctrlPresentacion;

    private JPanel PanelRoot = new JPanel();
    private JPanel panelContenido = new JPanel();
    private JPanel panelGrid = new JPanel();
    private JPanel panelPadreGrid = new JPanel();
    private JPanel panelBotones = new JPanel();

    private JButton buttonVolver = new JButton("Volver");
    private JButton buttonSwap = new JButton("Intercambiar Sesiones");
    private JButton buttonHelp = new JButton("?");

    private JList<String> list1;
    private JList<String> list2;

    private int index;
    private Integer idfecha1;
    private String aula1;
    private Integer idfecha2;
    private String aula2;

    // Menus
    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("Opciones");
    private JMenuItem menuitemQuit = new JMenuItem("Salir");
    private JMenuItem menuitemGuardar = new JMenuItem("Guardar horarios");

    //////////// Constructor y metodos publicos

    public vistaHorario(CtrlPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        inicializarComponentes();
    }

    public void hacerVisible() {
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

    public void actionPerformed_buttonVolver(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaHorario_a_MenuHorario();
    }
    public void actionPerformed_buttonSwap(ActionEvent event) {
        String[] aux = list1.getSelectedValue().toString().split(",");
        idfecha1 = Integer.parseInt(aux[aux.length-1]);
        aula1 = aux[2];

        aux = list2.getSelectedValue().toString().split(",");
        idfecha2 = Integer.parseInt(aux[aux.length-1]);
        aula2 = aux[2];

        boolean b = false;
        if (idfecha1 != idfecha2) b = ctrlPresentacion.swap(idfecha1, idfecha2, aula1, aula2);
        actualizar_PanelGrid();
        hacerVisible();
        list1.clearSelection();
        list2.clearSelection();
        list1 = null;
        list2 = null;
        buttonSwap.setEnabled(false);

        if (!b) JOptionPane.showMessageDialog(null, "Las sesiones seleccionadas no se pueden intercambiar");

    }

    public void actionPerformed_guardarHorarios(ActionEvent event) {
        try {
            ctrlPresentacion.guardarHorarios();
        }
        catch (java.io.IOException a) {
            JOptionPane.showMessageDialog(panelContenido, "No se han guardado los horarios");
        }
    }

    private void asignar_listenersComponetnes() {
        buttonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_buttonVolver(e);
            }
        });
        buttonSwap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_buttonSwap(e);
            }
        });

        menuitemQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent event) {
                System.exit(0);
            }
        });

        menuitemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent event) {
                actionPerformed_guardarHorarios(event);
            }
        });


        buttonHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Cada entrada del horario muestra:\n" +
                        "Titulación, asignatura, aula, grupo, identificador de fecha (el usuario puede ignorarlo)\n" +
                        "Si se seleccionan dos turnos y se hace click en swap, se intercambiarán siempre que\n" +
                        "el cambio no rompa ninguna restricción\n" +
                        "Gestionar los requisitos de una asignatura existente\n");
            }

        });

    }

    private void inicializarComponentes() {
        index = 0;
        inicializarVista();
        inicializar_Menu();
        inicializar_PanelContenido();
        inicializar_PanelBotones();
        inicializar_PanelLista();
        inicializar_PanelGrid();
//        inicializar_PanelInfo();
        asignar_listenersComponetnes();
    }

    private void inicializarVista() {
        //setTitle(ctrlPresentacion.getHorarioSeleccionadado());
        setMinimumSize(new Dimension(800,700));
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelContenido);
    }

    public void inicializar_Menu() {
//        menuFile.add(menuitemGuardar);
//        menuFile.add(menuitemCargar);
        menuFile.add(menuitemQuit);
        menubarVista.add(menuFile);
        setJMenuBar(menubarVista);
    }

    private void inicializar_PanelContenido() {
        panelContenido.setLayout(new BorderLayout());

        PanelRoot.setLayout(new BorderLayout(1,1));



        panelPadreGrid.setLayout(new BorderLayout(1,1));

        JPanel mufasa = new JPanel();
        mufasa.setLayout(new BorderLayout());
        mufasa.setPreferredSize(new Dimension(752,564));
        mufasa.add(new JScrollPane(panelPadreGrid));

        JPanel panellabeltop = new JPanel();
        panellabeltop.setLayout(new GridLayout(1,5,10,10));
        panellabeltop.setPreferredSize(new Dimension(680,10));
        panellabeltop.add(new JLabel("Lunes") );
        panellabeltop.add(new JLabel("Martes") );
        panellabeltop.add(new JLabel("Miercoles") );
        panellabeltop.add(new JLabel("Jueves") );
        panellabeltop.add(new JLabel("Viernes") );

        JPanel panelabelleft = new JPanel();
        panelabelleft.setLayout(new GridLayout(13,1,1,1));
        //panelabelleft.setPreferredSize(new Dimension(40,500));
        panelabelleft.add(new JLabel("8:00") );
        panelabelleft.add(new JLabel("9:00") );
        panelabelleft.add(new JLabel("10:00") );
        panelabelleft.add(new JLabel("11:00") );
        panelabelleft.add(new JLabel("12:00") );
        panelabelleft.add(new JLabel("13:00") );
        panelabelleft.add(new JLabel("14:00") );
        panelabelleft.add(new JLabel("15:00") );
        panelabelleft.add(new JLabel("16:00") );
        panelabelleft.add(new JLabel("17:00") );
        panelabelleft.add(new JLabel("18:00") );
        panelabelleft.add(new JLabel("19:00") );
        panelabelleft.add(new JLabel("20:00") );

        JPanel paneltop = new JPanel();

        paneltop.add(panellabeltop,BorderLayout.NORTH);
        panelPadreGrid.add(panelabelleft,BorderLayout.WEST);
        panelPadreGrid.add(panelGrid,BorderLayout.EAST);
        paneltop.add(mufasa,BorderLayout.SOUTH);
        paneltop.setPreferredSize(new Dimension(600,600)); //


        PanelRoot.add(paneltop);

        //PanelRoot.add(panelInfo, BorderLayout.SOUTH);

        panelContenido.add(PanelRoot, BorderLayout.NORTH);
        panelContenido.add(panelBotones, BorderLayout.SOUTH);
    }

    private void inicializar_PanelLista() {
    }

    private void actualizar_PanelGrid(){
        panelGrid.removeAll();
        inicializar_PanelGrid();
    }

    private void inicializar_PanelGrid() {
        panelGrid.setLayout(new GridLayout(13,6,2,2));
        panelGrid.setPreferredSize(new Dimension(700,1200));
        ArrayList<Integer> listafechas = ctrlPresentacion.listarIdFechas();
        for(int fecha : listafechas){
            ArrayList<ArrayList<String>> sesiones = ctrlPresentacion.getSesionesPorFecha(fecha);
            String[] artx = new String[sesiones.size()];
            Integer i = 0;

            for (ArrayList<String> al : sesiones) {
                String texto = new String();
                texto = al.get(4)+","+al.get(3)+","+al.get(1)+","+al.get(2)+","+al.get(0);
//                boolean first = true;
//                for (String s : al) {
//                     if (!first) texto = texto + "," + s;
//                     else texto = s;
//                     first = false;
//
//                }
                artx[i] = texto;
                ++i;
            }
            JList<String> listp = new JList<>(artx);
            listp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listp.addListSelectionListener(
                    new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            if (!e.getValueIsAdjusting()) {
                                JList l = (JList) e.getSource();
                                if (l.getSelectedValue() != null) {
                                    if(list1 != null )list1.clearSelection();
                                    if(list2 == null )list2 = l;
                                    else {
                                        list1 = list2;
                                        list2 = l;
                                        buttonSwap.setEnabled(true);
                                    }

                                }
                            }
                        }
                    }
            );
            JPanel panelList = new JPanel();
            panelList.setLayout(new BorderLayout());
            panelList.add(listp);
            panelList.add(new JScrollPane(listp,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
            panelGrid.add(panelList);
        }
    }

    private void inicializar_PanelBotones() {
        panelBotones.setLayout(new FlowLayout());
        panelBotones.setPreferredSize(new Dimension(500,40));
        panelBotones.add(buttonSwap);
        panelBotones.add(buttonVolver);
        panelBotones.add(buttonHelp);
        buttonSwap.setEnabled(false);
    }


}