import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.util.*;
//package com.pacSuse;

public class Broki implements Runnable{
Ludus myLudus;
Iris myIris;
Yggdrasil myBoard;
Rectangle suseHitBox;
Rectangle detectBox;
Thread chrono;
int deltaX;
int deltaY;
boolean alpha;
ArrayList<Rectangle> board;
int score;
ArrayList<Draugr> enemies;


public Broki(Ludus myLudus, Iris myIris, Yggdrasil myBoard, ArrayList<Draugr> enemies){
this.enemies = enemies;
this.myLudus = myLudus;
this.myIris = myIris;
this.myBoard = myBoard;
}
public void init(){
suseHitBox = new Rectangle(0,50,100,100);
deltaX = 0;
deltaY = 0;
alpha = false;
board = myBoard.getBoard();
myIris.setBoard(board);
myIris.setPills(myBoard.pills);
score = 0;
enemies.add(new Draugr( 400, 300,"gantar"));
}
public void start(){
alpha = true;
chrono = new Thread(this);
chrono.start();
}

public void run(){
	int i = 0;
	while(alpha){ try{
		if(i % 10 == 0){
			if(canMove()){
			updatePos();} else{
			deltaX = 0;
			deltaY = 0;
			myLudus.setVector('o');
			}
		}
		if(i == 500){
			i = 0;
			if(myLudus.vect != 'o'){
			myIris.toggleMouth();}
		}
		updateVector();
		updateDelta();
		pillCheck();
		myIris.setScore(score);
		i++;
		myIris.setSusePos(getSusePos());
		myIris.repaint();
		Thread.sleep(1);
		}catch(InterruptedException e){}
	}
}
public void updateVector(){
	if(myLudus.getDhukka() == 'N' && canTurn()){
		myLudus.setVector('N');
	}
	if(myLudus.getDhukka() == 'S' && canTurn()){
		myLudus.setVector('S');
	}
	if(myLudus.getDhukka() == 'W' && canTurn()){
		myLudus.setVector('W');
	}
	if(myLudus.getDhukka() == 'E' && canTurn()){
		myLudus.setVector('E');
	}
	
}
public void updateDelta(){
	if((myLudus.vect == 'N')){
		deltaY = -1;
		deltaX = 0;
		}
	if((myLudus.vect == 'S')){
		deltaY = 1;
		deltaX = 0;
	}
	if(myLudus.vect == 'E'){
		deltaX = 1;
		deltaY = 0;
	}
	if(myLudus.vect == 'W'){
		deltaX = -1;
		deltaY = 0;
	}
	if(!canMove()){
	}
}
public void updatePos(){
	suseHitBox.y += deltaY;
	suseHitBox.x += deltaX;
}

public boolean canMove(){
	detectBox = new Rectangle(suseHitBox.x,suseHitBox.y,suseHitBox.width,suseHitBox.height);
	boolean can = true;
	if(myLudus.vect == 'N'){
		detectBox.y += -1;
	}
	if(myLudus.vect == 'S'){
		detectBox.y += 1;
	}
	if(myLudus.vect == 'E'){
		detectBox.x += 1;
	}
	if(myLudus.vect == 'W'){
		detectBox.x += -1;
	}
	for(Rectangle z : board){
	if(RectOverLap(detectBox,z)){
	can = false;
	}
	}
	return can;

}
public boolean canTurn(){
	boolean can = true;
	detectBox = new Rectangle(suseHitBox.x,suseHitBox.y,suseHitBox.width,suseHitBox.height);
	if(myLudus.getDhukka() == 'N'){
		detectBox.y += -1;
	}
	if(myLudus.getDhukka() == 'S'){
		detectBox.y += 1;
	}
	if(myLudus.getDhukka() == 'E'){
		detectBox.x += 1;
	}
	if(myLudus.getDhukka() == 'W'){
		detectBox.x += -1;
	}
	for(Rectangle z : board){
	if(RectOverLap(detectBox,z)){
	can = false;
	}
	}
	return can;
}


public Point getSusePos(){
	Point pos = new Point();
	pos.x = suseHitBox.x;
	pos.y = suseHitBox.y;
	return pos;
}
public boolean RectOverLap(Rectangle a, Rectangle b){
	if(b.y < a.y + a.height && b.y + b.height > a.y){
		
		if(b.x < a.x + a.width && b.x + b.width > a.x){
			return true;
		} else {
			return false;
		}


	} else{
		return false;
	}
}
public void pillCheck(){
	Rectangle pillToRemove = new Rectangle();
	for(Rectangle z : myBoard.pills){
		if(RectOverLap(suseHitBox,z)){
			pillToRemove = z;
			AddPoints(200);
		}
	}
	myBoard.pills.remove(pillToRemove);
}
public void AddPoints(int points){
	score += points;
}

}
