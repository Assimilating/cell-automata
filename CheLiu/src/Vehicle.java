import java.util.Random;

//import vehicle.pseuRanGen;

public class Vehicle {
	public int velocity;
	public int position;
	public double RanPosi;
	public double DecPosi;
	public pseuRanGen pseuRanGen = null;
	public int id;
	public boolean crash;
	
	public Vehicle(int v, int p, int id) {
		this.velocity = v;
		this.position = p;
		//this.pseuRanGen = new pseuRanGen();
		
		//this.RanPosi = 100 * pseuRanGen.random();
		this.RanPosi = 20 * new Random().nextFloat();
		
		//this.DecPosi = pseuRanGen.random();
		this.DecPosi = new Random().nextFloat();
		//DecPosi = (float)0.9;
		
		this.id = id;
		crash = false;
	}
}
