package njurestaurant.njutakeout.springcontroller;

import njurestaurant.njutakeout.exception.SystemException;
import njurestaurant.njutakeout.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexTestController {
    @RequestMapping(value = "order/getTokenByAli")
    public String getTokenByAli() throws SystemException {

        String authorizeUrl="https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2018110561986668&scope=auth_base&redirect_uri=http%3a%2f%2fguren.natapp1.cc%2forder%2fgetUserIdByAli&state=";


        //发送Post数据并返回数据
//        String string= Utils.sendGetRequest(authorizeUrl, null);

//        if(resultVo.getCode() != 200) {       //进行异常处理
//            try {
//                throw new SiteException(resultVo.getMessage());
//            } catch (SiteException e) {
//                e.printStackTrace();
//            }
//        }
        return "redirect:"+authorizeUrl;
    }
}
