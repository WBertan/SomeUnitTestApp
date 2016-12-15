package com.wbertan.someunittestapp.controller;

import com.wbertan.someunittestapp.Contact;
import com.wbertan.someunittestapp.R;
import com.wbertan.someunittestapp.exception.ContactExceptionEnum;
import com.wbertan.someunittestapp.exception.ContactFieldException;

/**
 * Created by william.bertan on 15/12/2016.
 */

public class ControllerContact {
    private ControllerContact() {}

    private static class SingletonHolder {
        private static final ControllerContact INSTANCE = new ControllerContact();
    }

    public static ControllerContact getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public boolean validateContact(Contact aContact) throws ContactFieldException {
        if(aContact.getName().isEmpty()) {
            throw new ContactFieldException(ContactExceptionEnum.NO_NAME, R.string.edit_text_name_validate_error_no_name);
        }
        if(aContact.getPhone().isEmpty()) {
            throw new ContactFieldException(ContactExceptionEnum.NO_PHONE, R.string.edit_text_phone_validate_error_no_phone);
        }
        if(aContact.getPhone().length() < 8) {
            throw new ContactFieldException(ContactExceptionEnum.FEW_NUMBERS, R.string.edit_text_phone_validate_error_few_numbers);
        }
        return true;
    }
}
