import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;



public class Sea {	
	
	public static void main(String[] args){		
		MyFrame frame = new MyFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Море");			
		frame.show();
}}
class MyFrame extends JFrame {

	public MyFrame() {
		setSize(1280,730);
		MyPanel panel = new MyPanel();
		Container pane = getContentPane();
		pane.add(panel);
		//Cursor c1 = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon(new byte[0])).getImage(), new Point(0,0),	"custom");
		//pane.setCursor(c1);
	}
}
class MyPanel extends JPanel implements ActionListener{	
	
	Draw draw = new Draw();	
	Rectangle key=new Rectangle(500,50,220,35);	
	int x,y;//координаты мышки
	int time;
	Timer timer=new Timer(1,this);
	
	ArrayList<Player> players=new ArrayList<Player>();
	ArrayList<Room> rooms=new ArrayList<Room>();
	
	
	
	MyPanel(){				
		addMouseMotionListener( new MyMouse());
		addMouseListener( new MyMouse());
		timer.start();
		//игроки
		players.add(new Player(true));
		players.add(new Player(false));
		//планки кают
		for(int i=0;i<3;i++){
			rooms.add(new Room(i*530));
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		for(Player player:players){
			
			time++;
			if(time>player.timer){
				time=0;				
				for(Room room:rooms){
					if(!player.team)room.y--;
					//else room.y++;
				}
				
			}
			
		}
			//player.click();
		
		repaint();	
	}
	void movement(){
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//фон
		setBackground(new Color(120,220,255));
		
		
		//g.fillOval(x, y, 10, 10);//
		
		//g.drawRoundRect(500,50,220,35,10,10);
		//g.drawString("Количество кликов: "+player.getClickCount(), 540, 70);
		
		//rooms
		for(Room room:rooms){
		g.drawImage(room.image,room.x,room.y,null);		
		}
		//вода			
		g.setColor(new Color(0,95,140));
		g.fillRect(Okean.x, Okean.y, Okean.width, Okean.height);
		//стенки
		g.setColor(Color.black);
		for(Room room:rooms){			
			g.fillRect(375, room.y-30, 500, 30);
		}				
		g.fillRect(345,0,30,1050);
		g.fillRect(875,0,30,1050);
		
		
	}
	
	public class MyMouse extends MouseAdapter implements MouseMotionListener{				  				
		   
		public void mousePressed(MouseEvent event){					
				if(key.contains(event.getPoint())){
					
					//player.click();
					
				}																	
		}		

		public void mouseDragged(MouseEvent e) {
			//System.out.println(e.getX());			
		}

		public void mouseMoved(MouseEvent e) {
			x=e.getX();
			y=e.getY();
			
		}
		
	}	 
}
class Okean{
	static int x=375,y=350,width=500,height=1000;
	
	
	static void movement(){
		y--;  height++;
	}
}

class Room{
	int x=375,y,width=500,height=500;	
	Image image;
	
	Room(int y){
		this.y=y;
		
		try{
			image = ImageIO.read(new File("Image/Sea.jpg"));
		}catch(IOException exception){}
	}	
	
}

/**
* Created by Andrej on 15.02.2016.
*/
class Draw {
	   Graphics2D graphics;

	   /*public Draw(Graphics2D g){
	       graphics = g;
	   }*/	  
}

/**
* Created by Andrej on 15.02.2016.
*/
 class Player {
   private int clickCount;
   boolean team;
   int timer=100;
   
   Player(boolean team){
	   this.team=team;
   }

   public int getClickCount(){
       return clickCount;
   }

   public boolean click(){
       clickCount++;
       return true;
   }

}