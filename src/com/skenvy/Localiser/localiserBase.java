package com.skenvy.Localiser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.skenvy.SeleniumNG.baseTest;

public abstract class localiserBase extends baseTest {
	
	public localiserBase() {
		super();
	}
	
	/*
	 * Abstracts
	 */
	
	/***
	 * Abstracted preface to call baseTest methods of "declare*" type
	 */
	public abstract void declarations();
	
	/***
	 * Return the single "Language" enum corresponding to the language of the
	 * source reference file. Example: 
	 * <pre>{@code 
	 * 	return Language.SomeLanguage;
	 * }</pre>
	 * @return
	 */
	public abstract Language getLanguageToTranslateFrom();
	
	/***
	 * Returns a list of the languages to have the reference translated to.
	 * Example: 
	 * <pre>{@code 
	 * 	return new HashMap<Language,String>() {{
	 * 		put(Language.FirstLanguage,"C:\Some\Path"); 
	 * 		put(Language.SecondLanguage,"C:\Some\Path"); 
	 * 	}};
	 * </pre>
	 * @return
	 */
	public abstract Map<Language,String> getLanguagesToTranslateToMappedToOutputPaths();
	
	/***
	 * If this returns true, then the abstract 'getTheTranslationSubquery" MUST
	 * return the path that can be appended to the test default parameters in
	 * the configuration file, as a subroot query. Else, the abstract 
	 * "interactWithQuerylesslyLoadedWebPageToPrepareForCorrectTranslation"
	 * MUST dictate a way of interacting with the web page loaded without a 
	 * query such that it is ready to have text entered into some "translate
	 * from" box and retrieve the output from some "result" box.
	 * @return
	 */
	public abstract boolean translationPageLoadableFromSubquery();
	
	/***
	 * If "translationPageLoadableFromSubquery" returns TRUE, then this MUST
	 * return the path that can be appended to the test default parameters in
	 * the configuration file, as a subroot query.
	 * @param translateTo
	 * @return
	 */
	public abstract String getTheTranslationSubquery(Language translateTo);
	
	/***
	 * If "translationPageLoadableFromSubquery" returns FALSE, then this MUST
	 * dictate a way of interacting with the web page loaded without a query 
	 * such that it is ready to have text entered into some "translate from" 
	 * box and retrieve the output from some "result" box.
	 * @param translateTo
	 */
	public abstract void interactWithQuerylesslyLoadedWebPageToPrepareForCorrectTranslation(Language translateTo);
	
	/***
	 * The XPath of the text input WebElement which received from "sendKeys"
	 * @return
	 */
	public abstract String XPathToTypeTextInto();
	
	/***
	 * The XPath of the text field WebElement which takes "getText"
	 * @return
	 */
	public abstract String XPathToGetTextFrom();
	
	/***
	 * The XPaths of the text fields WebElements which take "getText"; iterates
	 * from 0.
	 * @return
	 */
	public abstract String[] XPathsToGetDisambiguousTextFrom(int iteration);
	
	/***
	 * Returns the literal path to the file to be read from.
	 * @return
	 */
	public abstract String getPathToReferenceFileBeingTranslatedFrom();
	
	/*** 
	 * Read in from the reference file the translation targets, converting key
	 * value pairs into a {@code LinkedHashMap<String,String>}
	 * @param fis
	 * @return
	 */ //TODO
	public abstract LinkedHashMap<String,String> getTextToTranslateFromFromReferenceFile(FileInputStream fis);
	
	/***
	 * Writes to a file, the results of having done the translations
	 * @param fos
	 * @param translations
	 */
	public abstract void writeOutTextTranslatedFromReferenceFile(FileOutputStream fos, LinkedHashMap<String,String> translations);
	
	/*
	 * The translation test
	 */
	
	/***
	 * Wrapped under the data provider that iterates languages to translate to,
	 * translates all the strings in to the lookup and writes them to file.
	 * @param translateTo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(dataProvider = "LanguagesBeingTranslatedTo")
	public final void translate(Language translateTo) throws InterruptedException, IOException {
		//Make test declarations and read in from file, and prepare output
		declarations();
		LinkedHashMap<String,String> translationTargets = openFileStreamAndParseInFile();
		LinkedHashMap<String,String> translationResults = new LinkedHashMap<String,String>();
		loadTheTranslatingWebPage(translateTo);
		String previousTranslation = "";
		String translation = "";
		for(Map.Entry<String,String> entry : translationTargets.entrySet()) {
			typeIntoEnterTextBox(entry.getValue());
			translation = retrieveTheTranslatedText();
			while(translation.equals(previousTranslation)) {
				translation = retrieveTheTranslatedText();
			}
			translationResults.put(entry.getKey(), translation);
			messageOutPerTranslation(entry.getValue(),translateTo,translation);
			previousTranslation = translation;
		}
		openFileStreamAndWriteOutFile(translateTo,translationResults);
	}
	
	/*
	 * Private finals : File streaming
	 */
	
