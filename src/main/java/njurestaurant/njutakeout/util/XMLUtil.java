package njurestaurant.njutakeout.util;


import njurestaurant.njutakeout.exception.SystemException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XMLUtil {

    public static String parserXmlToGetPrepayId(String xmlStr) throws SystemException {
        try {
            xmlStr = xmlStr.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
            Document document = DocumentHelper.parseText(xmlStr);
            Element root = document.getRootElement();
            return root.element("prepay_id").getStringValue();
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new SystemException();
        }
    }

    public static String parserXmlToGetNonceStr(String xmlStr) throws SystemException {
        try {
            xmlStr = xmlStr.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
            Document document = DocumentHelper.parseText(xmlStr);
            Element root = document.getRootElement();
            return root.element("nonce_str").getStringValue();
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new SystemException();
        }
    }
}
