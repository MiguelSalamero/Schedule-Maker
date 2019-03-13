package presentation;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

////////////////////////

public class VistaAnyadirRequisito {
    // Controlador de presentacion
    private CtrlPresentacion ctrlPresentacion;

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame("Gestion de requisitos");
    // Paneles
    private JPanel panelContenidos = new JPanel();
    private JPanel panelInformacion = new JPanel();
    private JPanel panelAnyadir = new JPanel();
    private JPanel panelBotones = new JPanel();
    // Botones
    private JButton buttonAnyadirRequisito = new JButton("Añadir Requisito");
    private JButton buttonEliminarRequisito = new JButton("Eliminar Requisito");
    private JButton buttonSalir = new JButton("Volver");
    private JButton buttonHelp = new JButton("?");
    // Texto
    private JLabel labelTituloLista = new JLabel("Asignaturas");
    private JLabel labelNuevoRequisito = new JLabel("Introducir nuevo requisito");

    private JTextField textField = new JTextField();
    private JList<String> list = new JList<String>();

    // Menus

    // Resto de atributos
    private Integer selectedIndex = -1;

//////////////////////// Constructor y metodos publicos


    public VistaAnyadirRequisito(CtrlPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        inicializarComponentes();
    }

    public void hacerVisible() {
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public void activar() {
        frameVista.setEnabled(true);
    }

    public void desactivar() {
        frameVista.setEnabled(false);
    }


//////////////////////// Metodos de las interfaces Listener




//////////////////////// Asignacion de listeners


    private void asignar_listenersComponentes() {

        // Listeners para los botones

        buttonSalir.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        ctrlPresentacion.requisitosAAsignaturas();
                    }
                });

        buttonEliminarRequisito.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        if (selectedIndex != -1) {

                            DefaultListModel model = (DefaultListModel) list.getModel();
                            ctrlPresentacion.eliminarRequisito(model.getElementAt(selectedIndex).toString());
                            model.remove(selectedIndex);
                            ctrlPresentacion.eliminarTodosHorarios();
                            selectedIndex= -1;
                            buttonEliminarRequisito.setEnabled(false);
                        }
                    }
                });

        buttonAnyadirRequisito.addActionListener
                (new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                         if (!ctrlPresentacion.existeAsignatura(textField.getText())) JOptionPane.showMessageDialog(null, "No existe la asignatura");

                         else if (ctrlPresentacion.esRequisito(textField.getText())) JOptionPane.showMessageDialog(null, "Ya es un requisito");

                         else if (ctrlPresentacion.getNombreAsignaturaSeleccionada().equals(textField.getText())) JOptionPane.showMessageDialog(null, "Una asignatura no puede ser requisito de si misma");

                         else if (!ctrlPresentacion.getTitulacionSeleccionada().equals(ctrlPresentacion.getTitulacionAsignatura(textField.getText()))) JOptionPane.showMessageDialog(null, "La asignatura no es de la misma titulación");


                         else {
                             ctrlPresentacion.anyadirRequisito(textField.getText());
                             ctrlPresentacion.eliminarTodosHorarios();
                         }

                         inicializarLista();
                     }
                });

        buttonHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Desde este menú puedes:\n" +
                        "Asociar una asignatura como requisito de la seleccionada\n" +
                        "Eliminar un requisito\n" +
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
                                buttonEliminarRequisito.setEnabled(true);
                                selectedIndex = l.getSelectedIndex();
                            }
                        }
                    }
                }
        );

    }


//////////////////////// Resto de metodos privados

    public void inicializarLista() {

        DefaultListModel dlm = (DefaultListModel) list.getModel();
        dlm.removeAllElements();
        ArrayList<String> requisitos = ctrlPresentacion.listarRequisitos();
        int i = 0;
        for (String r : requisitos) {
            dlm.add(i, r);
            ++i;
        }

        list.validate();
        list.revalidate();
        list.repaint();
    }

    public void inicializarComponentes() {
        list.setModel(new DefaultListModel());
        inicializarLista();
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelInformacion();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }

    private void inicializar_frameVista() {
        // Tamanyo
        frameVista.setMinimumSize(new Dimension(700,400));
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
        // Layout
        panelInformacion.setLayout(new BorderLayout());
        // Componentes
        panelInformacion.add(labelTituloLista,BorderLayout.NORTH);
        panelInformacion.add(list,BorderLayout.CENTER);

        panelInformacion.add(new JScrollPane(list),BorderLayout.CENTER);
        panelAnyadir.setLayout(new BorderLayout());
        panelAnyadir.add(textField, BorderLayout.SOUTH);
        panelAnyadir.add(labelNuevoRequisito, BorderLayout.NORTH);
        panelInformacion.add(panelAnyadir, BorderLayout.SOUTH);
    }


    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());
        // Componentes
        panelBotones.add(buttonEliminarRequisito);
        panelBotones.add(buttonAnyadirRequisito);
        panelBotones.add(buttonSalir);
        panelBotones.add(buttonHelp);
        buttonEliminarRequisito.setEnabled(false);
        buttonAnyadirRequisito.setEnabled(true);
        // Tooltips
    }

    public void hacerInvisible() {
        frameVista.setVisible(false);
    }

}

////////////////////////
