package com.skenvy.Localiser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.skenvy.SeleniumNG.baseTest;

public abstract class localiserBase extends baseTest {
	
	/***
	 * XPath to the "Enter text..." box in Google Translate
	 */
	private final static String enterTextBoxXpath = "//*[@id=\"source\"]";
	
	/***
	 * XPath to the "results of translating" box in Google Translate.
	 */
	private final static String getTextBoxXpath = "/html/body/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[2]/div/span[1]/span";
	
	public localiserBase() {
		super();
	}
	
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
	
	/***
	 * Abstracted preface to call baseTest methods of "declare*" type
	 */
	public abstract void declarations();
	
	/***
	 * Wrapped under the data provider that iterates languages to translate to,
	 * translates all the strings in to the lookup and writes them to file.
	 * @param translateTo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(dataProvider = "LanguagesBeingTranslatedTo")
	public final void translate(Language translateTo) throws InterruptedException, IOException {
		declarations();
		LinkedHashMap<String,String> translationTargets = openFileStreamAndParseInFile();
		LinkedHashMap<String,String> translationResults = new LinkedHashMap<String,String>();
		loadNewGoogleTranslatePage(translateTo);
		for(Map.Entry<String,String> entry : translationTargets.entrySet()) {
			typeIntoEnterTextBox(entry.getValue());
			String translation = retrieveTheTranslatedText();
			translationResults.put(entry.getKey(), translation);
			messageOutPerTranslation(entry.getValue(),translateTo,translation);
		}
		openFileStreamAndWriteOutFile(translateTo,translationResults);
	}
	
	/***
	 * Craft the string appropriate for the subquery to select to "to" and
	 * "from" languages.
	 * @param translateTo
	 * @return
	 */
	private final String getGoogleTranslateSubquery(Language translateTo) {
		return "?hl=en&tab=wT#view=home&op=translate&sl=" +
				getLanguageToTranslateFrom().getCode() +
				"&tl="+translateTo.getCode();
	}
	
	/***
	 * Opens Google translate page with the languages already selected
	 * @param translateTo
	 */
	private final void loadNewGoogleTranslatePage(Language translateTo) {
		nwd.openTestDefaultWithHTTPSAndSubroot(getGoogleTranslateSubquery(translateTo));
	}
	
	/***
	 * Types the string to be translated into the enter box
	 * @param textToType
	 * @throws InterruptedException
	 */
	private final void typeIntoEnterTextBox(String textToType) throws InterruptedException {
		sendKeysToXPathElementIfExists(enterTextBoxXpath, textToType);
	}
	
	/***
	 * Gets the translation from the result box
	 * @return
	 * @throws InterruptedException
	 */
	private final String retrieveTheTranslatedText() throws InterruptedException {
		//TODO The string must be interpreted as UTF8
		return clickOnXPathElementIfExists(getTextBoxXpath).getText();
	}
	
	/***
	 * Prints a console message per translations
	 * @param untranslated
	 * @param translateTo
	 * @param translation
	 */
	private void messageOutPerTranslation(String untranslated, Language translateTo, String translation) {
		System.out.println("\""+untranslated+"\" :from "+getLanguageToTranslateFrom().toString()+" to "+translateTo.toString()+": \""+translation+"\"");
	}
	
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
	
	/***
	 * Wraps the return of "languages to translate into" into an 
	 * {@code Object[list.size()][1]}
	 * @return
	 */
	@DataProvider(name = "LanguagesBeingTranslatedTo")
	public final Object[][] getLanguagesBeingTranslatedTo(){
		return wrapCollectionToDataProvider(getLanguagesToTranslateToMappedToOutputPaths().keySet());
	}
	
	public static void main(String[] args) {
		//System.out.println("哇"); //Save as UTF8
		//System.out.println("你好，早上好！");
	}

}
