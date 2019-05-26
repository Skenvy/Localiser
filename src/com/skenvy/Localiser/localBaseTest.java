package com.skenvy.Localiser;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.skenvy.SeleniumNG.baseTest;

public class localBaseTest extends baseTest {

	public localBaseTest() {
		super();
	}

	@Override
	public String getPathToDomainConstantsConfig() {
		return System.getProperty("user.dir")+"\\config.xml";
	}
	
	static final String english = "en";
	static final String chinese = "zh-CN";
	
	@Test
	public void CanDoASimpleTranslation() throws InterruptedException {
		declareThisTestAsCurrentlyBeingUnderDevelopment();
		promptEnterKey();
		nwd.openTestDefaultWithHTTPSAndSubroot("?hl=en&tab=wT#view=home&op=translate&sl="+english+"&tl="+chinese);
		promptEnterKey();
		sendKeysToXPathElementIfExists("//*[@id=\"source\"]", "How are you today?");
		String translation = clickOnXPathElementIfExists("/html/body/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[2]/div/span[1]/span").getText();
		System.out.println(translation);
	}
	
	public static void main(String[] args) {
		//System.out.println("哇"); //Save as UTF8
		//System.out.println("你好，早上好！");
	}

}
