package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties; 

public class RequestMapping {
	
	// SingleTonPattorn : 
	private static RequestMapping requestMapping;
	
	// dispatcher에서 domain control을 위한 instance
	// 히스토리에서 사용 추후확인
	private Map<String, Action> map;
	
	// mapping의 대상이 될 도메인과 클래스경로를 가지고 있는 Properties
	//Properties extends Hashtable<object,object> 
	private Properties properties;
	
	/*constructor
	 * resource : com/model2/mvc/resources/actionmapping.properties 
	 */
	
	
	// constructor(String)
	private RequestMapping(String resources) {
		
		// map in HashMap instance 
		map = new HashMap<String, Action>();
		//InputStram 저장을 위한 임시 변수 in
		InputStream in = null;
		//getClass : RunctimeException
		try{
			/*  사용할 클래스 동적으로 로딩	
			 * 	this. getClass() : return to Class  => class com.model2.mvc.framework.RequestMapping
			 * 	getClass().getClassLoader() : 
			 * 	클래스로더는 클래스를 실행시키는 집합으로 생각해야함.
			 *  한 프로그램에 여러개의 클래스로더를 실행시킬수있음
			 *  -> 클래스의 이름이 같다면 클래스로더를 명시해주어야 함 ex) date
			 *  
			 *  디폴트 JVM 기본클래스로더를 사용 
			 *  http://javacan.tistory.com/entry/2 참조
			 *  System.out.println("getClass().getClassLoader() : " + getClass().getClassLoader());
			 *  =>WebappClassLoader from WebappClassLoader-tomcat 6.0.28
			 *    ㄴorg.apache.catalina.loader.WebappClassLoader
			 *  -> 사용법 :http://tomcat.apache.org/tomcat-7.0-doc/api/index.html
			 *  			http://egloos.zum.com/slog2/v/3916840
			 *  대략적인 정리
			 *  클래스로더의 getResoruceAsStream(string)을 써서 리소스 가져옴.
			 *  대신, 톰캣의클래스로더 기준에 맞춘형식으로 가져오게 된다.
			 *  */
			
			System.out.println("getClass().getClassLoader().getResourceAsStream(resources) : " + getClass().getClassLoader().getResourceAsStream(resources));
			in = getClass().getClassLoader().getResourceAsStream(resources);
			//인스턴스생성
			properties = new Properties();
			//in으로 대입된 InputStream을 properties에 넣어줌.
			//properties 는 해쉬테이블로서 '왼쪽' = '오른쪽'을 파싱하여 입력을 해줌
			properties.load(in);
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties 파일 로딩 실패 :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	/*동기화문제 해결을 위한 synchronized
	 *한사람 문제없음. 두사람이상 동시접근시 에러발생가능성의 요인 
	 * --> 해결방법 singleton pattorn
	 */
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	//Action 을 정하는부분 path is URI
	@SuppressWarnings("rawtypes")
	public Action getAction(String path){
		Action action = map.get(path);
		//초기 map에는 아무런 인스턴스가 없으므로 임의의 도메인 첫실행시 map에 저장하는 단계 필요.
		if(action == null){
			//uri에 해당하는 property -> 클래스정보를 넘김
			String className = properties.getProperty(path);
			//properties에 있는 정보 모두 출력됨.
			System.out.println("prop : " + properties);
			//path is uri
			System.out.println("path : " + path);
			//classname is property
			System.out.println("className : " + className);
			//띄어쓰기 없앰
			className = className.trim();
			try{
				//클래스도 하나의 자료형으로 생각하여 저장 가능함.
				//유지보수에좋음
				Class c = Class.forName(className);
				// c 클래스의 임시인스턴스 생성.
				Object obj = c.newInstance();
				// obj가 Action의 자식클래스라면 참 아니면 거짓
				if(obj instanceof Action){
					//조건이 맞을시 맵에 저장 obj is Servlet path is uri
					map.put(path, (Action)obj);
					//실질적으로 전달될 반환값 action에 대입.
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class형변환시 오류 발생  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action정보를 구하는 도중 오류 발생 : " + ex);
			}
		}
		return action;
	}
}