package com.thoughtworks.moneydroid.transaction;

import static com.thoughtworks.moneydroid.sms.SmsMother.smsFromBankAboutPurchase;
import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import android.telephony.gsm.SmsMessage;

import com.thoughtworks.moneydroid.sms.MoneyDroidSmsMessage;
import com.thoughtworks.moneydroid.sms.handlers.PurchaseSmsHandler;

public class PurchaseSmsHandlerTest {

	@Test
	public void contains_information_about_amount_spent() throws Exception {
		
		SmsMessage smsFromBankAboutPurchase = smsFromBankAboutPurchase(100);
		MoneyDroidSmsMessage moneyDroidSmsMessage = new MoneyDroidSmsMessage(smsFromBankAboutPurchase);
		
		PurchaseSmsHandler purchase = new PurchaseSmsHandler(moneyDroidSmsMessage);
		
		assertTrue(purchase.createTransaction() != null);
	}
	
	@Test
	public void creates_a_fully_populated_purchase_object() throws Exception {
		SmsMessage smsFromBankAboutPurchase = smsFromBankAboutPurchase(100);
		MoneyDroidSmsMessage moneyDroidSmsMessage = new MoneyDroidSmsMessage(smsFromBankAboutPurchase);
		
		PurchaseSmsHandler purchaseHandler = new PurchaseSmsHandler(moneyDroidSmsMessage);
		Transaction purchase = purchaseHandler.createTransaction();
		
		assertEquals("100.0", purchase.amount());
	}
	
	@Test
	public void foo() throws Exception {
		
		Pattern pattern = Pattern.compile("You have done a debit purchase of INR ([0-9,.]*) at ([A-Za-z ]*).Available balance as on ([0-9A-Za-z: ]*) is INR ([0-9,.]*)");
		SmsMessage smsFromBankAboutPurchase = smsFromBankAboutPurchase(100);
		String displayMessageBody = smsFromBankAboutPurchase.getDisplayMessageBody();
		Matcher matcher = pattern.matcher(displayMessageBody);
		System.out.println(displayMessageBody);
//		"You have done a debit purchase of INR 100.0 at ARJAY RETAIL SONY BANGALORE.Available balance as on Jan 6 2010 8:45 PM IST is INR 10,000.00"

//		Matcher matcher = pattern.matcher("You have done a debit purchase of INR 100 at ARJAY RETAIL STORE BANGALORE.Available balance as on Jan 6 2010 8:45PM IST is INR 10,000");
		System.out.println(matcher.find());
		System.out.println(matcher.group(1));
		System.out.println(matcher.group(2));
		System.out.println(matcher.group(3));
		System.out.println(matcher.group(4));
	}
}