package ysomap.util;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * @author wh1t3P1g
 * @since 2020/2/17
 */
public class HTTPHelper {

    public static void makeSimpleHTTPServer(int port, String path, HttpHandler handler) throws IOException {
        System.err.println("* Opening Payload HTTPServer on " + port);
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext(path, handler);
        server.setExecutor(null);
        server.start();
    }


    public static class PayloadHandler implements HttpHandler {

        private byte[] obj;

        public PayloadHandler(byte[] obj) throws Exception {
            this.obj = obj;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.err.println("Have request from "+exchange.getRemoteAddress());
            System.err.println("Get request <"+exchange.getRequestMethod()+"> "+exchange.getRequestURI());
            exchange.sendResponseHeaders(200, obj.length);
            OutputStream os = exchange.getResponseBody();
            os.write(obj);
            os.close();
            System.err.println("return payload and close");
        }
    }
}
