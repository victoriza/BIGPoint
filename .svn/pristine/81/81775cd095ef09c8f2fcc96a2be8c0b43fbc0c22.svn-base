package net.bigpoint.assessment.gasstation.test;

import java.util.concurrent.TimeUnit;

import net.bigpoint.assessment.gasstation.GasType;

public class TestGasStationBasic extends TestGasStationBase {

	public void testGasStation() throws InterruptedException{
		clientExecutor.execute(new GasClient(1, GasType.DIESEL , 1 , DIESEL_PRICE));
		clientExecutor.execute(new GasClient(1, GasType.REGULAR , 1 , REGULAR_PRICE));
		clientExecutor.execute(new GasClient(1, GasType.SUPER , 1 , SUPER_PRICE));

		// the executor won t accept more new threads
		// and will finish ALL existing in queue
		clientExecutor.shutdown();

		//Wait until the max allowed time
		clientExecutor.awaitTermination(MAX_WAITING_TIME, TimeUnit.SECONDS);
		
		Thread.sleep(4);
		
		assertEquals(gasStation.getNumberOfSales(), 3);
	}
}
