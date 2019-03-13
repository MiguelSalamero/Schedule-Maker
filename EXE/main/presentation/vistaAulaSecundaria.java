package presentation;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.WindowAdapter;
        import java.awt.event.WindowEvent;

public class vistaAulaSecundaria extends JFrame {

    // Controlador presentacion
    private CtrlPresentacion ctrlPresentacion;
    private vistaAula vistaaula;
    private TipoVista tipoVista;

    // Componentes de la interficie grafica
    private JButton buttonConfirmar = new JButton("Confirmar");
    private JButton buttonCancelar = new JButton("Cancelar");
    private JButton buttonHelp = new JButton("?");

    private JPanel panelContenido = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JPanel panelDatos = new JPanel();

    private JLabel labelNombre = new JLabel("Nombre:");
    private JLabel labelCapacidad = new JLabel("Capacidad máxima:");
    private JLabel labelConPC = new JLabel("¿Tiene ordenadores? (si/no):");
    private JTextField nombreAula = new JTextField(20);
    private JTextField capacidadAula = new JTextField(20);
    private JTextField conPCAula = new JTextField(20);


    public enum TipoVista {
        ALTA,MODIFICAR
    }



    //////////// Constructor y metodos publicos


    public vistaAulaSecundaria(CtrlPresentacion ctrlPresentacion, vistaAula vistaaula, TipoVista tipoVista){
        this.ctrlPresentacion = ctrlPresentacion;
        this.vistaaula = vistaaula;
        this.tipoVista = tipoVista;
        inicializarComponentes();
    }

    public void hacerVisible() {
        // check que hace pack()
        //pack();
        setVisible(true);
    }


    public void hacerInvisible() {
        this.setVisible(false);
    }


    public void desactivar() {
        setEnabled(false);
    }


    public void setTipoVista(TipoVista tipoVista) {
        this.tipoVista = tipoVista;
    }


    //////////// Metodos de las interficies Listener


    public void actionPerformed_buttonConfirmar(ActionEvent event) {
        String valorConPC = null;

        if (nombreAula.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Se debe indicar un nombre de la aula");
            inicializar_PanelDatos();
        }
        else {

            if (capacidadAula.getText().matches("-?\\d+") && Integer.parseInt(capacidadAula.getText()) > 0) {

                // por defecto no tiene ordenadores
                if ("si".equals(conPCAula.getText()) || "yes".equals(conPCAula.getText()) || "true".equals(conPCAula.getText()) || "sí".equals(conPCAula.getText()) || "no".equals(conPCAula.getText()) || "false".equals(conPCAula.getText())) {
                    //if ("yes".equals(conPCAula.getText()) || "no".equals(conPCAula.getText())) {

                    if ("no".equals(conPCAula.getText()) || "false".equals(conPCAula.getText())) valorConPC = "false";
                    else valorConPC = "true";

                    boolean works = false;

                    if (tipoVista == TipoVista.ALTA) {
                        if (ctrlPresentacion.crearAula(nombreAula.getText(), capacidadAula.getText(), valorConPC)){
                            works = true;
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Ya existe una aula con ese nombre");
                            inicializar_PanelDatos();
                        }
                    }

                    else if (tipoVista == TipoVista.MODIFICAR) {
                        if (ctrlPresentacion.setNombreAulaSeleccionada(nombreAula.getText())) works = true;
                        else {
                            JOptionPane.showMessageDialog(null, "Ya existe una aula con ese nombre");
                            inicializar_PanelDatos();
                        }
                        ctrlPresentacion.setCapacidadAulaSeleccionada(capacidadAula.getText());
                        ctrlPresentacion.setConPCAulaSeleccionada(valorConPC);

                    }
                    if (works) {
                        ctrlPresentacion.sincronizacionVistaSecundariaAula_a_Principal();
                        ctrlPresentacion.eliminarTodosHorarios();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Solo se acepta si/no para indicar si la aula tiene ordenadores");
                    inicializar_PanelDatos();
                    //ctrlPresentacion.sincronizacionVistaSecundariaAula_a_Principal();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Solo se aceptan valores positivos como capacidad de una aula");
                inicializar_PanelDatos();
                //ctrlPresentacion.sincronizacionVistaSecundariaAula_a_Principal();
            }
        }

    }

    public void actionPerformed_buttonCancelar(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaSecundariaAula_a_Principal();
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


        buttonHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Rellena los siguientes campos:\n" +
                        "Nombre para el aula, sin repetir\n" +
                        "Capacidad del aula (máximo de alumnos que caben en ella)\n" +
                        "Tiene PC? sólo se acepta 'si' o 'no'\n" +
                        "\n" +
                        "IMPORTANTE: Cualquier cambio en el sistema invalidará y destruirá\n" +
                        "todos los horarios previamente guardados");
            }

        });

        // Listener para cerrar ventana

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                //if(JOptionPane.showConfirmDialog(panelContenido, "Are you sure ?") == JOptionPane.OK_OPTION){
                ctrlPresentacion.sincronizacionVistaSecundariaAula_a_Principal();
                //}
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
        setBounds(200,200,200,200);
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
        //nombreAula.setText("");
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.add(labelNombre);
        //nombreAula.setText("");
        panelDatos.add(nombreAula);
        panelDatos.add(labelCapacidad);
        //capacidadAula.setText("");
        panelDatos.add(capacidadAula);
        panelDatos.add(labelConPC);
        //conPCAula.setText("");
        panelDatos.add(conPCAula);

        if (tipoVista == TipoVista.MODIFICAR) {
            String[] datosAula = ctrlPresentacion.getParametrosAula(ctrlPresentacion.getAulaSeleccionada());
            nombreAula.setText(datosAula[0]);
            capacidadAula.setText(datosAula[1]);
            if ("true".equals(datosAula[2])) conPCAula.setText("yes");
            else conPCAula.setText("no");
        }

    }


}
