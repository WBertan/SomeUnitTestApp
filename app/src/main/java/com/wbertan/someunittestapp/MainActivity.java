package com.wbertan.someunittestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.wbertan.someunittestapp.controller.ControllerContact;
import com.wbertan.someunittestapp.exception.ContactExceptionEnum;
import com.wbertan.someunittestapp.exception.ContactFieldException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wbertan.someunittestapp.R.id.editTextName;
import static com.wbertan.someunittestapp.R.id.editTextPhone;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editTextId)
    EditText mEditTextId;

    @BindView(editTextName)
    EditText mEditTextName;

    @BindView(editTextPhone)
    EditText mEditTextPhone;

    @BindView(R.id.recyclerViewContacts)
    RecyclerView mRecyclerViewContacts;

    private Contact mContactEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        clearFields();
    }

    private void init() {
        mRecyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewContacts.setAdapter(new AdapterContacts(new AdapterContacts.ViewHolderAction() {
            @Override
            public void executeRecyclerItemClick(View view, int position) {
                mContactEdit = ((AdapterContacts)mRecyclerViewContacts.getAdapter()).getSelectedItem();
                fillContactData();
            }
        }));
    }

    public boolean validateFields() {
        if(mEditTextName.getText().toString().trim().isEmpty()) {
            mEditTextName.setError(getString(R.string.edit_text_name_validate_error_no_name));
            mEditTextName.requestFocus();
            return false;
        }
        if(mEditTextPhone.getText().toString().trim().isEmpty()) {
            mEditTextPhone.setError(getString(R.string.edit_text_phone_validate_error_no_phone));
            mEditTextPhone.requestFocus();
            return false;
        }
        if(mEditTextPhone.getText().toString().trim().length() < 8) {
            mEditTextPhone.setError(getString(R.string.edit_text_phone_validate_error_few_numbers));
            mEditTextPhone.requestFocus();
            return false;
        }
        return true;
    }

    private void clearFields(){
        mEditTextId.setText(String.valueOf(mRecyclerViewContacts.getAdapter().getItemCount() + 1));
        mEditTextName.setText(null);
        mEditTextPhone.setText(null);
        mContactEdit = null;
        mEditTextName.requestFocus();
    }

    private void fillContactData() {
        mEditTextId.setText(String.valueOf(mContactEdit.getId()));
        mEditTextName.setText(mContactEdit.getName());
        mEditTextPhone.setText(mContactEdit.getPhone());
    }

    public void saveContact(View aView){
        if(mContactEdit == null) {
            mContactEdit = new Contact();
        }
        mContactEdit.setId(Long.valueOf(mEditTextId.getText().toString()));
        mContactEdit.setName(mEditTextName.getText().toString());
        mContactEdit.setPhone(mEditTextPhone.getText().toString());

        try{
            ControllerContact.getInstance().validateContact(mContactEdit);
        } catch (ContactFieldException exception) {
            if(exception.mCode.equals(ContactExceptionEnum.NO_NAME)) {
                mEditTextName.setError(getString(exception.mMessageResource));
                mEditTextName.requestFocus();
            } else if(exception.mCode.equals(ContactExceptionEnum.NO_PHONE)) {
                mEditTextPhone.setError(getString(exception.mMessageResource));
                mEditTextPhone.requestFocus();
            } else if(exception.mCode.equals(ContactExceptionEnum.FEW_NUMBERS)) {
                mEditTextPhone.setError(getString(exception.mMessageResource));
                mEditTextPhone.requestFocus();
            }
            return;
        }

        ((AdapterContacts) mRecyclerViewContacts.getAdapter()).add(mContactEdit);
        clearFields();
    }
}
