import javax.swing.*;
import java.awt.*;
import java.util.*;
import jxl.*;
import jxl.read.biff.BiffException;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.io.File.*;
import javax.imageio.ImageIO;
import java.lang.*;

//package com.pacSuse;



public class Main{
Ludus myInput;
Iris myIris;
Yggdrasil myBoard;
ScoreSetter scoreSetter;
ArrayList<Draugr> enemies;
Omega state;
JFrame frame;
public static void main(String args[]){
Omega state = new Omega();

	System.out.print(state.pheonix);
	ArrayList<Draugr> enemies = new ArrayList<Draugr>();
	Ludus myInput = new Ludus();
	Iris myIris = new Iris();
	Yggdrasil myBoard = new Yggdrasil();
	JFrame frame = new JFrame();
	Broki myBroki = new Broki(myInput, myIris, myBoard, enemies, state,frame);
	Main game = new Main();
	if(state.alpha){
	game.setFrame(myInput, myIris);
	
	state.alpha = false;
	}
	myBroki.init();
	myBroki.start();
	
	//game.setHighScore();
	
}

public void setFrame(Ludus myInput, Iris myIris){
frame = new JFrame();
frame.add(myInput);
frame.add(myIris);
frame.setSize(32 * 20,34 * 20);
frame.setVisible(true);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.addKeyListener(myInput);
}
public void setHighScore(){
	frame = new JFrame();
	scoreSetter = new ScoreSetter();
	//frame.remove(myIris);
	frame.add(scoreSetter);
	frame.setSize(300,200);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.addKeyListener(scoreSetter);
}
}

class Omega {
boolean pheonix;
boolean alpha;
boolean omega;
public Omega(){
pheonix = true;
alpha = true;
omega = false;
}
}








class ScoreSetter extends JPanel implements KeyListener{
char curInitial;
char Initial1;
char Initial2;
char Initial3;
int i;
ArrayList<Character> Inits;
PrintWriter highWrite;
PrintWriter nameWrite;
BufferedReader highBuf;
Scanner highReader;
Scanner nameReader;
File highFile;
File nameFile;
Scanner curScoreReader;
BufferedReader curBuf;
File curFile;
int curScore;
ArrayList<Integer> scores;
ArrayList<String> names;
Scanner nameIn;
PrintWriter nameOut;
String curName;
boolean done;
ArrayList<Score> both;
String message;
boolean newS;

public ScoreSetter(){
	try{
	highFile = new File("/home/gantar/Documents/com/pacSuse/highScores/HighScores.txt");
	
	highReader = new Scanner(highFile);
	scores = new ArrayList<Integer>();
	while(highReader.hasNext()){
		scores.add(Integer.parseInt(highReader.nextLine()));
	}
	//curFile = ;
	curScoreReader = new Scanner(new File("/home/gantar/Documents/com/pacSuse/highScores/cur.txt"));
	
	curScore = curScoreReader.nextInt();
	curScoreReader.close();
	
	
	nameFile = new File("/home/gantar/Documents/com/pacSuse/highScores/names.txt");
	nameIn = new Scanner(new BufferedReader(new FileReader(nameFile)));
	names = new ArrayList<String>();
	while(nameIn.hasNext()){
		names.add(nameIn.next());
	}
	nameIn.close();
	both = new ArrayList<Score>();
	for(int j = 0; j < names.size(); j++){
		both.add(new Score(scores.get(j),names.get(j)));
	}
	for(Score g : both){System.out.println(g.nam + "" + g.num);}
	}catch(IOException e){e.printStackTrace();}
	Inits = new ArrayList<Character>();
	Initial1 = 'A';
	Initial2 = 'A';
	Initial3 = 'A';
	curInitial = 'A';
	Inits.add(Initial1);
	Inits.add(Initial2);
	Inits.add(Initial3);
	i = 0;
	setFocusable(true);
	this.addKeyListener(this);
	done = false;
}
public void keyPressed(KeyEvent e){
	System.out.println("!");
	if(e.getKeyCode() != KeyEvent.VK_ENTER){
	curInitial = Character.toUpperCase(e.getKeyChar());
	repaint();
	}
}

public void keyReleased(KeyEvent e){
	
	if(e.getKeyCode() == KeyEvent.VK_ENTER){
		System.out.println(i);
		switch(i){
			case 0: Initial1 = curInitial;
				break;
			case 1: Initial2 = curInitial;
				break;
			case 2: Initial3 = curInitial;
				break;
			}
		i++;
		if(i > 2){
			curName = Initial1 + "" + Initial2 + "" + Initial3;
			both.add(new Score(curScore,curName));
			message = "Nice Try";
			newS = false;
			

			orderScores();
			System.out.println(newS);
		
			
			highReader.close();
			try{
			highWrite = new PrintWriter(highFile);
			nameOut = new PrintWriter(nameFile);
				for(Score g: both){highWrite.println(g.num);
					nameOut.println(g.nam);
					System.out.println("#" + g.nam + " " + g.num);
				}	
			highWrite.close();	
			nameOut.close();	
			}catch(IOException ex){}
			System.out.println(i);
			done = true;
		}
		curInitial = 'A';
	}
	repaint();
	
}

public void orderScores(){
int max1 = Integer.MIN_VALUE;
int max2 = Integer.MIN_VALUE;
int max3 = Integer.MIN_VALUE;
String name1 = both.get(0).nam;
String name2 = both.get(1).nam;
String name3 = both.get(2).nam;
ArrayList<Integer> toRemove = new ArrayList<Integer>();
	for(Score s : both){
		if(s.num > max1){
			max1 = s.num;
			name1 = s.nam;
		}
	} System.out.println(max1);
	for(Score s : both){
		if(((s.num == max1 && s.nam != name1)||s.num < max1) && s.num > max2){
			max2 = s.num;
			name2 = s.nam;
		}
	}System.out.println(max2);
	for(Score s : both){
		if(((s.num == max2 && s.nam != name2)||s.num < max2) && s.num > max3){
			max3 = s.num;
			name3 = s.nam;
		}
	}System.out.println(max3);
/**int scoreamount = scores.size();
for(int h = 0; h < scoreamount; h++){
	if((scores.get(h) != max1)&&(scores.get(h) != max2)&&(scores.get(h) != max3))
	toRemove.add(h);
}	
	for(int h : scores){System.out.println(h + " " + scores.indexOf(h));}
for(int h : toRemove){
	System.out.println(h + " " + scores.get(h));
	scores.remove(scores.get(h));
	}
*/
both = new ArrayList<Score>();
if(max1 != Integer.MIN_VALUE){both.add(new Score(max1,name1));}
if(max2 != Integer.MIN_VALUE){both.add(new Score(max2,name2));}
if(max3 != Integer.MIN_VALUE){both.add(new Score(max3,name3));}
if((max1 < curScore) ||(max2 < curScore)||(max3 < curScore) ||(max1 == curScore && name1 == curName)||(max2 == curScore && name3 == curName)||(max2 == curScore && name3 == curName) ){newS = true;}
}

public void keyTyped(KeyEvent e){}

public void paintComponent(Graphics g){
super.paintComponent(g);

g.setFont(new Font("TimesRoman",Font.PLAIN,12));

g.setColor(Color.black);
if(!done){
	if(i!=0)g.drawString(Character.toString(Initial1),100 + 20 * 0,100);
	if(i!=1)g.drawString(Character.toString(Initial2),100 + 20 * 1,100);
	if(i!=2)g.drawString(Character.toString(Initial3),100 + 20 * 2,100);
	g.setColor(Color.red);
	g.drawString(Character.toString(curInitial),100 + 20 * i,100);
	}else{
	if(newS){g.drawString( "New Record",100,80);} else{
	g.drawString(message,100,80);}
		for(Score f : both){
			g.drawString(Integer.toString(f.num),110,100 + 20*both.indexOf(f));
			g.drawString(f.nam,80,100 + 20 *both.indexOf(f));
		}
	}
}

}




