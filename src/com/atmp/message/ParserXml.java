package com.atmp.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class ParserXml {
	
	private static ArrayList<NodeLeaf> eList=new ArrayList<NodeLeaf>();
	private static final Logger logger= Logger.getLogger(ParserXml.class);
	
	public void getElementList(Element element){
		
		List elements=element.elements();
		if(elements.isEmpty()){
			String nodePath=element.getPath();
			String nodeValue=element.getTextTrim();
			eList.add(new NodeLeaf(nodePath,nodeValue));
		}else{
			Iterator i=elements.iterator();
			while(i.hasNext()){
				Element ele=(Element)i.next();
				getElementList(ele);
			}
			
		}
	}
	
	public Map<String, Object> getListString(List<NodeLeaf> eList){
		Map<String, Object> result=new HashMap<String ,Object>();
		List<NodeLeaf> list=new ArrayList<NodeLeaf>();
		list.addAll(eList);
		for(Iterator i=list.iterator();i.hasNext();){
			NodeLeaf nodeLeaf=(NodeLeaf) i.next();
			String strNodePath=nodeLeaf.getNodePath();
			String[] strNodes = strNodePath.split("/");
			strNodePath=strNodes[strNodes.length-1];
			result.put(strNodePath, nodeLeaf.getNodeValue());
		}
		return result;
	}
	
	public Map<String, Object> getXml(String getMessage){ 
		Map<String, Object> result=new HashMap<String ,Object>();
		try {
			Document document = DocumentHelper.parseText(getMessage);
			Element root=document.getRootElement();
			ParserXml parserXml=new ParserXml();
			parserXml.getElementList(root);     
			result=parserXml.getListString(eList);
		} catch (DocumentException e) {
			logger.error(e.getMessage(),e);
		}
		return result;
	}
	
	public Map<String,Object> getListXml(String getMessage){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			Document document = DocumentHelper.parseText(getMessage);
			Element root=document.getRootElement();
			Element C40QueAccCardInqRs = root.element("C40QueAccCardInqRs"); 
			ParserXml parserXml=new ParserXml();
			List rootList=C40QueAccCardInqRs.elements();
			for (int i=0;i<rootList.size();i++) {  
				
				eList.clear();
				Element whereArrayElement = (Element) rootList.get(i);  
				parserXml.getElementList(whereArrayElement);  
				list.add(parserXml.getListString(eList));
				if(i==0){
					result.put("datahead", list);
					list= new ArrayList<Map<String,Object>>(); 
				}
			}
			result.put("databody", list);
			logger.info(result);
		} catch (DocumentException e) {
			logger.error(e.getMessage(),e);
		}
		return result;
	}

}



class NodeLeaf{
	
	private String nodePath;//节点路径
	private String nodeValue;//节点值
	
	public NodeLeaf(String nodePath,String nodeValue){
	        this.nodePath = nodePath;     
	        this.nodeValue = nodeValue;  
	}


	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}
	
	
}