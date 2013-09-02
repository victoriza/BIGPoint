package net.bigpoint.assessment.gasstation.test;

import java.security.InvalidParameterException;
import java.util.concurrent.TimeUnit;

import net.bigpoint.assessment.gasstation.GasType;

import org.junit.Test;

public class TestInvalidParams extends TestGasStationBase {

	@Test(expected=InvalidParameterException.class)
	public void testInvalidType()  throws InterruptedException {
		clientExecutor.execute(new GasClient(1, null , DIESEL_AMOUNT + 1, DIESEL_PRICE));
		// the executor won t accept more new threads
		// and will finish ALL existing in queue
		clientExecutor.shutdown();

		//Wait until the max allowed time
		clientExecutor.awaitTermination(MAX_WAITING_TIME, TimeUnit.SECONDS);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testInvalidAmount()  throws InterruptedException {
		clientExecutor.execute(new GasClient(1, GasType.DIESEL , -1 , DIESEL_PRICE));
		// the executor won t accept more new threads
		// and will finish ALL existing in queue
		clientExecutor.shutdown();

		//Wait until the max allowed time
		clientExecutor.awaitTermination(MAX_WAITING_TIME, TimeUnit.SECONDS);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testInvalidMaxPrice()  throws InterruptedException {
		clientExecutor.execute(new GasClient(1, GasType.DIESEL , 1 , -1));
		// the executor won t accept more new threads
		// and will finish ALL existing in queue
		clientExecutor.shutdown();

		//Wait until the max allowed time
		clientExecutor.awaitTermination(MAX_WAITING_TIME, TimeUnit.SECONDS);
	}


}
