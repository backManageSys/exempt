package njurestaurant.njutakeout.publicdatas.order;

public enum OrderState {
    WAITING_FOR_PAYING,//等待支付
    DELETED,//已删除
    CONFIRMED,//客户已确认
    REQUESTING,//等待商户确认
    REJECTED,//商户拒绝接单
    ARRIVED,//商户送达确认
    VALID,//等待客户确认
    COMMENTED,//已评论
    PAID, //已支付
    EXPIRED //已失效
}
