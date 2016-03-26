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
	ArrayList<Cube> cubes=new ArrayList<Cube>();
	int x,y;//координаты мышки
	Timer timer=new Timer(20,this);
	Image im;
	
	
	
	MyPanel(){				
		addMouseMotionListener( new MyMouse());
		addMouseListener( new MyMouse());
		timer.start();
		
		try{
			im = ImageIO.read(new File("Image/Sea.jpg"));
		}catch(IOException exception){}
		
		cubes.add(new Cube(345,120,500,500));
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(cubes.size()>0)cubes.get(0).okean.height++;
		//player.click();
		
		repaint();	
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//фон
		setBackground(new Color(120,220,255));
		g.drawImage(im,375,150,null);
		//������
		//g.fillOval(x, y, 10, 10);//
		//������
		g.drawRoundRect(500,50,220,35,10,10);
		g.drawString("Количество кликов: "+player.getClickCount(), 540, 70);
		//комнаты
		
		for(Cube cube:cubes){
			//вода			
			g.setColor(new Color(120,220,255));
			g.fillRect(cube.okean.x, cube.okean.y, cube.okean.width, cube.okean.height);
			//стены
			g.setColor(Color.white);
			for(Rectan rectangle:cube.rectangles){				
				g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			}			
		}
		
		
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


class Cube{
	Okean okean;
	int x,y,width,height;	
	ArrayList<Rectan> rectangles=new ArrayList<Rectan>();
	
		Cube(int x,int y,int width,int height){			
				this.x=x;this.y=y;this.width=width;this.height=height;			
			
				okean=new Okean(this.width);			
				rectangles.add(new Rectan(x, y, true));
				rectangles.add(new Rectan(x+30, y, false));
				rectangles.add(new Rectan(x+30, y+500, false));
				rectangles.add(new Rectan(x+530, y, true));
			
		}
}
class Okean{
	int x,y,width,height;
	Okean(int width){
		this.width=width;
	}
}
class Rectan{
	int x,y,width=530,height=30,volum;	
	Rectan(int x,int y,boolean location){
		this.x=x;
		this.y=y;
		if(location){
			volum=width;
			width=height;
			height=volum;
		}
		
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