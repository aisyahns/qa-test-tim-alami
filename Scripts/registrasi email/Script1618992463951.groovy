import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl(GlobalVariable.url)

//click on Buat Akun button when the button has appeared in the page
WebUI.waitForElementClickable(findTestObject('registration page/btn_buat_akun_baru'), GlobalVariable.timeOut)

WebUI.click(findTestObject('registration page/btn_buat_akun_baru'))

//fill the form when form has loaded
WebUI.waitForElementVisible(findTestObject('registration page/nama_depan'), GlobalVariable.timeOut)

WebUI.setText(findTestObject('registration page/nama_depan'), nama_depan)

WebUI.setText(findTestObject('registration page/nama_belakang'), nama_belakang)

WebUI.setText(findTestObject('registration page/email_no'), no_email)

//check if field reinput email has appeared
//OPTIONAL because it does not determine the failure status of test case
boolean reinput = WebUI.verifyElementVisible(findTestObject('registration page/reinput_email'), FailureHandling.OPTIONAL)

//if reinput email field appear, then fill the field with data
if (reinput == true) {
	WebUI.setText(findTestObject('registration page/reinput_email'), reinput_email)
}

WebUI.setEncryptedText(findTestObject('registration page/password'), password)

WebUI.selectOptionByValue(findTestObject('registration page/dropdown_tahun'), tahun, true)

//default Jenis Kelamin in Perempuan value
WebUI.click(findTestObject('registration page/label_perempuan'))

//submit data
WebUI.click(findTestObject('registration page/btn_daftar'))

//verify error message about wrong email with checking if the text present
boolean text = WebUI.verifyTextPresent("Harap masukkan nomor telepon atau alamat email yang valid", false, FailureHandling.OPTIONAL)

if (text == true){
	
	WebUI.verifyTextPresent("Harap masukkan nomor telepon atau alamat email yang valid", false, FailureHandling.STOP_ON_FAILURE)
	
} else { 

	//if error message does not present or value email is correct
	//check whether the account is deactivated by facebook

	boolean nonaktif =  WebUI.verifyElementVisible(findTestObject('facebook page/header_nonaktif'), FailureHandling.OPTIONAL)
	
	if (nonaktif == true) {
		WebUI.verifyElementVisible(findTestObject('facebook page/header_nonaktif'), FailureHandling.STOP_ON_FAILURE)
	} else {
	
		//if the account is not deactivated and user can continue the registration process
		WebUI.waitForElementVisible(findTestObject('facebook page/header_success'), GlobalVariable.timeOut)
		
		WebUI.verifyElementVisible(findTestObject('facebook page/header_success'), FailureHandling.STOP_ON_FAILURE)
		
		WebUI.verifyElementVisible(findTestObject('facebook page/body_success'), FailureHandling.STOP_ON_FAILURE)
		
	}
}

WebUI.closeBrowser()