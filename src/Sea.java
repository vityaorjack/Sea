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
	Player player = new Player();
	Rectangle key=new Rectangle(500,50,220,35);	
	int x,y;//координаты мышки
	int time;
	Timer timer=new Timer(1,this);
	Image im;
	ArrayList<Rect> rects=new ArrayList<Rect>();
	
	
	
	MyPanel(){				
		addMouseMotionListener( new MyMouse());
		addMouseListener( new MyMouse());
		timer.start();
		
		try{
			im = ImageIO.read(new File("Image/Sea.jpg"));
		}catch(IOException exception){}
		//планки кают
		for(int i=0;i<3;i++){
			rects.add(new Rect());
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		time++;
		if(time>Okean.timer){
			time=0;
			Okean.movement();
		}
		//player.click();
		
		repaint();	
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//фон
		setBackground(new Color(120,220,255));
		g.drawImage(im,375,10,null);
		
		//g.fillOval(x, y, 10, 10);//
		
		g.drawRoundRect(500,50,220,35,10,10);
		g.drawString("Количество кликов: "+player.getClickCount(), 540, 70);
		//вода			
		g.setColor(new Color(0,95,140));
		g.fillRect(Okean.x, Okean.y, Okean.width, Okean.height);
		//стенки
		g.setColor(Color.black);
		
		g.fillRect(345,0,30,1050);
		g.fillRect(875,0,30,1050);
		
		g.fillRect(355,-20,540,30);
		g.fillRect(355,510,540,30);
	}
	
	public class MyMouse extends MouseAdapter implements MouseMotionListener{				  				
		   
		public void mousePressed(MouseEvent event){					
				if(key.contains(event.getPoint())){
					player.click();
					
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
	static int timer=500;
	
	static void movement(){
		y--;  height++;
	}
}

class Rect{
	int x=355,y=-20,width=540,heigth=30;
	
	void movement(){
		y++;
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

   public int getClickCount(){
       return clickCount;
   }

   public boolean click(){
       clickCount++;
       return true;
   }

}