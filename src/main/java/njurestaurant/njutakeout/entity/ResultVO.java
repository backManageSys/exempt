package njurestaurant.njutakeout.entity;
/**
 * @description: 数据返回类
 */
public class ResultVO<T> {

    private Integer code;

    private String message;


    public ResultVO() {
    }

    public ResultVO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
