package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


////////////////////////

public class VistaGestionAsignatura {

    // Controlador de presentacion
    private CtrlPresentacion ctrlPresentacion;
    private Integer i; //0 -> crear, 1-> modificar

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame();
    private JPanel panelContenidos = new JPanel();
    private JPanel panelInformacion = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JPanel panelTextos = new JPanel();
    private JButton buttonCrear = new JButton("Crear");
    private JButton buttonVolver = new JButton("Volver");

    private JLabel labelNombre = new JLabel("Nombre:");
    private JLabel labelAlumnos = new JLabel("Alumnos:");
    private JLabel labelCurso = new JLabel("Curso:");
    private JLabel labelMaxTeoria = new JLabel("Horas semanales de teoría:");
    private JLabel labelMaxLabo = new JLabel("Horas semanales de laboratorio:");

    private JTextField textareaNombre = new JTextField();
    private JTextField textareaAlumnos = new JTextField();
    private JTextField textareaCurso = new JTextField();
    private JTextField textareaMaxTeoria = new JTextField();
    private JTextField textareaMaxLabo = new JTextField();


//////////////////////// Constructor y metodos publicos


    public void activar() {
        frameVista.setEnabled(true);
    }

    public void desactivar() {
        frameVista.setEnabled(false);
    }

    public VistaGestionAsignatura(CtrlPresentacion pCtrlPresentacion, int i) {
        ctrlPresentacion = pCtrlPresentacion;
        this.i = i;
        if (i == 0) buttonCrear.setText("Crear");
        else buttonCrear.setText("Modificar");
        inicializarComponentes();
    }

    public void hacerVisible() {
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public void hacerInvisible() {
        frameVista.setVisible(false);
    }


//////////////////////// Metodos de las interfaces Listener



    public void actionPerformed_buttonVolver (ActionEvent event) {
        ctrlPresentacion.gestionAAsignaturas();
    }


//////////////////////// Asignacion de listeners


    private void asignar_listenersComponentes() {

        // Listeners para los botones

        buttonVolver.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        actionPerformed_buttonVolver(event);
                    }
                });

        buttonCrear.addActionListener
                (new ActionListener() {
                     public void actionPerformed(ActionEvent event) {
                         if (textareaNombre.getText().equals("") ||
                            textareaMaxLabo.getText().equals("") ||
                            textareaMaxTeoria.getText().equals("") ||
                            textareaAlumnos.getText().equals("") ||
                            textareaCurso.getText().equals("")) JOptionPane.showMessageDialog(null, "Rellena todos los campos");

                         else if (!textareaAlumnos.getText().matches("-?\\d+") ||
                         !textareaMaxTeoria.getText().matches("-?\\d+") ||
                         !textareaMaxLabo.getText().matches("-?\\d+")) JOptionPane.showMessageDialog(null, "Solo se aceptan dígitos en los campos en los que se pide un número");

                         else if (i == 1) {
                             if (ctrlPresentacion.setNombreAsignaturaSeleccionada(textareaNombre.getText())) {
                                 ctrlPresentacion.setAlumnosAsignaturaSeleccionada(textareaAlumnos.getText());
                                 ctrlPresentacion.setCursoAsignaturaSeleccionada(textareaCurso.getText());
                                 ctrlPresentacion.setMaxTeoriaAsignaturaSeleccionada(textareaMaxTeoria.getText());
                                 ctrlPresentacion.setMaxLaboAsignaturaSeleccionada(textareaMaxLabo.getText());
                                 ctrlPresentacion.eliminarTodosHorarios();
                             }
                             else {
                                 JOptionPane.showMessageDialog(null, "Ya existe la asignatura");
                             }
                             ctrlPresentacion.gestionAAsignaturas();
                         }
                         else {
                             if (ctrlPresentacion.crearAsignatura(textareaNombre.getText(), textareaCurso.getText(), textareaAlumnos.getText(), ctrlPresentacion.getTitulacionSeleccionada(), textareaMaxTeoria.getText(), textareaMaxLabo.getText())) {}
                             else {
                                 JOptionPane.showMessageDialog(null, "Ya existe la asignatura");
                             }
                             ctrlPresentacion.gestionAAsignaturas();
                         }
                     }
                });

        // Listener para cerrar ventana

        frameVista.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                ctrlPresentacion.gestionAAsignaturas();
            }
        });
    }


//////////////////////// Resto de metodos privados


    public void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelInformacion();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }

    private void inicializar_frameVista() {
        // Tamanyo
        if (this.i == 0) frameVista.setTitle("Crear Asignatura");
        else frameVista.setTitle("Modificar Asignatura");
        frameVista.setMinimumSize(new Dimension(400,250));
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
        panelContenidos.add(panelBotones,BorderLayout.NORTH);
        panelContenidos.add(panelInformacion,BorderLayout.SOUTH);
    }

    private void inicializar_panelInformacion() {
        panelInformacion.setLayout(new BoxLayout(panelInformacion, BoxLayout.Y_AXIS));
        labelNombre.setLabelFor(textareaNombre);
        panelInformacion.add(labelNombre);
        panelInformacion.add(textareaNombre);
        panelInformacion.add(labelAlumnos);
        panelInformacion.add(textareaAlumnos);
        panelInformacion.add(labelCurso);
        panelInformacion.add(textareaCurso);
        panelInformacion.add(labelMaxLabo);
        panelInformacion.add(textareaMaxLabo);
        panelInformacion.add(labelMaxTeoria);
        panelInformacion.add(textareaMaxTeoria);
    }


    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());
        // Botones
        panelBotones.add(buttonCrear);
        panelBotones.add(buttonVolver);
    }

}

////////////////////////
