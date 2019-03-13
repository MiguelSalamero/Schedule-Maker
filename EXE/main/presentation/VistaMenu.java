package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

////////////////////////

public class VistaMenu {

    // Controlador de presentacion
    private CtrlPresentacion ctrlPresentacion;

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame("Menú principal");
    // Paneles
    private JPanel panelContenidos = new JPanel();
    private JPanel panelInformacion = new JPanel();
    private JPanel panelBotones = new JPanel();
    // Botones
    private JButton buttonTitulaciones = new JButton("Titulaciones");
    private JButton buttonAulas = new JButton("Aulas");
    private JButton buttonHorarios = new JButton("Gestión de horarios");
    private JButton buttonGuardar = new JButton("Guardar sistema");
    private JButton buttonSalir = new JButton("Salir");
    private JButton buttonHelp = new JButton("?");
    // Texto
    private JLabel labelTitulo = new JLabel("MENÚ PRINCIPAL - Toca '?' para obtener ayuda en cualquier ventana");


//////////////////////// Constructor y metodos publicos


    public VistaMenu(CtrlPresentacion ctrlPresentacion) {
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



    //////////// Metodos de las interficies Listener


    public void actionPerformed_buttonAula(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaMenu_a_Aulas();
    }

    public void actionPerformed_buttonTitulaciones(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaMenu_a_Titulaciones();
    }



    //////////// Asignacion de listeners

    private void asignar_listenersComponentes() {

        // Listeners para los botones

        buttonSalir.addActionListener
                (new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        System.exit(0);
                    }
                });


        buttonAulas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_buttonAula(e);
            }
        });

        buttonTitulaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed_buttonTitulaciones(e);
            }
        });

        buttonGuardar.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ctrlPresentacion.guardarSistema();
                    }
                });

        buttonHorarios.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ctrlPresentacion.sincronizacionVistaMenu_a_Horarios();
                    }
                });

        buttonHelp.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "Desde este menú puedes:\n" +
                                "Gestionar las titulaciones\n" +
                                "Gestionar las aulas\n" +
                                "Generar o mostrar horarios cargados\n" +
                                "Guardar el sistema (horarios generados, titulaciones, aulas, etc)\n" +
                                "Salir");
                    }
                });
    }



    //////////// Resto de metodos privados


    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelInformacion();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }

    private void inicializar_frameVista() {
        // Tamanyo
        frameVista.setMinimumSize(new Dimension(700,150));
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
        panelContenidos.add(panelBotones,BorderLayout.CENTER);
    }

    private void inicializar_panelInformacion() {
        // El panelInformacion es solo un contenedor para panelInformacionA, que
        // contendra panelInformacion1 (inicialmente) o panelInformacion2
        panelInformacion.setLayout(new BorderLayout());
        panelInformacion.add(labelTitulo,BorderLayout.NORTH);
    }
    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());
        // Componentes
        panelBotones.add(buttonTitulaciones);
        panelBotones.add(buttonAulas);
        panelBotones.add(buttonHorarios);
        panelBotones.add(buttonGuardar);
        panelBotones.add(buttonSalir);
        panelBotones.add(buttonHelp);
    }

}

////////////////////////
