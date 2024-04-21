import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Bienvenido");
        ventana.setSize(300, 300);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.getContentPane().setBackground(new Color(240, 240, 240));
        ventana.setLayout(new BorderLayout());
        ventana.setResizable(false);

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setOpaque(false);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel etiqueta = new JLabel("<html><center>Desarrollado por:<br>Gilberto Urbina Larios<br>Carlos Alberto Santiago<br>Versión 1.0</center></html>");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 16));
        etiqueta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenido.add(etiqueta);

        panelContenido.add(Box.createVerticalStrut(30));

        JButton boton = new JButton("Abrir Analizador");
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirAnalizador(ventana);
            }
        });
        panelContenido.add(boton);

        ventana.add(panelContenido, BorderLayout.CENTER);
        ventana.setVisible(true);
    }

    // Método para abrir el analizador y cerrar la ventana actual
    private static void abrirAnalizador(JFrame ventana) {
        ventana.dispose();

        // Abrir el analizador
        Analizador.main(new String[]{});
    }
}
