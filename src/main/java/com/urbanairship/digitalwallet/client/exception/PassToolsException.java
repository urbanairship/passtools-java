package com.urbanairship.digitalwallet.client.exception;


public abstract class PassToolsException extends Exception {
    private static final long serialVersionUID = 1L;

    public PassToolsException(String msg){super(msg,null);}
    public PassToolsException(String msg,Throwable e){super(msg,e);}

}
