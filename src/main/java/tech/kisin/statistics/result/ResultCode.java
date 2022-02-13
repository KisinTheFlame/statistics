package tech.kisin.statistics.result;

public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(200,"成功"),
    /**/
    FAILURE(400, "错误"),
    /* 1000-1999：参数错误 */
    PARAM_IS_INVALID(1000, "参数无效"),
    PARAM_IS_BLANK(1001,"参数为空"),
    PARAM_TYPE_BIND_ERROR(1002,"参数类型错误"),
    PARAM_NOT_COMPLETE(1003,"参数缺失"),
    /* 2000-2999：用户相关 */
    USER_NOT_LOGGED_IN(2000, "未登录"),
    USER_FORBIDDEN(2001,"权限不足"),
    USER_NOT_EXIST(2002,"用户不存在"),
    USER_HAS_EXISTED(2003,"用户已存在"),
    USERNAME_IS_MISSING(2004,"请输入用户名"),
    PASSWORD_NOT_CORRECT(2005,"密码不正确"),
    PASSWORD_IS_MISSING(2006,"请输入密码");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
