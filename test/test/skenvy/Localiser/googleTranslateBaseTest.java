package test.skenvy.Localiser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.skenvy.Localiser.Language;
import com.skenvy.Localiser.googleTranslateBase;

public class googleTranslateBaseTest extends googleTranslateBase {

	public googleTranslateBaseTest() throws IOException {
		super();
	}
	
	@Override //Super of baseTest
	public String getPathToDomainConstantsConfig() {
		return System.getProperty("user.dir")+"\\config_test.xml";
	}

	@Override
	public Language getLanguageToTranslateFrom() {
		return Language.English;
	}

	@SuppressWarnings("serial")
	@Override
	public Map<Language,String> getLanguagesToTranslateToMappedToOutputPaths() {
		// TODO : Correct the path, right now any path just to validate file open and close
		return new HashMap<Language,String>() {{
			put(Language.Chinese_Simplified,"C:\\workspaces\\MYGITHUB\\Localiser\\writable_test.xml");
			put(Language.Spanish,"C:\\workspaces\\MYGITHUB\\Localiser\\writable_test.xml");
			put(Language.German,"C:\\workspaces\\MYGITHUB\\Localiser\\writable_test.xml");
			put(Language.Italian,"C:\\workspaces\\MYGITHUB\\Localiser\\writable_test.xml");
			put(Language.Japanese,"C:\\workspaces\\MYGITHUB\\Localiser\\writable_test.xml");
		}};
	}

	@Override
	public String getPathToReferenceFileBeingTranslatedFrom() {
		// TODO : Correct the path, right now any path just to validate file open and close
		return "C:\\workspaces\\MYGITHUB\\Localiser\\writable_test.xml";
	}

	@SuppressWarnings("serial")
	@Override
	public LinkedHashMap<String, String> getTextToTranslateFromFromReferenceFile(FileInputStream fis) {
		// TODO Auto-generated method stub
		return new LinkedHashMap<String,String>(){{put("A","How are you today?");}};
	}

	@Override
	public void writeOutTextTranslatedFromReferenceFile(FileOutputStream fos, LinkedHashMap<String, String> translations) {
		// TODO Auto-generated method stub
	}

	@Override
	public void declarations() {
		//declareThisTestAsCurrentlyBeingUnderDevelopment();
		//declaseThisTestAsHavingVerboseOutput();
	}
	
	

}
