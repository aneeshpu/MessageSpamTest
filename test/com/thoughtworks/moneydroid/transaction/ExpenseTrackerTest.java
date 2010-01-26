package com.thoughtworks.moneydroid.transaction;

import static com.thoughtworks.moneydroid.sms.SmsMother.smsFromBankAboutSomethingElseThanWithdrawal;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import android.telephony.gsm.SmsMessage;

import com.thoughtworks.moneydroid.sms.MoneyDroidSmsMessage;

public class ExpenseTrackerTest {

	@Test
	public void does_not_create_a_txn_if_sms_is_not_withdrawal() throws Exception {
		ExpenseTracker expenseTracker = new ExpenseTracker();
		
		SmsMessage smsFromSomeSourceOtherThanTheBank = smsFromBankAboutSomethingElseThanWithdrawal();
		MoneyDroidSmsMessage moneyDroidSmsMessage = new MoneyDroidSmsMessage(smsFromSomeSourceOtherThanTheBank);
		
		Transaction transaction = expenseTracker.newExpense(moneyDroidSmsMessage);
		assertEquals(transaction.getClass(),NullTransaction.class);
		
		verify(smsFromSomeSourceOtherThanTheBank);
	}
	
	
}
