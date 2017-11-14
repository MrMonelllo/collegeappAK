package com.example.cspeir.collegeapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import static android.content.ContentValues.TAG;

/**
 * Created by shunt on 10/6/2017.
 */

public class SiblingFragment extends Fragment {

    TextView sFirstNameText;
    TextView sLastNameText;
    EditText sFirstNameEditText;
    EditText sLastNameEditText;
    Sibling mSibling;
    Button ssubmitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup v, Bundle bundle) {
        super.onCreateView(inflater, v, bundle);
        mSibling = new Sibling("Bishop", "Hunt");

        View rootView = inflater.inflate(R.layout.fragment_sibling, v, false);
        ssubmitButton = (Button) rootView.findViewById(R.id.ssubmit_button);
        sFirstNameEditText = (EditText) rootView.findViewById(R.id.sfirst_name_edit);
        sLastNameEditText = (EditText) rootView.findViewById(R.id.slast_name_edit);
        sLastNameText = (TextView) rootView.findViewById(R.id.slast_name_text);
        sFirstNameText = (TextView) rootView.findViewById(R.id.sfirst_name_text);
        sFirstNameText.setText(mSibling.getFirstName());
        sLastNameText.setText(mSibling.getLastName());
        ssubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ssubmitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sFirstNameEditText.getText().length() > 0) {
                            String firstName = sFirstNameEditText.getText().toString();
                            mSibling.setFirstName(firstName);
                            sFirstNameText.setText(firstName);
                            sFirstNameEditText.setText("");
                        }

                        if (sLastNameEditText.getText().length() > 0) {
                            String lastName = sLastNameEditText.getText().toString();
                            mSibling.setLastName(lastName);
                            sLastNameText.setText(lastName);
                            sLastNameEditText.setText("");
                        }
                        Backendless.Persistence.save(mSibling, new AsyncCallback<Sibling>() {
                            @Override
                            public void handleResponse(Sibling sibling) {
                                Log.i(TAG, "Saved" + sibling.toString());
                            }

                            @Override
                            public void handleFault(BackendlessFault backendlessFault) {
                                Log.i(TAG, backendlessFault.toString());
                            }
                        });
                    }
                });
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        int index = getActivity().getIntent().getIntExtra(FamilyMember.EXTRA_INDEX, -1);
        super.onStart();
        if (index != -1) {
            mSibling = (Sibling) Family.get().getFamily().get(index);
            sFirstNameText.setText(mSibling.getFirstName());
            sLastNameText.setText(mSibling.getLastName());
        }

    }
}