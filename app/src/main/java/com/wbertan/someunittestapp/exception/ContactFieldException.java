package com.wbertan.someunittestapp.exception;

/**
 * Created by william.bertan on 15/12/2016.
 */

public class ContactFieldException extends Exception {
    public ContactExceptionEnum mCode;
    public int mMessageResource;

    public ContactFieldException(ContactExceptionEnum aCode, int aMessageResource) {
        this.mCode = aCode;
        this.mMessageResource = aMessageResource;
    }
}
