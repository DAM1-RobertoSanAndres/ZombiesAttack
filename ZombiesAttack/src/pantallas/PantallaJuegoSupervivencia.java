package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import base.PanelJuego;
import base.SpriteFondo;
import base.SpriteProtagonista;
import base.SpriteZombie;

public class PantallaJuegoSupervivencia implements Pantalla {
	final int[] RASPECTO = new int[] { 759007, 1312358, 1401826 };
	final int RASPECTOPROTA = 1938320;
	int tamañoPersonajesY;

	double tiempoInicial;
	double tiempoDeJuego;
	Font fuenteTiempo;
	private DecimalFormat formatoDecimal;

	// Imagenes
	Image imagenAux = null;
	Image imagenFondo = null;
	Image imagenProtagonista = null;
	ArrayList<Image> imagenesZombie = null;
	int[] posicionesLimiteNuevas = new int[5];
	int[] posicionesPersonajes = new int[4];

	ArrayList<SpriteFondo> spritesFondo;
	SpriteProtagonista protagonista;
	ArrayList<SpriteZombie> spritesZombie;

	// Posicion en pantalla del ultimo zombie generado
	int ultimoCreado = -1;
	int ultimoSegundoZombies = -1;
	double ultimoSegundoRedimensionado = -1;
	int tamañoAntiguo = -1;
	// Variable para el panel:
	PanelJuego panelJuego;

	public PantallaJuegoSupervivencia(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
		inicializarPantalla();
		redimensionarPantalla();
	}

	@Override
	public void inicializarPantalla() {
		spritesZombie = new ArrayList<SpriteZombie>();
		spritesFondo = new ArrayList<SpriteFondo>();
		imagenesZombie = new ArrayList<Image>();

		generarImagenSprites();
		generarPosiciones();
		crearObjetosBase();

		// El tiempo de juego es cero
		tiempoDeJuego = 0;
		// La letra para pintar el tiempo
		fuenteTiempo = new Font("MiLetra", Font.BOLD, 20);
		// Formato decimal
		formatoDecimal = new DecimalFormat("#.##");
		tiempoInicial = System.nanoTime();
		tamañoAntiguo = panelJuego.getHeight();
	}

