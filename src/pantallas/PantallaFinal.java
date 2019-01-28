package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import base.PanelJuego;

public class PantallaFinal implements Pantalla {
	int destruidos=0;
	Image fondo = null;
	Image fondoEscalado = null;
	PanelJuego panelJuego;
	Color colorLetra = Color.RED;
	double tiempoDeJuego;
	private DecimalFormat formatoDecimal= new DecimalFormat("#.##");;
	private Font fuenteTiempo= new Font("MiLetra", Font.PLAIN, 25);
	private Font fueteLetra = new Font("Arial", Font.ITALIC/Font.BOLD, 60);
	boolean victoria;

	public PantallaFinal(PanelJuego panelJuego, double tiempoDeJuego) {
		this.panelJuego = panelJuego;
		this.tiempoDeJuego = tiempoDeJuego;
		victoria=true;
		inicializarPantallaVictoria();
		redimensionarPantalla();
	}
	public PantallaFinal(PanelJuego panelJuego, double tiempoDeJuego, int destruidos) {
		this.panelJuego = panelJuego;
		this.tiempoDeJuego = tiempoDeJuego;
		this.destruidos=destruidos;
		victoria=false;
		inicializarPantalla();
		redimensionarPantalla();
	}

	@Override
	public void inicializarPantalla() {
		try {
			fondo = ImageIO.read(new File("Imagenes/fondoDerrota.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void inicializarPantallaVictoria() {
		try {
			fondo = ImageIO.read(new File("Imagenes/fondoVictoria.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		rellenarFondo(g);		
		g.setColor(colorLetra);
		g.setFont(fueteLetra);
		
		if (victoria) {
			g.drawString("¡VICTORIA!", panelJuego.getWidth() / 2-30, panelJuego.getHeight() / 2+80);
		}else {
			g.drawString("DERROTA", panelJuego.getWidth() / 2-30, panelJuego.getHeight() / 2+80);
		}
		g.setFont(fuenteTiempo);
		
		g.drawString("Tiempo vivo:"+formatoDecimal.format(tiempoDeJuego / 1000000000), panelJuego.getWidth() / 2, panelJuego.getHeight() / 2+120);
		if (destruidos!=0) {
			g.drawString("Zombies asesinados:"+destruidos, panelJuego.getWidth() / 2, panelJuego.getHeight() / 2+150);
		}
	}

	@Override
	public void ejecutarFrame() {
		panelJuego.repaint();
	}

	@Override
	public void moverRaton(MouseEvent e) {

	}

	@Override
	public void pulsarRaton(MouseEvent e) {
		panelJuego.setPantallaActual(new PantallaInicial(panelJuego));
	}

	@Override
	public void redimensionarPantalla() {
		fondoEscalado = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
	}

	private void rellenarFondo(Graphics g) {
		g.drawImage(fondoEscalado, 0, 0, null);
	}
}
