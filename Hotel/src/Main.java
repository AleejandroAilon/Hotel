import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private JFrame ventanaPrincipal;
    private JButton botonConsultar, botonSalida;
    private Habitacion[] habitaciones;
    private Map<Integer, Huesped> mapaHuespedes;

    public Main() {
        habitaciones = new Habitacion[10];
        mapaHuespedes = new HashMap<>();
        inicializarHabitaciones();
        crearMenuPrincipal();
    }

    private void inicializarHabitaciones() {
        for (int i = 0; i < 10; i++) {
            double precio = (i < 5) ? 120 : 160;
            habitaciones[i] = new Habitacion(i + 1, precio, true);
        }
    }

    private void crearMenuPrincipal() {
        ventanaPrincipal = new JFrame("Gestión del Hotel");
        ventanaPrincipal.setSize(600, 400);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLayout(new BorderLayout());

        // Titulo
        JLabel titulo = new JLabel("Sistema de gestión de un hotel", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        ventanaPrincipal.add(titulo, BorderLayout.NORTH);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBackground(new Color(240, 248, 255));

        botonConsultar = new JButton("Consultar habitaciones");
        botonSalida = new JButton("Salida de huéspedes");

        botonConsultar.setFont(new Font("Arial", Font.BOLD, 18));
        botonSalida.setFont(new Font("Arial", Font.PLAIN, 18));

        botonConsultar.setPreferredSize(new Dimension(250, 50));
        botonSalida.setPreferredSize(new Dimension(250, 50));

        botonConsultar.addActionListener(e -> consultarHabitaciones());
        botonSalida.addActionListener(e -> procesarSalidaHuespedes());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.add(botonConsultar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(botonSalida, gbc);

        ventanaPrincipal.add(panelPrincipal, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(149, 211, 69));
        panelInferior.setPreferredSize(new Dimension(ventanaPrincipal.getWidth(), 40));
        ventanaPrincipal.add(panelInferior, BorderLayout.SOUTH);

        ventanaPrincipal.setVisible(true);
    }

    private void consultarHabitaciones() {
        JFrame ventanaConsulta = new JFrame("Consultar habitaciones");
        ventanaConsulta.setSize(500, 600);
        ventanaConsulta.setLayout(new BorderLayout());

        JLabel etiquetaInfo = new JLabel("Seleccione una habitación para registrar la entrada", JLabel.CENTER);
        etiquetaInfo.setFont(new Font("Arial", Font.BOLD, 18));
        ventanaConsulta.add(etiquetaInfo, BorderLayout.NORTH);

        JPanel panelHabitaciones = new JPanel(new GridLayout(10, 1, 5, 5));
        panelHabitaciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Habitacion habitacion : habitaciones) {
            JButton botonHabitacion = new JButton("Habitación " + habitacion.getNumero() + " - " + (habitacion.isDisponible() ? "Disponible" : "No disponible"));
            botonHabitacion.setFont(new Font("Arial", Font.PLAIN, 16));
            botonHabitacion.setEnabled(habitacion.isDisponible());
            botonHabitacion.addActionListener(e -> {
                if (habitacion.isDisponible()) {
                    registrarHuesped(habitacion);
                    ventanaConsulta.dispose();
                } else {
                    JOptionPane.showMessageDialog(ventanaConsulta, "Habitación no disponible. Seleccione otra", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            panelHabitaciones.add(botonHabitacion);
        }

        ventanaConsulta.add(panelHabitaciones, BorderLayout.CENTER);
        ventanaConsulta.setVisible(true);
    }

    private void registrarHuesped(Habitacion habitacion) {
        JFrame ventanaRegistro = new JFrame("Registrar huésped");
        ventanaRegistro.setSize(500, 400);
        ventanaRegistro.setLayout(new GridBagLayout());
        ventanaRegistro.getContentPane().setBackground(new Color(240, 255, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel etiquetaNombre = new JLabel("Nombre: ");
        etiquetaNombre.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        ventanaRegistro.add(etiquetaNombre, gbc);

        JTextField campoNombre = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        ventanaRegistro.add(campoNombre, gbc);

        JLabel etiquetaApellidos = new JLabel("Apellidos: ");
        etiquetaApellidos.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        ventanaRegistro.add(etiquetaApellidos, gbc);

        JTextField campoApellidos = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        ventanaRegistro.add(campoApellidos, gbc);

        JLabel etiquetaDPI = new JLabel("DPI: ");
        etiquetaDPI.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        ventanaRegistro.add(etiquetaDPI, gbc);

        JTextField campoDPI = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        ventanaRegistro.add(campoDPI, gbc);

        JLabel etiquetaFechaIngreso = new JLabel("Fecha: ");
        etiquetaFechaIngreso.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        ventanaRegistro.add(etiquetaFechaIngreso, gbc);

        JTextField campoFechaIngreso = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        ventanaRegistro.add(campoFechaIngreso, gbc);

        JButton botonRegistrar = new JButton("Registrar");
        botonRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        ventanaRegistro.add(botonRegistrar, gbc);

        botonRegistrar.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            String apellidos = campoApellidos.getText().trim();
            String dpi = campoDPI.getText().trim();
            String fechaIngresoTexto = campoFechaIngreso.getText().trim();

            if (nombre.isEmpty() || apellidos.isEmpty() || dpi.isEmpty() || fechaIngresoTexto.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaRegistro, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaIngreso;
            try {
                fechaIngreso = sdf.parse(fechaIngresoTexto);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(ventanaRegistro, "Formato inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Huesped huesped = new Huesped(nombre, apellidos, dpi, fechaIngreso);
            mapaHuespedes.put(habitacion.getNumero(), huesped);
            habitacion.setDisponible(false);
            JOptionPane.showMessageDialog(ventanaRegistro, "Huésped registrado.");
            ventanaRegistro.dispose();
        });

        ventanaRegistro.setVisible(true);
    }

    private void procesarSalidaHuespedes() {
        JFrame ventanaSalida = new JFrame("Salida de huéspedes");
        ventanaSalida.setSize(450, 250);
        ventanaSalida.setLayout(new GridBagLayout());
        ventanaSalida.getContentPane().setBackground(new Color(255, 245, 238));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel etiquetaNumeroHabitacion = new JLabel("Número de habitación:");
        etiquetaNumeroHabitacion.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        ventanaSalida.add(etiquetaNumeroHabitacion, gbc);

        JTextField campoNumeroHabitacion = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        ventanaSalida.add(campoNumeroHabitacion, gbc);

        JButton botonProcesarSalida = new JButton("Procesar Salida");
        botonProcesarSalida.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        ventanaSalida.add(botonProcesarSalida, gbc);

        botonProcesarSalida.addActionListener(e -> {
            String textoNumeroHabitacion = campoNumeroHabitacion.getText().trim();

            try {
                int numeroHabitacion = Integer.parseInt(textoNumeroHabitacion);
                if (numeroHabitacion < 1 || numeroHabitacion > 10) {
                    JOptionPane.showMessageDialog(ventanaSalida, "Número de habitación no válido", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Habitacion habitacion = habitaciones[numeroHabitacion - 1];
                Huesped huesped = mapaHuespedes.get(numeroHabitacion);

                if (huesped == null) {
                    JOptionPane.showMessageDialog(ventanaSalida, "No hay un huésped registrado en esta habitación", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                mostrarVentanaSalida(numeroHabitacion, huesped);
                ventanaSalida.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventanaSalida, "Número de habitación inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ventanaSalida.setVisible(true);
    }

    private void mostrarVentanaSalida(int numeroHabitacion, Huesped huesped) {
        JFrame ventanaSalida = new JFrame("Procesar salida");
        ventanaSalida.setSize(500, 300);
        ventanaSalida.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel etiquetaFechaSalida = new JLabel("Fecha de salida: ");
        etiquetaFechaSalida.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        ventanaSalida.add(etiquetaFechaSalida, gbc);

        JTextField campoFechaSalida = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        ventanaSalida.add(campoFechaSalida, gbc);

        JButton botonCalcular = new JButton("Calcular total");
        botonCalcular.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        ventanaSalida.add(botonCalcular, gbc);

        botonCalcular.addActionListener(e -> {
            String fechaSalidaTexto = campoFechaSalida.getText().trim();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaSalida;

            try {
                fechaSalida = sdf.parse(fechaSalidaTexto);
                if (fechaSalida.before(huesped.getCheckIn())) {
                    JOptionPane.showMessageDialog(ventanaSalida, "La fecha de salida debe ser mayor a la de ingreso", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                long diferencia = fechaSalida.getTime() - huesped.getCheckIn().getTime();
                int dias = (int) (diferencia / (1000 * 60 * 60 * 24));

                // Obtener el precio de la habitación
                double precioPorNoche = habitaciones[numeroHabitacion - 1].getPrecio();

                // Calcular el total basado en el número de habitación
                double total = dias * precioPorNoche;
                JOptionPane.showMessageDialog(ventanaSalida, "Total a pagar: $" + total);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(ventanaSalida, "Formato de fecha incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ventanaSalida.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}

class Habitacion {
    private int numero;
    private double precio;
    private boolean disponible;

    public Habitacion(int numero, double precio, boolean disponible) {
        this.numero = numero;
        this.precio = precio;
        this.disponible = disponible;
    }

    public int getNumero() {
        return numero;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}

class Huesped {
    private String nombre;
    private String apellidos;
    private String dpi;
    private Date checkIn;

    public Huesped(String nombre, String apellidos, String dpi, Date checkIn) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dpi = dpi;
        this.checkIn = checkIn;
    }

    public Date getCheckIn() {
        return checkIn;
    }
}
