package com.thoughtworks.moneydroid.transaction;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class MoneyTest {

	@Test
	public void can_compare_equal_amounts() throws Exception {
		assertEquals(new Money(100.00), new Money(100.00));
	}
	
	@Test
	public void unequal_amounts_are_not_equal() throws Exception {
		assertNotSame(new Money(100.00), new Money(50.00));
	}
	
	@Test
	public void gives_amount_as_string_representation() throws Exception {
		assertEquals("100.0", new Money(100.00).toString());
	}
}
