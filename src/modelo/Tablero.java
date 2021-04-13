package modelo;

import java.util.Iterator;

import utiles.RespuestaColocacion;

public class Tablero {

	private int dimension = 3;
	private Tipo matriz[][] = new Tipo[dimension][dimension];
	private String errorActual = "";

	public Tipo getValorCasilla(int x, int y) {
		return matriz[x][y];
	}

	public Tablero() {
		super();
		inicializarMatriz();
	}

	private void inicializarMatriz() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				matriz[i][j] = Tipo.blanco;
			}
		}
	}

	public boolean limpiarCasilla(Coordenada coordenada) {
		if (getPosicion(coordenada) != Tipo.blanco) {
			setPosicion(coordenada, Tipo.blanco);
			return true;
		}
		return false;
	}

	private void setPosicion(Coordenada coordenada, Tipo blanco) {
		matriz[coordenada.getX()][coordenada.getY()] = blanco;

	}

	public boolean borrarCasilla(Coordenada coordenada, Tipo tipo) {
		// primero hay que borrar una casilla no bloqueada de tu turno
		if (isPropiedad(coordenada, tipo) && !comprobarCasillaBloqueada(coordenada)) {
			limpiarCasilla(coordenada);
			return true;
		}
		return false;
	}

	public boolean colocarFicha(Coordenada coordenada, Tipo tipoActual) {

		if (matriz[coordenada.getX()][coordenada.getY()] == Tipo.blanco) {
			matriz[coordenada.getX()][coordenada.getY()] = tipoActual;
//			System.out.println("coordenada puesta");
			return true;
		}
		return false;
	}

	public Tipo getPosicion(Coordenada coordenada) {
		return matriz[coordenada.getX()][coordenada.getY()];
	}

	private boolean isLibre(Coordenada coordenada) {
		return getPosicion(coordenada) == Tipo.blanco;
	}

	/**
	 * 
	 * @param coordenada
	 * @return false si encuentra una libre y true en caso contrario
	 */
	public boolean comprobarCasillaBloqueada(Coordenada coordenada) {
		for (int x = coordenada.getX() - 1; x <= coordenada.getX() + 1; x++)
			for (int y = coordenada.getY() - 1; y <= coordenada.getY() + 1; y++)
				if (x >= 0 && x < this.dimension && y >= 0 && y < this.dimension)
					if (this.isLibre(new Coordenada(x, y)))
						return false;
		return true;
	}

	public String getErrorActual() {
		return this.errorActual;
	}

	public boolean isPropiedad(Coordenada coordenada, Tipo tipo) {
		return getPosicion(coordenada) == tipo;
	}

	public boolean isTresEnRaya() {

		return hayLineaHorizontal() || hayLineaVertical() || hayLineaDiagonal();
	}

	private boolean hayLineaDiagonal() {
		boolean linea = true;
		for (int i = 0; i < matriz.length && linea; i++) {
			Tipo primeraCasilla = matriz[0][0];
			if (primeraCasilla == Tipo.blanco || primeraCasilla != matriz[i][i]) {
				linea = false;
			}
		}
		if (!linea) {
			linea = true;
			for (int i = matriz.length - 2; i >= 0 && linea; i--) {
				Tipo primeraCasilla = matriz[0][matriz.length - 1];
				if (primeraCasilla == Tipo.blanco || primeraCasilla != matriz[matriz.length - 1 - i][i]) {
					linea = false;
				}
			}
		}
		return linea;
	}

	private boolean hayLineaVertical() {
		boolean linea = false;
		for (int i = 0; i < matriz.length && !linea; i++) {
			linea = true;
			for (int j = 1; j < matriz[i].length && linea; j++) {
				Tipo primeraCasilla = matriz[0][i];
				if (primeraCasilla == Tipo.blanco || primeraCasilla != matriz[j][i]) {
					linea = false;
				}
			}
		}

		return linea;

	}

	private boolean hayLineaHorizontal() {
		boolean linea = false;
		for (int i = 0; i < matriz.length && !linea; i++) {
			linea = true;
			for (int j = 1; j < matriz[i].length && linea; j++) {
				Tipo primeraCasilla = matriz[i][0];
				if (primeraCasilla == Tipo.blanco || primeraCasilla != matriz[i][j]) {
					linea = false;
				}
			}
		}

		return linea;
	}

}