	/***
	 * Loads the reference file
	 * @param configFilePath
	 * @throws IOException
	 */
	private final LinkedHashMap<String,String> openFileStreamAndParseInFile() throws IOException{
		FileInputStream in = new FileInputStream(getPathToReferenceFileBeingTranslatedFrom());
		LinkedHashMap<String,String> translationTargets = getTextToTranslateFromFromReferenceFile(in);
		in.close();
		return translationTargets;
	}
	
	/***
	 * Writes the translations out
	 * @param configFilePath
	 * @throws IOException
	 */
	private final void openFileStreamAndWriteOutFile(Language translatedTo, LinkedHashMap<String,String> translations) throws IOException{
		FileOutputStream out = new FileOutputStream(getLanguagesToTranslateToMappedToOutputPaths().get(translatedTo));
		writeOutTextTranslatedFromReferenceFile(out,translations);
		out.close();
	}
	
	/*
	 * Private finals : Page interaction
	 */
	
	/***
	 * Load the web page that will be used for translating text
	 * @param translateTo
	 */
	private final void loadTheTranslatingWebPage(Language translateTo) {
		if(this.translationPageLoadableFromSubquery()) {
			unwrapNiceWebDriver().openTestDefaultWithHTTPSAndSubroot(getTheTranslationSubquery(translateTo));
		} else {
			//unwrapNiceWebDriver().openTestDefaultWithHTTPSAtBase();
			//"openTestDefaultWithHTTPSAtBase" currently already called by "baseTest": may change
			interactWithQuerylesslyLoadedWebPageToPrepareForCorrectTranslation(translateTo);
		}
	}
	
	/***
	 * Types the string to be translated into the enter box
	 * @param textToType
	 * @throws InterruptedException
	 */
	private final void typeIntoEnterTextBox(String textToType) throws InterruptedException {
		sendKeysAfterClickingToXPathElementIfExists(XPathToTypeTextInto(), textToType);
	}
	
	/***
	 * Gets the translation from the result box
	 * @return
	 * @throws InterruptedException
	 */
	private final String retrieveTheTranslatedText() throws InterruptedException {
		//TODO The string must be interpreted as UTF8
		try {
			WebElement clicked = clickOnXPathElementIfExists(XPathToGetTextFrom());
			if(clicked != null) {
				return clicked.getText();
			} else {
				String[] disambiguousTranslationPaths = XPathsToGetDisambiguousTextFrom(0);
				clicked = clickOnXPathElementIfExists(disambiguousTranslationPaths[0]);
				if(clicked == null) {
					return "TRANSLATION LOADED A DISAMBIGUOUS RESPONSE THAT COULD NOT BE CLICKED INTO";
				} else {
					String partial = "{";
					int iter = 1;
					while(clicked != null) {
						partial += " (";
						for(String disambiguousTranslationPath : disambiguousTranslationPaths) {
							partial += (clickOnXPathElementIfExists(disambiguousTranslationPath).getText()+" | ");
						}
						partial = partial.substring(0, partial.length()-3)+") ";
						disambiguousTranslationPaths = XPathsToGetDisambiguousTextFrom(iter);
						clicked = clickOnXPathElementIfExists(disambiguousTranslationPaths[0]);
						iter++;
					}
					return (partial+"}");
				}
			}
		} catch(StaleElementReferenceException e) {
			return this.retrieveTheTranslatedText();
		}
	}
	
	/*
	 * Private finals : Niceties
	 */
	
	/***
	 * Prints a console message per translations
	 * @param untranslated
	 * @param translateTo
	 * @param translation
	 */
	private final void messageOutPerTranslation(String untranslated, Language translateTo, String translation) {
		System.out.println("\""+untranslated+"\": from "+getLanguageToTranslateFrom().toString()+" to "+translateTo.toString()+": \""+translation+"\"");
	}
	
	/***
	 * Wraps the return of "languages to translate into" into an 
	 * {@code Object[list.size()][1]}
	 * @return
	 */
	@DataProvider(name = "LanguagesBeingTranslatedTo")
	protected final Object[][] getLanguagesBeingTranslatedTo(){
		return wrapCollectionToDataProvider(getLanguagesToTranslateToMappedToOutputPaths().keySet());
	}
	
	public static void main(String[] args) {
		//System.out.println("哇"); //Save as UTF8
		//System.out.println("你好，早上好！");
	}

}
