package net.bigpoint.assessment.gasstation.test;

import java.util.concurrent.TimeUnit;

import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

import org.junit.Test;

public class TestGasStationNotEnoughGas extends TestGasStationBase{

	private final static GasType type = GasType.DIESEL;

	@Test(expected=NotEnoughGasException.class)
	public void testNotEnoughGasException() throws InterruptedException{
		clientExecutor.execute(new GasClient(1, type , DIESEL_AMOUNT + 1, DIESEL_PRICE));
		// the executor won t accept more new threads
		// and will finish ALL existing in queue
		clientExecutor.shutdown();

		//Wait until the max allowed time
		clientExecutor.awaitTermination(MAX_WAITING_TIME, TimeUnit.SECONDS);
	}
}
