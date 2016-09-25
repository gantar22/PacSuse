import java.util.*;
import javax.swing.*;
import java.awt.*;
//package com.pacSuse;

public class Yggdrasil{
ArrayList<Rectangle> board;
ArrayList<Rectangle> pills;
public Yggdrasil(){
board = new ArrayList<Rectangle>();
pills = new ArrayList<Rectangle>();
board.add(new Rectangle(0,-1,500,1));
board.add(new Rectangle(-1,0,1,500));
board.add(new Rectangle(500,0,1,500));
board.add(new Rectangle(0,500,500,1));
board.add(new Rectangle(100,0,200,300));
board.add(new Rectangle(100,400,200,100));
for(int i = 0; i < 4; i++){
pills.add(new Rectangle(40,100 * i + 140, 10,10));
}
}
public ArrayList<Rectangle> getBoard(){
	return board;
}
}
