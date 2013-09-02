package net.bigpoint.assessment.gasstation.test;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;

import org.junit.Test;

public class TestGasStationMaxPrice extends TestGasStationBase{

	private final static GasType type = GasType.DIESEL;

	public void tearDown() throws java.lang.Exception {
		LOGGER.log(Level.INFO, gasStation.toString());
	}
	@Test(expected=GasTooExpensiveException.class)
	public void testGasTooExpensiveException() throws InterruptedException{
		clientExecutor.execute(new GasClient(1, type , DIESEL_AMOUNT , DIESEL_PRICE - 1));
		// the executor won t accept more new threads
		// and will finish ALL existing in queue
		clientExecutor.shutdown();

		//Wait until the max allowed time
		clientExecutor.awaitTermination(MAX_WAITING_TIME, TimeUnit.SECONDS);
	}
}
