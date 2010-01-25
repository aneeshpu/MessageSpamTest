package com.thoughtworks.moneydroid.sms;

import org.easymock.classextension.EasyMock;

import android.telephony.gsm.SmsMessage;
import static com.thoughtworks.moneydroid.sms.CommonNumbers.twice;

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
		return SmsBuilder.sms().fromStandardChartered().andExpectThatCall(twice()).aboutDeposit().create();
	}

	private static class SmsBuilder {

		private SmsMessage anySmsMessage;

		private SmsBuilder() {
			anySmsMessage = EasyMock.createMock(SmsMessage.class);
		}

		public SmsBuilder andExpectThatCall(int repeat) {
			EasyMock.expectLastCall().times(repeat);
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
			return this;
		}
	}



}
