package njurestaurant.njutakeout.publicdatas.account;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AgentDailyFlow {
    public static Date date;
    /*记录每日每个代理的流量<代理id，流量>*/
    public static Map<Integer, Double> flow = new HashMap<>();
    /*每日每个代理的佣金<代理id，佣金>*/
    public static Map<Integer, Double> commission = new HashMap<>();
}
