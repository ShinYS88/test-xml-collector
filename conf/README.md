{
	"setting":{
		"openPath" : "data",						// "data" (폴더내 전체파일) or "data/test.xml" (파일1개)
		"saveFilePath" : "D:/test/incomming",		// 저장 폴더 Path
		"saveFileName" : "test.sql",			// 저장 파일명
		"tableName" : "test",					// insert문 대상 테이블 명
		"outputQuery" : "false"						// console에 Insert문 출력 여부
	},
	"seed": {
		"content": "content"						// 추출기준 tag Name
	},
	"field": {
		"unique_key": "getId",						// "필드명" : "xml path" or (Agent.java => xmlResult에 저장된 key값)
		"parent_key": "getParentId",
		"author": "item/front/author",
		"book_no": "item/front/volumeNo",
		"pub_date": "item/front/publicationDate",
		"doc_type": "item/front/docType",
		"CLASSA": "level1/title",
		"CLASSB": "level2/title",
		"CLASSC": "level3/title"
	},
	"addField": {
		"title" : "title",							// 추가필드영역, "field" 에 몰아서 사용해도됨.
		"CLASSINFO" : "classInfo",
		"all_text" : "allText",
		"type":"getType"
	}
}

