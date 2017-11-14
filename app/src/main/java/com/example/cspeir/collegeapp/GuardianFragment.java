package com.example.cspeir.collegeapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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
 * Created by shunt on 10/5/2017.
 */

public class GuardianFragment extends Fragment {
    TextView mFirstNameText;
    TextView mLastNameText;
    TextView mOccupationText;
    EditText mFirstNameEditText;
    EditText mLastNameEditText;
    EditText mOccupationEditText;
    Guardian mGuardian;
    Button mSubmitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup v, Bundle bundle) {
        super.onCreateView(inflater, v, bundle);
        mGuardian = new Guardian("John", "Cena");
        View rootView = inflater.inflate(R.layout.fragment_guardian, v, false);
        mSubmitButton = (Button) rootView.findViewById(R.id.gsubmit_button);
        mFirstNameEditText = (EditText) rootView.findViewById(R.id.gfirst_name_edit);
        mLastNameEditText = (EditText) rootView.findViewById(R.id.glast_name_edit);
        mOccupationEditText = (EditText) rootView.findViewById(R.id.g_occupation_edit);
        mLastNameText = (TextView) rootView.findViewById(R.id.glast_name_text);
        mFirstNameText = (TextView) rootView.findViewById(R.id.gfirst_name_text);
        mOccupationText = (TextView) rootView.findViewById(R.id.g_occupation_text);
        mFirstNameText.setText(mGuardian.getFirstName());
        mLastNameText.setText(mGuardian.getLastName());
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFirstNameEditText.getText().length() > 0) {
                    String firstName = mFirstNameEditText.getText().toString();
                    mGuardian.setFirstName(firstName);
                    mFirstNameText.setText(firstName);
                    mFirstNameEditText.setText("");
                }

                if (mLastNameEditText.getText().length() > 0) {
                    String lastName = mLastNameEditText.getText().toString();
                    mGuardian.setLastName(lastName);
                    mLastNameText.setText(lastName);
                    mLastNameEditText.setText("");
                }

                if (mOccupationEditText.getText().length() > 0) {
                    String occupation = mOccupationEditText.getText().toString();
                    mGuardian.setOccupation(occupation);
                    mOccupationText.setText(occupation);
                    mOccupationEditText.setText("");
                }
                Backendless.Persistence.save(mGuardian, new AsyncCallback<Guardian>() {
                    @Override
                    public void handleResponse(Guardian guardian) {
                        Log.i(TAG, "Saved" + guardian.toString());
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Log.i(TAG, backendlessFault.toString());
                    }
                });
            }
        });
        return rootView;
    }

    public void onStart() {
        super.onStart();
        int index = getActivity().getIntent().getIntExtra(FamilyMember.EXTRA_INDEX, -1);
        if (index != -1) {
            mGuardian = (Guardian) Family.get().getFamily().get(index);
            mFirstNameText.setText(mGuardian.getFirstName());
            mLastNameText.setText(mGuardian.getLastName());
            mOccupationText.setText(mGuardian.getOccupation());
        }
    }
}