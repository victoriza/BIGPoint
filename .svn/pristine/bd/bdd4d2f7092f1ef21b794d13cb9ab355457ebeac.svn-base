package net.bigpoint.assessment.gasstation.test;

import java.util.concurrent.TimeUnit;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.impl.GasStationImpl;

public class TestMultiGasPumpConcurrent extends TestGasStationBase{

	public void setUp() throws java.lang.Exception{
		GasPump gasPumpD = new GasPump(GasType.DIESEL, DIESEL_AMOUNT);
		GasPump gasPumpD1 = new GasPump(GasType.DIESEL, DIESEL_AMOUNT);
		GasPump gasPumpD2 = new GasPump(GasType.DIESEL, DIESEL_AMOUNT);

		gasStation = new GasStationImpl();

		//prices
		gasStation.setPrice(GasType.DIESEL, DIESEL_PRICE);
		gasStation.setPrice(GasType.REGULAR, REGULAR_PRICE);
		gasStation.setPrice(GasType.SUPER, SUPER_PRICE);

		gasStation.addGasPump(gasPumpD);
		gasStation.addGasPump(gasPumpD1);
		gasStation.addGasPump(gasPumpD2);
				
	}
		
	public void testMultiDieselGasPumpsConcurrent() throws InterruptedException{
		
		for (int i = 0; i < 5; i++){
			clientExecutor.execute(new GasClient(i, GasType.DIESEL , 1 , DIESEL_PRICE));
		}
		// the executor won t accept more new threads
		// and will finish ALL existing in queue
		clientExecutor.shutdown();

		//Wait until the max allowed time
		clientExecutor.awaitTermination(MAX_WAITING_TIME, TimeUnit.SECONDS);
		
		LOGGER.info("status: "+gasStation.toString());
		
		assertEquals(gasStation.getNumberOfSales(), 5);
	}
	
}
