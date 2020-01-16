package kr.co.shinys.util.test_backup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * XmlParserUtil.java
 *
 * @author ShinYS
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * ShinYS 최초 생성
 * 
 * ===============================
 * 
 * xml 순수 tag text => Map추출 (진행중)
 *
 * </pre>
 */
public class XmlParserUtil {
	
	public static Map<String, Object> xmlToMap(String xml){

		Map<String, Object> msgMap = new HashMap<String, Object>();
		
		// XMl 파싱(Parsing)
//		InputSource is = new InputSource(new StringReader(new String(xml)));
//		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
		
		// XMl 파싱(Parsing)
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		Document document = null;
		
		try {
			documentBuilder = factory.newDocumentBuilder();
			document = documentBuilder.parse(xml);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
//		document.getDocumentElement().normalize();	//optional, but recommended
//		Element root = document.getDocumentElement();
		
		NodeList nodeList = null;
		List<Map<String, String>> xmlList = new ArrayList<>();
		String getNodeName = "";
		
		
		// root 다음의 첫번째 노드 명
		if(document.getFirstChild().getChildNodes().getLength() > 0) {
			
			getNodeName = document.getFirstChild().getNodeName();
			
			// root 다음의 첫번째 노드를 찾아서 셋팅
			nodeList = document.getElementsByTagName(getNodeName);
			System.out.println(nodeList.getLength());
			
			for(int i=0; i < nodeList.getLength(); i++) {
				// nodeList의 Child Node를 다시 nodeList에 셋팅 
				NodeList childList = nodeList.item(i).getChildNodes();
				
				// Child Node의 key, value를 map에 순차적으로 셋팅
				Map<String, String> childNodeMap = new HashMap<String, String>();
				for (int j = 0; j < childList.getLength(); j++) {
					String childNodeName = childList.item(j).getNodeName();
					String childNodeValue = childList.item(j).toString();
					
					childNodeMap.put(childNodeName, childNodeValue);
				}
				xmlList.add(childNodeMap);
			}
			msgMap.put("resultMap", xmlList);
		}else {
			msgMap.put("resultMap", null);
		}
		return msgMap;
	}
}
