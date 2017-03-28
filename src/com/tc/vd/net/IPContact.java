package com.tc.vd.net;


import com.tc.vd.model.MsgContent;

public interface IPContact extends IContact{
	
	/**
	 * 发送报文
	 * @param msgContent
	 * @return
	 * @throws Exception
	 */
	public MsgContent send(MsgContent msgContent) throws Exception;
}
