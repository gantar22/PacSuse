import java.awt.event.*;
import javax.swing.*;
//package com.pacSuse;

public class Ludus extends JComponent implements KeyListener{
char dhukka = 'o';
char vect = 'o';

public Ludus(){
	setFocusable(true);
	this.addKeyListener(this);
}

public void keyPressed(KeyEvent e){
	if((e.getKeyCode() == KeyEvent.VK_UP) && (dhukka != 'S') && (vect != 'N')){
		dhukka = 'N';
	}
	if((e.getKeyCode() == KeyEvent.VK_DOWN) && (dhukka != 'N') && (vect != 'S')){
		dhukka = 'S';
	}
	if((e.getKeyCode() == KeyEvent.VK_LEFT) && (dhukka != 'E') && (vect != 'W')){
		dhukka = 'W';
	}
	if((e.getKeyCode() == KeyEvent.VK_RIGHT) && (dhukka != 'W') && (vect != 'E')){
		dhukka = 'E';
	}
}
public void keyReleased(KeyEvent e){
	if((e.getKeyCode() == KeyEvent.VK_RIGHT) && dhukka == 'E'){
		dhukka = 'o';
	}
	if((e.getKeyCode() == KeyEvent.VK_LEFT) && dhukka == 'W'){
			dhukka = 'o';
	}
	if((e.getKeyCode() == KeyEvent.VK_UP) && dhukka == 'N'){
			dhukka = 'o';
	}
	if((e.getKeyCode() == KeyEvent.VK_DOWN) && dhukka == 'S'){
			dhukka = 'o';
	}
	
}
public void keyTyped(KeyEvent e){

}
	
	public void resetDhukka(){
		dhukka = 'o';
	}
	public char getDhukka(){
		return dhukka;
	}

	
	public void setVector(char d){
		vect = d;
	}
}
