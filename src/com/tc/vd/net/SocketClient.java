package com.tc.vd.net;

/**
 * 非阻塞连接
 */
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import com.tc.vd.model.ContactGoalConfig;
import org.apache.log4j.Logger;


public class SocketClient implements IContact{
	private Logger log = Logger.getLogger(getClass());
	
	private SocketChannel client = null;
	private String serverIp;
	private int port;
	private int timeout = 300 * 100;	//乘以100是因为非阻塞连接时休眠了10毫秒的
	public Integer bufferSize = 1024;
	
	public SocketClient() {
	}

	public SocketClient(String serverIp, int port) {
		this.serverIp = serverIp;
		this.port = port;

	}
	
	public SocketClient(String serverIp, int port, int timeout) {
		this.serverIp = serverIp;
		this.port = port;
		this.timeout = timeout * 100;
	}

	public void setConnectParameter(String serverIp, int port) {
		this.serverIp = serverIp;
		this.port = port;
		
	}

	public void setConnectParameter(String serverIp, int port, int timeout) {
		this.serverIp = serverIp;
		this.port = port;
		this.timeout = timeout * 100;
	}

	public boolean connect() {//throws Exception {
		boolean reBool = false;
		
		if( serverIp == null )
			throw new RuntimeException("远端地址不能为空");


		if( log.isDebugEnabled() )
			log.debug( String.format("创建远端连接,IP=%s, PORT=%d", serverIp, port));
		
		try {
			client = SocketChannel.open();
			
			InetSocketAddress isa = new InetSocketAddress(this.serverIp, this.port);
			
			client.connect( isa );
			client.configureBlocking(false);
			
			reBool = client.isConnected();
			
			if( reBool ) {
				if( log.isDebugEnabled() )
					log.debug( "创建远端连接成功");
			}
			else {
				if( log.isDebugEnabled() )
					log.debug( "创建远端连接失败");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		
		return reBool;
	}

	public byte[] send(String data) throws Exception {

		return send( data, "GBK" );
	}


	public byte[] send(String data, String encode) throws Exception {
		return send( data.getBytes( encode ), bufferSize);
	}
	
	public byte[] send( byte data[],Integer bufferSize) throws Exception {
		if(null == client) throw new Exception("未打开连接");
		byte[] retBytes = new byte[0];
		ByteBuffer writeBuf = null;
	    try {
	    	writeBuf = ByteBuffer.wrap(data);
	    	while( writeBuf.hasRemaining())
	    		client.write(writeBuf);
	    	
	    	ByteBuffer readBuf = ByteBuffer.allocate(bufferSize);
	    	boolean flag = true;
	    	int count = 0;
	    	while (flag) {
	    		while ((client.read(readBuf)) > 0) {
	    			readBuf.flip();
	    			byte[] readBytes = new byte[readBuf.limit()];
	    			readBuf.get(readBytes);
	    			retBytes = appendByte(retBytes, readBytes);
	    			readBuf.clear();
	    			flag = false;
	    		}
		        count++;
		        if ((!flag) || (count > timeout)) {//count 控制超时
		        	if( flag ) {
		        		log.error("远程读取数据超时");
		        		throw new Exception("远程读取数据超时");
		        	}
		        	break;
		        }
	    		Thread.sleep(10L);
	    	}
	    }
	    catch (Exception ex) {
	    	throw ex;
	    }
	    finally {
	    	if( writeBuf != null ) writeBuf.compact();
	    }
	    return retBytes;
	}

	public String sendRev(String data, String encode) throws Exception {
		return sendRev( data.getBytes( encode ), encode );
	}
	

	public String sendRev( byte data[], String encode ) throws Exception {
		return new String( send( data , bufferSize), encode );
	}
	
	public void close() {
		if ((client != null) && (client.isConnected())) {
			try {
		        client.close();
		    } catch (Exception ex) {
		        client = null;
		        ex.printStackTrace();
		    }
		}
		client = null;
	}
	
	private byte[] appendByte(byte[] src, byte[] dst)
	{
	    byte[] newBytes = new byte[src.length + dst.length];
	    System.arraycopy(src, 0, newBytes, 0, src.length);
	    System.arraycopy(dst, 0, newBytes, src.length, dst.length);
	    return newBytes;
	}

	public Integer getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout * 100;
	}
	
	public void setContactGoalConfig(ContactGoalConfig cgc){
		URI uri = cgc.getUri();
		this.setBufferSize(cgc.getBufferSize());
		this.setServerIp(uri.getHost());
		this.setPort(uri.getPort());
		this.setTimeout(cgc.getTimeOut());
	}
	
/*
	public static void main( String argv[] ) throws Exception {
		String ip = "172.16.66.213";
		int port = 38001;

		for (String string : argv) {
			System.out.println( string );
		}
		System.out.println(argv.length);
		
		int num = 30000;
		
		if( argv.length > 0 )
		num = Integer.parseInt( argv[0] );
		
		
		SocketClient socket = new SocketClient();
		socket.setConnectParameter(ip, port, 60);
		if( !socket.connect() ) {
			System.out.println("无法连接");
			System.exit(-1);
		}
		
		
		StringBuilder data = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		data.append("<root><PUBREQHEAD>");
		data.append("<ib_channel_id>00000000</ib_channel_id>")
			.append("<ib_channel_date>20141105</ib_channel_date>")
			.append("<ib_channel_time>114925</ib_channel_time>")
			.append("<ib_service_id>091041</ib_service_id>")
			.append("<ib_branch_code>9998</ib_branch_code>")
			.append("<ib_teller_id>7961</ib_teller_id>")
			.append("<ib_term_id>7961</ib_term_id>")
			.append("<ib_prod_id>101017</ib_prod_id>")
			.append("<ib_item_id>100</ib_item_id>")
			.append("</PUBREQHEAD>")
			.append("<PUBREQBODY>")
			.append("<recode>")
			.append("<ywbh>201100023637</ywbh>")
			.append("<sfzhm>50022319861125887X</sfzhm>")
			.append("<yxtlsh>3412259</yxtlsh>")
			.append("<yhlsh>000009502</yhlsh>")
			.append("<yhbm>04416530</yhbm>")
			.append("<jflx>YZJ</jflx>")
			.append("<yf>201411</yf>")
			.append("<je>442.64</je>")
			.append("<znj>0.00</znj>")
			.append("</recode>");
		
		for( int i=0; i<num; i++ ) {
			data.append("<recode>")
			   .append("<ywbh>201100094555</ywbh>")
			   .append("<sfzhm>510221198112246638</sfzhm>")
			   .append("<yxtlsh>3453070</yxtlsh>")
			   .append("<yhlsh>")
			   .append( String.format("%09d", i) )
			   .append("</yhlsh>")
			   .append("<yhbm>04416530</yhbm>")
			   .append("<jflx>YZJ</jflx>")
			   .append("<yf>201411</yf>")
			   .append("<je>443.07</je>")
			   .append("<znj>0.00</znj>")
			   .append("</recode>");
		}
	
   data.append("</PUBREQBODY></root>");
		
   
		
		System.out.println( socket.sendRev(String.format("%08d%s", data.toString().getBytes().length, data.toString()), "GBK") );
		
	}*/
}
