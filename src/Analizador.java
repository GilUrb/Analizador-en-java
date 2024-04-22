import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analizador {
    // Array que almacena todas las palabras reservadas del lenguaje Python
    public static final String[] PALABRAS_RESERVADAS = {"if", "else", "elif", "while", "for", "def", "return", "print", "and", "or", "not", "in", "is", "True", "False", "None"};

    // Array que almacena todos los símbolos permitidos en Python
    public static final String[] OPERADORES_ARITMETICOS = {"+", "-", "*", "/", "%"};

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Analizador Léxico de Python");
        ventana.setSize(800, 600);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear entradas de texto
        JLabel funcionLabel = new JLabel("Ingrese valor:");
        funcionLabel.setBounds(10, 5, 100, 50);

        JTextArea funcion = new JTextArea();
        JScrollPane scrollPaneTexto = new JScrollPane(funcion);
        scrollPaneTexto.setBounds(10, 40, 560, 250);

        //botón
        JButton boton = new JButton("Analizar");
        boton.setBounds(10, 300, 100, 30);

        // Crear tabla con modelo de datos
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Token");
        modelo.addColumn("Tipo");
        modelo.addColumn("Fila");
        modelo.addColumn("Columna");

        JTable tabla = new JTable(modelo);
        JScrollPane scrollPaneTabla = new JScrollPane(tabla);
        scrollPaneTabla.setBounds(10, 350, 760, 200);

        // Agregar elementos a la ventana
        ventana.add(funcionLabel);
        ventana.add(scrollPaneTexto);
        ventana.add(boton);
        ventana.add(scrollPaneTabla);

        // Acción del botón Analizar
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar la tabla antes de agregar nuevos datos
                modelo.setRowCount(0);

                // Obtener el texto ingresado
                String textoIngresado = funcion.getText();
                ArrayList<Token> tokens = new ArrayList<>();

                // Patrón para identificar los tokens
                Pattern pattern = Pattern.compile("(\\b\\w+\\b)|(\\d+\\.\\d+|\\d+)|([()\\[\\]{}.,;:+\\-*/%=<>!]|==|!=|<=|>=|[#])");
                Matcher matcher = pattern.matcher(textoIngresado);
                int fila = 1;
                int columna = 1;

                while (matcher.find()) {
                    String token = matcher.group(1);
                    if (token == null) {
                        token = matcher.group(2);
                    }
                    if (token == null) {
                        token = matcher.group(3);
                    }
                    Token.Tipo tipo;

                    // Actualizar la fila y la columna
                    columna = getColumna(textoIngresado, matcher.start());
                    fila = getFila(textoIngresado, matcher.start());

                    if (isPalabraReservada(token)) {
                        tipo = Token.Tipo.PALABRA_RESERVADA;
                    } else if (isOperadorAritmetico(token)) {
                        tipo = Token.Tipo.OPERADOR_ARITMETICO;
                    } else if (isSimbolo(token)) {
                        tipo = Token.Tipo.SIMBOLO;
                    } else {
                        // Verificar si es un número entero o decimal
                        if (token.matches("\\d+")) {
                            tipo = Token.Tipo.NUMERO_ENTERO;
                        } else if (token.matches("\\d+\\.\\d+")) {
                            tipo = Token.Tipo.NUMERO_DECIMAL;
                        } else {
                            tipo = Token.Tipo.CADENA;
                        }
                    }
                    tokens.add(new Token(token, tipo, fila, columna));
                }

                // Agregar tokens a la tabla
                for (Token token : tokens) {
                    modelo.addRow(new Object[]{token.getValor(), token.getTipo().toString(), token.getFila(), token.getColumna()});
                }
            }
        });

        ventana.setLayout(null);
        ventana.setVisible(true);
    }

    private static boolean isPalabraReservada(String palabra) {
        for (String pr : PALABRAS_RESERVADAS) {
            if (palabra.equalsIgnoreCase(pr)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSimbolo(String simbolo) {
        for (String s : OPERADORES_ARITMETICOS) {
            if (simbolo.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isOperadorAritmetico(String operador) {
        for (String op : OPERADORES_ARITMETICOS) {
            if (operador.equals(op)) {
                return true;
            }
        }
        return false;
    }

    private static int getColumna(String texto, int indice) {
        String[] lineas = texto.substring(0, indice).split("\n");
        return lineas[lineas.length - 1].length() + 1;
    }

    private static int getFila(String texto, int indice) {
        return texto.substring(0, indice).split("\n").length;
    }

    static class Token {
        enum Tipo {
            PALABRA_RESERVADA, SIMBOLO, CADENA, NUMERO_ENTERO, NUMERO_DECIMAL, OPERADOR_ARITMETICO
        }

        private final String valor;
        private final Tipo tipo;
        private final int fila;
        private final int columna;

        public Token(String valor, Tipo tipo, int fila, int columna) {
            this.valor = valor;
            this.tipo = tipo;
            this.fila = fila;
            this.columna = columna;
        }

        public String getValor() {
            return valor;
        }

        public Tipo getTipo() {
            return tipo;
        }

        public int getFila() {
            return fila;
        }

        public int getColumna() {
            return columna;
        }
    }
}
