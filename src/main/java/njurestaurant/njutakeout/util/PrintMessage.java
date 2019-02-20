package njurestaurant.njutakeout.util;

import njurestaurant.njutakeout.exception.PrintFailException;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class PrintMessage {

    private static String partner = "28121";//用户id
    private static String machine_code = "4004569171";//打印机终端号
    private static String apiKey = "35a22ec4694df85d689d9d70caf061b14e1a869b";//API密钥
    private static String mKey = "b6nhrmr5qw8y";//打印机密钥

    public static void sendContent(String content) throws PrintFailException {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("partner", partner);
            params.put("machine_code", machine_code);
            String time = String.valueOf(System.currentTimeMillis());
            params.put("time", time);
            String sign = signRequest(params);

            byte[] data = ("partner=" + partner + "&machine_code=" + machine_code + "&content=" + content + "&sign=" + sign + "&time=" + time).getBytes();
            URL url = new URL("http://open.10ss.net:8888");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5 * 1000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "text/html; charset=utf-8");
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));

            //获取输出流
            OutputStream outStream = conn.getOutputStream();
            //传入参数
            outStream.write(data);
            outStream.flush();
            outStream.close();


            //获取输入流
            InputStream is = conn.getInputStream();

            System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                int i = -1;
                byte[] b = new byte[1024];
                StringBuffer result = new StringBuffer();
                while ((i = is.read(b)) != -1) {
                    result.append(new String(b, 0, i));
                }

                String sub = result.toString();
                if (sub.charAt(10) == '1') {//数据已经发送到客户端
                    System.out.println("打印成功");
                } else {
                    System.out.println("打印失败,返回值：" + result);
                    throw new PrintFailException();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PrintFailException();
        }
    }

    /**
     * 打印签名
     *
     * @param params
     * @return
     */
    private static String signRequest(Map<String, String> params) {
        Map<String, String> sortedParams = new TreeMap<String, String>();
        sortedParams.putAll(params);
        Set<Map.Entry<String, String>> paramSet = sortedParams.entrySet();
        StringBuilder query = new StringBuilder();
        query.append(apiKey);
        for (Map.Entry<String, String> param : paramSet) {
            query.append(param.getKey());
            query.append(param.getValue());
        }
        query.append(mKey);
        String encryptStr = MD5.MD5Encode(query.toString(), "").toUpperCase();
        return encryptStr;
    }
}
