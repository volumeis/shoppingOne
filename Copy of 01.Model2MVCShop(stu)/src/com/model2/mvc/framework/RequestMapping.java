package com.model2.mvc.framework;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties; 

public class RequestMapping {
	
	// SingleTonPattorn : 
	private static RequestMapping requestMapping;
	
	// dispatcher���� domain control�� ���� instance
	// �����丮���� ��� ����Ȯ��
	private Map<String, Action> map;
	
	// mapping�� ����� �� �����ΰ� Ŭ������θ� ������ �ִ� Properties
	//Properties extends Hashtable<object,object> 
	private Properties properties;
	
	/*constructor
	 * resource : com/model2/mvc/resources/actionmapping.properties 
	 */
	
	
	// constructor(String)
	private RequestMapping(String resources) {
		
		// map in HashMap instance 
		map = new HashMap<String, Action>();
		//InputStram ������ ���� �ӽ� ���� in
		InputStream in = null;
		//getClass : RunctimeException
		try{
			/*  ����� Ŭ���� �������� �ε�	
			 * 	this. getClass() : return to Class  => class com.model2.mvc.framework.RequestMapping
			 * 	getClass().getClassLoader() : 
			 * 	Ŭ�����δ��� Ŭ������ �����Ű�� �������� �����ؾ���.
			 *  �� ���α׷��� �������� Ŭ�����δ��� �����ų������
			 *  -> Ŭ������ �̸��� ���ٸ� Ŭ�����δ��� ������־�� �� ex) date
			 *  
			 *  ����Ʈ JVM �⺻Ŭ�����δ��� ��� 
			 *  http://javacan.tistory.com/entry/2 ����
			 *  System.out.println("getClass().getClassLoader() : " + getClass().getClassLoader());
			 *  =>WebappClassLoader from WebappClassLoader-tomcat 6.0.28
			 *    ��org.apache.catalina.loader.WebappClassLoader
			 *  -> ���� :http://tomcat.apache.org/tomcat-7.0-doc/api/index.html
			 *  			http://egloos.zum.com/slog2/v/3916840
			 *  �뷫���� ����
			 *  Ŭ�����δ��� getResoruceAsStream(string)�� �Ἥ ���ҽ� ������.
			 *  ���, ��Ĺ��Ŭ�����δ� ���ؿ� ������������ �������� �ȴ�.
			 *  */
			
			System.out.println("getClass().getClassLoader().getResourceAsStream(resources) : " + getClass().getClassLoader().getResourceAsStream(resources));
			in = getClass().getClassLoader().getResourceAsStream(resources);
			//�ν��Ͻ�����
			properties = new Properties();
			//in���� ���Ե� InputStream�� properties�� �־���.
			//properties �� �ؽ����̺�μ� '����' = '������'�� �Ľ��Ͽ� �Է��� ����
			properties.load(in);
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("actionmapping.properties ���� �ε� ���� :"  + ex);
		}finally{
			if(in != null){
				try{ in.close(); } catch(Exception ex){}
			}
		}
	}
	
	/*����ȭ���� �ذ��� ���� synchronized
	 *�ѻ�� ��������. �λ���̻� �������ٽ� �����߻����ɼ��� ���� 
	 * --> �ذ��� singleton pattorn
	 */
	public synchronized static RequestMapping getInstance(String resources){
		if(requestMapping == null){
			requestMapping = new RequestMapping(resources);
		}
		return requestMapping;
	}
	
	//Action �� ���ϴºκ� path is URI
	@SuppressWarnings("rawtypes")
	public Action getAction(String path){
		Action action = map.get(path);
		//�ʱ� map���� �ƹ��� �ν��Ͻ��� �����Ƿ� ������ ������ ù����� map�� �����ϴ� �ܰ� �ʿ�.
		if(action == null){
			//uri�� �ش��ϴ� property -> Ŭ���������� �ѱ�
			String className = properties.getProperty(path);
			//properties�� �ִ� ���� ��� ��µ�.
			System.out.println("prop : " + properties);
			//path is uri
			System.out.println("path : " + path);
			//classname is property
			System.out.println("className : " + className);
			//���� ����
			className = className.trim();
			try{
				//Ŭ������ �ϳ��� �ڷ������� �����Ͽ� ���� ������.
				//��������������
				Class c = Class.forName(className);
				// c Ŭ������ �ӽ��ν��Ͻ� ����.
				Object obj = c.newInstance();
				// obj�� Action�� �ڽ�Ŭ������� �� �ƴϸ� ����
				if(obj instanceof Action){
					//������ ������ �ʿ� ���� obj is Servlet path is uri
					map.put(path, (Action)obj);
					//���������� ���޵� ��ȯ�� action�� ����.
					action = (Action)obj;
				}else{
					throw new ClassCastException("Class����ȯ�� ���� �߻�  ");
				}
			}catch(Exception ex){
				System.out.println(ex);
				throw new RuntimeException("Action������ ���ϴ� ���� ���� �߻� : " + ex);
			}
		}
		return action;
	}
}