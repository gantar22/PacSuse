import java.awt.*;
import java.awt.image.*;
//package com.pacSuse;

public class Draugr{
int deltaX;
int deltaY;
char vect;
Rectangle hitBox;
String name;
BufferedImage profile;

public Draugr(int x, int y, String name){
deltaX = 0;
deltaY = 0;
vect = 'o';
hitBox = new Rectangle(x,y, 100, 100);
this.name = name;

}
public Point pos(){
	return new Point(hitBox.x,hitBox.y);
}

}
