package kr.co.shinys;

import java.io.File;

import kr.co.shinys.config.Global;

public class Start {

	public static void main(String[] args) throws Exception {

		Start st = new Start();
		st.execute();

	}

	public void execute() throws Exception {

		//config 셋팅하기
		Global.setConfig("conf/config.json");
		
		final File folder = new File(Global.settingMap.get("openPath"));
		if(folder.isDirectory()) {
			Global.fileFlag = true;
			//폴더 시작
			for (final File fileEntry : folder.listFiles()) {
		        if (!fileEntry.isDirectory()) {
		            
		        	Agent agent = new Agent();
					agent.execute(fileEntry.getPath());
					System.out.println(fileEntry.getName());
					
		        }
		    }
			
			System.out.println(Global.settingMap.get("openPath") + " Directory End.");
		}else {
			//파일 시작
			Agent agent = new Agent();
			agent.execute(Global.settingMap.get("openPath"));
			
			System.out.println(Global.settingMap.get("openPath") + " File End.");
		}
		
	}
}
