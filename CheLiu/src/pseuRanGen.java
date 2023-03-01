
import java.text.DecimalFormat;
import java.util.Random;



final public class pseuRanGen {
	static private long A = 0;
	static private long M = 0;
	static private long c = 0;
	private double Q = 0;
	private double R = 0;
	private double Seed = 0; 
	
	public pseuRanGen() {
		// TODO Auto-generated constructor stub
		this.A = 48271;
		this.M = 2147483647;
		this.Q = (M / A);
		this.R = (M % A);
		this.Seed = 1;
		//this.Initialize();
		//this.Initialize(seed);
	}
	
	public pseuRanGen(int seed)
	{
		this.A = 48271;
		this.M = 2147483647;
		this.Q = (M / A);
		this.R = (M % A);
		this.Initialize(seed);
	}
	
	public void Initialize(long Initval)
	{
	    this.Seed = Initval;
	}
	public double random()
	{
	    double TempSeed;
	    TempSeed = A * (Seed % Q) - R * (Seed / Q);
	    if(TempSeed >= 0)
	        Seed = TempSeed;
	    else
	        Seed = TempSeed + M;
	    return (double) Seed / M;
	}
	
	public long mod(long X, int N)
	{
		return X & (~((~0) << N));
	}
	
	public static void main(String[] argvs)
	{
		pseuRanGen pseu = new pseuRanGen(61);
		DecimalFormat dFormat = new DecimalFormat("0.00000");
		//pseu.Initialize(61);
		int stop = 400;
		for(int i = 0; i < stop; i++)
			System.out.println(dFormat.format(pseu.random()));
	}
	
}

