
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import javax.swing.JPanel;

public class TrafficFlow extends JPanel{
	// width of each cell
	private int cellWidth = 0;
	//row and column of the traffic flow panel
	private int cellRow = 0;
	private int cellCol = 0;
	// panel width
	private int panelWidth = 0;
	private int panelHeight = 0;

	// received information for painting
	private int[][] cellArray = null;
	
	public TrafficFlow(int frameWidth, int cr,int co) {
		panelWidth = frameWidth;
		cellRow = cr;
		cellCol = co;
		cellWidth = panelWidth / cellCol;
		panelHeight = cr * cellWidth;
		setPreferredSize(new Dimension(panelWidth, 150));
        setBackground(Color.BLACK);
        cellArray = new int[cellRow][cellCol];
        for(int row = 0; row < cellRow; row++)
			for(int column = 0; column < cellCol; column++)
				cellArray[row][column] = 0;
	}
	
	public void updatePanel(int[][] cellArray)
	{
		this.cellArray = cellArray;
		repaint();
		
	}


	@Override
	
	public void paint(Graphics g)//g is the panel which you manipulate
	{
		Graphics2D g2d = (Graphics2D)g;// the object you can paint
		super.paint(g2d);
		int xCellPos,yCellPos;
		for(int i = 0; i < cellRow; i++){
			for(int j = 0; j < cellCol; j++){
				xCellPos = j * cellWidth;
				yCellPos = i * cellWidth;
				
				if(cellArray[i][j] == 1)
					paintCell(g2d,xCellPos,yCellPos,Color.GREEN);
				else if(cellArray[i][j] == 0)
					paintCell(g2d,xCellPos,yCellPos,Color.WHITE);
				//else 
					//paintCell(g2d,xCellPos,yCellPos,Color.RED);
			}
		}
	}


	private void paintCell(Graphics2D g2d, int xCellPos, int yCellPos, Color color) {
		g2d.setColor(color);
		g2d.fillRect(xCellPos, yCellPos, cellWidth, cellWidth);
	}

	public int getcellRow()
	{
		return cellRow;
	}
	public int getcellCol()
	{
		return cellCol;
	}
	public int[][] getcellarray()
	{
		return cellArray;
	}

	public void debugArray() {
		System.out.println("ARRAY");
		for (int i=0; i < cellRow; i++) {
			for (int j=0; j < cellCol; j++) {
				System.out.print(cellArray[i][j]);
			}
			System.out.println();
		}
	}
	


}
