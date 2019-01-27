package base;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author RobertoSanAndresAsensio Clase Sprite. Representa un elemento pintable
 *         y colisionable del juego.
 */
public class SpriteZombie {
	private int nZombie;
	private BufferedImage buffer;
	// Variables de dimension
	private int ancho;
	private int alto;
	// Variables de colocacion
	private int posX;
	private int posY;
	// Variables para la velocidad
	private int velocidadX;
	Image imagenAuxiliar = null;
	int vida;
	int carril;

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
	public SpriteZombie(int nZombie, int ancho, int alto, int posX, int posY, int velocidadX,int vida, Image imagenAuxiliar, int carril) {
		this.nZombie = nZombie;
		this.ancho = ancho;
		this.alto = alto;
		this.posX = posX;
		this.posY = posY;
		this.velocidadX = velocidadX;
		this.vida=vida;
		this.imagenAuxiliar=imagenAuxiliar;
		this.carril=carril;
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
	 * Metodo que me permite comprobar si dos Sprites colisionan
	 * 
	 * @param protagonista
	 * @return
	 */
	public boolean colisionan(SpriteProtagonista protagonista) {
		// Checkeamos si comparten algun espacio a lo ancho:
		boolean colisionAncho = false;
		if (posX < protagonista.getPosX()) {
			colisionAncho = ancho + posX > protagonista.getPosX();
		} else {
			colisionAncho = protagonista.getAncho() + protagonista.getPosX() > posX;
		}

		// Checkeamos si comparten algun espacio a lo alto:
		boolean colisionAlto = false;
		if (posY < protagonista.getPosY()) {
			colisionAlto = alto > protagonista.getPosY() - posY;
		} else {
			colisionAlto = protagonista.getAlto() > posY - protagonista.getPosY();
		}

		return colisionAncho && colisionAlto;
	}

	/**
	 * Metodo para mover el Sprite por el mundo.
	 * 
	 * @param anchoMundo
	 *            ancho del mundo sobre el que se mueve el Sprite
	 * @param altoMundo
	 *            alto del mundo sobre el que se mueve el Sprite
	 */

	/**
	 * Actualiza las posiciones teniendo en cuenta la velocidad.
	 */
	public void moverSprite() {
		posX = posX - velocidadX;
	}

	/**
	 * Método que pinta el Sprite en el mundo teniendo en cuenta las
	 * características propias del Sprite.
	 * 
	 * @param g
	 *            Es el Graphics del mundo que se utilizará para pintar el Sprite.
	 */
	public void pintarSpriteEnMundo(Graphics g) {
		g.drawImage(buffer, posX, posY, null);
	}

	// Metodos para obtener:
	
	public int getAncho() {
		return ancho;
	}

	public int getCarril() {
		return carril;
	}

	public void setCarril(int carril) {
		this.carril = carril;
	}

	public int getnZombie() {
		return nZombie;
	}

	public void setnZombie(int nZombie) {
		this.nZombie = nZombie;
	}

	public Image getImagenAuxiliar() {
		return imagenAuxiliar;
	}

	public void setImagenAuxiliar(Image imagenAuxiliar) {
		this.imagenAuxiliar = imagenAuxiliar;
		actualizarBuffer();
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
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

	public int getVelocidadX() {
		return velocidadX;
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

	public void setVelocidadX(int velocidadX) {
		this.velocidadX = velocidadX;
	}

}
