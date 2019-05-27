package com.skenvy.Localiser;

public enum Language {

	Afrikaans("af"),
	Albanian("sq"),
	Amharic("am"),
	Arabic("ar"),
	Armenian("hy"),
	Azerbaijani("az"),
	Basque("eu"),
	
	//TODO
	Belarusian(""),
	Bengali(""),
	Bosnian(""),
	Bulgarian(""),
	Catalan(""),
	Cebuano(""),
	Chichewa(""),
	
	
	Chinese_Simplified("zh-CN"),
	Chinese_Traditional("zh-TW"),
	
	//TODO
	Corsican(""),
	Croatian(""),
	Czech(""),
	Danish(""),
	Dutch(""),
	
	
	English("en"),
	Esperanto("eo"),
	
	//TODO
	Estonian(""),
	Filipino(""),
	Finnish(""),
	French(""),
	Frisian(""),
	Galician(""),
	Georgian(""),
	
	
	German("de"),
	Greek("el"),
	
	//TODO
	Gujarati(""),
	Haitian_Creole(""),
	Hausa(""),
	Hawaiian(""),
	Hebrew(""),
	Hindi(""),
	Hmong(""),
	Hungarian(""),
	Icelandic(""),
	Igbo(""),
	Indonesian(""),
	Irish(""),
	
	
	Italian("it"),
	Japanese("ja"),
	
	//TODO
	Javanese(""),
	Kannada(""),
	Kazakh(""),
	Khmer(""),
	Korean(""),
	Kurdish_Kurmanji(""),
	Kyrgyz(""),
	Lao(""),
	Latin(""),
	Latvian(""),
	Lithuanian(""),
	Luxembourgish(""),
	Macedonian(""),
	Malagasy(""),
	Malay(""),
	Malayalam(""),
	Maltese(""),
	Maori(""),
	Marathi(""),
	Mongolian(""),
	Myanmar_Burmese(""),
	Nepali(""),
	Norwegian(""),
	Pashto(""),
	Persian(""),
	Polish(""),
	Portuguese(""),
	Punjabi(""),
	Romanian(""),
	Russian(""),
	Samoan(""),
	Scots_Gaelic(""),
	Serbian(""),
	Sesotho(""),
	Shona(""),
	Sindhi(""),
	Sinhala(""),
	Slovak(""),
	Slovenian(""),
	Somali(""),
	
	
	Spanish("es"),
	
	//TODO
	Sundanese(""),
	Swahili(""),
	Swedish(""),
	Tajik(""),
	Tamil(""),
	Telugu(""),
	Thai(""),
	Turkish(""),
	Ukrainian(""),
	Urdu(""),
	Uzbek(""),
	Vietnamese(""),
	Welsh(""),
	Xhosa(""),
	Yiddish(""),
	Yoruba(""),
	Zulu("");

	private final String code;

	private Language(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

}
