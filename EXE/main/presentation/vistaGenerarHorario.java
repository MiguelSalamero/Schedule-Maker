package presentation;

        import domain.restrictions.DemasiadasTitulaciones;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.WindowAdapter;
        import java.awt.event.WindowEvent;

public class vistaGenerarHorario extends JFrame {

    // Controlador presentacion
    private CtrlPresentacion ctrlPresentacion;
    private vistaMenuHorario vistamhorario;

    // Componentes de la interficie grafica
    private JButton buttonConfirmar = new JButton("Confirmar");
    private JButton buttonCancelar = new JButton("Cancelar");
    private JButton buttonHelp = new JButton("?");

    private JPanel panelContenido = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JPanel panelDatos = new JPanel();

    private JLabel labMAX_GENERATION = new JLabel("MAX_GENERATION:");
    private JLabel labPOPULATION_SIZE = new JLabel("POPULATION_SIZE:");
    private JLabel labARCHIVE_SIZE = new JLabel("ARCHIVE_SIZE:");
    private JLabel labCROSSOVER_RATE = new JLabel("CROSSOVER_RATE:");
    private JLabel labMUTATION_RATE = new JLabel("MUTATION_RATE:");
    private JLabel labnomHorari = new JLabel("nombre del horario:");
    private JTextField nomHorari = new JTextField(20);
    private JTextField MAX_GENERATION = new JTextField(20);
    private JTextField POPULATION_SIZE = new JTextField(20);
    private JTextField ARCHIVE_SIZE = new JTextField(20);
    private JTextField CROSSOVER_RATE = new JTextField(20);
    private JTextField MUTATION_RATE = new JTextField(20);

    boolean loading = false;

    public vistaGenerarHorario(CtrlPresentacion ctrlPresentacion, vistaMenuHorario vistamenuhurario){
        this.ctrlPresentacion = ctrlPresentacion;
        this.vistamhorario = vistamenuhurario;
        inicializarAlgoritmo();
        inicializarComponentes();
    }

    public void hacerVisible() {
        setVisible(true);
    }

    public void hacerInvisible() {
        this.setVisible(false);
    }

    public void desactivar() {
        setEnabled(false);
    }

