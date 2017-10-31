package com.example.cspeir.collegeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.Date;
import java.util.List;

import static weborb.util.ThreadContext.context;

/**
 * Created by shunt on 10/6/2017.
 */

public class ProfileFragment extends Fragment {
    String email;
    TextView mFirstNameText;
    TextView mLastNameText;
    EditText mFirstNameEditText;
    EditText mLastNameEditText;
    Profile mProfile;
    Button msubmitButton;
    Button dateButton;
    private Context context;
    private static final int REQUEST_DATE_OF_BIRTH = 0;
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup v, Bundle bundle) {
        super.onCreateView(inflater, v, bundle);
        mProfile = new Profile("Sebastian", "Hunt");
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        email = sharedPreferences.getString(ApplicantActivity.EMAIL_PREF,null);
        if (mProfile.getEmail()==null){
            mProfile.setEmail(email);
        }
        View rootView = inflater.inflate(R.layout.fragment_profile, v, false);
        dateButton = (Button) rootView.findViewById(R.id.pdate_button);
        msubmitButton = (Button) rootView.findViewById(R.id.psubmit_button);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment().newInstance(mProfile.getDates());
                dialog.setTargetFragment(ProfileFragment.this, REQUEST_DATE_OF_BIRTH);
                dialog.show(fm, "DialogDateOfBirth");
            }
        });
        mFirstNameEditText = (EditText) rootView.findViewById(R.id.pfirst_name_edit);
        mLastNameEditText = (EditText) rootView.findViewById(R.id.plast_name_edit);
        mLastNameText = (TextView) rootView.findViewById(R.id.plast_name_text);
        mFirstNameText = (TextView) rootView.findViewById(R.id.pfirst_name_text);
        context = this.getContext();
        mFirstNameText.setText(mProfile.getFirstName());
        mLastNameText.setText(mProfile.getLastName());
        msubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = "";
                String last = "";
                first = mFirstNameEditText.getText().toString().trim();
                last = mLastNameEditText.getText().toString().trim();
                if ((first != null && !first.equals("")) && (last != null && !last.equals(""))) {
                    mFirstNameText.setText(first);
                    mLastNameText.setText(last);
                    mProfile.setFirstName(first);
                    mProfile.setLastName(last);

                String whereClause = "email = '" + email + "'";
                DataQueryBuilder queryBuilder = DataQueryBuilder.create();
                queryBuilder.setWhereClause(whereClause);
                Backendless.Data.of(Profile.class).find(queryBuilder, new AsyncCallback<List<Profile>>() {
                    @Override
                    public void handleResponse(List<Profile> response) {
                        if (!response.isEmpty()) {
                            String profileId = response.get(0).getObjectId();
                            mProfile.setObjectId(profileId);
                            Log.i("ProfileFragment", profileId);
                        }
                        Backendless.Data.of(Profile.class).save(mProfile, new AsyncCallback<Profile>() {
                            @Override
                            public void handleResponse(Profile response) {
                                Log.i("ProfileFragment saved", response.toString());
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Log.i("ProfileFragment failed", fault.getMessage());
                            }
                        });
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i("ProfileFragment", fault.getMessage());
                    }
                });
            }
        }

        });
        return rootView;
    }
        @Override
        public void onStart(){
            super.onStart();
            String whereClause = "email = '" +email+"'";
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setWhereClause(whereClause);
            Backendless.Data.of(Profile.class).find(queryBuilder, new AsyncCallback<List<Profile>>() {
                @Override
                public void handleResponse(List<Profile> response) {
                    if(!response.isEmpty()){
                        String profileId = response.get(0).getObjectId();
                        mProfile=response.get(0);
                        Log.i("ProfileFragment", profileId);
                        mFirstNameText.setText(mProfile.getFirstName());
                        mLastNameText.setText(mProfile.getLastName());
                    }
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.i("ProfileFragment", fault.getMessage());
                }
            });
        }
        @Override
        public void onPause() {
            super.onPause();
            String whereClause = "email = '" +email+"'";
            DataQueryBuilder queryBuilder = DataQueryBuilder.create();
            queryBuilder.setWhereClause(whereClause);
            Backendless.Data.of(Profile.class).find(queryBuilder, new AsyncCallback<List<Profile>>() {
                @Override
                public void handleResponse(List<Profile> response) {
                    if(!response.isEmpty()){
                        String profileId = response.get(0).getObjectId();
                        mProfile.setObjectId(profileId);
                        Log.i("ProfileFragment", profileId);
                    }
                    Backendless.Data.of(Profile.class).save(mProfile, new AsyncCallback<Profile>() {
                        @Override
                        public void handleResponse(Profile response) {
                            Log.i("ProfileFragment saved", response.toString());
                            Toast.makeText(context, "Save to BackendLess", Toast.LENGTH_SHORT ).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.i("ProfileFragment failed", fault.getMessage());
                        }
                    });
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.i("ProfileFragment", fault.getMessage());
                    Backendless.Data.of(Profile.class).save(mProfile, new AsyncCallback<Profile>() {
                        @Override
                        public void handleResponse(Profile response) {
                            Log.i("ProfileFragment saved", response.toString());
                            Toast.makeText(context, "Save to BackendLess", Toast.LENGTH_SHORT ).show();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.i("ProfileFragment failed", fault.getMessage());
                        }
                    });
                }
            });
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode,Intent intent){
            if (resultCode==Activity.RESULT_OK && requestCode==REQUEST_DATE_OF_BIRTH){
                mProfile.setDates((Date)intent.getSerializableExtra(DatePickerFragment.EXTRA_DATE_OF_BIRTH));
                String getdate = mProfile.getDates().toString();
                getdate = getdate.substring(4,7)+" "+getdate.substring(8,10)+" " +getdate.substring(getdate.length()-4,getdate.length());
                dateButton.setText(getdate);
            }
        }

}