class Score{
String nam;
int num;
public Score(int num, String nam){
this.num = num;
this.nam = nam;
}
}



//package com.pacSuse;

class Ludus extends JComponent implements KeyListener{
char dhukka = 'o';
char vect = 'o';

Ludus(){
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

//package com.pacSuse;

class Iris extends JPanel{
Point susePos;
BufferedImage susPNG;
File openSus;
File closedSus;
File gantarFile;
File pillFile;
File OpenFileE;
File OpenFileW;
File OpenFileN;
File OpenFileS;
File ClosedFileE;
File ClosedFileW;
File ClosedFileS;
File ClosedFileN;
File LivesFile;
File maddiFile;
File bigFile;
File aMoneyFile;
File sweenFile;
File dueceFile;
File cruzFile;
File omegaFile;


boolean openState;
boolean omega;
BufferedImage openImage;
BufferedImage closedImage;
ArrayList<Rectangle> board;
java.util.List<Rectangle> pills;
java.util.List<Rectangle> bigPills;
ArrayList<Rectangle> disPills;
ArrayList<Rectangle> disBigPills;
ArrayList<String> wallTiles;
int score;
ArrayList<Draugr> enemies;
java.util.List<Boolean> maddies;
boolean profilesInited;
BufferedImage gantar;
BufferedImage pillImage;
BufferedImage OpenSusE;
BufferedImage OpenSusW;
BufferedImage OpenSusN;
BufferedImage OpenSusS;
BufferedImage ClosedSusE;
BufferedImage ClosedSusW;
BufferedImage ClosedSusS;
BufferedImage ClosedSusN;
BufferedImage LivesImage;
BufferedImage maddiImage;
BufferedImage bigImage;
BufferedImage aMoneyImage;
BufferedImage sweenImage;
BufferedImage dueceImage;
BufferedImage cruzImage;
BufferedImage omegaImage;

Rectangle portO;
Rectangle portC;
int lives;
int killPoints;
boolean kill;
Point killSpot;
boolean livesPlus;
int addLives;
int occupied;

public Iris(){
livesPlus = false;
addLives = 0;
omega = false;
profilesInited = false;
lives = 4;
kill = false;
ArrayList<Draugr> enemies = new ArrayList<Draugr>();
susePos = new Point(50,50);
setLayout(new FlowLayout());
openSus = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusOpenSmall.png");
closedSus = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusClosedSmall.png");
gantarFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/draugr.png");
pillFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/pillsfw.png");
LivesFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/lifeCounter.png");
maddiFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/maddi.png");
bigFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/bigpillsfw.png");
aMoneyFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/aMoney.png");
sweenFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/sween.png");
dueceFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/duece.png");
cruzFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/cruz.png");
omegaFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/gameOver.png");

OpenFileE = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusOpenSmallE.png");
OpenFileW = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusOpenSmallW.png");
OpenFileS = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusOpenSmallS.png");
OpenFileN = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusOpenSmallN.png");

ClosedFileE = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusClosedSmallE.png");
ClosedFileW = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusClosedSmallW.png");
ClosedFileS = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusClosedSmallS.png");
ClosedFileN = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusClosedSmallN.png");

try{
gantar =  ImageIO.read(gantarFile);
openImage = ImageIO.read(openSus);
closedImage = ImageIO.read(closedSus);
pillImage = ImageIO.read(pillFile);
LivesImage = ImageIO.read(LivesFile);
maddiImage = ImageIO.read(maddiFile);
bigImage = ImageIO.read(bigFile);
aMoneyImage = ImageIO.read(aMoneyFile);
sweenImage  = ImageIO.read(sweenFile);
dueceImage  = ImageIO.read(dueceFile);
cruzImage = ImageIO.read(cruzFile);
omegaImage = ImageIO.read(omegaFile);

OpenSusE = ImageIO.read(OpenFileE);
OpenSusW = ImageIO.read(OpenFileW);
OpenSusS = ImageIO.read(OpenFileS);
OpenSusN = ImageIO.read(OpenFileN);

ClosedSusE = ImageIO.read(ClosedFileE);
ClosedSusW = ImageIO.read(ClosedFileW);
ClosedSusS = ImageIO.read(ClosedFileS);
ClosedSusN = ImageIO.read(ClosedFileN);

} catch(IOException e){}
maddies = Collections.synchronizedList(new ArrayList<Boolean>());
for(int i = 0;i < 4; i++){maddies.add(false);}
susPNG = openImage;
openState = true;
board = new ArrayList<Rectangle>();
pills = new ArrayList<Rectangle>();
bigPills = new ArrayList<Rectangle>();
disPills = new ArrayList<Rectangle>();
disBigPills = new ArrayList<Rectangle>();
wallTiles = new ArrayList<String>();
score = 0;
portO = new Rectangle();
portC = new Rectangle();

}
public void init(){
	
omega = false;
profilesInited = false;
lives = 4;
kill = false;
ArrayList<Draugr> enemies = new ArrayList<Draugr>();
susePos = new Point(50,50);
setLayout(new FlowLayout());
openSus = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusOpenSmall.png");
closedSus = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusClosedSmall.png");
gantarFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/draugr.png");
pillFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/pillsfw.png");
LivesFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/lifeCounter.png");
maddiFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/maddi.png");
bigFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/bigpillsfw.png");
aMoneyFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/aMoney.png");
sweenFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/sween.png");
dueceFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/duece.png");
cruzFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/cruz.png");
omegaFile = new File("/home/gantar/Documents/com/pacSuse/texturePack/gameOver.png");

OpenFileE = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusOpenSmallE.png");
OpenFileW = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusOpenSmallW.png");
OpenFileS = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusOpenSmallS.png");
OpenFileN = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusOpenSmallN.png");

ClosedFileE = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusClosedSmallE.png");
ClosedFileW = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusClosedSmallW.png");
ClosedFileS = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusClosedSmallS.png");
ClosedFileN = new File("/home/gantar/Documents/com/pacSuse/texturePack/pacSusClosedSmallN.png");

try{
gantar =  ImageIO.read(gantarFile);
openImage = ImageIO.read(openSus);
closedImage = ImageIO.read(closedSus);
pillImage = ImageIO.read(pillFile);
LivesImage = ImageIO.read(LivesFile);
maddiImage = ImageIO.read(maddiFile);
bigImage = ImageIO.read(bigFile);
aMoneyImage = ImageIO.read(aMoneyFile);
sweenImage  = ImageIO.read(sweenFile);
dueceImage  = ImageIO.read(dueceFile);
cruzImage = ImageIO.read(cruzFile);
omegaImage = ImageIO.read(omegaFile);

OpenSusE = ImageIO.read(OpenFileE);
OpenSusW = ImageIO.read(OpenFileW);
OpenSusS = ImageIO.read(OpenFileS);
OpenSusN = ImageIO.read(OpenFileN);

ClosedSusE = ImageIO.read(ClosedFileE);
ClosedSusW = ImageIO.read(ClosedFileW);
ClosedSusS = ImageIO.read(ClosedFileS);
ClosedSusN = ImageIO.read(ClosedFileN);

} catch(IOException e){}
maddies = Collections.synchronizedList(new ArrayList<Boolean>());
for(int i = 0;i < 4; i++){maddies.add(false);}
susPNG = openImage;
openState = true;
board = new ArrayList<Rectangle>();
pills = new ArrayList<Rectangle>();
bigPills = new ArrayList<Rectangle>();
disPills = new ArrayList<Rectangle>();
disBigPills = new ArrayList<Rectangle>();
wallTiles = new ArrayList<String>();
score = 0;
portO = new Rectangle();
portC = new Rectangle();

}
@Override
public void paintComponent(Graphics g){
	
	super.paintComponent(g);
	
	int enemyCounter = 0;
	//System.out.println("You got painted!");
	Graphics2D g2 = (Graphics2D)g;
	g2.setFont(new Font("TimesRoman",Font.PLAIN,14));
	g2.setColor(Color.gray);
	g2.fillRect(0,0,1000,1000);	
	g2.setColor(Color.blue);
	for(Rectangle z : board){
		/**
		if(wallTiles.get(board.indexOf(z)).equals("xAEF")){
			//g2.fillRect(z.x,z.y,z.width,z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xCDF")){
			g2.drawLine(z.x,z.y + 10, z.x + z.width,z.y + 10);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xCDEF")){
			g2.drawLine(z.x,z.y + 10, z.x + z.width,z.y + 10);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xBDE")){
			g2.drawLine(z.x + 10,z.y, z.x + 10,z.y + z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xBDEF")){
			g2.drawLine(z.x + 10,z.y, z.x + 10,z.y + z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xBC")){
			g2.fillRect(z.x,z.y,z.width,z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xBCEF")){
			g2.fillRect(z.x,z.y,z.width,z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xBCF")){
			g2.fillRect(z.x,z.y,z.width,z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xBCE")){
			g2.fillRect(z.x,z.y,z.width,z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xDF")){
			g2.fillRect(z.x,z.y,z.width,z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xDE")){
			g2.fillRect(z.x,z.y,z.width,z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xB")){
			g2.fillRect(z.x,z.y,z.width,z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xBF")){
			g2.fillRect(z.x,z.y,z.width,z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xCE")){
			g2.fillRect(z.x,z.y,z.width,z.height);
		}
		if(wallTiles.get(board.indexOf(z)).equals("xC")){
			g2.fillRect(z.x,z.y,z.width,z.height);
		}

		*/
						
		g2.fillRect(z.x,z.y,z.width,z.height);
	}
	try{
	setDisPills(); //this can be done in init()?
	for(Rectangle z : disPills){
		g.drawImage(pillImage,z.x,z.y,null);
	}
	for(Rectangle z : disBigPills){
		g2.drawImage(bigImage,z.x - 3,z.y - 3,null);
	}
	for(int i = 0; i < lives; i++){
		g.drawImage(LivesImage,i * 20 + 150, 0, null);
	}	
	}catch(ConcurrentModificationException ce){System.out.println("!");}
	g2.setColor(Color.white);
	g2.drawString(Integer.toString(score),10,10);
	g.drawImage(susPNG, susePos.x - 10, susePos.y - 10,null);
	for(Draugr d : enemies){
		enemyCounter++;
		try{if(maddies.get(enemyCounter - 1)){
					g.drawImage(maddiImage,d.pos().x - 10,d.pos().y - 10,null);
				} else{
			switch(enemyCounter){
				
					case 1: g2.drawImage(aMoneyImage,d.pos().x - 10,d.pos().y - 10,null);
						break;
					case 2: g.drawImage(dueceImage,d.pos().x - 10,d.pos().y - 10,null);
						break;
					case 3: g2.drawImage(sweenImage,d.pos().x - 10,d.pos().y - 10,null);
						break;
					case 4: g.drawImage(cruzImage,d.pos().x - 10,d.pos().y - 10,null);
						break;
					}
				}}catch(IndexOutOfBoundsException e){}	
	}
	g2.setColor(Color.white);
	if(kill){
		g2.drawString(Integer.toString(killPoints),killSpot.x,killSpot.y);
	}
	if(livesPlus){
		g2.setColor(Color.yellow);
		g2.setFont(new Font("TimesRoman",Font.PLAIN,40));
		g2.drawString("+" +Integer.toString(addLives),susePos.x,susePos.y);
		g2.setFont(new Font("TimesRoman",Font.PLAIN,16));
		g2.setColor(Color.white);
	}
	if(omega){
		g.drawImage(omegaImage,0,0,null);
	enemyCounter = 0;
	g.drawImage(susPNG, susePos.x - 10, susePos.y - 10,null);
	for(Draugr d : enemies){
		enemyCounter++;
		//System.out.println(enemyCounter);
		try{if(maddies.get(enemyCounter - 1)){
					g.drawImage(maddiImage,d.pos().x - 10,d.pos().y - 10,null);
				} else{
			switch(enemyCounter){
				
					case 1: g2.drawImage(aMoneyImage,d.pos().x - 10,d.pos().y - 10,null);
						break;
					case 2: g.drawImage(dueceImage,d.pos().x - 10,d.pos().y - 10,null);
						break;
					case 3: g2.drawImage(sweenImage,d.pos().x - 10,d.pos().y - 10,null);
						break;
					case 4: g.drawImage(cruzImage,d.pos().x - 10,d.pos().y - 10,null);
						break;
					}
				}}catch(IndexOutOfBoundsException e){}	
	}	
	}
	}


public void setOmega(boolean b){
 	omega = b;
	dueceImage = pillImage;
	sweenImage = pillImage;
	aMoneyImage = pillImage;
	cruzImage = pillImage;
	susPNG = ClosedSusW;
}
public void biggerPills(){
	dueceImage = bigImage;
	sweenImage = bigImage;
	aMoneyImage = bigImage;
	cruzImage = bigImage;
}
public void setSusePos(Point pos){
	 susePos = pos;
}
public void setBoard(ArrayList<Rectangle> b, ArrayList<String> w){
	board = b;
	wallTiles = w;
}
public void setPills(ArrayList<Rectangle> p, ArrayList<Rectangle> b){
	pills = new ArrayList<Rectangle>();
	for(Rectangle pill : p){
		pills.add(pill);
	}
	bigPills = new ArrayList<Rectangle>();
	for(Rectangle big : b){
		bigPills.add(big);
	}
}
public void setDisPills(){
	disPills = new ArrayList<Rectangle>();
	for(Rectangle pill : pills){
		disPills.add(pill);
	}
	disBigPills = new ArrayList<Rectangle>();
	for(Rectangle big : bigPills){
		disBigPills.add(big);
	}
}
public void setMaddi(){
	maddies = Collections.synchronizedList(new ArrayList<Boolean>());
	for(Draugr d : enemies){
		if(d.maddi == true){
			maddies.add(true);
		}else{
			maddies.add(false);
		}
	}
	//System.out.println(maddies.size());
}
public void setEnemies(ArrayList<Draugr> en){
	enemies = en;
}
public void setLives(int l){
	lives = l;
}
public void toggleMouth(){
	if(openState){
		susPNG = closedImage;
		openState = false;
	} else {
		susPNG = openImage;
		openState = true;
	}
	
}
public void setScore(int points){
	score = points;
}
public  void killPoints(int k, Point s){
	killPoints = k;
	killSpot = s;
	kill = true;
}
public void lifePoints(int a){
	addLives = a;
	livesPlus = true;
}
public  void setImageVect(char v){
	if(v == 'N'){
		openImage = OpenSusN;
		closedImage = ClosedSusN;
	}
	if(v == 'S'){
		openImage = OpenSusS;
		closedImage = ClosedSusS;
	}
	if(v == 'W'){
		openImage = OpenSusW;
		closedImage = ClosedSusW;
	}
	if(v == 'E'){
		openImage = OpenSusE;
		closedImage = ClosedSusE;
	}
	if(v == 'o'){
		openImage = OpenSusE;
		closedImage = ClosedSusE;
	}
	//System.out.println(v);

}
public void setPortals(Rectangle o, Rectangle c){
portO = o;
portC = c;
}

}









//package com.pacSuse;

class Yggdrasil{
	ArrayList<Rectangle> board;
	ArrayList<Rectangle> pills;
	ArrayList<Rectangle> bigPills;
	ArrayList<String> wallTiles;
	String tag;
	Sheet st;
	Workbook wk;
	public Yggdrasil(){
	try{
	board = new ArrayList<Rectangle>();
	pills = new ArrayList<Rectangle>();
	bigPills = new ArrayList<Rectangle>();
	wallTiles = new ArrayList<String>();
	tag = "x";

	wk = Workbook.getWorkbook(new File("/home/gantar/Documents/com/pacSuse/mapPack/map.xls"));
	st = wk.getSheet(0);
	//System.out.println(st.getCell(0,0).getContents());	
	setBoard(st);
	
	}catch(IOException|BiffException e){System.out.println(e);}
	}

	public void reset(){
		setBoard(st);
	}
	
	public void setBoard(Sheet myST){
	board = new ArrayList<Rectangle>();
	pills = new ArrayList<Rectangle>();
	bigPills = new ArrayList<Rectangle>();
	wallTiles = new ArrayList<String>();
	tag = "x";
		for(int i = 2; i < 30; i++){
			for(int j = 0; j < 32; j++){
			int adjx = 0;
			int adjy = 0;
			try{
				if(Integer.parseInt(myST.getCell(i,j).getContents()) == 1){ 
					System.out.println(myST.getCell(i,j).getContents() + " " + j + " " + i);
					for(int h = 0; h < 2; h++){
						if(Integer.parseInt(myST.getCell((i - 1) + 2 * h,j).getContents()) == 1){
							adjx++;
						}
						if(Integer.parseInt(myST.getCell(i,(j-1) + 2 * h).getContents()) == 1){
							adjy++;
						}
					}
					tag = "x";
					if(adjx + adjy == 4){
						tag += "A";
					}
					if(adjx == 1){
						tag += "B";
					}
					if(adjy == 1){
						tag += "C";
					}
					if((adjx == 2 || adjy == 2) && !(adjx == 2 && adjy == 2)){
						tag += "D";
					}
					if(Integer.parseInt(myST.getCell(i,j - 1).getContents()) == 1){
						tag += "E";
					}
					if(Integer.parseInt(myST.getCell(i + 1,j).getContents()) == 1){
						tag += "F";
					}
					wallTiles.add(tag);
					board.add(new Rectangle(i * 20,j * 20,20,20));
				}
				if(Integer.parseInt(myST.getCell(i,j).getContents()) == 0){
					pills.add(new Rectangle(i * 20 + 5, j* 20 + 5,10,10));
				}
				if(Integer.parseInt(myST.getCell(i,j).getContents()) == 2){
					bigPills.add(new Rectangle(i * 20 + 5, j* 20 + 5,10,10));
				}
				
				}catch(NullPointerException e){}
			}
		}
	for(String w : wallTiles){
		System.out.println(w);
	}
	}
	
	public ArrayList<Rectangle> getBoard(){
		return board;
	}
}









//package com.pacSuse;

class Broki implements Runnable{
Ludus myLudus;
Iris myIris;
Yggdrasil myBoard;
Rectangle suseHitBox;
Rectangle detectBox;
Thread chrono;
int deltaX;
int deltaY;
boolean alpha;
boolean omega;
ArrayList<Rectangle> board;
int score;
ArrayList<Draugr> enemies;
Rectangle portalO;
Rectangle portalC;
int lives;
boolean alive;
int respawnTime;
boolean maddiTime;
boolean maddiAlpha;
int maddiCounter;
int boxOcc;
int suseSpeed;
boolean pause;
boolean mortPause;
int i;	
Ludus ld;
Iris ir;
Yggdrasil yg;
ArrayList<Draugr> en;
Omega state;
PrintWriter curScoreWriter;
FileReader curScoreReader;
BufferedReader curBuf;
File curFile;
int extraLives;
boolean omegaAlpha;
boolean hasPaused;
int j;
int signX;
int signY;
int lifej;
int livesNow;
int occupied;
JFrame frame;

public Broki(Ludus myLudus, Iris myIris, Yggdrasil myBoard, ArrayList<Draugr> enemies, Omega state, JFrame frame){
this.frame = frame;
this.enemies = enemies;
this.myLudus = myLudus;
this.myIris = myIris;
this.myBoard = myBoard;
this.state = state;
try{
curFile = new File("/home/gantar/Documents/com/pacSuse/highScores/cur.txt");
curScoreWriter = new PrintWriter(curFile);
}catch(IOException e){e.printStackTrace();}
lives = 3;
extraLives = 1;
}
public void init() {

suseHitBox = new Rectangle(16 * 20,25 * 20,20,20);

deltaX = 0;
deltaY = 0;
i = 0;
lifej = 0;
livesNow = 0;
alpha = true;
omega = false;
omegaAlpha = false;
alive = true;

pause = false;
mortPause = false;
hasPaused = false;
suseSpeed = 10;
maddiTime = false;
respawnTime = 0;
maddiCounter = 0;
myIris.setLives(lives);
board = myBoard.getBoard();
myIris.setBoard(board, myBoard.wallTiles);
myIris.setPills(myBoard.pills, myBoard.bigPills);
try{

curScoreReader = new FileReader(curFile);
curBuf = new BufferedReader(curScoreReader);
curBuf.mark(1);
//System.out.println(curBuf.readLine());
curBuf.reset();
	if(curBuf.readLine() == null){//(score = Integer.parseInt(curBuf.readLine())) == null){
	curScoreWriter.println("0");
	curScoreWriter.flush();
	score = 0;
	System.out.println("#");
	} else{
	curBuf.reset();
	score = Integer.parseInt(curBuf.readLine());
	System.out.println(score);
	System.out.println("K");
	}
curBuf.close();

}catch(IOException e){e.printStackTrace();}

boxOcc = 0;
enemies.add(new Draugr( 16 * 20, 16 * 20,"amoney",.001,1));
enemies.add(new Draugr( 16 * 20, 17 * 20,"duece",.07,.3));
enemies.add(new Draugr(15 * 20, 16 * 20, "sween",.00001,.7));
enemies.add(new Draugr(17* 20, 17 * 20, "cruz",.005,.1));
enemies.get(1).kaged = true;
enemies.get(1).kageTime = 0;
boxOcc++;
enemies.get(2).kaged = true;
enemies.get(2).kageTime = 1000;
boxOcc++;
enemies.get(3).kaged = true;
enemies.get(3).kageTime = 2000;
boxOcc++;
enemies.get(0).lazarus();

myIris.setEnemies(enemies);
portalO = new Rectangle(30 * 20, 15 * 20, 20,20);
portalC = new Rectangle(1 * 20, 15 * 20, 20,20);
myIris.setPortals(portalO,portalC);
state.pheonix = false;
}

public void restart(){

}

public void start(){
chrono = new Thread(this);
alpha = true;
chrono.start();
}

public void run(){
myIris.setSusePos(getSusePos());
try{chrono.sleep(1000);}catch(InterruptedException e){}

while(!state.omega){
	while(!omega && alpha){ try{
		//System.out.println(pause + " " + enemies.size() + " " + myLudus.vect);
		//if(pause){myIris.repaint();chrono.sleep(1000); pause = false;myIris.kill = false;}
		//System.out.print(boxOcc + " " );
			occupied = 0;
			for(Draugr d : enemies){if(d.kaged){occupied++;}}
			boxOcc = occupied;
		//System.out.println(boxOcc);
		updateVector();
		myIris.setImageVect(myLudus.vect);
		if(canMove(suseHitBox,myLudus.vect)){
			if(i % (15 - suseSpeed) == 0){	
				updatePos();
			}
		} else{
		deltaX = 0;
		deltaY = 0;
		myLudus.setVector('o');
		}
		if(i % 2 == 0){
			myIris.repaint();
		} else{
			pillCheck();
			myIris.setPills(myBoard.pills, myBoard.bigPills);
		}
		if(i == 20 * suseSpeed){
			i = 0;
			if(myLudus.vect != 'o'){
			myIris.toggleMouth();}
		}
		moveAI(i);
			//for(Draugr d : enemies){System.out.println(d.maddi);}
		incrementKage();
		maddiProc();
		myIris.setMaddi();
		checkPortals(suseHitBox);
		newLife();
		
		updateDelta();
		
		myIris.setScore(score);
		i++;
		myIris.setSusePos(getSusePos());
		mort();
		if(mortPause){j = 0;}
		while(mortPause){
			
			chrono.sleep(100);
			j++;
			if(j > 10){mortPause = false;}		
		}
		Thread.sleep(1);
		nextLevel();
		//System.out.println(myLudus.vect);
		}catch(InterruptedException e){}
	} 

	if(!omega){
	myBoard.reset();
	myIris.init();
	enemies = new ArrayList<Draugr>();
	init();
	alpha = true;
	}
	if(omega){
		if(!omegaAlpha){
			i = 0;
			for(int j = 0; j < enemies.size(); j++){
				enemies.get(j).hitBox = new Rectangle((26 - (2*j)) * 20, 18 * 20,20,20 );
			}
			suseHitBox = new Rectangle(16 * 20, (int)(17.5 * 20),20,20);
			myIris.setSusePos(getSusePos());
			omegaAlpha = true;
		}
		myIris.setSusePos(getSusePos());
		myIris.repaint();
		if(i < 600){
			for(Draugr d : enemies){
				d.hitBox.x--;
				//	System.out.print(d.hitBox.x);
			}
			suseHitBox.x--;
		}
		if(i > 600){
			myIris.biggerPills();
			myIris.setImageVect('E');
			for(Draugr d : enemies){
				d.hitBox.x++;
			}
			suseHitBox.x++;
			if(i % 15 == 0){
				myIris.toggleMouth();
			}
		}
		if(i > 1550)
		{
			alpha = false;
			state.omega = true;
		}
		//System.out.println("$");
		i++;					
		try{chrono.sleep(9);}catch(InterruptedException e){}
		
	}
	
	}
	try{curScoreWriter = new PrintWriter(curFile);}catch(IOException e){e.printStackTrace();}
	curScoreWriter.println(score);
	curScoreWriter.flush();
	setHighScore();
	
}
public void nextLevel(){
	if(myBoard.pills.size() == 0){
		try{curScoreWriter = new PrintWriter(curFile);}catch(IOException e){e.printStackTrace();}
		curScoreWriter.println(score);
		curScoreWriter.flush();
		myIris.repaint();
		alpha = false;
		state.pheonix = true;


	}
}
public void setHighScore(){
	ScoreSetter scoreSetter;
	frame = new JFrame();
	scoreSetter = new ScoreSetter();
	//frame.remove(myIris);
	frame.add(scoreSetter);
	frame.setSize(300,200);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.addKeyListener(scoreSetter);
}
public void newLife(){
	if(score > 50000 * extraLives){
		lives++;
		extraLives++;
		livesNow++;
		myIris.setLives(lives);
		myIris.lifePoints(livesNow);

	}
		if(myIris.livesPlus){
			lifej++;
		}
		if(lifej > 5000){
			myIris.livesPlus = false;
			lifej = 0;
			livesNow = 0;
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
	
}
public void updatePos(){
	suseHitBox.y += deltaY;
	suseHitBox.x += deltaX;
}

public void mort(){
boolean pheonix = false;
	for(Draugr d : enemies){
		if(RectOverLap(suseHitBox,d.hitBox)){
			if(!d.maddi){
				suseHitBox.x = -100;
				suseHitBox.y = 9999999;
				alive = false;
				mortPause = true;
			} else{
				d.kaged = true;
				//boxOcc++;
				AddPoints((int)(200 + Math.pow(20 * boxOcc,1 + 1)));
				myIris.killPoints((int)(200 + Math.pow(20 * boxOcc,1 + 1)),new Point(d.hitBox.x+2,d.hitBox.y+2));
				pause = true;
				d.hitBox.x = 999999;
				myIris.repaint();try{chrono.sleep(1000);}catch(InterruptedException e){}myIris.kill = false;
				if(boxOcc < 3){
					d.hitBox = new Rectangle((14* 20), 17 * 20,20,20);
				} else {
					d.hitBox = new Rectangle((14* 20), 17 * 20,20,20);	
				}
				//System.out.println(boxOcc);
			}
		}
		if(!alive){
			respawnTime++;
		}
		if(respawnTime > 5000){
			alive = true;
			lives--;
			myIris.setLives(lives);
			respawnTime = 0;
			myLudus.setVector('o');
			myLudus.resetDhukka();
			deltaX = 0;
			deltaY = 0;
			myIris.setImageVect('o');
			suseHitBox = new Rectangle(16 * 20,25 * 20,20,20);
			
			
			myIris.setImageVect('o');
			myIris.toggleMouth();
			pheonix = true;
			
		}
		if(lives < 0){
			alpha = false;
			omega = true;
			myIris.setOmega(true);
			myBoard.wk.close();
			try{curBuf.close();}catch(IOException e){}	
			//chrono.stop();
		}
	}
	if(pheonix){
	
	for(Draugr d : enemies){
	pheonix = false;
	d.kaged = true;
		boxOcc++;
		
		d.hitBox = new Rectangle((14* 20), 17 * 20,20,20);
		d.kageTime = 500 * (boxOcc - 1);
		if(boxOcc > 3){d.lazarus(); boxOcc--;}
	}
	}
}

public void checkPortals(Rectangle box){
	if(RectOverLap(box,portalO) || RectOverLap(box,portalC)){
		if(box.x > 20 * 15){
			box.x = portalC.x + 20;
		} else{
			box.x = portalO.x - 20;
		}
	}
}

public boolean canMove(Rectangle box, char v){
	detectBox = new Rectangle(box.x,box.y,box.width,box.height);
	boolean can = true;
	if(v == 'N'){
		detectBox.y += -1;
	}
	if(v == 'S'){
		detectBox.y += 1;
	}
	if(v == 'E'){
		detectBox.x += 1;
	}
	if(v == 'W'){
		detectBox.x += -1;
	}
	for(Rectangle z : board){
	if(RectOverLap(detectBox,z)){
	can = false;
	}
	}
	return can;

}

public boolean chase(char c, Draugr d){
	boolean chase = false;
	if(vectAxis(c) == 'y'){
		if(c == 'N'){
			if(suseHitBox.y < d.pos().y && Math.random() < d.chaseChance){
				chase = true;
			}
		} else if(c == 'S'){	
			if(suseHitBox.y > d.pos().y && Math.random() < d.chaseChance){
				chase = true;
			}
		}
	}

	if(vectAxis(c) == 'x'){
		if(c == 'W'){
			if(suseHitBox.x < d.pos().x && Math.random() < d.chaseChance){
				chase = true;
			}
		} else if(c == 'E'){	
			if(suseHitBox.x > d.pos().x && Math.random() < d.chaseChance){
				chase = true;
			}
		}
	}

	return chase;

}
public void incrementKage(){
	for(Draugr d : enemies){
	if(d.kaged){
		d.kageTime++;
		if(d.kageTime > 3000){
		d.lazarus();
		//System.out.println(d.name);
		//boxOcc--;
		}		
	}}
}
public void maddiProc(){
			if(maddiTime){
				maddiCounter++;
				//System.out.println(maddiCounter);
				
				if(maddiCounter > 5000){maddiTime = false;}
				if(maddiAlpha){
					for(Draugr d : enemies){
					if(!d.kaged){
						d.makeMaddi();
						if((suseHitBox.y < d.hitBox.y && d.vect == 'N') || (suseHitBox.y > d.hitBox.y && d.vect == 'S')){
							d.vect = d.flipVect(d.vect); 
						}
						if((suseHitBox.x < d.hitBox.x && d.vect == 'W') || (suseHitBox.x > d.hitBox.x && d.vect =='E')){
							d.vect = d.flipVect(d.vect); 
						}
					}
					}
				
					maddiAlpha = false;
				}
			}else {for(Draugr d : enemies){d.unmakeMaddi();}}

}
public void moveAI(int i){
	for(Draugr d : enemies){
	if(i % (10 - d.speed) == 0){
		if(!d.kaged){

				char temp = d.vect;
				for(char c : pathOptions(d)){	
					if(vectAxis(temp) != vectAxis(c)){
						if(chase(c,d)){				
							d.vect = c;
						}
					}
				}		

				if(canMove(d.hitBox,d.vect)){
					d.updateDelta();
				} else{
					while(!canMove(d.hitBox,d.vect)){
						d.unstop(d.flipVect(d.vect));
					}
					d.updateDelta();
				}

	

				if(Math.random() < d.turnChance){
					d.vect = d.flipVect(d.vect);
				}
				d.updatePos();
				checkPortals(d.hitBox);
			
		} else{
			d.unmakeMaddi();
			/*
			d.vect = 'o';
			if(d.vect == 'o'){
				d.vect = 'N';
			} else if(d.vect == 'N'){
				if(!canMove(d.hitBox,d.vect)){
					d.vect = 'S';
				}
			} else {
				if(!canMove(d.hitBox,d.vect)){
					d.vect = 'N';
				}
			} */
			d.vect = 'o';
			signX = -1 + (int)(Math.random() * 2);
			signY = -1 + (int)(Math.random() * 2);
			 //d.deltaX = (int)(Math.random()*3) * sign;
			// d.deltaY = (int)(Math.random()*3) * sign;
				if(d.deltaX + d.deltaY == 0){ d.deltaX = (int)(Math.random()*3) * signX;
				 d.deltaY = (int)(Math.random()*3) * signY;}
			if(!canMove(d.hitBox,'S')){
				 d.deltaY = (int)(Math.random()*-2);
			//	System.out.println("s");	
			}
			if(!canMove(d.hitBox,'N')){
				 d.deltaY = (int)(Math.random()*2);
			//	System.out.println("n");	
			}
			if(!canMove(d.hitBox,'E')){
				 d.deltaX = (int)(Math.random()*-2);
			//	System.out.println("e");	
			}
			if(!canMove(d.hitBox,'W')){
				 d.deltaX = (int)(Math.random()*2);
			//	System.out.println("w");	
			}
			//d.updateDelta();
			
			d.updatePos();
			//System.out.print(d.deltaY);
		}
	}
	}
}
public char vectAxis(char v){
	if(v == 'N' || v == 'S'){
		return 'y';
	} else {
		return 'x';
	}

}
public ArrayList<Character> pathOptions(Draugr d){
	ArrayList<Character> butts = new ArrayList<Character>();

	if(canMove(d.hitBox,'N')){
		butts.add('N');
	}
	if(canMove(d.hitBox,'S')){
		butts.add('S');
	}
	if(canMove(d.hitBox,'E')){
		butts.add('E');
	}
	if(canMove(d.hitBox,'W')){
		butts.add('W');
	}


	return butts;
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
			AddPoints(50);
		}
	}
	myBoard.pills.remove(pillToRemove);
	for(Rectangle z : myBoard.bigPills){
		if(RectOverLap(suseHitBox, z)){
			pillToRemove = z;
			maddiTime = true;
			maddiAlpha = true;
			maddiCounter = 0;
		}
	}
	myBoard.bigPills.remove(pillToRemove);
	
}
public void AddPoints(int points){
	score += points;
}

}

//package com.pacSuse;

