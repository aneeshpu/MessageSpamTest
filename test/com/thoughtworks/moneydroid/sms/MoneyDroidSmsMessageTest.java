package com.thoughtworks.moneydroid.sms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.classextension.EasyMock;
import org.junit.Test;

import android.telephony.gsm.SmsMessage;

public class MoneyDroidSmsMessageTest {

	@Test
	public void identifies_an_sms_from_bank() throws Exception {
		SmsMessage moneyWithdrawalSms = SmsMother.smsFromStandardChartered();
		
		assertTrue("Expected sms to be from bank",new MoneyDroidSmsMessage(moneyWithdrawalSms).isFromMyBank());
		
		EasyMock.verify(moneyWithdrawalSms);
	}
	
	@Test
	public void identifies_a_transaction_sms_from_the_bank() throws Exception {
		SmsMessage smsFromBank = SmsMother.withdrawalSmsFromStandardChartered();
		
		assertTrue("Expected the message to be a withdrawal txn sms", new MoneyDroidSmsMessage(smsFromBank).isAWithdrawalTransactionMessage());
		
		EasyMock.verify(smsFromBank);
	}
	
	@Test
	public void identifies_that_a_message_is_not_a_withdrawal_sms() throws Exception {
		SmsMessage smsFromSourceOtherThanTheBank = SmsMother.smsFromSomeSourceOtherThanTheBank();
		
		assertFalse("Did not expect the message to be a withdrawal sms", new MoneyDroidSmsMessage(smsFromSourceOtherThanTheBank).isFromMyBank());
		
		EasyMock.verify(smsFromSourceOtherThanTheBank);
	}
	
	@Test
	public void identifies_a_non_withdrawal_message_from_the_bank() throws Exception {
		SmsMessage depositSmsFromBank = SmsMother.smsFromBankAboutSomethingElseThanWithdrawal();
		MoneyDroidSmsMessage moneyDroidSmsMessage = new MoneyDroidSmsMessage(depositSmsFromBank);
		
		assertFalse(moneyDroidSmsMessage.isAWithdrawalTransactionMessage());
		assertTrue(moneyDroidSmsMessage.isFromMyBank());
		
		EasyMock.verify(depositSmsFromBank);
	}
}