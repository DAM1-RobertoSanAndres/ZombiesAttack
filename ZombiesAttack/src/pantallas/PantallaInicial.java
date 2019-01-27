package pantallas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import base.PanelJuego;

public class PantallaInicial implements Pantalla {

	Color colorLetra = Color.PINK;	
	PanelJuego panelJuego;
	
	public PantallaInicial(PanelJuego panelJuego) {
		this.panelJuego = panelJuego;
	}
	
	@Override
	public void inicializarPantalla() {
		
	}

	@Override
	public void renderizarPantalla(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, panelJuego.getWidth(), panelJuego.getHeight());
		g.setColor(colorLetra);
		g.setFont(panelJuego.getFuenteGrande());
		g.drawString("�Estas preparado?", panelJuego.getWidth()/2-100, panelJuego.getHeight()/2-30);
		g.drawString("Haz click para jugar", panelJuego.getWidth()/2-100, panelJuego.getHeight()/2);
	}

	@Override
	public void ejecutarFrame() {
		panelJuego.repaint();
		try {	Thread.sleep(200);	} catch (InterruptedException e) {e.printStackTrace();}
		colorLetra = colorLetra == Color.PINK ? Color.RED : Color.PINK;
	}

	@Override
	public void moverRaton(MouseEvent e) {
	}

	@Override
	public void pulsarRaton(MouseEvent e) {		
		panelJuego.setPantallaActual(new PantallaJuegoSupervivencia(panelJuego));
	}

	@Override
	public void redimensionarPantalla() {

	}
}
