import org.omg.PortableServer.CurrentOperations;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.VetoableChangeListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Control extends JFrame{
	
	public static void PR(int[] x, int l) {
		for (int i=0; i < l; i++) {
			System.out.print(x[i]);
		}
		System.out.println();
	}
	
	
	public static void main(String[] argvs)
	{
		
		VehicleList ve = new VehicleList(6, 3, 50);
		int[] x = ve.lanes;
		int[] y = ve.idlane;
		int[] z = ve.crash;
		for (int i=0; i < 5; i++) {
			Control.PR(x, 50);
			Control.PR(y, 50);
			Control.PR(z, 50);
			System.out.println("ƽ���ܶ�"+ve.getavgDensity());
			System.out.println("ƽ���ռ��ٶ�"+ve.getavgSpaceSpeed());
			System.out.println("ƽ��ʱ���ٶ�"+ve.getavgTimeSpeed());
			System.out.println("��ͨ����"+ve.getTransportationFlux());
			System.out.println("VehTable:"+ve.getVehTable());
			System.out.println("LaneTable"+ve.getLaneTable());
			//Control.PR(ve.crash, 60);
			x = ve.drive();
			y = ve.idlane;
			z = ve.crash;
		}

	}
}
