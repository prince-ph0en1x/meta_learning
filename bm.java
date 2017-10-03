public class bm {

	private static final double PI = 3.14159;
	private static final double aFactor = 0.99;
	
	public static Boltzmann1 Net;
	
	public static void main(String[] args) {
		Net = new Boltzmann1(8, 100, 7);

	    Initialize();
	    Net.CalcWeights();
	    Net.SetRandom();    // Randomly set output nodes to either On or Off. 

	    while(ValidTour() == false)
	    {
	        Net.ReduceHeat();
	        showResults();
	        Net.Temperature(Net.Temperature() * aFactor);
	    }
	    return;
	}
	
	public static void Initialize()
	{
		int n1, n2;
		double x1, x2, y1, y2;
		double Alpha1, Alpha2;
		int nCities = Net.Cities();

	    for(n1 = 0; n1 < nCities; n1++)
	    {
	        for(n2 = 0; n2 < nCities; n2++)
	        {
	            Alpha1 = ((double)n1 / nCities) * 2.0 * PI;
	            Alpha2 = ((double)n2 / nCities) * 2.0 * PI;
	            x1 = Math.cos(Alpha1);
	            y1 = Math.sin(Alpha1);
	            x2 = Math.cos(Alpha2);
	            y2 = Math.sin(Alpha2);
	            Net.Distance(n1, n2, (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2))));
	        } // n2
	    } // n1

	    System.out.println("Temperature     Valid   Length      Tour\n");
	    return;
	}
	
	public static boolean ValidTour()
	{
		int Cities, Stops;
		int i, j;
		int nCities = Net.Cities();

	    for(i = 0; i < nCities; i++)
	    {
	        Cities = 0;
	        Stops = 0;
	        for(j = 0; j < nCities; j++)
	        {
	            if(Net.Output(i * nCities + j) == true){
	                Cities += 1;
	                if(Cities > 1){
	                    return false;
	                }
	            }
	            if(Net.Output(j * nCities + i) == true){
	                Stops += 1;
	                if(Stops > 1){
	                    return false;
	                }
	            }
	        } // j
	        if((Cities != 1) || (Stops != 1)){
	            return false;
	        }
	    } // i

	    return true;
	}
	
	public static void showResults()
	{
		if(ValidTour()){
	    	System.out.println("\nTemperature     Valid   Length      Tour");
	    	System.out.print(Net.Temperature() + "          yes     " + Net.LengthOfTour() + "     ");
	    	getTour();
	    }else{
	    	System.out.print(Net.Temperature() + "          no     ");
	    	getTour();
	    }
	    
	    System.out.println();
	    return;
	}
	
	public static void getTour()
	{
		int nCities = Net.Cities();
		int i, j;
		
	    for(i = 0; i < nCities; i++)
	    {
	        for(j = 0; j < nCities; j++)
	        {
	            if(Net.Output(i * nCities + j) == true){
	            	System.out.print(j);
	            }
	        } // j
	    } // i

	    return;

	}

}
