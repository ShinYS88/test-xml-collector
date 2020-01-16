package kr.co.shinys.util.test_backup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class JsoupParser {

//	
//	public static Map<String, Object> xmlToMapAll(String xml) throws ParserConfigurationException
//	{
//	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//	    DocumentBuilder db = dbf.newDocumentBuilder();
//
//	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//	    Map<String, Object> map = new HashMap<String, Object>();
//	    try
//	    {
//	        InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
//
//	        Document xmlDoc = db.parse(stream);
//
//	        Element rootNode = xmlDoc.getDocumentElement();
//
//	        NodeList eles = rootNode.getChildNodes();
//
//	        for (int i = 0; i < eles.getLength(); i++)
//	        {
//	            Node ele = (Node) eles.item(i);
//
//	            Map<String, Object> mi = new HashMap<String, Object>();
//
//	            if (ele.getChildNodes().getLength() > 1 && ele.getNodeType() == Node.ELEMENT_NODE)
//	            {
//	                mi.put(ele.getNodeName(), getChilds(ele.getChildNodes()));
//	            }
//	            else
//	            {
//	                Node subChild = ele.getFirstChild();
//
//	                if (subChild != null)
//	                {
//	                    if (!subChild.hasChildNodes())
//	                    {
//	                        mi.put(ele.getNodeName(), subChild.getNodeValue());
//	                    }
//	                    else
//	                    {
//	                        mi.put(ele.getNodeName(), "");
//	                    }
//	                }
//	            }
//
//	            if (!mi.isEmpty())
//	            {
//	                list.add(mi);
//	            }
//	        }
//
//	        map.put("data", list);
//	        map.put("Status", "SUCCESS");
//	    }
//	    catch (Exception ce)
//	    {
//	         log.error("IndoXMLParseUtil.xmlToMapAll() ce " + IndoUtil.getFullLog(ce));
//	    }
//	    return map;
//	}
//
//	public static Map<String, Object> getChilds(NodeList childs)
//	{
//	    Map<String, Object> map = new HashMap<String, Object>();
//	    for (int a = 0; a < childs.getLength(); a++)
//	    {
//	        Node child = (Node) childs.item(a);
//
//	        if (child.getNodeType() == Node.ELEMENT_NODE)
//	        {
//	            if (child.getChildNodes().getLength() > 1)
//	            {
//	                map.put(child.getNodeName(), getChilds(child.getChildNodes()));
//	            }
//	            else
//	            {
//	                Node subChild = child.getFirstChild();
//
//	                if (subChild != null && !subChild.hasChildNodes())
//	                {
//	                    map.put(child.getNodeName(), subChild.getNodeValue());
//	                }
//	                else
//	                {
//	                    map.put(child.getNodeName(), "");
//	                }
//	            }
//	        }
//	    }
//	    return map;
//	}
//
//	public static String getNodeValue(Node node)
//	{
//	    String result = "";
//
//	    final NodeList childs = node.getChildNodes();
//
//	    for (int i = 0; i < childs.getLength(); i++)
//	    {
//	        final Node child = childs.item(i);
//	        if (child != null)
//	        {
//	            if ((child.getNodeType() == Node.TEXT_NODE) || (child.getNodeType() == Node.CDATA_SECTION_NODE))
//	            {
//	                result += child.getNodeValue().trim();
//	            }
//	        }
//	    }
//	    return result;
//	}
	
}
