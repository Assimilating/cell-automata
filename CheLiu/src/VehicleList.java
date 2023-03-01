import java.awt.image.VolatileImage;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;
import java.util.Vector;

import org.omg.CORBA.PUBLIC_MEMBER;

public class VehicleList {
	public int VehNum;
	public int Vmax;
	public int LaneLength;
	
	public TreeSet randomPos;
	
	// 车队
	public LinkedList<Vehicle> vehicles = null;
	// 道路的车的分布，1 为车
	public int[] lanes = null;//车辆在道路上的分布，0与1表示
	public int[] crash = null;
	public int[] idlane = null;//车辆在道路上分布，0与id表示
	//是否循环右移的标志位
	public boolean rotate = false;
	
	
	private double avgDensity;
	
	private double avgTimeSpeed;
	private double avgTimeSpeedCount;
	private Vector<Vehicle> avgTimeSpeedVector;
	
	private double avgSpaceSpeed;
	private double transportationFlux;
	
	private Vector<String> VehRow;//save the vehicles's details as a row of JTable
	private Vector<String> LaneRow;//save the transportation data 
	
	public int time;
	
	private Vector VehTable;
	private Vector LaneTable;
	
	public VehicleList (int cnt, int vmax, int len) {
		this.VehNum = cnt;
		this.Vmax = vmax;
		this.LaneLength = len;
		this.vehicles = new LinkedList<Vehicle>();
		this.randomPos = new TreeSet<>();
		while(randomPos.size() != VehNum) {
        	randomPos.add(new Random().nextInt(LaneLength));
        }
		// TODO   初始化车的位置
		for(int i=1; i <= this.VehNum; i++) {
			// 车头在链表的表头
			// 车尾为链表表尾
			int currPos = (int)randomPos.pollLast();
			Vehicle ve = new Vehicle(new Random().nextInt(vmax+1), currPos, i);
			vehicles.add(ve);
		}
		
		// 初始化道路及车
		lanes = new int[LaneLength];
		updateLanes();
		
		//初始化碰撞数组与编号数组
		crash = new int[LaneLength];
		idlane = new int[LaneLength];
		for(int j = 0; j < LaneLength; j++) {
			crash[j] = 0;
			idlane[j] = 0;
		}
		
		
		rotate = false;
		
		avgDensity = (double)VehNum / LaneLength;
		
		avgTimeSpeed = 0;
		avgTimeSpeedCount = 0;
		avgTimeSpeedVector = new Vector<>();
		
		avgSpaceSpeed = 0;
		transportationFlux = 0;
		
		VehRow = new Vector<>();
		LaneRow = new Vector<>();
		
		time = 0;
		
		VehTable = new Vector<>();
		LaneTable = new Vector<>();
	}
	
