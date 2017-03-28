package com.tc.vd.model.addressBook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * 报文内容
 * 封装了一个org.jdom.Element，可以自由组装报文
 * @author tangcheng
 *
 */
public class MsgContent {
	private Element content;
	
	public MsgContent() {
	}

	public MsgContent(Element content) {
		super();
		this.content = content;
	}
	
	public String getNode(String name){
		if(null == content) return null;
		Element ele = null;
		try {
			ele = (Element)XPath.selectSingleNode(content, ".//" + name);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return ele.getText();
	}
	
	public String getNodeByXpath(String xpath){
		if(null == content) return null;
		Element ele = null;
		try {
			ele = (Element)XPath.selectSingleNode(content, xpath);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return ele.getText();
	}

	public static MsgContent parse(String msg){
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(new StringReader(msg));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return new MsgContent(doc.getRootElement());
	}
	
	public String getString(){
		if(null == content)
			return "";
		ByteArrayOutputStream byteRsp=new ByteArrayOutputStream();
		XMLOutputter out = new XMLOutputter();
		try {
			out.output(content, byteRsp);
			return byteRsp.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public Element getContent() {
		return content;
	}

	public void setContent(Element content) {
		this.content = content;
	}
}
