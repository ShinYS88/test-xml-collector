package kr.co.shinys.util.test_backup;

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
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParserUtil_backup {

	/**
	 * Method : readXMLFile 작성자 : ShinYS 변경이력 :
	 * 
	 * @param xmlPath
	 * @return Method 설명 : xml파일 불러오기
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

	// xml => json
	public static JSONObject parseXmlToJson(String xml) {
		JSONObject json = XML.toJSONObject(xml);
		return json;
	}

	// json => Map
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
	 * Method : convertNodesFromXml 작성자 : ShinYS 변경이력 :
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 *             Method 설명 : 순수 tag text 뽑기 테스트 진행중.
	 */
	public static void convertNodesFromXml(String xml) throws Exception {

		// InputSource is = new InputSource(new StringReader(xml));
		// Document document =
		// DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		// Element el = document.getDocumentElement();
		//
		// Gson gson = new Gson();
		//
		// JsonElement test = gson.toJsonTree(xml);
		//
		// System.out.println(test);
		// System.out.println(xml);

		// Map으로 만들기 => 에러 : 같은 레벨에 같은 key값 있으면 덮어씀.
		// Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
		// Elements nodeList = doc.select("item");
		// Map<String, Object> resultMap = createXmlToMap(nodeList);
		// System.out.println(resultMap);

		String seedTag = "content";

		Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
		Elements elements = doc.select(seedTag);

		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

		//부모 리스트 만들기
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

			// 부모 레벨 별로 접근
			for (Element parent : el.parents()) {
				// System.out.println(parent.tagName()+":"+parent.select("> title").text());

				// for(Element child:parent.children()){
				//
				// boolean flag = true;
				// for(Element par : el.parents()){
				// // System.out.println(child.elementSiblingIndex() +" : "+
				// par.elementSiblingIndex());
				// if(child.nodeName().equals(par.nodeName())){
				// // System.out.println(child.nodeName() +":"+ par.nodeName());
				// flag = false;
				// break;
				// }
				// }
				//
				// if(flag){
				// String tmp_tagName = parent.tagName();
				// tmp_tagName += "/"+child.tagName();
				// System.out.println(tmp_tagName+" : "+ child.ownText());
				// resultMap.put(tmp_tagName, child.ownText());
				//
				// }
				// }

				getChild(resultMap, parent, el, seedParents, null);

			}

			resultList.add(resultMap);

			System.out.println("============");
		}

		// System.out.println(resultList.get(3).get("content"));

	}

	public static void getChild(Map<String, String> resultMap, Element parent,
			Element el, List<String> seedParents, String tmp_tagName) {
		if(tmp_tagName == null){
			tmp_tagName = parent.tagName();
		}else{
			tmp_tagName += "/" + parent.tagName();
		}
		
		boolean firstFlag = true;
		
		for (Element child : parent.children()) {
			String childName = tmp_tagName + "/" +child.tagName();
			boolean flag = true;
			for ( String parentName : seedParents) {
				// System.out.println(child.elementSiblingIndex() +" : "+
				// par.elementSiblingIndex());
				if (child.nodeName().equals(parentName)) {
					
					if(firstFlag){
						System.out.println(childName + " : " + child.ownText());
						resultMap.put(childName, child.ownText());
						
						firstFlag = false;
					}
					
					flag = false;
					break;
				}else if(child.nodeName().equals("content")){
					flag = false;
					break;
				}
			}

			if (flag) {
				System.out.println(childName + " : " + child.ownText());
				resultMap.put(childName, child.ownText());

				if (child.children().size() > 0) {
					getChild(resultMap, child, el, seedParents, tmp_tagName);
					// if(child.tagName().equals("content")){
					// resultMap.put("값)"+child.tagName(), child.html());
					// }else{
					// resultMap.put("값)"+child.tagName(),
					// createXmlToMap(child.children()));
					// }
				}else{
				}
			}
		}
		tmp_tagName = "";

	}

	public static Map<String, Object> createXmlToMap(Elements nodeList) {

		Map<String, Object> resultMap = new HashMap<>();

		for (int i = 0; i < nodeList.size(); i++) {
			Element currentNode = nodeList.get(i);
			for (Attribute attrNode : currentNode.attributes()) {
				resultMap.put("속성)" + attrNode.getKey(), attrNode.getValue());
			}

			// if (currentNode.getFirstChild() != null &&
			// node.getFirstChild().getNodeType() == Node.ELEMENT_NODE) {
			if (currentNode.children().size() > 0) {
				resultMap.put("값)" + currentNode.tagName(), currentNode.text());
				if (currentNode.tagName().equals("content")) {
					resultMap.put("값)" + currentNode.tagName(),
							currentNode.html());
				} else {
					resultMap.put("값)" + currentNode.tagName(),
							createXmlToMap(currentNode.children()));
				}
			} else {
				resultMap.put("값)" + currentNode.tagName(), currentNode.html());
			}

		}

		return resultMap;
	}

	// public static Map<String, String> createXmlToMap(Node node) {
	// Map<String, String> map = new HashMap<String, String>();
	// NodeList nodeList = node.chi.getChildNodes();
	// for (int i = 0; i < nodeList.getLength(); i++) {
	// System.out.println(i);
	// Node currentNode = nodeList.item(i);
	//
	// if (currentNode.hasAttributes()) {
	// for (int j = 0; j < currentNode.getAttributes().getLength(); j++) {
	// Node item = currentNode.getAttributes().item(i);
	// if(item != null)
	// map.put(item.getNodeName(), item.getTextContent());
	// }
	// }
	//
	// if (node.getFirstChild() != null && node.getFirstChild().getNodeType() ==
	// Node.ELEMENT_NODE) {
	// map.putAll(createXmlToMap(currentNode));
	// } else if (node.getFirstChild().getNodeType() == Node.TEXT_NODE) {
	// map.put(node.getLocalName(), node.getTextContent());
	// }
	// }
	// return map;
	// }

}