 class Draugr{
int deltaX;
int deltaY;
char vect;
Rectangle hitBox;
Rectangle detectBox;
String name;
BufferedImage profile;
double turnChance;
double chaseChance;
boolean alive;
boolean maddi;
double latentChase;
boolean kaged;
int kageTime;
int speed;

public Draugr(int x, int y, String name, double turnChance, double chaseChance){
deltaX = 0;
deltaY = 0;
vect = 'o';
speed = 2;
this.turnChance = turnChance;
this.chaseChance = chaseChance;
latentChase = chaseChance;
hitBox = new Rectangle(x,y, 20, 20);
this.name = name;
alive = false;
maddi = false;
kaged = true;
int kageTime = 0;
}
public Point pos(){
	return new Point(hitBox.x,hitBox.y);
}
public void updateDelta(){
	if((vect == 'N')){
		deltaY = -1;
		deltaX = 0;
		}
	if((vect == 'S')){
		deltaY = 1;
		deltaX = 0;
	}
	if(vect == 'E'){
		deltaX = 1;
		deltaY = 0;
	}
	if(vect == 'W'){
		deltaX = -1;
		deltaY = 0;
	}	
}
public void updatePos(){
	hitBox.y += deltaY;
	hitBox.x += deltaX;
}

public void unstop(char v){
		
		double vectNum = Math.random();
		//System.out.println(vectNum);
		if(vectNum < .25){
			vect = 'S';
		} else if(vectNum < .50){
			vect = 'N';
		}else if(vectNum < .75){
			vect = 'E';
		}else if(vectNum < 1){
			vect = 'W';
		}
	if(vect == v){
		vectNum = Math.random();
		//System.out.println(vectNum);
		if(vectNum < .25){
			vect = 'S';
		} else if(vectNum < .50){
			vect = 'N';
		}else if(vectNum < .75){
			vect = 'E';
		}else if(vectNum < 1){
			vect = 'W';
		}
	}
}
public void makeMaddi(){
	maddi = true;
	chaseChance = 0;
	speed = 1;
}
public void unmakeMaddi(){
	maddi = false;
	chaseChance = latentChase;
	speed = 2;
}
public char flipVect(char v){
	if(v == 'N'){
		return 'S';
	}
	if(v == 'S'){
		return 'N';
	}
	if(v == 'E'){
		return 'W';
	}
	if(v == 'W'){
		return 'E';
	}
	return 'o';
}

public void lazarus(){
	hitBox = new Rectangle(15 * 20, 12 * 20, 20,20);
	kaged = false;
	kageTime = 0;
	deltaX = 0;
	deltaY = 0;
	if(Math.random() < .5){
	vect = 'W';
	} else{
	vect = 'E';
	}
	chaseChance = latentChase;
	unstop(vect);
}

}
