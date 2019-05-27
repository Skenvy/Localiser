package com.skenvy.Localiser;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.skenvy.SeleniumNG.baseTest;

public abstract class localiserBase extends baseTest {
	
	public localiserBase() {
		super();
	}
	
	public abstract Language getLanguageToTranslateFrom();
	
	public abstract List<Language> getLanguagesToTranslateTo();
	
	public abstract String getPathToJSONReferenceFileBeingTranslatedFrom();
	
	public abstract String getPathToXMLReferenceFileBeingTranslatedFrom();
	
	public abstract void declarations();
	
	@Test
	public void CanDoASimpleTranslation() throws InterruptedException {
		declarations();
		for(Language translateTo : getLanguagesToTranslateTo()) {
			nwd.openTestDefaultWithHTTPSAndSubroot(getGoogleTranslateSubquery(translateTo));
			sendKeysToXPathElementIfExists("//*[@id=\"source\"]", "How are you today?");
			String translation = clickOnXPathElementIfExists("/html/body/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[2]/div/span[1]/span").getText();
			//The string must be interpreted as UTF8
			System.out.println("From "+getLanguageToTranslateFrom().toString()+" to "+translateTo.toString()+": "+translation);
		}
	}
	
	private String getGoogleTranslateSubquery(Language translateTo) {
		return "?hl=en&tab=wT#view=home&op=translate&sl=" +
				getLanguageToTranslateFrom().getCode() +
				"&tl="+translateTo.getCode();
	}
	
	@DataProvider(name = "StringsToTranslate")
	private Object[][] getStringsToTranslate(){
		if(getPathToJSONReferenceFileBeingTranslatedFrom() != null) {
			//TODO Read in a top level JSON
			return null;
		} else if (getPathToJSONReferenceFileBeingTranslatedFrom() != null) {
			//TODO Read in a top level XML
			return null;
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		//System.out.println("哇"); //Save as UTF8
		//System.out.println("你好，早上好！");
	}

}
