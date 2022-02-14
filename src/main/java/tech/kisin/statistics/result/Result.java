package tech.kisin.statistics.result;

public class Result<T> {
    private int code;
    private String message;
    private T content;

    public Result(ResultCode resultCode, T content) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
