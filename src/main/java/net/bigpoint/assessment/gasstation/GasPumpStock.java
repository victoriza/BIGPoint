package net.bigpoint.assessment.gasstation;

/**
 * This class is an implementation of a gas pump stock.
 * It wraps a gas  pump to be able to add prepaid amount to a gasPump
 * It means people waiting to fill gas.
 * 
 * It is not thread-safe It should only ever be used by one thread.
 */
public class GasPumpStock implements Comparable<GasPumpStock>{

	private double totalPrepaidLiters;

	private double availableLiters;
	
	private GasPump gasPump;

	public GasPumpStock(GasPump gp){
		this.gasPump = gp;
		this.totalPrepaidLiters = 0;
		this.availableLiters = gp.getRemainingAmount();
	}
	
	/**
	 * Add the prepaid amount that the client
	 * is waiting to fill in the gas pump
	 * 
	 * @param prepaidLiters the amount in liters
	 */
	public void addPrepaid(double prepaidLiters){
		this.totalPrepaidLiters += prepaidLiters;
		this.availableLiters -= prepaidLiters;
	}
	
	/**
	 * Rest the prepaid amount that the client
	 * just filled in the gas pump
	 * It has to be called to release the gas pump
	 * 
	 * @param prepaidLiters the amount in liters
	 */
	public void endOperation(double prepaidLiters){
		this.totalPrepaidLiters -= prepaidLiters;
	}

	public GasPump getGasPump() {
		return gasPump;
	}
	
	/**
	 * The total prepaid amount on that gas pump stock
	 * it cannot be bigger than the gas pump amount
	 * 
	 * @return
	 */
	public double getTotalPrepaidLiters() {
		return totalPrepaidLiters;
	}
	
	/**
	 * Compare the gasp pump stock with the following criteria:
	 * The gas pump with less liters to serve first
	 */
	public int compareTo(GasPumpStock p2){
		return (int)(totalPrepaidLiters - p2.getTotalPrepaidLiters());
	}

	public double getAvailableLiters() {
		return availableLiters;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("type: ");
		s.append(gasPump.getGasType().name());
		s.append("/");
		s.append("totalPrepaidLiters: ");
		s.append(totalPrepaidLiters);
		s.append("/");
		s.append("totalLiters: ");
		s.append(availableLiters);
		s.append("/");
		s.append("remainingAmount: ");
		s.append(getGasPump().getRemainingAmount());

		return s.toString();
	}
}
