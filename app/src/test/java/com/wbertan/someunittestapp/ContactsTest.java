package com.wbertan.someunittestapp;

import com.wbertan.someunittestapp.controller.ControllerContact;
import com.wbertan.someunittestapp.exception.ContactExceptionEnum;
import com.wbertan.someunittestapp.exception.ContactFieldException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by william.bertan on 15/12/2016.
 */

public class ContactsTest {
    @Mock
    List<Contact> listContacts = new ArrayList<>();

    @Before
    public void mockContacts() {
        listContacts.add(new Contact(1, "Bertan", "12345678"));
        listContacts.add(new Contact(2, "", "12345678"));
        listContacts.add(new Contact(3, "Bertan", ""));
        listContacts.add(new Contact(4, "Bertan", "1234"));
    }

    @Test
    public void testContactValidation() throws Exception {
        //This will fail because some of the contacts are not getting through the validations!
        ControllerContact controllerContact = ControllerContact.getInstance();
        for(Contact contact : listContacts) {
            controllerContact.validateContact(contact);
        }
    }

    @Test
    public void testContactValidationRules() throws Exception {
        testValidationRule(new Contact(1, "Bertan", "12345678"), ContactExceptionEnum.NO_NAME);
        testValidationRule(new Contact(2, "", "12345678"), ContactExceptionEnum.NO_NAME);
        testValidationRule(new Contact(3, "Bertan", ""), ContactExceptionEnum.NO_PHONE);
        testValidationRule(new Contact(4, "Bertan", "1234"), ContactExceptionEnum.FEW_NUMBERS);
        //Uncomment this to check a failure in the test!
//        testValidationRule(new Contact(5, "B", ""), ContactExceptionEnum.NO_NAME);
    }

    public void testValidationRule(Contact aContact, ContactExceptionEnum aEnumTest) {
        try {
            ControllerContact controllerContact = ControllerContact.getInstance();
            controllerContact.validateContact(aContact);
        } catch (ContactFieldException ex) {
            assertTrue("Problem with validating the Contact = (" + aContact + ") with exception " + aEnumTest, ex.mCode.equals(aEnumTest));
        }
    }

    @Test
    public void testContactNameLength() {
        //Please, change the number to fit your maximum length of the name that one contact could be
        int greatestLengthCouldHave = 6;
        for(Contact contact : listContacts) {
            assertFalse("You have contacts with more than " + greatestLengthCouldHave, contact.getName().length() > greatestLengthCouldHave);
        }
    }

    @Test
    public void testMaximumContactsStoredWithMockito(){
        //This test it's about test the maximum cap of the 'storage'
        // (even if this local app doesn't work if persistence)
        System.out.println("== testMaximumContactsStoredWithMockito ==");
        System.out.println("Trying 10 loops and maximum of 20 contacts");
        loopingContactsAdd(10, 20);
        System.out.println("-- Went trought!");
        //Uncomment this part below to get a fail (test of the test purpouses ;)
//        System.out.println("Trying 10 loops and maximum of 5 contacts");
//        loopingContactsAdd(10, 5);
//        System.out.println("-- Went trought");
    }

    public void loopingContactsAdd(int aLoops, int aMaxStoredContactsCouldHave) {
        List<Contact> listContacts2 = Mockito.mock(ArrayList.class);

        Mockito.when(listContacts2.size()).thenAnswer(new Answer() {
            int theCounter = 0;
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return theCounter++;
            }
        });

        int maxStoredContactsCouldHave = aMaxStoredContactsCouldHave;
        for(int loopCounter = 0; loopCounter < aLoops; loopCounter++) {
            assertTrue("You reach the maximum stored contacts in " + loopCounter + " loops!", listContacts2.size() < maxStoredContactsCouldHave);
        }
    }
}
