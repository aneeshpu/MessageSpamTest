package com.thoughtworks.moneydroid.expenses;

import static org.junit.Assert.*;

import org.easymock.classextension.EasyMock;
import org.junit.Test;

import android.telephony.gsm.SmsMessage;

import com.thoughtworks.moneydroid.sms.MoneyDroidSmsMessage;

public class ExpenseTrackerTest {

	@Test
	public void identifies_an_sms_from_bank() throws Exception {
		
		SmsMessage smsMock = EasyMock.createMock(SmsMessage.class);
		MoneyDroidSmsMessage moneyDroidSmsMessage = new MoneyDroidSmsMessage(smsMock);
		
		assertTrue(moneyDroidSmsMessage.isFromMyBank());
	}
}
