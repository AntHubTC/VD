package com.tc.vd.net;


import com.tc.vd.model.MsgContent;

public interface IPContact extends IContact{
	
	/**
	 * 发送报文
	 * @param msgContent
	 * @return
	 * @throws Exception
	 */
	public MsgContent psSend(MsgContent msgContent) throws Exception;

	/**
	 * 发送报文
	 * @param msgContent
	 * @return
	 * @throws Exception
	 */
	public String psSend(String msgContent) throws Exception;
}
