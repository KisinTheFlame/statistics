package tech.kisin.statistics.result;

public class Result<T> {
    private final int code;
    private final String message;
    private final T data;

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }
}
