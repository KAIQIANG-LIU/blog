package org.zaker.blog.entity;

public abstract class Result<T> {
    public ResultStatus getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public enum ResultStatus {
        ok("ok"),
        fail("fail");

        private String status;

        ResultStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
    private ResultStatus status;
    private String msg;
    private T data;

    Result(ResultStatus resultStatus, String msg, T data) {
        this.status = resultStatus;
        this.msg = msg;
        this.data = data;
    }


}