	public int[] drive() {
		//计时
		time++;
		//表格清空
		VehTable.clear();
		LaneTable.clear();
		//除车头外，剩余车辆的计数
		int restNum = 1;
		//车头更新开始---------------------------------------------------
		Vehicle vpre = vehicles.get(0);
		if(vpre.velocity > 0 && new Random().nextInt(100) < vpre.RanPosi)
			vpre.velocity--;
		else if(vpre.velocity < Vmax)
			++vpre.velocity;
//		System.out.print("vpre's id "+vpre.id+"  "+"vpre's velocity "+
//				vpre.velocity+"  "+"vpre's old position "+vpre.position+"  ");
		int pospre = vpre.position + vpre.velocity;
		if (pospre >= LaneLength) {
			
			avgTimeSpeedCount++;
			avgTimeSpeedVector.add(vpre);
			
			pospre -= LaneLength;
			if(pospre >= vehicles.get(VehNum-1).position)
				pospre = 0;
			vpre.position = pospre;
			
			for(int i = 1; i < vehicles.size(); i++) {
				Vehicle vehicle=vehicles.get(i);
				vehicles.set(i-1, vehicle);
			}
			vehicles.set(vehicles.size()-1, vpre);
			//循环右移的标志位设为true
			rotate = true;
//			System.out.print("11overflow vpre new position " + vpre.position+"\n");
//			for(int i = 0; i < vehicles.size(); i++) {
//				System.out.println("id order:"+ vehicles.get(i).id);
//			}
			//表格数据-1-------------------------------------------------------------------
			VehTable.add(receiveVehRow(vpre));
		}else {
			vpre.position = pospre;
//			System.out.print("12not overflow vpre new position " + vpre.position+"\n");
			//表格数据-2-------------------------------------------------------------------
			VehTable.add(receiveVehRow(vpre));
		}
		//车头更新结束---------------------------------------------------------------------
		//剩余车辆更新开始------------------------------------------------------------------
		Vehicle vcurr = null;
		int currNum = 1;
		while(restNum++ < VehNum) {
			if(rotate == true) {
				vcurr = vehicles.get(0);
				rotate = false;
				if (vcurr.velocity > 0 && new Random().nextInt(100) < vcurr.RanPosi) {
					--vcurr.velocity;
				}  else if(vcurr.velocity < Vmax) {
					++vcurr.velocity;
				}
//				System.out.print("vcurr's id "+vcurr.id+"  "+"vcurr's velocity "+
//						vcurr.velocity+"  "+"vcurr's old position "+vcurr.position+"  ");
				int poscurr = vcurr.position + vcurr.velocity;
				if (poscurr >= LaneLength) {
					
					avgTimeSpeedCount++;
					avgTimeSpeedVector.add(vcurr);
					
					poscurr -= LaneLength;
					if(poscurr >= vehicles.get(VehNum-1).position)
						poscurr = 0;
					vcurr.position = poscurr;
					
					for(int i = 1; i < vehicles.size(); i++) {
						Vehicle vehicle=vehicles.get(i);
						vehicles.set(i-1, vehicle);
					}
					vehicles.set(vehicles.size()-1, vcurr);
					//循环右移的标志位设为true
					rotate = true;
//					System.out.print("11overflow vpre new position " + vpre.position+"\n");
//					for(int i = 0; i < vehicles.size(); i++) {
//						System.out.println("id order:"+ vehicles.get(i).id);
//					}
					//System.out.println("id order:"+ vehicles.id);
					//表格数据-3-------------------------------------------------------------------
					VehTable.add(receiveVehRow(vcurr));
				}else {
					vcurr.position = poscurr;
//					System.out.print("12not overflow vcurr new position " + vcurr.position+"\n");
					//表格数据-4-------------------------------------------------------------------
					VehTable.add(receiveVehRow(vcurr));
				}
			} else {
				vcurr = vehicles.get(currNum);
				currNum++;

				//更新速度开始-----------------------------------------------------------
				int distance = vpre.position - vcurr.position - 1;
				int decePara = ((int)(distance + (1- vcurr.DecPosi) * vpre.velocity));
				
				if (vcurr.velocity > 0 && new Random().nextInt(100) < vcurr.RanPosi) {
					--vcurr.velocity;
				} else if (vcurr.velocity > decePara && decePara > 0) {
					vcurr.velocity = ((int)(distance + (1- vcurr.DecPosi) * vpre.velocity));
					//if(vcurr.velocity<0){
						//vcurr.velocity=0;
					//}
				} else if(vcurr.velocity < Vmax) {
					++vcurr.velocity;
				}
//				System.out.print("vcurrent's id "+vcurr.id+"  "+"vcurrent's velocity"+
//						vcurr.velocity+"  "+"vcurrent's position"+vcurr.position+"  ");
				//更新速度结束-----------------------------------------------------------
				//更新位置开始-----------------------------------------------------------
				int pos = vcurr.position + vcurr.velocity;
				if(pos >= vpre.position && rotate == false){
					vcurr.velocity = new Random().nextInt(3);
					//vcurr.position = vpre.position;
					vpre.crash = true;
//					System.out.print("22vcurrent new position "+vcurr.position+"\n");
					//表格数据-5-------------------------------------------------------------------
					VehTable.add(receiveVehRow(vcurr));
				} else {
					vcurr.position = pos;
//					System.out.print("23vcurrent new position "+vcurr.position+"\n");
					//表格数据-6-------------------------------------------------------------------
					VehTable.add(receiveVehRow(vcurr));
				}	
			}	
			//更新位置结束---------------------------------------------------------------
			vpre = vcurr;
		}
		//剩余车辆更新结束------------------------------------------------------------------
		// 更新道路并返回
		updateLanes();
		updateidLane();
		updateCrash();
		
		calavgSpaceSpeed();
		calavgTimeSpeed();
		caltransportationFlux();
		//表格数据-7-------------------------------------------------------------------
		LaneTable.add(receiveLaneRow(this));
		
		return lanes;
	}

