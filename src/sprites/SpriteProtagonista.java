package sprites;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author RobertoSanAndresAsensio Clase Sprite. Representa un elemento pintable
 *         y colisionable del juego.
 */
public class SpriteProtagonista {

	private BufferedImage buffer;
	// Variables de dimension
	private int ancho;
	private int alto;
	// Variables de colocacion
	private int posX;
	private int posY;
	
	
	Image imagenAuxiliar = null;
	
	// Vida del personaje
	int vida = 100;

	/**
	 * Constructor para un Sprite
	 * 
	 * @param ancho
	 *            Ancho que ocupa el Sprite (en pixels)
	 * @param alto
	 *            Altura que ocupa el Sprite (en pixels)
	 * @param posX
	 *            posicion horizontal del sprite en el mundo.
	 * @param posY
	 *            posicion vertical del Sprite en el mundo. El origen se sitúa en
	 *            la parte superior.
	 * @param velocidadX
	 *            velocidad horizontal del Sprite.
	 * @param ruta
	 *            es la ruta de la imagen a cargar
	 */
	public SpriteProtagonista(int ancho, int alto, int posX, int posY, Image imagenAuxiliar) {
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.imagenAuxiliar = imagenAuxiliar;
		actualizarBuffer();
	}

	/**
	 * Metodo para actualizar el buffer que guarda cada Sprite.
	 * 
	 * @param ruta
	 *            es la ruta de la imagen a cargar
	 */
	public void actualizarBuffer() {
		// Creo un nuevo buffer del tama�o adecuado
		buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
		Graphics g = buffer.getGraphics();

		// Intento pintarlo con una imagen
		g.drawImage(imagenAuxiliar, 0, 0, null);
	}

	/**
	 * Metodo que pinta el Sprite en el mundo teniendo en cuenta las
	 * características propias del Sprite.
	 * 
	 * @param g
	 *            Es el Graphics del mundo que se utilizara para pintar el Sprite.
	 */
	public void pintarSpriteEnMundo(Graphics g) {
		g.drawImage(buffer, posX, posY, null);
	}

	// Metodos para obtener:
	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public BufferedImage getBuffer() {
		return buffer;
	}

	// Metodos para cambiar:
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setBuffer(BufferedImage buffer) {
		this.buffer = buffer;
	}
}