    //    if (nombreTitulacion.getText().equals("")) {
//            JOptionPane.showMessageDialog(null, "Se debe indicar un nombre de la titulación");
//            inicializar_PanelDatos();
//        }
//        else {
//            if (alumnosLab.getText().matches("-?\\d+") && alumnosTeoria.getText().matches("-?\\d+") && Integer.parseInt(alumnosLab.getText()) > 0 && Integer.parseInt(alumnosTeoria.getText()) > 0) {
//
//                if (tipoVista == TipoVista.ALTA) {
//                    try {
//                        ctrlPresentacion.crearTitulacion(nombreTitulacion.getText(), alumnosTeoria.getText(), alumnosLab.getText());
//                    } catch (DemasiadasTitulaciones demasiadasTitulaciones) {
//
//                    }
//                } else if (tipoVista == TipoVista.MODIFICAR) {
//                    ctrlPresentacion.setAlumnosTeoriaSeleccionada(alumnosTeoria.getText());
//                    ctrlPresentacion.setAlumnosLaboratorioSeleccionada(alumnosLab.getText());
//                    ctrlPresentacion.setNombreTitulacionSeleccionada(nombreTitulacion.getText());
//                }
//                ctrlPresentacion.sincronizacionVistaSecundariaTitulacion_a_Principal();
//            }
//            else {
//                JOptionPane.showMessageDialog(null, "Solo se aceptan números enteros como alumnos de teoría y laboratorio");
//                inicializar_PanelDatos();
//            }
//
//        }
    public void actionPerformed_buttonConfirmar(ActionEvent event) {
        boolean ok = true;
        if (nomHorari.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Se debe indicar un nombre");
            inicializar_PanelDatos();
            ok = false;
        }
        try {
            Double num = Double.parseDouble(CROSSOVER_RATE.getText());
            if(num <= 0) throw new NumberFormatException();
            num = Double.parseDouble(MUTATION_RATE.getText());
            if(num <= 0) throw new NumberFormatException();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Los campos de mutacion_rate y crossover_rate deben ser decimales positivos");
            inicializar_PanelDatos();
            ok=false;
        }
        try {
            Integer num = Integer.parseUnsignedInt(MAX_GENERATION.getText());
            num = Integer.parseUnsignedInt(POPULATION_SIZE.getText());
            num = Integer.parseUnsignedInt(ARCHIVE_SIZE.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Los campos de max_generation, population_size y archive_size han de ser naturales");
            inicializar_PanelDatos();
            ok =false;
        }

        if(ok){
            ctrlPresentacion.setParametrosAlgoritmo(MAX_GENERATION.getText(),
                    POPULATION_SIZE.getText(),
                    ARCHIVE_SIZE.getText(),
                    CROSSOVER_RATE.getText(),MUTATION_RATE.getText());
            loading = true;
            //t.start();
            //ctrlPresentacion.sincronizacionVistaGenerarHorario_a_Generando(loading);
            boolean b = ctrlPresentacion.ejecutar(nomHorari.getText());
            if (!b) {
                JOptionPane.showMessageDialog(null, "No se ha podido generar un horario valido");
                inicializar_PanelDatos();
            }
            //while (loading);
            //        this.vistamhorario.actualizar_ListaHorarios();
            //        this.vistamhorario.hacerVisible();
            //        this.vistamhorario.activar();
            ctrlPresentacion.sincronizacionVistaGenerarHorario_a_MenuHorario();
        }
    }

    public void actionPerformed_buttonCancelar(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaGenerarHorario_a_MenuHorario();
    }



    //////////// Asignacion de listeners


    private void asignar_listenersComponetnes() {

        // Listeners para los botones

        buttonConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                actionPerformed_buttonConfirmar(event);
            }
        });


        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                actionPerformed_buttonCancelar(event);
            }
        });

        // Listener para cerrar ventana

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                ctrlPresentacion.sincronizacionVistaGenerarHorario_a_MenuHorario();
            }
        });

        buttonHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Para generar el horario asignale un nombre y rellena las variables del algoritmo necesarias\n");

            }

        });


    }


    //////////// Resto de metodos privados

    private void inicializarComponentes() {
        inicializarVista();
        inicializar_PanelContenido();
        inicializar_PanelBotones();
        inicializar_PanelDatos();
        asignar_listenersComponetnes();
    }

    private void inicializarVista() {
        setBounds(200,200,200,300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        add(panelContenido);
    }


    private void inicializar_PanelContenido() {
        panelContenido.setLayout(new BorderLayout());
        panelContenido.add(panelBotones, BorderLayout.SOUTH);
        panelContenido.add(panelDatos, BorderLayout.CENTER);
    }

    private void inicializar_PanelBotones() {
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(buttonConfirmar);
        panelBotones.add(buttonCancelar);
        panelBotones.add(buttonHelp);
    }

    private void inicializar_PanelDatos() {
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.add(labnomHorari);
        panelDatos.add(nomHorari);
        panelDatos.add(labMAX_GENERATION);
        panelDatos.add(MAX_GENERATION);
        panelDatos.add(labPOPULATION_SIZE);
        panelDatos.add(POPULATION_SIZE);
        panelDatos.add(labARCHIVE_SIZE);
        panelDatos.add(ARCHIVE_SIZE);
        panelDatos.add(labCROSSOVER_RATE);
        panelDatos.add(CROSSOVER_RATE);
        panelDatos.add(labMUTATION_RATE);
        panelDatos.add(MUTATION_RATE);



        String[] parametrosactuales = ctrlPresentacion.getParametrosAlgoritmo();
        MAX_GENERATION.setText(parametrosactuales[0]);
        POPULATION_SIZE.setText(parametrosactuales[1]);
        ARCHIVE_SIZE.setText((parametrosactuales[2]));
        CROSSOVER_RATE.setText((parametrosactuales[3]));
        MUTATION_RATE.setText((parametrosactuales[4]));

    }

    private void inicializarAlgoritmo(){
        ctrlPresentacion.inicializarAlgoritmo();
    }
}