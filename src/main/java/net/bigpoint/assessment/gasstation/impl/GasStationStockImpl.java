package net.bigpoint.assessment.gasstation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasPumpStock;
import net.bigpoint.assessment.gasstation.GasStationStock;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

/**
 * This class is an implementation of a gas pump stock.
 * 
 * It is thread-safe syncronized by gas type.
 * 
 */
public class GasStationStockImpl implements GasStationStock {

	private final List<GasPump> gasPumps = new ArrayList<GasPump>();

	private final List<GasPumpStock> gasPumpStocks = new ArrayList<GasPumpStock>();
	
	/**
	 * Add a gas pump to the gas pump collection
	 * Add a gas pump stock to the gas pump stock collection
	 */
	public void addGasPump(GasPump gasPump) {
		gasPumpStocks.add(new GasPumpStock(gasPump));
		gasPumps.add(gasPump);
	}
	
	/**
	 * We return a defensive copy
	 */
	public List<GasPump> getGasPumps() {
		return new ArrayList<GasPump>(gasPumps);
	}

	public GasPumpStock prepaidOnBestGasPump(GasType type, double requestedLiters)throws NotEnoughGasException{

		GasPumpStock gasPump;
		
		//sync by gas type so no more than one client choose gas pump stock at a time.
		//This avoid two clients going to the same gas pump and wait while others are empty
		synchronized (type) {
			gasPump = findBestGasPump(type , requestedLiters);

			if (gasPump == null) {
				throw new NotEnoughGasException();
			}
			//IMPORTANT: we add the prepaid liters, so another we know that this gas pump has pending operations and
			//it will cost more time to be ready to do more work
			gasPump.addPrepaid(requestedLiters);
		}
		return gasPump;
	}
	/**
	 * Finds the best gas pump stock for the client based on:
	 * 1-Has enough gas of the desired type
	 * 2-Time to end with the previous pumps

	 * @param type gas type requested
	 * @param requestedLiters requested liters
	 * @return gas pump if any null in other case
	 * @throws NotEnoughGasException if there is not enough gas in any gas pump stock
	 */
	public GasPumpStock findBestGasPump(GasType type, double requestedLiters)throws NotEnoughGasException{
		
		//we use the PriorityQueue for sorting the gas pump stock for better performance
		PriorityQueue<GasPumpStock> validGasPumpStocks = new PriorityQueue<GasPumpStock>();
		
		for (GasPumpStock gasPumpS : gasPumpStocks) {
			if (gasPumpS.getGasPump().getGasType() == type && (gasPumpS.getAvailableLiters() >= requestedLiters)) {
				validGasPumpStocks.add(gasPumpS);
			}
		}
		//we cannot do it
		if (validGasPumpStocks.isEmpty()){
				throw new NotEnoughGasException();
		}
		return validGasPumpStocks.poll();
		
	}


	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		for (GasPumpStock gasPumpStock: gasPumpStocks) {
			result.append( gasPumpStock.toString());
			result.append(newLine);
		}
		return result.toString();
	}
}
