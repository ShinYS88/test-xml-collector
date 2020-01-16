package kr.co.shinys;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kr.co.shinys.config.Global;
import kr.co.shinys.util.ParserUtil;
import kr.co.shinys.util.Util;

public class Agent {

	public void execute(String openFilePathName) throws Exception{
		
		
		// dir => for	
		
		//seedTagName 가져오기
		String seedTag = "";
		Iterator<String> seedKeySet = Global.seedMap.keySet().iterator();
		if(seedKeySet.hasNext()) {
			String Key = seedKeySet.next();
			seedTag = Global.seedMap.get(Key);
		}
		
		//xml seed기준으로 Parser
		String xml = ParserUtil.readXMLFile(openFilePathName);
		List<Map<String, String>> xmlResult = ParserUtil.convertNodesFromXml(xml, seedTag);
		
		
		//==[ xmlResult에 데이터 가공하여 추가하기 ]========================================================
		for (Map<String, String> result : xmlResult) {
			//title 생성
			result.put("title", result.get(result.get("getLevel")+"/title"));
			
			//classInfo 생성
			List<String> classList = new ArrayList<>();
			classList.add(result.get("item/front/biblioData/sabuClass0"));
			classList.add(result.get("item/front/biblioData/sabuClass1"));
			classList.add(result.get("item/front/biblioData/title"));
			classList.add(result.get("level1/title"));
			classList.add(result.get("level2/title"));
			classList.add(result.get("level3/title"));
			
			String classInfo = "";
			for(String cl:classList) {
				if(cl != null) {
					classInfo += cl + ";";
				}
			}
			classInfo = Util.lastTxtDelete(classInfo,';');
			result.put("classInfo", classInfo);
			
			//allText 생성
			List<String> allTextList = new ArrayList<>();
			allTextList.add(result.get("content"));						//컨텐츠
			allTextList.add(result.get("classInfo"));					//카테고리
			allTextList.add(result.get("item/front/biblioData/author"));
			
			String allText = "";
			for(String at:allTextList) {
				if(at != null) {
					allText += at+" ";
				}
			}
			
			classInfo = Util.lastTxtDelete(classInfo);
			result.put("allText", allText);
			
		}
		
		
		//xmlResult에서 insert sql 추출
		String resultTxt = "";
		for (Map<String, String> result : xmlResult) {
			// 필드, 값 설정
			String keyTxt = "";
			String valueTxt = "";
			
			// fieldMap => insert 추가
			Set<String> fieldKeys = Global.fieldMap.keySet();
			Iterator<String> fieldIt = fieldKeys.iterator();
			while (fieldIt.hasNext()) {
				String key = fieldIt.next();
//				String contentTxt = result.get(fieldMap.get(key));
				String contentTxt = Util.rt(result.get(Global.fieldMap.get(key)));
				keyTxt += key + ",";
				valueTxt += contentTxt + ",";
			}

			// content => insert 추가
			Set<String> seedKeys = Global.seedMap.keySet();
			Iterator<String> seedIt = seedKeys.iterator();
			while (seedIt.hasNext()) {
				String key = seedIt.next();
				String contentTxt = Util.rt(result.get(Global.seedMap.get(key)));
					
				//테스트용 내용 상략	(실제 사용시에는 제거해야함)
//				 if(contentTxt.length() > 14) {
//					 contentTxt = contentTxt.substring(0,14) + "(..내용 생략)";
//				 }
				 
				keyTxt += key + ",";
				valueTxt += contentTxt + ",";
			}
						
			
			// addField => insert 추가
			Set<String> addFieldKeys = Global.addFieldMap.keySet();
			Iterator<String> addFieldIt = addFieldKeys.iterator();
			while (addFieldIt.hasNext()) {
				String key = addFieldIt.next();
				String contentTxt = Util.rt(result.get(Global.addFieldMap.get(key)));
				
				keyTxt += key + ",";
				valueTxt += contentTxt + ",";
			}

			keyTxt = Util.lastTxtDelete(keyTxt);
			valueTxt = Util.lastTxtDelete(valueTxt);
			resultTxt += "Insert Into " + Global.settingMap.get("tableName") + " (" + keyTxt + ") Values (" + valueTxt + ");\n";
		}
		
		if(Global.settingMap.get("outputQuery").equals("true")) {
			System.out.println(resultTxt);
		}

		String filePath = Global.settingMap.get("saveFilePath");
		String fileName = Global.settingMap.get("saveFileName");
		
		//파일 중복 확인
		String finalFileName = fileName;
		if(Global.fileFlag) {
			String[] farr = fileName.split("\\.");
			String fName = "";
			if(farr.length > 0) {
				fName = farr[0]+ "_" + Global.fileCnt;
				finalFileName = fName + "." + farr[1];
				Global.fileCnt++;
			}else {
				finalFileName = fileName + "_" +  Global.fileCnt;
				Global.fileCnt++;
			}
		}
		
		//파일 저장
		Util.outFile(filePath, finalFileName, false, resultTxt);
	}
}
