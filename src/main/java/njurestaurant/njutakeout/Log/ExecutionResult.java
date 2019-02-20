package njurestaurant.njutakeout.Log;

import java.util.List;

/**
 * @Description:
 * @Author: vesus
 * @CreateDate: 2018/5/21 上午9:46
 * @Version: 1.0
 */
public class ExecutionResult {

    //错误编码
    private String resultCode ;
    //是否成功标志
    private boolean flag ;
    //返回消息
    private String msg ;
    //数据条数
    private int total ;
    //数据集
    private List<?> data ;

    private int exceptioncode ;// 异常code

    private String exceptiondetail ;// 异常详情

    public String getResultCode() {
        return resultCode;
    }

    public int getExceptioncode() {
        return exceptioncode;
    }

    public void setExceptioncode(int exceptioncode) {
        this.exceptioncode = exceptioncode;
    }

    public String getExceptiondetail() {
        return exceptiondetail;
    }

    public void setExceptiondetail(String exceptiondetail) {
        this.exceptiondetail = exceptiondetail;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
