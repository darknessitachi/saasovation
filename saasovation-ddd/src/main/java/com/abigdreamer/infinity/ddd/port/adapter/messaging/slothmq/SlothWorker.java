package com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * socket工作器
 *  
 * @author Darkness
 * @date 2014-12-16 下午10:01:13
 * @version V1.0
 * @since ark 1.0
 */
public abstract class SlothWorker {

	private static final int HUB_PORT = 55555;

	private int port;
	private ServerSocketChannel socket;

	/**
	 * 打开管道
	 */
	protected SlothWorker() {
		super();

		this.open();
	}

	/**
	 * 关闭管道
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午9:52:55
	 * @version V1.0
	 * @since ark 1.0
	 */
	protected void close() {
	    try {
            this.socket.close();
        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName() + ": problems closing socket.");
        }

	    this.socket = null;
	}

	/**
	 * 判断管道是否关闭
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午9:53:30
	 * @version V1.0
	 * @since ark 1.0
	 */
	protected boolean isClosed() {
	    return this.socket == null;
	}

	/**
	 * 获取管道端口
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午9:53:45
	 * @version V1.0
	 * @since ark 1.0
	 */
	protected int port() {
	    return this.port;
	}

	/**
	 * 接收消息
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午9:54:04
	 * @version V1.0
	 * @since ark 1.0
	 */
    protected String receive() {
        SocketChannel socketChannel = null;

        try {
            socketChannel = this.socket.accept();

            if (socketChannel == null) {
                return null; // if non-blocking
            }

            ReadableByteChannel readByteChannel =
                    Channels.newChannel(socketChannel.socket().getInputStream());

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

            ByteBuffer readBuffer = ByteBuffer.allocate(8);

            while (readByteChannel.read(readBuffer) != -1) {
                readBuffer.flip();

                while (readBuffer.hasRemaining()) {
                    byteArray.write(readBuffer.get());
                }

                readBuffer.clear();
            }

            return new String(byteArray.toByteArray());

        } catch (IOException e) {
            System.out.println("SLOTH SERVER: Failed to receive because: " + e.getMessage() + ": Continuing...");
            e.printStackTrace();

            return null;

        } finally {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * 向指定端口发送消息
     *  
     * @author Darkness
     * @date 2014-12-16 下午9:54:53
     * @version V1.0
     * @since ark 1.0
     */
	protected void sendTo(int aPort, String anEncodedMessage) {
	    SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();

            socketChannel.connect(new InetSocketAddress(aPort));

            socketChannel.write(ByteBuffer.wrap(anEncodedMessage.getBytes()));

            System.out.println(this.getClass().getSimpleName() + ": Sent: " + anEncodedMessage);

        } catch (IOException e) {
            System.out.println(this.getClass().getSimpleName() + ": Failed to send because: " + e.getMessage() + ": Continuing...");
            e.printStackTrace();
        } finally {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    System.out.println(this.getClass().getSimpleName() + ": Failed to close client socket because: " + e.getMessage() + ": Continuing...");
                }
            }
        }
    }

	/**
	 * 向服务器端口发送消息
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午9:55:41
	 * @version V1.0
	 * @since ark 1.0
	 */
    protected void sendToServer(String anEncodedMessage) {
        this.sendTo(HUB_PORT, anEncodedMessage);
    }

    /**
     * 睡眠指定毫秒
     *  
     * @author Darkness
     * @date 2014-12-16 下午9:56:05
     * @version V1.0
     * @since ark 1.0
     */
    protected void sleepFor(long aMillis) {
        try {
            Thread.sleep(aMillis);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    /**
     * 是否是hub
     *  
     * @author Darkness
     * @date 2014-12-16 下午9:56:37
     * @version V1.0
     * @since ark 1.0
     */
	protected boolean slothHub() {
	    return false;
	}

	/**
	 * 发现可用的客户端端口号
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午9:57:34
	 * @version V1.0
	 * @since ark 1.0
	 */
    private int discoverClientPort() {
        boolean discovered = false;
        int discoveryPort = HUB_PORT + 1;
        final int errorPort = discoveryPort + 20;

        while (!discovered && discoveryPort < errorPort) {
            try {
                this.socket.bind(new InetSocketAddress(discoveryPort));

                discovered = true;

            } catch (Exception e) {
                ++discoveryPort;
            }
        }

        if (!discovered) {
            throw new IllegalStateException("No ports available.");
        }

        return discoveryPort;
    }

    /**
     * 打开socket
     *  
     * @author Darkness
     * @date 2014-12-16 下午9:58:14
     * @version V1.0
     * @since ark 1.0
     */
	private void open() {
	    if (this.slothHub()) {
	        this.openHub();
	    } else {
	        this.openClient();
	    }
	}

	/**
	 * 打开客户端socket
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午9:58:35
	 * @version V1.0
	 * @since ark 1.0
	 */
    private void openClient() {
        try {
            this.socket = ServerSocketChannel.open();
            this.port = this.discoverClientPort();
            this.socket.configureBlocking(false);
            System.out.println("SLOTH CLIENT: Opened on port: " + this.port);

        } catch (Exception e) {
            System.out.println("SLOTH CLIENT: Cannot connect because: " + e.getMessage());
        }
    }

    /**
     * 打开服务端socket
     *  
     * @author Darkness
     * @date 2014-12-16 下午9:59:09
     * @version V1.0
     * @since ark 1.0
     */
    private void openHub() {
        try {
            this.socket = ServerSocketChannel.open();
            this.socket.bind(new InetSocketAddress(HUB_PORT));
            this.socket.configureBlocking(true);
            this.port = HUB_PORT;
            System.out.println("SLOTH SERVER: Opened on port: " + this.port);

        } catch (Exception e) {
            System.out.println("SLOTH SERVER: Cannot connect because: " + e.getMessage());
        }
    }
}
