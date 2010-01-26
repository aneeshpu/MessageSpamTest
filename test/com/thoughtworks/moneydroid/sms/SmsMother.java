package com.thoughtworks.moneydroid.sms;

import org.easymock.classextension.EasyMock;

import android.telephony.gsm.SmsMessage;

public class SmsMother {

	public static SmsMessage withdrawalSmsFromStandardChartered() {
		return SmsBuilder.sms().fromStandardChartered().aboutAWithdrawal().create();
	}

	public static SmsMessage smsFromSomeSourceOtherThanTheBank() {
		return SmsBuilder.sms().fromSourceOtherThanTheBank().create();
	}

	public static SmsMessage smsFromStandardChartered() {
		return SmsBuilder.sms().fromStandardChartered().create();
	}

	public static SmsMessage smsFromBankAboutSomethingElseThanWithdrawal() {
		return SmsBuilder.sms().fromStandardChartered().aboutDeposit().create();
	}

	public static SmsMessage smsFromBankAboutPurchase() {
		return smsFromBankAboutPurchase(4900.00f);
	}
	
	public static SmsMessage smsFromBankAboutPurchase(float amount){
		return SmsBuilder.sms().fromStandardChartered().aboutPurchase(String.valueOf(amount)).create();
	}

	private static class SmsBuilder {

		private SmsMessage anySmsMessage;

		private SmsBuilder() {
			anySmsMessage = EasyMock.createMock(SmsMessage.class);
		}

		public SmsBuilder aboutPurchase(String amount) {
			EasyMock.expect(anySmsMessage.getDisplayMessageBody()).andReturn(
					String.format("You have done a debit purchase of INR %s at ARJAY RETAIL SONY BANGALORE.Available balance as on Jan 6 2010 8:45 PM IST is INR 10,000.00",amount));
			EasyMock.expectLastCall().anyTimes();
			return this;
		}

		public SmsBuilder aboutDeposit() {
			EasyMock.expect(anySmsMessage.getDisplayMessageBody()).andReturn("Rs.500 has been deposited into into ACCT.NO XXXXXXX1533");
			return this;
		}

		public SmsBuilder aboutAWithdrawal() {
			EasyMock.expect(anySmsMessage.getDisplayMessageBody()).andReturn(
					"You have withdrawn Rs.500.00 from ACCT.NO XXXXXXX1533 ON jAN 12 2010 12:59PM IST Avail.Bal:Rs.1000.11 For Mini Statement sms TXN to 09987123123-StanChart");
			return this;
		}

		public SmsBuilder fromStandardChartered() {
			EasyMock.expect(anySmsMessage.getDisplayOriginatingAddress()).andReturn("DM-StanChrt");
			EasyMock.expectLastCall().anyTimes();
			return this;
		}

		public static SmsBuilder sms() {
			return new SmsBuilder();
		}

		public SmsMessage create() {
			EasyMock.replay(anySmsMessage);
			return anySmsMessage;
		}

		public SmsBuilder fromSourceOtherThanTheBank() {
			EasyMock.expect(anySmsMessage.getDisplayOriginatingAddress()).andReturn("TA-Adiclub");
			EasyMock.expectLastCall().anyTimes();
			return this;
		}
	}

}
