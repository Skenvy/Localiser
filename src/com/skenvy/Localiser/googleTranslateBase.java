package com.skenvy.Localiser;


public abstract class googleTranslateBase extends localiserBase {

	/***
	 * XPath to the "Enter text..." box in Google Translate
	 */
	private final static String enterTextBoxXPath = "//*[@id=\"source\"]";
	
	/***
	 * XPath to the "results of translating" box in Google Translate.
	 */
	private final static String getTextBoxXPath = "/html/body/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[2]/div/span[1]/span";
	
	private final static String getTextBoxXPathPartialIterationPrefix = "/html/body/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[";
	
	private final static String getTextBoxXPathPartialIterationMedian = "]/div[1]/div[2]/div/span[";
	
	private final static String getTextBoxXPathPartialIterationSuffix = "]";
	
	public googleTranslateBase() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * These are the methods that must be overridden to abstract the
	 * interactivity of a specific web page. Preferably, overridden 
	 * and finalised
	 */
	
	@Override
	public final boolean translationPageLoadableFromSubquery() {
		return true;
	}
	
	@Override
	public final String getTheTranslationSubquery(Language translateTo) {
		return "?hl=en&tab=wT#view=home&op=translate&sl=" +
				getLanguageToTranslateFrom().getCode() +
				"&tl="+translateTo.getCode();
	}
	
	@Override
	public final void interactWithQuerylesslyLoadedWebPageToPrepareForCorrectTranslation(Language translateTo) {
		return;
	}

	@Override
	public final String XPathToTypeTextInto() {
		return enterTextBoxXPath;
	}

	@Override
	public final String XPathToGetTextFrom() {
		return getTextBoxXPath;
	}

	@Override
	public final String[] XPathsToGetDisambiguousTextFrom(int iteration) {
		return new String[] {
			(getTextBoxXPathPartialIterationPrefix + (iteration+3) + getTextBoxXPathPartialIterationMedian + "1" + getTextBoxXPathPartialIterationSuffix),
			(getTextBoxXPathPartialIterationPrefix + (iteration+3) + getTextBoxXPathPartialIterationMedian + "2" + getTextBoxXPathPartialIterationSuffix)
		};
	}

}
