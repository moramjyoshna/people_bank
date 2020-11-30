package com.bank.people.util;

public class BankConstants {

	private BankConstants() {
		throw new IllegalStateException("Utility class");
	}

	public static final String INVALID_BENEFICIARY_ACCOUNT = "Invalid Beneficiary Account";
	public static final String BENEFICIARY_NOT_FOUND = "Beneficiary account not exist";
	public static final String DUPLICATE_BENEFICIARY = "You have already added this customer as beneficiary!!";
	public static final String CANNOT_ADD_OWN_ACCOUNT = "Canot add own account as favorite";
	public static final String ADD_BENEFICIARY_FAILED = "Add beneficiary failed";
	public static final String BENEFICIARY_SERVICE_IMPL = "Inside Beneficiary Service impl";
	public static final String BENEFICIARY_CONTROLLER = "Inside Beneficiary Controller";
	public static final String INVALID_BENEFICIARY_NAME="Beneficiary name may contains letters, numbers, ' and -";
	public static final String INVALID_BENEFICIARY_NUMBER="Please enter a valid Beneficiary account number which contains first 4 digits as letters and next 16 digists as numbers";
	public static final String NO_CUSTOMER_FOUND ="Invalid customer";
	public static final String BANK_CODE_NOT_EXISTS="Such bank not exists";

}
