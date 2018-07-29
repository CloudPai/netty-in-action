package nia.chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kerr.
 *
 * Listing 1.1 Blocking I/O example
 */
//代码清单1-1 阻塞I/O示例
public class BlockingIoExample {

    /**
     * Listing 1.1 Blocking I/O example
     * */

    //这段代码片段将只能同时处理一个连接
    public void serve(int portNumber) throws IOException {
        //1.创建一个新的 ServerSocket，用以监听指定端口上的连接请求
        ServerSocket serverSocket = new ServerSocket(portNumber);
        //1.对 accept（）方法的调用将被**阻塞**，直到一个连接建立
        Socket clientSocket = serverSocket.accept();

        //Socket的输入流：2.这些流对象都派生于该套接字的流对象
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        //Socket的输出流：2.这些流对象都派生于该套接字的流对象
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);


        String request, response;

        //3.处理循环开始
        while ((request = in.readLine()) != null) {
            //如果客户端发送了“Done”,则退出处理循环
            if ("Done".equals(request)) {
                break;
            }
            //4.请求被传递给服务器的处理方法
            response = processRequest(request);
            //服务器的响应被发送给了客户端
            out.println(response);
        }//继续执行处理循环
    }

    private String processRequest(String request){
        return "Processed";
    }
}
