/*Что осталось сделать ?
1.Телепорт комнат, когда ушли из зоны видимости
2.Уровень, ускорения
3.

*/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;



public class Sea {	
	static Thread thread=new Thread(new Server());	
	
	public static void main(String[] args) throws IOException{		
		MyFrame frame = new MyFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1280,730);
		frame.setTitle("Море");			
		frame.show();
		
		thread.start();
	}
}
class Server extends Thread {	
	
	static Socket socket;
	ServerSocket server;
	
	public void run(){		
		
	      try {
	    	  server = new ServerSocket(8080);
		      System.out.println("Server Started");
	         while (true) {	           
	           socket = server.accept();
	           new Client(socket);	            
	         }
	      } catch (IOException e) {e.printStackTrace();}
	      finally {
	    	  System.out.println("Server Stoped");
	    	  try {server.close();} 
	    	  catch (IOException e) {e.printStackTrace();}
	      }
	}
	
}
class Client{
	
	private BufferedReader in;
	Bim bim = new Bim();   
	static int number;
	String name=""+number++;
	
	FileOutputStream fos;
	ObjectInputStream oin;
	ObjectOutputStream oos;

	public Client(Socket socket) throws IOException {				
	    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public String toString(){
		return name;
		
	}
}
class Bim implements Serializable{	
	int v; static volatile int w;
	
	Bim(){
		
	}
			
}


//сама игра непосредственно

class MyFrame extends JFrame {

	public MyFrame() {		
		getContentPane().add(new MyPanel());
		//Cursor c1 = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon(new byte[0])).getImage(), new Point(0,0),	"custom");
		//getContentPane().setCursor(c1);
	}
}
class MyPanel extends JPanel implements ActionListener{	
	
	Draw draw = new Draw();	
	Rectangle key=new Rectangle(500,50,220,35);	
	int x,y;//координаты мышкиса
	int time;
	Timer timer=new Timer(1,this);
	//игрок не бот
	boolean click;
	int clicks,energy;
	
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
		
		//время для хода
		for(Player player:players){
			
			time++;
			if(time>player.timer){			
				time=0;
				if(player.team & energy<3000){clicks++;    energy+=player.timer;}
				else for(Room room:rooms)room.y++;				
			}			
		}
		//для играков
		
			if(click){					
				for(int i=0;i<clicks;i++){
					for(Room room:rooms)room.y--;
					clicks--;    energy-=players.get(0).timer;
				}
				click=false;
			}
			
			//player.click();
		
		repaint();	
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
		//energy
		g.drawRect(28, 98, 34, 304);
		g.setColor(Color.green);
		g.fillRect(30, 100, 30, energy/10);
		
	}
	
	public class MyMouse extends MouseAdapter implements MouseMotionListener{				  				
		   
		public void mousePressed(MouseEvent event){					
				if(key.contains(event.getPoint())){					
					//player.click();
				}
				click=true;
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
