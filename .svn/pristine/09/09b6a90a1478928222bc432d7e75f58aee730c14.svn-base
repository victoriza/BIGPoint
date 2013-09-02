package net.bigpoint.assessment.gasstation.test;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasType;

public class TestModifyGasPump extends TestGasStationBase {
	
	public void testModifyGasPump()  throws InterruptedException {
		
		Collection<GasPump> gasPumps = gasStation.getGasPumps();
		for (GasPump gp : gasPumps) {
			gp = null;
		}
		
		clientExecutor.execute(new GasClient(1, GasType.DIESEL , 1 , DIESEL_PRICE));
		// the executor won t accept more new threads
		// and will finish ALL existing in queue
		clientExecutor.shutdown();

		//Wait until the max allowed time
		clientExecutor.awaitTermination(MAX_WAITING_TIME, TimeUnit.SECONDS);
		
		assertEquals(gasStation.getNumberOfSales(), 1);
	}


}
