
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.security.cert.X509CRLEntry;

import javax.swing.JPanel;

public class motorcade extends JPanel{
	private int[] road = null;
	
	public int roadLength = 0;
	public int roadWidth = 0;
	
	public int cellWidth = 0;
	public int cellCol = 0;

	
	public motorcade(int cl, int cw) {
		// TODO Auto-generated constructor stub
		cellCol = cl;
		cellWidth = cw;
		
		roadLength = cellCol * cellWidth;
		roadWidth = cw;
		setPreferredSize(new Dimension(roadWidth, roadLength));
        setBackground(Color.BLACK);
        
		road = new int[cl];
		for(int i = 0; i < cellCol; i++) {
			road[i] = 0;
		}
	}
	
	public void updateMot(int[] motArray)
	{
		this.road = motArray;
		repaint();
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		super.paint(g2d);
		int xCellPos,yCellPos;
		yCellPos = 0;
		for(int i = 0; i < cellCol; i++){
				xCellPos = i * cellWidth;
				if(road[i] != 0){
					paintCell(g2d,xCellPos,yCellPos,transInttoColor(road[i]));
					//paintCellNum(g2d, xCellPos, yCellPos, i);
				}	
				else if(road[i] == 0)
					paintCell(g2d,xCellPos,yCellPos,Color.WHITE);
				//else 
					//paintCell(g2d,xCellPos,yCellPos,Color.RED);
		}
	}
	
	public void paintCell(Graphics2D g2d, int xCellPos, int yCellPos, Color color)
	{
		g2d.setColor(color);
		g2d.fillRect(xCellPos, yCellPos, cellWidth, cellWidth*5);
	}
	
	public void paintCellNum(Graphics2D g2d, int xCellPos, int yCellPos, int i)
	{
		char[] cellNum = new char[3];
		String string = new String();
		string = String.valueOf(i);
		cellNum = string.toCharArray();
		g2d.drawChars(cellNum, 0, 4, xCellPos, yCellPos);
	}
	
	public Color transInttoColor(int i)
	{
		if (i % 7 == 0)
			return new Color(255,0, 0); 
		else if (i % 7 == 1)
			return new Color(255, 125, 0);
		else if (i % 7 == 2)
			return new Color(255, 255, 0);
		else if (i % 7 == 3)
			return new Color(0, 255, 0);
		else if (i % 7 == 4)
			return new Color( 0, 0, 255);
		else if (i % 7 == 5)
			return new Color(0, 255, 255);
		else 
			return new Color(255, 0, 255);
	}
}

