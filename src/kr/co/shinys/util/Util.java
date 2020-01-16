package kr.co.shinys.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Clob;
import java.sql.SQLException;

public class Util {

	
	/**
	 * Method : lastTxtDelete
	 * 작성자 : ShinYS
	 * 변경이력 :
	 * @param str	: 문자열 param
	 * @param txt	: 삭제한 문자m
	 * @return
	 * Method 설명 : 마지막 문자 삭제 메서드
	 */
	public static String lastTxtDelete(String str, char txt) {
		if (str.length() > 0 && str.charAt(str.length() - 1) == txt) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	//오버로딩
	public static String lastTxtDelete(String str) {
		if (str.length() > 0 && str.charAt(str.length() - 1) == ',') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	
	// 파일로 저장한다.
	public static void outFile(String filePath, String fileName, boolean fileFlag, String txt) {

		try {

			if (fileFlag) {
				txt = ("\n" + txt);
			}

			System.out.println("filePath+fileName,fileFlag : " + filePath + File.separator + fileName);

			Writer outFile = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filePath + File.separator + fileName, fileFlag), "utf-8"));
			outFile.write(txt);
			outFile.flush();
			outFile.close();

		} catch (Exception e) {
			System.out.println("파일 저장 에러 : " + e.getMessage());
		}
	}

	// html 태그를 제거한다.
	public static String rt(String str) {

		String str2 = "";
		String result = "'";

		if (str != null && !"".equals(str)) {
			str2 = str;
			// if(!"NULL".equals(str)){
			// String replace[] = {"<[^>]*>", "\n", "\\n", "'", "<", ">", "˂", ">", "˃",
			// "\r\n", "/r/n", "\\r\\n", "\\\\n", "\\\\r\\\\n", "<br/>", "</br>", "\\s",
			// "\t", "&gt;", "&lt;", "&quot;",
			// "&apos;","\\\\","&nbsp;","&amp;","amp;","#35;"};
			String replace[] = { "\n" };
			for (int i = 0; i < replace.length; i++) {
				str2 = str2.replaceAll(replace[i].toString(), " ");
			}
			
			str2 = str2.replaceAll("'", "\"");

			// }
		}

		// if("".equals(str)){
		// str = "''";
		// }

		result += (str2.trim() + result);

		return result;

	}

	/*
	 * Clob 를 String 으로 변경
	 */
	public static String clobToString(Clob clob) throws SQLException, IOException {

		if (clob == null) {
			return "";
		}

		StringBuffer strOut = new StringBuffer();

		String str = "";

		BufferedReader br = new BufferedReader(clob.getCharacterStream());

		while ((str = br.readLine()) != null) {
			strOut.append(str);
		}
		return strOut.toString();
	}

	
	// 경로에 폴더가 있는지 확인 후, 없으면 생성
	public static void setFolder(String iwazSqlFolderPath) {
		// 생성할 파일경로 지정

		if ("".equals(iwazSqlFolderPath)) {

		} else {
			String path = iwazSqlFolderPath;
			// 파일 객체 생성
			File file = new File(path);
			// !표를 붙여주어 파일이 존재하지 않는 경우의 조건을 걸어줌
			if (!file.exists()) {
				// 디렉토리 생성 메서드
				file.mkdirs();
			}
		}
	}

}
