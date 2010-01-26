package com.thoughtworks.moneydroid.sms;

import static com.thoughtworks.moneydroid.sms.SmsMother.smsFromBankAboutPurchase;
import static com.thoughtworks.moneydroid.sms.SmsMother.smsFromBankAboutSomethingElseThanWithdrawal;
import static com.thoughtworks.moneydroid.sms.SmsMother.smsFromSomeSourceOtherThanTheBank;
import static com.thoughtworks.moneydroid.sms.SmsMother.smsFromStandardChartered;
import static com.thoughtworks.moneydroid.sms.SmsMother.withdrawalSmsFromStandardChartered;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.classextension.EasyMock;
import org.junit.Test;

import android.telephony.gsm.SmsMessage;

import com.thoughtworks.moneydroid.sms.handlers.PurchaseSmsHandler;
import com.thoughtworks.moneydroid.transaction.NullTransaction;
import com.thoughtworks.moneydroid.transaction.Purchase;

public class MoneyDroidSmsMessageTest {

	@Test
	public void identifies_an_sms_from_bank() throws Exception {
		SmsMessage moneyWithdrawalSms = smsFromStandardChartered();

		assertTrue("Expected sms to be from bank", new MoneyDroidSmsMessage(moneyWithdrawalSms).isFromMyBank());

		verify(moneyWithdrawalSms);
	}

	@Test
	public void identifies_a_transaction_sms_from_the_bank() throws Exception {
		SmsMessage smsFromBank = withdrawalSmsFromStandardChartered();

		assertTrue("Expected the message to be a withdrawal txn sms", new MoneyDroidSmsMessage(smsFromBank).isAWithdrawal());

		verify(smsFromBank);
	}

	@Test
	public void identifies_that_a_message_is_not_a_withdrawal_sms() throws Exception {
		SmsMessage smsFromSourceOtherThanTheBank = smsFromSomeSourceOtherThanTheBank();

		assertFalse("Did not expect the message to be a withdrawal sms", new MoneyDroidSmsMessage(smsFromSourceOtherThanTheBank).isFromMyBank());

		EasyMock.verify(smsFromSourceOtherThanTheBank);
	}

	@Test
	public void identifies_a_non_withdrawal_message_from_the_bank() throws Exception {
		SmsMessage depositSmsFromBank = smsFromBankAboutSomethingElseThanWithdrawal();
		MoneyDroidSmsMessage moneyDroidSmsMessage = new MoneyDroidSmsMessage(depositSmsFromBank);

		assertFalse(moneyDroidSmsMessage.isAWithdrawal());

		verify(depositSmsFromBank);
	}

	@Test
	public void identifies_that_a_message_is_not_withdrawal() throws Exception {
		SmsMessage smsFromBankAboutSomethingElseThanWithdrawal = smsFromBankAboutSomethingElseThanWithdrawal();

		assertTrue(new MoneyDroidSmsMessage(smsFromBankAboutSomethingElseThanWithdrawal).isNotAWithdrawal());

		verify(smsFromBankAboutSomethingElseThanWithdrawal);
	}

	@Test
	public void gives_a_withdrawal_transaction_if_sms_is_about_withdrawal() throws Exception {

		SmsMessage withdrawalSmsFromBank = withdrawalSmsFromStandardChartered();
		MoneyDroidSmsMessage moneyDroidSmsMessage = new MoneyDroidSmsMessage(withdrawalSmsFromBank);

		assertEquals(moneyDroidSmsMessage.getTransaction().getClass(), Withdrawal.class);

		verify(withdrawalSmsFromBank);
	}

	@Test
	public void gives_a_null_transaction_if_sms_is_not_a_known_type() throws Exception {
		SmsMessage smsFromSomeSourceOtherThanTheBank = smsFromSomeSourceOtherThanTheBank();

		MoneyDroidSmsMessage moneyDroidSmsMessage = new MoneyDroidSmsMessage(smsFromSomeSourceOtherThanTheBank);

		assertEquals(moneyDroidSmsMessage.getTransaction().getClass(), NullTransaction.class);
		verify(smsFromSomeSourceOtherThanTheBank);
	}

	@Test
	public void identifies_a_message_is_about_purchase() throws Exception {
		SmsMessage smsFromBankAboutPurchase = smsFromBankAboutPurchase();
		MoneyDroidSmsMessage moneyDroidSmsMessage = new MoneyDroidSmsMessage(smsFromBankAboutPurchase);

		assertTrue(moneyDroidSmsMessage.isPurchase());
		verify(smsFromBankAboutPurchase);
	}

	@Test
	public void identifies_that_a_message_is_not_about_purchase() throws Exception {
		SmsMessage withdrawalSmsFromStandardChartered = withdrawalSmsFromStandardChartered();

		assertFalse(new MoneyDroidSmsMessage(withdrawalSmsFromStandardChartered).isPurchase());
		verify(withdrawalSmsFromStandardChartered);
	}

	@Test
	public void gives_a_purchase_transaction_if_sms_is_about_purchase() throws Exception {
		SmsMessage withdrawalSmsFromBank = smsFromBankAboutPurchase();
		MoneyDroidSmsMessage moneyDroidSmsMessage = new MoneyDroidSmsMessage(withdrawalSmsFromBank);

		assertEquals(Purchase.class, moneyDroidSmsMessage.getTransaction().getClass());

		verify(withdrawalSmsFromBank);

	}
}