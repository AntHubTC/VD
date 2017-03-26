package com.tc.vd.net;

/**
 * 通讯接口
 * @author tangcheng
 *
 */
public interface IContact {
	
	/**
	 * 建立连接
	 * @return
	 */
	public boolean connect();
	/**
	 * 发送数据
	 * @param data 数据
	 * @return
	 * @throws Exception
	 */
	public byte[] send(String data) throws Exception;
	/**
	 * 发送数据
	 * @param data 数据
	 * @param encode 编码
	 * @return
	 * @throws Exception
	 */
	public byte[] send(String data, String encode) throws Exception;
	/**
	 * 发送数据
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] send(byte data[], Integer bufferSize) throws Exception;
	/**
	 * 发送数据
	 * @param data
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public String sendRev(String data, String encode) throws Exception;
	/**
	 * 发送数据
	 * @param data
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public String sendRev(byte data[], String encode) throws Exception;
	
	/**
	 * 关闭连接 
	 */
	public void close();
}
