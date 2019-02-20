package njurestaurant.njutakeout.config.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.WebSocketHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {


    /**
     * 在调用hanler前处理方法
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("11222222222222221");
        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            HttpSession session = servletRequest.getSession(true);
            System.out.println("11111111111111111");
            System.out.println(session);
            if (session != null) {
                String imei = servletRequest.getParameter("imei");
                System.out.println(imei);
                //使用imei区分WebSocketHandler，以便定向发送消息
                String imeiSession = (String) session.getAttribute("imei");
                if (imei == null || imei.equals("")) {
                    return false;
                } else {
                    attributes.put("imei", imei);
                }
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Exception e) {
        // TODO Auto-generated method stub
        System.out.println("After Handshake");
    }
}
