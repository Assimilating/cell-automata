import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class IntegralView extends JFrame {

	private JPanel contentPane;
	//�������-down-----
	private JTable VehDataShow;
	private JTable LaneDataShow;
	private Vector<String> VehTitle = null;//������Ϣ����
	private Vector<String> LaneTitle = null;//������Ϣ����
	
	private Vector VehContents = null;//������Ϣ����
	private Vector LaneContents = null;//������Ϣ����
	//�������-up---------------------------------------------------------------
	//��������-down
	public int[] idLane;
	private motorcade moto;
	//��������-up---------------------------------------------------------------
	//����control�������-down---------------------------------------------------
	private int vhCnt = 0;
	private int lock = 0;
	public int[][] cellMatrix = null;
	public VehicleList vehs = null;
	public TrafficFlow tFlow = null;
	private JButton  bStart;
	private JSpinner textField;
	//����control�������-up--------------------------------------------------
	
	
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					IntegralView frame = new IntegralView();
//					frame.setVisible(true);
//					frame.manipulate();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static void main(String[] args)
	{
		IntegralView frame = new IntegralView();
		frame.setVisible(true);
		frame.manipulate();
	}
	/**
	 * Create the frame.
	 */
	public IntegralView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1084, 882);
		setTitle("Traffic Flow Simulation");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		//������ݵĳ�ʼ��-down-------------------------------------
		iniTable();
		
		//������ݳ�ʼ��-up-----------------------------------------
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(48, 10, 800, 199);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("��������");
		lblNewLabel_1.setBackground(new Color(0, 128, 128));//RGB��ɫ����
		lblNewLabel_1.setFont(new Font("���Ŀ���", Font.BOLD, 15));
		lblNewLabel_1.setForeground(new Color(0 ,0, 0));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_1, BorderLayout.NORTH);
		
		VehDataShow = new JTable(VehContents,VehTitle);//��ʼ���������
		VehDataShow.setBorder(new CompoundBorder(new CompoundBorder(),
				new LineBorder(new Color(0, 0, 0))));
		VehDataShow.setFont(new Font("���Ŀ���", Font.BOLD, 15));
		VehDataShow.setFillsViewportHeight(true);
		VehDataShow.setColumnSelectionAllowed(true);
		VehDataShow.setCellSelectionEnabled(true);
		VehDataShow.setLayout(getLayout());
		
		JScrollPane scrollPane = new JScrollPane(VehDataShow);
		panel_1.add(scrollPane, BorderLayout.CENTER);
				
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(48, 219, 800, 133);
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("��������");
		lblNewLabel_2.setBackground(new Color(0, 128, 128));//RGB��ɫ����
		lblNewLabel_2.setFont(new Font("���Ŀ���", Font.BOLD, 15));
		lblNewLabel_2.setForeground(new Color(0 ,0, 0));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_2, BorderLayout.NORTH);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		
		LaneDataShow = new JTable(LaneContents, LaneTitle);//��ʼ����·���
		LaneDataShow.setBorder(new CompoundBorder(new CompoundBorder(),
				new LineBorder(new Color(0, 0, 0))));
		LaneDataShow.setFont(new Font("���Ŀ���", Font.BOLD, 15));
		LaneDataShow.setFillsViewportHeight(true);
		LaneDataShow.setColumnSelectionAllowed(true);
		LaneDataShow.setCellSelectionEnabled(true);
		LaneDataShow.setLayout(getLayout());
		scrollPane_1.setViewportView(LaneDataShow);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(48, 383, 800, 200);
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("����ʱ�̳�������");
		lblNewLabel_3.setBackground(new Color(0, 128, 128));//RGB��ɫ����
		lblNewLabel_3.setFont(new Font("���Ŀ���", Font.BOLD, 15));
		lblNewLabel_3.setForeground(new Color(0 ,0, 0));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblNewLabel_3, BorderLayout.NORTH);
		
		tFlow = new TrafficFlow(800,50,200);
		//JPanel panel_4 = new JPanel();
		panel_3.add(tFlow, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(58, 600, 790, 60);
		panel.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_5 = new JLabel("��������");
		lblNewLabel_5.setBackground(new Color(0, 128, 128));//RGB��ɫ����
		lblNewLabel_5.setFont(new Font("���Ŀ���", Font.BOLD, 15));
		lblNewLabel_5.setForeground(new Color(0 ,0, 0));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(lblNewLabel_5, BorderLayout.NORTH);
		
		//JPanel panel_6 = new JPanel();
		moto = new motorcade(200, 4);
		panel_5.add(moto, BorderLayout.CENTER);
		
		JLabel lblNewLabel_6 = new JLabel("һά���ε�·");
		lblNewLabel_6.setBackground(new Color(255, 255, 255));//RGB��ɫ����
		lblNewLabel_6.setFont(new Font("���Ŀ���", Font.BOLD, 15));
		lblNewLabel_6.setForeground(new Color(0 ,0, 0));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		moto.add(lblNewLabel_6);
		
		textField = new JSpinner();
		textField.setBounds(867, 113, 163, 60);
		textField.setModel(new SpinnerNumberModel(new Integer(1),
				new Integer(1), null, new Integer(1)));
		textField.setFont(new Font("���Ŀ���", Font.BOLD, 20));
		textField.setForeground(new Color(255, 99, 71));
		panel.add(textField);
		
		bStart = new JButton("\u5F00\u59CB");
		bStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					//System.out.println(textField.getText());
					vhCnt = Integer.parseInt(textField.getValue().
							toString());
					System.out.println("vhCnt"+vhCnt);
					vehs = new VehicleList(vhCnt, 5, 200);
					System.out.println(vehs.lanes+"");
					//System.out.println(vhCnt);
					if (lock == 0) {
						lock = 1;
						bStart.setText("��ͣ");
					} else {
						lock = 0;
						bStart.setText("��ʼ");
					}
				} catch (Exception e) {
					// TODO: handle exception
					// e.printStackTrace();
					//textField.setText("Please input the number of vehicles");
				}
			
			}
		});
		bStart.setBounds(867, 552, 163, 60);
		panel.add(bStart);
		
		JLabel lblNewLabel_4 = new JLabel("��������");
		lblNewLabel_4.setBackground(new Color(0, 128, 128));//RGB��ɫ����
		lblNewLabel_4.setFont(new Font("���Ŀ���", Font.BOLD, 15));
		lblNewLabel_4.setForeground(new Color(0 ,0, 0));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(883, 68, 124, 35);
		panel.add(lblNewLabel_4);
		
		
		
		JButton btnNewButton = new JButton("����");
		btnNewButton.setBounds(867, 303, 163, 60);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("һά��������ͨ��ģ����");
		lblNewLabel.setBackground(new Color(0, 128, 128));//RGB��ɫ����
		lblNewLabel.setFont(new Font("���Ŀ���", Font.BOLD, 20));
		lblNewLabel.setForeground(new Color(0 ,0, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		cellMatrix = new int[tFlow.getcellRow()][tFlow.getcellCol()];//��tFlow��ʼ��֮��
		for(int i = 0; i < tFlow.getcellRow(); i++)
			for(int j = 0; j < tFlow.getcellCol(); j++)
				cellMatrix[i][j] = 0;
	}
	
	public void iniTable()
	{
		VehTitle = new Vector<>();
		LaneTitle = new Vector<>();
		VehContents = new Vector<>();
		LaneContents = new Vector<>();
		
		VehTitle.add("���");
		VehTitle.add("λ��");
		VehTitle.add("�ٶ�");
		VehTitle.add("���ٸ���");
		VehTitle.add("���������");
		
		LaneTitle.add("ʱ��");
		LaneTitle.add("ƽ�������ܶ�");
		LaneTitle.add("ƽ��ʱ���ٶ�");
		LaneTitle.add("ƽ���ռ��ٶ�");
		LaneTitle.add("��ͨ����");
	}
	//idLane
	public int[] updateIdLane()
	{
		return idLane = vehs.idlane;
	}
	
	
	
	/*update two-dimension cellMatrix-----------------------------------------------------------------------------------------------------*/
	public void updateMatrix()
	{
		int row;
		int column;
		for(row = 1; row < tFlow.getcellRow(); row++) {
			for(column = 0; column < tFlow.getcellCol(); column++) {
				this.cellMatrix[row-1][column] = this.cellMatrix[row][column];
			}
		}
	}
	
	public int[][] fillCellMatrix()
	{
		int[] recLane = vehs.drive();
		
		updateMatrix();
		
		for(int i = 0; i < tFlow.getcellCol(); i++)
			this.cellMatrix[tFlow.getcellRow()-1][i] = 0;
		
		for(int i = 0; i < tFlow.getcellCol(); i++)
			this.cellMatrix[tFlow.getcellRow()-1][i] = recLane[i];
		//updateMatrix();
		return this.cellMatrix;
	}
	
	
	//update Table----------------------------------------------------------------------
	public void updateVehDataShow()
	{
		for(int i = 0; i < vehs.getVehTable().size(); i++) {
			VehContents.add(vehs.getVehTable().get(i));
			//System.out.println("getVehTable"+vehs.getVehTable().get(i));
		}
		//System.out.println("VehTable"+vehs.getVehTable());	
		
		VehDataShow.repaint();
//		VehDataShow.updateUI();
	}
	
	public void updateLaneDataShow()
	{
		for(int i = 0; i < vehs.getLaneTable().size(); i++) {
			LaneContents.add(vehs.getLaneTable().get(i));
			//System.out.println("getLaneTable"+vehs.getLaneTable().get(i));
		}
		//System.out.println("LaneTable"+vehs.getLaneTable());	
		LaneDataShow.repaint();
//		LaneDataShow.updateUI();
	}
	//----------------------------------------------------------------------------------
	public void manipulate()
	{
		/*start or not*/
		while (lock == 0) {/* lock == 0,indicates process is fall asleep while program is getting into pause station*/
			try {
				/*
				 * avoiding CPU high load, lead it into bed
				 */
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		while(true) {
			/*continue or not*/
			while (lock == 0) {/* lock == 0,indicates process is fall asleep while program is getting into pause station*/
				try {
					/*
					 * avoiding CPU high load, lead it into bed
					 */
					Thread.sleep(100);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			/*time slot makes it visible*/
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��������ʱ�̳�������
			this.tFlow.updatePanel(fillCellMatrix());
			this.moto.updateMot(vehs.idlane);
			//�����������
			updateVehDataShow();
			updateLaneDataShow();
		}
			
		
	}
	
}
