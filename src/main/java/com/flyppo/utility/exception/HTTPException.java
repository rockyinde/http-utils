package com.flyppo.utility.exception;

/**
 * generic exception for shadow client
 * @author rockyinde
 *
 */
public class HTTPException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public HTTPException (String message) {
        super(message);
    }
    
    public HTTPException (String message, Throwable t) {
        super(message, t);
    }
}
