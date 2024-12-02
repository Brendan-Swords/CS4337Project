package cs4337.group6.AuthenticationService.Utility;

import lombok.Data;

@Data
public class PostmanResponseMessage<T>
{

    private String message;
    private int statusCode;
    private T data;

    public PostmanResponseMessage(String message, int statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }
}


