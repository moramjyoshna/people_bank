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
	public static final String INVALID_BENEFICIARY_NAME = "Beneficiary name may contains letters, numbers, ' and -";
	public static final String INVALID_BENEFICIARY_NUMBER = "Please enter a valid Beneficiary account number which contains first 4 digits as letters and next 16 digists as numbers";
	public static final String NO_CUSTOMER_FOUND = "Invalid customer";
	public static final String BANK_CODE_NOT_EXISTS = "Such bank not exists";

	public static final String BENEFICIARY_UPDATE_SERVICE = "Inside Beneficiary service";
	public static final String BENEFICIARYID_NOT_FOUND = "Beneficiary ID not found";

	public static final String BENEFICIARY_ID_DOES_NOT_EXISTS = "The beneficiary you entered does not exist!";

	public static final String BENEFICIARY_DOES_NOT_EXISTS = "The beneficiary you entered does not exist!";
	public static final String FAILED_TO_REMOVE_BENEFICIARY = "Failed to remove the beneficiary, we are sorry for the inconvenience!Please try again";
	public static final String BENEFICIARY_REMOVED_SUCCESSFULLY = "Beneficiary has been removed";
	public static final String REMOVE_BENEFICIARY = "Inside beneficiary controller. Call to remove a beneficiary";
	public static final String INVALID_CUSTOMER_ID = "please provide valid customer id";
	public static final String CUSTOMER_NOT_FOUND = "invalid customer id or password";
	public static final String LOGIN_SUCCESS = "logged in successfully";
	public static final String LOGIN_CONTROLLER = " Inside Login Controller";
	public static final int CUSTOMER_ID_COUNT = 6;
	public static final int EMPTY_VALUE = 0;
	public static final String IBAN_NUMBER_DOES_NOT_EXISTS = "IBAN Number does not exist!";
	public static final String IBAN_NUMBER_NOT_FOUND = "IBAN Number does not exist!";
	public static final String FAILED_TO_UPDATE_BENEFICIARY = "Failed to update Beneficiary";
	public static final Integer SIZE = 5;
	public static final String NO_BENEFICIARY_FOUND = "No Beneficiaries found for the customer";

	public static final String LOG_GET_BENEFICIARY_LIST = "Getting Beneficiary list";
	public static final String LOG_EXIST_BENEFICIARY_LIST = "Exiting getBeneficiaryList method";
	public static final String LOG_EXIST_BENEFICIARY_CONTROLLER = "Entered into Benifitiary controller getBeneficiaryList method ";
	public static final String LOG_EXISTING_BENEFICIARY_CONTROLLER = "Exiting from Benifitiary controller getBeneficiaryList method ";
	public static final String VIEW_CUSTOMER_NOT_FOUND = "Given customer is not found";

}
