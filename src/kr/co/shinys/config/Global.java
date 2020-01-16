package kr.co.shinys.config;

import java.util.Map;

import kr.co.shinys.util.ParserUtil;

public class Global {

	public static Map<String, Object> config;
	public static Map<String, String> seedMap;
	public static Map<String, String> fieldMap;
	public static Map<String, String> addFieldMap;
	public static Map<String, String> settingMap;
	public static int fileCnt;
	public static boolean fileFlag;
	
	public static void setConfig(String configPathName) {
		
		// 설정json 값 불러오기
		String json = ParserUtil.readXMLFile(configPathName);
		Map<String, Object> config = ParserUtil.parseJsonToMap(json);
		
		//seed 설정 값 가져오기
		seedMap = (Map<String, String>) config.get("seed");
		//field 설정 값 가져오기
		fieldMap = (Map<String, String>) config.get("field");
		//addField 설정 값 가져오기
		addFieldMap = (Map<String, String>) config.get("addField");
		//setting 설정 값 가져오기
		settingMap = (Map<String, String>) config.get("setting");
			
		fileCnt = 1;
		fileFlag = false;
		
	}
	
}
