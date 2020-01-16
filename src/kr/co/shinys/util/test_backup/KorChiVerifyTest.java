package kr.co.shinys.util.test_backup;

import java.util.HashMap;
import java.util.Map;

/**
 * Test1.java
 *
 * @author ShinYS
 * @version 1.0
 * @see
 * 
 * 한글, 한자 구분을 위한 테스트
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * ShinYS 최초 생성
 *
 * </pre>
 */
public class KorChiVerifyTest {

	public static void main(String[] args) {
		
//		String str = "한글테스트";
		String str = "出乎吾身。";
		
		int strLength = str.length();
		int kor = 0;
		int cn = 0;
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("strLength", str.length());
		for(int i=0;i<strLength;i++){
			if ( str.charAt(i) >= '\uAC00' && str.charAt(i) <= '\uD7A3' ){
				kor++;
			}else{
				cn++;
			}
		}
		
		int textFlag = 0;
		if(kor > 0) {
			textFlag = 1;
		}
		
		resultMap.put("kor", kor);
		resultMap.put("cn", cn);
		resultMap.put("text", textFlag);
		
		
		System.out.println("전체 문자 수 : "+resultMap.get("strLength"));
		System.out.println("한글 문자 수 : " + kor);
		System.out.println("한자 문자 수 : " + cn);
		System.out.println("[0:한자 / 1:한글 ] : [" +textFlag+ "]");

				
		
	}
}
