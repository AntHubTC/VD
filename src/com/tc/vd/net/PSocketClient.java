package com.tc.vd.net;

import java.io.UnsupportedEncodingException;

import com.tc.vd.model.ContactGoalConfig;
import com.tc.vd.model.MsgContent;
import org.apache.log4j.Logger;
import org.jdom.Element;


public class PSocketClient extends SocketClient implements IPContact{
	private static final Logger logger = Logger
			.getLogger(PSocketClient.class);
	private ContactGoalConfig cgc;
	
	public PSocketClient() {
	}

	public PSocketClient(ContactGoalConfig cgc) {
		super(cgc.getIp(), cgc.getPort(), cgc.getTimeOut());
		super.setBufferSize(cgc.getBufferSize());
		this.cgc = cgc;
	}

	/**
	 * 发送报文
	 */
	public MsgContent psSend(MsgContent msgContent) throws Exception{
		String content = msgContent.getString();
		String msgLen = String.valueOf(cgc.getSendLenEnd());
		try {
			String recvMsg = psSend(content);
			return MsgContent.parse(recvMsg.substring(Integer.valueOf(msgLen)));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 发送报文
	 */
	public String psSend(String msgContent) throws Exception{
		String charSet = cgc.getEncoding();
		String msgLen = String.valueOf(cgc.getSendLenEnd());
		try {
			int reqLen = msgContent.getBytes(charSet).length;
			if(!"0".equals(msgLen)){
				msgContent = String.format("%0" + msgLen + "d%s", reqLen, msgContent);
			}
			if(logger.isDebugEnabled()){
				logger.debug("=======================================");
				logger.info("报文长度:"+ reqLen);
				logger.info("发送报文:"+ msgContent);
				logger.info("=======================================");
			}

			String recvMsg = sendRev(msgContent, charSet);

			if(logger.isDebugEnabled()){
				logger.info("接收报文:"+ recvMsg);
			}
			return recvMsg.substring(Integer.valueOf(msgLen));
		} catch (UnsupportedEncodingException e) {
			logger.info("不支持的字符编码::" + charSet);
			e.printStackTrace();
			throw e;
		}
	}

	public ContactGoalConfig getCgc() {
		return cgc;
	}

	public void setCgc(ContactGoalConfig cgc) {
		super.setContactGoalConfig(cgc);
		this.cgc = cgc;
	}
	
	public static void main(String[] args) {
		String msg1 = "<root>"
					+ "<transCode>123</transCode>"
					+ "<cardNo>hahah</cardNo>"
				+ "</root>";
		MsgContent msgContent1 = MsgContent.parse(msg1);
		
		org.jdom.Element msgRoot = new Element("root");
		msgRoot.addContent(new Element("transCode"));
		msgRoot.addContent(new Element("cardNo"));
		MsgContent msgContent2 = new MsgContent(msgRoot);
//		msgContent2.setContent(msgRoot);
		
		System.setProperty("bpf.home", "E:\\DEV\\csii-jyiccard-all\\union-api-client");
		//ContactGoalConfig cgc = new ContactGoalConfig("card_exch_address");
		//IPContact contact = new PSocketClient(cgc);
//		try {
//			contact.connect();
//			MsgContent recvStr = contact.send(msgContent1);
//			System.out.println(recvStr);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			contact.close();
//		}
	}
}
