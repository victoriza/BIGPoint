package net.bigpoint.assessment.gasstation;

import java.util.Collection;

import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;


/**
 * This interface is for a gas station stock.
 * It introduces the prepaid option, so we know how many
 * pending liters we have to pump before the gas pump is free
 * for another client
 */
public interface GasStationStock {
	
	/**
	 * Returns the best gas pump wrapped in a gas pump stock for a client
	 * based on time to wait and requested liters.
	 * 
	 * @param type The type of gas the customer wants to buy
	 * @param requestedLiters The amount of gas the customer wants to buy
	 * @return the quickest option available if any
	 * @throws NotEnoughGasException if there is no gas pump with enough gas
	 */
	public GasPumpStock prepayOnBestGasPump(GasType type, double requestedLiters) throws NotEnoughGasException;
	
	/**
	 * Retrieve the collection of gas pump
	 * 
	 * @return the gas pumps collection
	 */
	public Collection<GasPump> getGasPumps();

	/**
	 * Add a gas pump to the stock.
	 * 
	 * @param pump the gas pump
	 */
	public void addGasPump(GasPump pump);

}