	private void updateLanes() 
	{
		for (int i = 0; i < LaneLength; ++i) {
			lanes[i] = 0;
		}
		
		for (Vehicle v : vehicles) {
			//System.out.println(v.position);
			lanes[v.position] = 1;
		}
	}
	
	private void updateidLane()
	{
		for(int i = 0; i < LaneLength; i++) {
			idlane[i] = 0;
		}
		
		for(Vehicle v : vehicles) {
			idlane[v.position] = v.id;
		}
	}
	
	private void updateCrash()
	{
		for(int i = 0; i < LaneLength; i++) {
			crash[i] = 0;
		}
		
		for(Vehicle v : vehicles) {
			if(v.crash == true) {
				crash[v.position] = 1;
			}
		}
	}
	public Vector getVehTable()
	{
		return VehTable;
	}
	
	public Vector getLaneTable()
	{
		return LaneTable;
	}
	
	public double getavgDensity()
	{
		return avgDensity;
	}
	
	public double getavgTimeSpeed() {
		return avgTimeSpeed;
	}
	
	public double getavgSpaceSpeed() {
		return avgSpaceSpeed;
	}
	
	public double getTransportationFlux()
	{
		return transportationFlux;
	}
	
	public Vector<String> getVehRow()
	{
		return VehRow;
	}
	
	public Vector<String> getLaneRow()
	{
		return LaneRow;
	}
	
	public void calavgTimeSpeed()
	{
		int velocitySum = 0;
		if(avgTimeSpeedVector.size() != 0) {
			for(Vehicle v : avgTimeSpeedVector) {
				velocitySum += v.velocity;
			}
			avgTimeSpeed = velocitySum / avgTimeSpeedCount;
		} else
			avgTimeSpeed = 0;
		
	}
	
	public void calavgSpaceSpeed()
	{
		int velocitySum = 0;
		for(Vehicle v : vehicles) {
			velocitySum += v.velocity;
		}
		if(velocitySum == 0)
			avgSpaceSpeed = 0;
		else
			avgSpaceSpeed =(double) VehNum / velocitySum;
	}
	
	public void caltransportationFlux()
	{
		transportationFlux = avgSpaceSpeed * avgDensity;
	}
	//车辆表格一行的数据
	public Vector<String> receiveVehRow(Vehicle veh)
	{
		DecimalFormat dFormat = new DecimalFormat("0.00000");
		Vector vector=new Vector<>();
		vector.add(veh.id+"");
		vector.add(veh.position+"");
		vector.add(veh.velocity+"");
		vector.add(dFormat.format(veh.DecPosi)+"");
		vector.add(dFormat.format(veh.RanPosi)+"");
		return vector;
	}
	//车队表格一行的数据
	public Vector<String> receiveLaneRow(VehicleList vehs)
	{
		DecimalFormat dFormat = new DecimalFormat("0.00000");
		Vector vector=new Vector<>();
		vector.add(vehs.time+"");
		vector.add(dFormat.format(vehs.avgDensity)+"");
		vector.add(dFormat.format(vehs.avgTimeSpeed)+"");
		vector.add(dFormat.format(vehs.avgSpaceSpeed)+"");
		vector.add(dFormat.format(vehs.transportationFlux)+"");
		return vector;
	}
		
}
