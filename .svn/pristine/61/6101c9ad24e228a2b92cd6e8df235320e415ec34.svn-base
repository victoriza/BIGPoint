package net.bigpoint.assessment.gasstation.test;

import java.util.concurrent.TimeUnit;

import net.bigpoint.assessment.gasstation.GasType;

public class TestGasStationSellAllDiesel extends TestGasStationBase {

	public void testGasStationSellAllDiesel() throws InterruptedException{
		
		
		clientExecutor.execute(new GasClient(1, GasType.DIESEL , (double)DIESEL_AMOUNT/3 , DIESEL_PRICE));
		clientExecutor.execute(new GasClient(3, GasType.DIESEL , (double)DIESEL_AMOUNT/3 , DIESEL_PRICE));
		clientExecutor.execute(new GasClient(4, GasType.DIESEL , (double)DIESEL_AMOUNT/3, DIESEL_PRICE));
		
		// the executor won t accept more new threads
		// and will finish ALL existing in queue
		clientExecutor.shutdown();

		//Wait until the max allowed time
		clientExecutor.awaitTermination(MAX_WAITING_TIME, TimeUnit.SECONDS);

		
		LOGGER.info("gasStation: "+gasStation);
		assertEquals(gasStation.getNumberOfSales(), 3);
	}
}
