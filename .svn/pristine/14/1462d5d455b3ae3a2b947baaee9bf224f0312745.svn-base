package net.bigpoint.assessment.gasstation.impl;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasPumpStock;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasStationStock;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

public class GasStationImpl implements GasStation {

	private final HashMap<GasType,Double> prices = new HashMap<GasType,Double>();

	private final GasStationStock gasStationStock =  new GasStationStockImpl();

	private double revenue = 0;

	private int numberOfCancellationsNoGas = 0;

	private int numberOfCancellationsTooExpensive = 0;

	private int numberOfSales = 0;

	private static Logger log = Logger.getLogger(GasStationImpl.class.getName());
	
	public double buyGas(GasType type, double amountInLiters,double maxPricePerLiter) throws NotEnoughGasException,	GasTooExpensiveException {
		
		//check parameter, exception is throw if not valid
		checkParameters(type, amountInLiters, maxPricePerLiter);
		
		//check maxPricePerLiter, exception is throw if not valid
		checkGasTooExpensive(type, maxPricePerLiter);

		//the price that the client will pay
		double price = calculatePrice(type,amountInLiters);

		try {
			log.info("Asking - for G.T: "+type.name() + ", l: "+amountInLiters);

			//Obtain the best gasPump for the combination GasType / liters
			//based on first gas pump free with the desired combination
			GasPumpStock gasPumpStock = gasStationStock.prepaidOnBestGasPump(type , amountInLiters);

			//GasPump operation has to be thread safe, so be block one petition for GasPump at a time
			log.info("Waiting - for G.T: "+type.name() + ", l: "+amountInLiters);
			synchronized (gasPumpStock) {
				doGasOperation(amountInLiters, gasPumpStock);
				log.info("bye! G.T: "+type.name() + ", liters: "+amountInLiters + " sales: "+numberOfSales);
			}
			//we add the current sale to the total revenue of the gas station 
			revenue += price;

		} catch (NotEnoughGasException ex) {
			log.severe("Not posible! - for G.T: "+type.name() + ", l: "+amountInLiters);
			//we increment the cancellations for not having enough gas
			incrementCancellations();
		}
		return price;
	}
	
	/**
	 * Check the params  
	 * 
	 * @param type The type of gas the customer wants to buy
	 * @param amountInLiters The amount of gas the customer wants to buy. Nothing less than this amount is acceptable!
	 * @param maxPricePerLiter The maximum price the customer is willing to pay per liter
	 * 
	 * @throws InvalidParameterException if amounts are below zero or we do not have price for the gas type
	 */
	private void checkParameters(GasType type, double amountInLiters, double maxPricePerLiter) throws InvalidParameterException{
		if (type == null || amountInLiters <= 0 || maxPricePerLiter < 0 || !prices.containsKey(type))
			throw new InvalidParameterException();
	}

	/**
	 * Checks that the price for the type gas suits the client demands
	 *	
	 * @param type The type of gas the customer wants to buy
	 * @param maxPricePerLiter The maximum price the customer is willing to pay per liter
	 * @throws GasTooExpensiveException if gas is not sold at the requested price (or any lower price) 
	 */
	private void checkGasTooExpensive(GasType type, double maxPricePerLiter) throws GasTooExpensiveException {
		if(prices.get(type) > maxPricePerLiter){
			numberOfCancellationsTooExpensive++;
			throw new GasTooExpensiveException();
		}
	}
	/**
	 * Do the pump gas operation, it increases the sales
	 * and remove the prepaid operation of the gas pump stock when the client is ready
	 * 
	 * @param amountInLiters the liters to pump
	 * @param gasPumpStock the gas pump stock selected
	 */
	private void doGasOperation(double amountInLiters, GasPumpStock gasPumpStock) {
		incrementSales();

		//do the operation of fill
		gasPumpStock.getGasPump().pumpGas(amountInLiters);

		gasPumpStock.endOperation(amountInLiters);
	}

	private void incrementSales() {
		numberOfSales++;
	}

	private void incrementCancellations() {
		numberOfCancellationsNoGas++;
	}

	private double calculatePrice(GasType type, double amountInLiters) {
		return prices.get(type) * amountInLiters;
	}

	public final Collection<GasPump> getGasPumps() {
		return gasStationStock.getGasPumps();
	}

	public int getNumberOfCancellationsNoGas() {
		return numberOfCancellationsNoGas;
	}

	public int getNumberOfCancellationsTooExpensive() {
		return numberOfCancellationsTooExpensive;
	}

	public int getNumberOfSales() {
		return numberOfSales;
	}

	public double getPrice(GasType type) {
		return prices.get(type);
	}

	public double getRevenue() {
		return revenue;
	}

	public void setPrice(GasType type, double price) {
		prices.put(type, price);
	}

	public void addGasPump(GasPump pump) {
		gasStationStock.addGasPump(pump);
	}

	@Override public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		//print field names paired with their values
		result.append(newLine);
		result.append( "################################## " );
		result.append(newLine);
		result.append( "GasType/price: " );
		result.append(newLine);
		for ( GasType gasType: GasType.values() ) {
			result.append( gasType.name());
			result.append("/"+prices.get(gasType));
			result.append(newLine);
		}

		result.append(newLine);

		result.append( "GasPumps: " );
		result.append(newLine);
		result.append(gasStationStock.toString());
		result.append(newLine);

		result.append( "revenue: " +revenue +newLine);
		result.append( "numberOfSales: " +numberOfSales+newLine);

		result.append( "numberOfCancellationsNoGas: " +numberOfCancellationsNoGas +newLine);
		result.append( "numberOfCancellationsTooExpensive: " +numberOfCancellationsTooExpensive);

		result.append(newLine);
		result.append( "################################## " );

		return result.toString();
	}

}