	public void generarImagenSprites() {
		tamañoPersonajesY = panelJuego.getHeight() / 8;

		try {
			// Renderizo el fondo
			imagenFondo = ImageIO.read(new File("Imagenes/carretera.jpg"));
			imagenFondo = imagenFondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(),
					Image.SCALE_SMOOTH);
			// Renderizo al protagonista
			imagenProtagonista = ImageIO.read(new File("Imagenes/protagonista.png"));
			imagenProtagonista = imagenProtagonista.getScaledInstance((tamañoPersonajesY * RASPECTOPROTA) / 1000000,
					tamañoPersonajesY, Image.SCALE_SMOOTH);
			// Renderizo a los zombies
			for (int i = 0; i < 3; i++) {
				imagenAux = ImageIO.read(new File("Imagenes/zombie" + i + "LowRes.png"));
				imagenAux = imagenAux.getScaledInstance((tamañoPersonajesY * RASPECTO[i]) / 1000000, tamañoPersonajesY,
						Image.SCALE_SMOOTH);
				if (imagenesZombie.size() > 2) {
					imagenesZombie.remove(0);
				}
				imagenesZombie.add(imagenAux);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generarPosiciones() {
		int j = 1;
		int k = 0;
		for (int i = 0; i < posicionesPersonajes.length; i++) {
			posicionesPersonajes[i] = panelJuego.getHeight() / 8 * j - panelJuego.getHeight() / 16;
			posicionesLimiteNuevas[i] = panelJuego.getHeight() / 8 * k;
			j += 2;
			k += 2;
		}
		posicionesLimiteNuevas[4] = panelJuego.getHeight();

	}

	public void crearObjetosBase() {
		// Genero imagen de fondo
		spritesFondo.add(new SpriteFondo(panelJuego.getWidth(), panelJuego.getHeight(), 0, 0, 10, imagenFondo));
		spritesFondo.add(new SpriteFondo(panelJuego.getWidth(), panelJuego.getHeight(), panelJuego.getWidth(), 0, 10,
				imagenFondo));
		protagonista = new SpriteProtagonista((tamañoPersonajesY * RASPECTOPROTA) / 1000000, tamañoPersonajesY,
				panelJuego.getWidth() / 20, posicionesPersonajes[0], imagenProtagonista);
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		for (int i = 0; i < spritesFondo.size(); i++) {
			spritesFondo.get(i).pintarSpriteEnMundo(g);
		}

		// Pintamos los zombies:
		if (!spritesZombie.isEmpty()) {
			for (SpriteZombie zombie : spritesZombie) {
				zombie.pintarSpriteEnMundo(g);
			}
		}

		// Pintamos al protagonista
		protagonista.pintarSpriteEnMundo(g);

		// Pintamos el contador de tiempo:
		g.setColor(Color.WHITE);
		g.setFont(fuenteTiempo);
		g.drawString(formatoDecimal.format(tiempoDeJuego / 1000000000), 25, 25);
	}

	public void generarZombies() {
		Random rdZombie = new Random();
		int nZombie = rdZombie.nextInt(3);

		int intTiempoDeJuego = (int) (tiempoDeJuego / 1000000000);
		if (intTiempoDeJuego != ultimoSegundoZombies) {
			Random rdCarril = new Random();
			int posicionCarril;
			do {
				posicionCarril = rdCarril.nextInt(posicionesPersonajes.length);
			} while (ultimoCreado == posicionCarril);
			ultimoCreado = posicionCarril;

			int posY = posicionesPersonajes[posicionCarril];

			SpriteZombie creador;
			int posX = panelJuego.getWidth();

			creador = new SpriteZombie(nZombie, (tamañoPersonajesY * RASPECTO[nZombie]) / 1000000, tamañoPersonajesY,
					posX, posY, 10 - nZombie, 90, imagenesZombie.get(nZombie), posicionCarril);
			spritesZombie.add(creador);
			ultimoSegundoZombies = intTiempoDeJuego;
		}
	}

	@Override
	public void ejecutarFrame() {
		generarZombies();
		panelJuego.repaint();
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		colisiones();
		moverSpritesZombies();
		actualizarTiempo();
	}

	@Override
	public void moverRaton(MouseEvent e) {

		for (int i = 0; i < posicionesLimiteNuevas.length - 1; i++) {
			if (e.getY() > posicionesLimiteNuevas[i] && e.getY() < posicionesLimiteNuevas[i + 1]) {
				protagonista.setPosY(posicionesPersonajes[i]);
			}
		}
	}

	@Override
	public void pulsarRaton(MouseEvent e) {

	}

	@Override
	public void redimensionarPantalla() {

		generarImagenSprites();

		generarPosiciones();
		crearObjetosBase();
		redimensionarZombies();
		tamañoAntiguo = panelJuego.getHeight();
	}

	public void redimensionarZombies() {
		if (!spritesZombie.isEmpty()) {
			for (int i = 0; i < spritesZombie.size(); i++) {
				spritesZombie.get(i)
						.setAncho((tamañoPersonajesY * RASPECTO[spritesZombie.get(i).getnZombie()]) / 1000000);
				spritesZombie.get(i).setAlto(tamañoPersonajesY);
				spritesZombie.get(i).setImagenAuxiliar(imagenesZombie.get(spritesZombie.get(i).getnZombie()));
				spritesZombie.get(i).setPosY(posicionesPersonajes[spritesZombie.get(i).getCarril()]);
			}
		}

	}

	private void moverSpritesZombies() {
		SpriteZombie aux;

		for (int i = 0; i < spritesZombie.size(); i++) {
			aux = spritesZombie.get(i);
			aux.moverSprite();
		}
		// usar else si no se usa este metodo
		// SpriteFondo auxF;
		// for (int i = 0; i < spritesFondo.size(); i++) {
		// auxF = spritesFondo.get(i);
		// auxF.moverSprite();
		// }
	}

	/**
	 * Metodo que actualiza el tiempo que ha transcurrido de juego
	 */
	public void actualizarTiempo() {
		float tiempoActual = System.nanoTime(); // <--AquÃ­ se mide el nuevo tiempo. En esta precisa instrucciÃ³n.
		tiempoDeJuego = tiempoActual - tiempoInicial;
	}

	public void colisiones() {
		for (int i = 0; i < spritesZombie.size(); i++) {
			if (spritesZombie.get(i).getPosX() + spritesZombie.get(i).getAncho() <= 0) {// por la izquierda
				spritesZombie.remove(i);
			}
		}
		for (int i = 0; i < spritesFondo.size(); i++) {
			if (spritesFondo.get(i).getPosX() < -(panelJuego.getWidth())) {
				spritesFondo.remove(i);
				spritesFondo.add(new SpriteFondo(panelJuego.getWidth(), panelJuego.getHeight(), panelJuego.getWidth(),
						0, 10, imagenFondo));
			}
		}
	}
}
