package kr.co.shinys.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.shinys.config.Global;

public class ParserUtil {

	/**
	 * Method : readXMLFile 
	 * 작성자 : ShinYS
	 * 변경이력 : 2020.01.15
	 * 
	 * @param xmlPath
	 * @return
	 * 
	 * Method 설명 : xml파일 불러오기
	 */
	public static String readXMLFile(String xmlPath) {
		BufferedReader reader;
		StringBuffer st = null;

		try {
			reader = new BufferedReader(new FileReader(xmlPath));
			st = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				st.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new String(st);
	}

	// xml => json	(json.jar)
	public static JSONObject parseXmlToJson(String xml) {
		JSONObject json = XML.toJSONObject(xml);
		return json;
	}

	// json => Map	(jackson.jar)
	public static Map<String, Object> parseJsonToMap(String json) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(json,
					new TypeReference<Map<String, Object>>() {
					});

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new HashMap<String, Object>();

	}

	
	/**
	 * Method : convertNodesFromXml
	 * 작성자 : ShinYS
	 * 변경이력 :
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 * Method 설명 : seed기준으로 XML수집을 위한 메서드
	 * jsoup.jar
	 * 
	 */
	public static List<Map<String, String>> convertNodesFromXml(String xml, String seedTag) throws Exception {

		Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
		Elements elements = doc.select(seedTag);

		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

		// 부모 리스트 만들기
		Set<String> hashSet = new HashSet<String>();
		List<String> seedParents = new ArrayList<String>();
		for (Element el : elements) {
			for (Element parent : el.parents()) {
				hashSet.add(parent.tagName());				
			}
		}
		seedParents.addAll(hashSet);

		
		
		for (Element el : elements) {
			
			Map<String, String> resultMap = new HashMap<String, String>();

			// seedTag : content 추가
			resultMap.put(seedTag, el.html());
			
			if(Global.settingMap.get("outputQuery").equals("true")) {
				System.out.println(el.parents().get(0).tagName());
			}
			
			// 컨텐츠 기준 - 부모 레벨 별로 접근
			for (Element parent : el.parents()) {
				getChild(resultMap, parent, seedParents, null, seedTag);
			}
			
								
			resultMap.put("getLevel", el.parents().get(0).tagName());
			resultMap.put("getId", el.parents().get(0).id());
			
			if(el.parents().size() > 1) {
				resultMap.put("getParentLevel", el.parents().get(1).tagName());
				resultMap.put("getParentId", el.parents().get(1).id());
			}else {
				resultMap.put("getParentLevel", "부모없음");
				resultMap.put("getParentId", null);
			}
			
			if(el.html().indexOf("<para") > -1) {
				resultMap.put("getType", "T");
			}else {
				resultMap.put("getType", "F");
			}
			
			//부모부터 => 자식까지
//			Element parent = el.parents().get(el.parents().size()-1);
//			Element endChild = el.parents().get(0);
//			getChild(resultMap, parent, el, seedParents, null);
				
				
			resultList.add(resultMap);

			if(Global.settingMap.get("outputQuery").equals("true")) {
				System.out.println("============");
			}
		}
		
		return resultList;
	}


	
	

	/**
	 * Method : getChild
	 * 작성자 : ShinYS
	 * 변경이력 :
	 * @param resultMap 	: 저장할 Map
	 * @param parent		: 조회할 부모 Element
	 * @param seedParents	: 조회에서 제외할 TagName
	 * @param tmp_tagName	: path 생성시 넘길 String
	 * @param seedTag		: seedTagName
	 * Method 설명 : Child 가져오기
	 */
	public static void getChild(Map<String, String> resultMap, Element parent,
			List<String> seedParents, String tmp_tagName, String seedTag) {
		if (tmp_tagName == null) {
			tmp_tagName = parent.tagName();
		} else {
			tmp_tagName += "/" + parent.tagName();
		}

		boolean firstFlag = true;

		for (Element child : parent.children()) {
			String childName = tmp_tagName + "/" + child.tagName();
			boolean flag = true;
			for (String parentName : seedParents) {
				if (child.nodeName().equals(parentName)) {
					if (firstFlag) {
						if(Global.settingMap.get("outputQuery").equals("true")) {
							System.out.println(childName + " : " + child.ownText());
						}
						resultMap.put(childName, child.ownText());

						firstFlag = false;
					}

					flag = false;
					break;
				} else if (child.nodeName().equals(seedTag)) {
					flag = false;
					break;
				}
			}

			if (flag) {
				if(Global.settingMap.get("outputQuery").equals("true")) {
					System.out.println(childName + " : " + child.ownText());
				}
				resultMap.put(childName, child.ownText());

				if (child.children().size() > 0) {
					getChild(resultMap, child, seedParents, tmp_tagName, seedTag);
				} else {
				}
			}
		}
		tmp_tagName = "";

	}

}
