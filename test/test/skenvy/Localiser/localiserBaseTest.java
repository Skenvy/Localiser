package test.skenvy.Localiser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.skenvy.Localiser.Language;
import com.skenvy.Localiser.localiserBase;

public class localiserBaseTest extends localiserBase {

	public localiserBaseTest() throws IOException {
		super();
	}
	
	@Override
	public String getPathToDomainConstantsConfig() {
		return System.getProperty("user.dir")+"\\config_test.xml";
	}

	@Override
	public Language getLanguageToTranslateFrom() {
		return Language.English;
	}

	@Override
	public List<Language> getLanguagesToTranslateTo() {
		return new ArrayList<Language>() {{
			add(Language.Chinese_Simplified);
			add(Language.Spanish);
			add(Language.German);
		}};
	}

	@Override
	public String getPathToJSONReferenceFileBeingTranslatedFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathToXMLReferenceFileBeingTranslatedFrom() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void declarations() {
		declareThisTestAsCurrentlyBeingUnderDevelopment();
		//declaseThisTestAsHavingVerboseOutput();
	}

	
	
	

}
