package Controller;

import Model.Ficha;
import Model.Jugador;
import Model.Tablero;
import View.Othello;
import View.View;
import java.awt.Color;
import javax.swing.JOptionPane;

public class OthelloController {
    private static OthelloController instance;
    private Tablero tablero;
    private Othello othello;
    private View view;

    private OthelloController(Tablero tablero, Othello othello, View view) {
        this.tablero = tablero;
        this.othello = othello;
        this.view = view;
    }

    public static OthelloController getInstance(Tablero tablero, Othello othello, View view) {
        if (instance == null) {
            instance = new OthelloController(tablero, othello, view);
        }
        return instance;
    }

    
    public static void resetInstance() {
        instance = null;
    }

    public void crearTablero() {
        tablero = new Tablero();
        actualizarTablero();
    }

    public void hacerMovimiento(int fila, int columna) {
        try {
            if (tablero.MovimientoValido(fila, columna)) {
                tablero.colocarFicha(fila, columna);
                actualizarTablero();

                othello.actualizarTurno();
                othello.actualizarMarcador();

                if (tablero.movimientosPosibles(tablero.getJugadorActual().getColor()) == 0) {
                    Jugador ganador = determinarGanador();
                    if (ganador != null) {
                        othello.displayMessage(ganador.getNombre() + " ha ganado");
                    } else {
                        othello.displayMessage("¡Empate!");
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarTablero() {
        Ficha[][] estadoTablero = tablero.getTablero();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (estadoTablero[i][j] != null) {
                    if (estadoTablero[i][j].getColor().equals("B")) {
                        othello.botonesTablero[i][j].setBackground(Color.BLACK);
                    } else {
                        othello.botonesTablero[i][j].setBackground(Color.PINK);
                    }
                } else {
                    if (tablero.MovimientoValido(i, j)) {
                        othello.botonesTablero[i][j].setBackground(Color.GRAY);
                    } else {
                        othello.botonesTablero[i][j].setBackground(Color.WHITE);
                    }
                }
            }
            othello.actualizarTurno();
            othello.actualizarMarcador();
        }
    }

    private Jugador determinarGanador() {
        int fichasJugador1 = tablero.contarFichasJugador1();
        int fichasJugador2 = tablero.contarFichasJugador2();

        if (fichasJugador1 > fichasJugador2) {
            return tablero.getJugador1();
        } else if (fichasJugador2 > fichasJugador1) {
            return tablero.getJugador2();
        } else {
            if (tablero.movimientosPosibles(tablero.getJugadorActual().getColor()) == 0) {
                return null;
            } else {
                tablero.cambiarTurno();
                return determinarGanador();
            }
        }
    }

    public void reiniciar() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                othello.botonesTablero[i][j].setBackground(Color.WHITE);
            }
        }
    }

    public void pedirNombresDeJugadores() {
        String nombreJugador1 = obtenerNombreValido("Nombre del Jugador 1 :");
        String nombreJugador2 = obtenerNombreValido("Nombre del Jugador 2 :");

        if (nombreJugador1 != null && !nombreJugador1.isEmpty()) {
            tablero.getJugador1().establecerNombre(nombreJugador1);
        }

        if (nombreJugador2 != null && !nombreJugador2.isEmpty()) {
            tablero.getJugador2().establecerNombre(nombreJugador2);
        }
    }

    private String obtenerNombreValido(String mensaje) {
        String nombre = "";
        while (true) {
            nombre = JOptionPane.showInputDialog(mensaje);
            if (nombre != null && nombre.length() <= 8) {
                return nombre;
            } else {
                JOptionPane.showMessageDialog(null, "El nombre debe tener menos o 8 letras.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public Jugador getJugador1() {
        return tablero.getJugador1();
    }

    public Jugador getJugador2() {
        return tablero.getJugador2();
    }

    public Jugador getJugadorActual() {
        return tablero.getJugadorActual();
    }

    public int contarFichasJugador1() {
        return tablero.contarFichasJugador1();
    }

    public int contarFichasJugador2() {
        return tablero.contarFichasJugador2();
    }
}
