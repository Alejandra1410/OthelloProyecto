package Model;

public class Tablero {
    private Ficha[][] tablero;
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;
 
    public Tablero() {
        tablero = new Ficha[12][12];
        jugador1 = new Jugador("Jugador 1", "B");
        jugador2 = new Jugador("Jugador 2", "P");
        jugadorActual = jugador1;

        tablero[5][5] = new Ficha("B");
        tablero[6][6] = new Ficha("B");
        tablero[5][6] = new Ficha("P");
        tablero[6][5] = new Ficha("P");
    }

    public Ficha[][] getTablero() {
        return tablero;
    }

    public void setTablero(Ficha[][] tablero) {
        this.tablero = tablero;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public void setJugadorActual(Jugador jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    public boolean MovimientoValido(int fila, int columna) {
        if (tablero[fila][columna] != null) {
            return false;
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (verificarRodeo(fila, columna, i, j, jugadorActual.getColor(), false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean verificarRodeo(int fila, int columna, int dirFila, int dirColumna, String colorJugador, boolean encontradaFichaContraria) {
        int i = fila + dirFila;
        int j = columna + dirColumna;

        if (i < 0 || i >= 12 || j < 0 || j >= 12) {
            return false;
        }

        if (tablero[i][j] == null) {
            return false;
        }

        if (tablero[i][j].getColor().equals(colorJugador)) {
            return encontradaFichaContraria;
        } else {
            encontradaFichaContraria = true;
        }

        return verificarRodeo(i, j, dirFila, dirColumna, colorJugador, encontradaFichaContraria);
    }

    public void colocarFicha(int fila, int columna) {
        if (MovimientoValido(fila, columna)) {
            tablero[fila][columna] = new Ficha(jugadorActual.getColor());

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    if (verificarRodeo(fila, columna, i, j, jugadorActual.getColor(), false)) {
                        realizarRodeoRecursivo(fila, columna, i, j, jugadorActual.getColor());
                    }
                }
            }
            cambiarTurno();
        }
    }

    public int contarFichasJugador1() {
        int contador = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (tablero[i][j] != null && tablero[i][j].getColor().equals(jugador1.getColor())) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public int contarFichasJugador2() {
        int contador = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (tablero[i][j] != null && tablero[i][j].getColor().equals(jugador2.getColor())) {
                    contador++;
                }
            }
        }
        return contador;
    }

    private void realizarRodeoRecursivo(int fila, int columna, int dirFila, int dirColumna, String colorJugador) {
        int i = fila + dirFila;
        int j = columna + dirColumna;

        while (tablero[i][j].getColor().equals(colorJugador) == false) {
            tablero[i][j].cambiarColor();
            i += dirFila;
            j += dirColumna;
        }
    }

    public int movimientosPosibles(String colorJugador) {
        int movimientosPosibles = 0;

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (MovimientoValido(i, j)) {
                    if (jugadorActual.getColor().equals(colorJugador)) {
                        movimientosPosibles++;
                    }
                }
            }
        }

        return movimientosPosibles;
    }

    public void cambiarTurno() {
        if (jugadorActual == jugador1) {
            jugadorActual = jugador2;
        } else {
            jugadorActual = jugador1;
        }
    }
}
