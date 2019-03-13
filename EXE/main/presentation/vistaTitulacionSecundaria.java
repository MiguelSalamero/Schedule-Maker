package presentation;

        import domain.restrictions.DemasiadasTitulaciones;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.WindowAdapter;
        import java.awt.event.WindowEvent;

public class vistaTitulacionSecundaria extends JFrame {

    // Controlador presentacion
    private CtrlPresentacion ctrlPresentacion;
    private vistaTitulacion vistatitulacion;
    private vistaTitulacionSecundaria.TipoVista tipoVista;

    // Componentes de la interficie grafica
    private JButton buttonConfirmar = new JButton("Confirmar");
    private JButton buttonCancelar = new JButton("Cancelar");
    private JButton buttonHelp = new JButton("?");

    private JPanel panelContenido = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JPanel panelDatos = new JPanel();

    private JLabel labelNombre = new JLabel("Nombre:");
    private JLabel labelAlumnosTeoria = new JLabel("Alumnos por grupo de teoría:");
    private JLabel labelAlumnosLab = new JLabel("Alumnos por grupo de laboratorio:");
    private JTextField nombreTitulacion = new JTextField(20);
    private JTextField alumnosTeoria = new JTextField(20);
    private JTextField alumnosLab = new JTextField(20);


    public enum TipoVista {
        ALTA,MODIFICAR
    }



    //////////// Constructor y metodos publicos


    public vistaTitulacionSecundaria(CtrlPresentacion ctrlPresentacion, vistaTitulacion vistatitulacion, TipoVista tipoVista){
        this.ctrlPresentacion = ctrlPresentacion;
        this.vistatitulacion = vistatitulacion;
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

    public void setTipoVista(vistaTitulacionSecundaria.TipoVista tipoVista) {
        this.tipoVista = tipoVista;
    }



    //////////// Metodos de las interficies Listener


    public void actionPerformed_buttonConfirmar(ActionEvent event) {
        String valorConPC = null;


        if (nombreTitulacion.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Se debe indicar un nombre de la titulación");
            inicializar_PanelDatos();
        }
        else {
            if (alumnosLab.getText().matches("-?\\d+") && alumnosTeoria.getText().matches("-?\\d+") && Integer.parseInt(alumnosLab.getText()) > 0 && Integer.parseInt(alumnosTeoria.getText()) > 0) {

                boolean works = false;

                if (tipoVista == TipoVista.ALTA) {
                    try {
                        if (ctrlPresentacion.crearTitulacion(nombreTitulacion.getText(), alumnosTeoria.getText(), alumnosLab.getText())) works = true;
                        else {
                            JOptionPane.showMessageDialog(null, "Ya existe una titulación con ese nombre");
                            inicializar_PanelDatos();
                        }
                    } catch (DemasiadasTitulaciones demasiadasTitulaciones) {

                    }
                }

                else if (tipoVista == TipoVista.MODIFICAR) {
                    if (ctrlPresentacion.setNombreTitulacionSeleccionada(nombreTitulacion.getText())) works = true;
                    else {
                        JOptionPane.showMessageDialog(null, "Ya existe una titulación con ese nombre");
                        inicializar_PanelDatos();
                    }
                    ctrlPresentacion.setAlumnosTeoriaSeleccionada(alumnosTeoria.getText());
                    ctrlPresentacion.setAlumnosLaboratorioSeleccionada(alumnosLab.getText());
                }
                if (works) {
                    ctrlPresentacion.sincronizacionVistaSecundariaTitulacion_a_Principal();
                    ctrlPresentacion.eliminarTodosHorarios();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Solo se aceptan valores positivos como alumnos de teoría y laboratorio");
                inicializar_PanelDatos();
            }

        }
    }

    public void actionPerformed_buttonCancelar(ActionEvent event) {
        ctrlPresentacion.sincronizacionVistaSecundariaTitulacion_a_Principal();
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
                //if(JOptionPane.showConfirmDialog(panelContenido, "Are you sure ?") == JOptionPane.OK_OPTION){
                ctrlPresentacion.sincronizacionVistaSecundariaTitulacion_a_Principal();
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
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.add(labelNombre);
        panelDatos.add(nombreTitulacion);
        panelDatos.add(labelAlumnosTeoria);
        panelDatos.add(alumnosTeoria);
        panelDatos.add(labelAlumnosLab);
        panelDatos.add(alumnosLab);

        if (tipoVista == TipoVista.MODIFICAR) {
            String[] datosTitulacion = ctrlPresentacion.getParametrosTitulacion(ctrlPresentacion.getTitulacionSeleccionada());
            nombreTitulacion.setText(datosTitulacion[0]);
            // numero asignaturas
            alumnosTeoria.setText(datosTitulacion[2]);
            alumnosLab.setText((datosTitulacion[3]));
        }
    }


}
