package net.bigpoint.assessment.gasstation.test;

import java.util.concurrent.TimeUnit;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;
import net.bigpoint.assessment.gasstation.impl.GasStationImpl;

public class TestGasPumpNonBlockingClient extends TestGasStationBase {

	public void setUp() throws java.lang.Exception{
		
		gasStation = new GasStationImpl();

		//prices
		gasStation.setPrice(GasType.DIESEL, DIESEL_PRICE);
		gasStation.setPrice(GasType.REGULAR, REGULAR_PRICE);
		gasStation.setPrice(GasType.SUPER, SUPER_PRICE);

		
		GasPump gasPumpD = new GasPump(GasType.DIESEL, DIESEL_AMOUNT*1000);
		gasStation.addGasPump(gasPumpD);
		
		GasPump gasPumpD1 = new GasPump(GasType.DIESEL, DIESEL_AMOUNT * 1000);
		gasStation.addGasPump(gasPumpD1);
		
//		for (int i = 0; i < 10; i++) {
//			GasPump gasPumpD = new GasPump(GasType.DIESEL, DIESEL_AMOUNT);
//			gasStation.addGasPump(gasPumpD);
//		}
	}
	
	public void testGasPumpStack() throws NotEnoughGasException, InterruptedException {
		int clients = 10;
		for (int i = 0; i < clients; i++) {
			if (i == 0){
				clientExecutor.execute(new GasClient(i, GasType.DIESEL , 100 , DIESEL_PRICE));
			} else {
				clientExecutor.execute(new GasClient(i, GasType.DIESEL , 1 , DIESEL_PRICE));
			}
		}
		clientExecutor.shutdown();
				
		clientExecutor.awaitTermination(50, TimeUnit.SECONDS);
		
		LOGGER.info("Station"+gasStation.toString());
		assertEquals(gasStation.getNumberOfSales(), clients);
	}


}
