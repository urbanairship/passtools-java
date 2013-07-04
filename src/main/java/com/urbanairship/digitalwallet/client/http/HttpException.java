package com.urbanairship.digitalwallet.client.http;

import org.apache.http.HttpStatus;

public class HttpException extends Exception {
    private final int code;

    /**
     * public constructor
     * @param code      The code associated with this exception.
     * @param message   The message associated with this exception.
     */
    public HttpException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * public constructor, will get error message associated with the code and use that as the message for the exception.
     * @param code  The error code associated with this exception.  Should be a HttpStatus enum value.
     */
    public HttpException(int code) {
        super(HttpException.errorMessage(code));
        this.code = code;
    }

    /**
     * @return  The error code associated with this exception
     */
    public int getCode() {
        return code;
    }

    /**
     * Maps an HTTPStatus error to a string.
     * @param sc  HttpStatus value, should be greater than or equal to 400
     * @return  The error string associated with that error code.
     */
    public static String errorMessage(int sc){
        String error = "";
        switch (sc) {
            case HttpStatus.SC_BAD_REQUEST:
                error = "The request is invalid.";
                break;
            case HttpStatus.SC_UNAUTHORIZED:
                error = "You are not authorized to make that request.";
                break;
            case HttpStatus.SC_PAYMENT_REQUIRED:
                error = "You must subscribe to make that request.";
                break;
            case HttpStatus.SC_FORBIDDEN:
                error = "You are not authorized to make that request (Forbidden).";
                break;
            case HttpStatus.SC_NOT_FOUND:
                error = "The resource is no longer at that location.";
                break;
            case HttpStatus.SC_METHOD_NOT_ALLOWED:
                error = "Method not allowed on this resource.";
                break;
            case HttpStatus.SC_NOT_ACCEPTABLE:
                error = "Not Acceptable:   The resource identified by the request is only capable of generating response entities which have content characteristics not acceptable according to the accept headers sent in the request.";
                break;
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED:
                error = "Proxy Authentication Required:  You must first authenticate yourself with the proxy.";
                break;
            case HttpStatus.SC_REQUEST_TIMEOUT:
                error = "The request timed out.";
                break;
            case HttpStatus.SC_CONFLICT:
                error = "Conflict:  The request could not be completed due to a conflict of current resources.";
                break;
            case HttpStatus.SC_GONE:
                error = "Resource is no longer available at this server and no forwarding address is known.";
                break;
            case HttpStatus.SC_LENGTH_REQUIRED:
                error = "The request can not be handled without a defined Content-Length.";
                break;
            case HttpStatus.SC_PRECONDITION_FAILED:
                error = "The precondition given in one or more of the request-header fields evaluated to false when it was tested on the server.";
                break;
            case HttpStatus.SC_REQUEST_TOO_LONG:
                error = "The request is too long.";
                break;
            case HttpStatus.SC_REQUEST_URI_TOO_LONG:
                error = "The Request-URI is longer than the server is willing to interpret.";
                break;
            case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE:
                error = "Unsupported Media Type.";
                break;
            case HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE:
                error = "The server can not satisfy the specified byte range.";
                break;
            case HttpStatus.SC_EXPECTATION_FAILED:
                error = " the server could not meet the expectation given in the Expect request header.";
                break;
            case HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE:
                error = "Insufficient Space on Resource.";
                break;
            case HttpStatus.SC_METHOD_FAILURE:
                error = "The method failed.";
                break;
            case HttpStatus.SC_UNPROCESSABLE_ENTITY:
                error = "Could not process the provided entity.";
                break;
            case HttpStatus.SC_LOCKED:
                error = "Locked.";
                break;
            case HttpStatus.SC_FAILED_DEPENDENCY:
                error = "Dependency Failed.";
                break;
            case HttpStatus.SC_INTERNAL_SERVER_ERROR:
                error = "Internal Server Error.";
                break;
            case HttpStatus.SC_NOT_IMPLEMENTED:
                error = "Not Implemented.";
                break;
            case HttpStatus.SC_BAD_GATEWAY:
                error = "Bad Gateway.";
                break;
            case HttpStatus.SC_SERVICE_UNAVAILABLE:
                error = "Service unavailable.";
                break;
            case HttpStatus.SC_GATEWAY_TIMEOUT:
                error = "Gateway timed out.";
                break;
            case HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED:
                error = "Http Version Not Supported.";
                break;
            case HttpStatus.SC_INSUFFICIENT_STORAGE:
                error = "Insufficient Storage.";
                break;
        }

        return error;
    }
}
