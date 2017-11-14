package com.example.cspeir.collegeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.io.File;
import java.util.Date;
import java.util.List;

import static com.backendless.media.SessionBuilder.TAG;
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

    private ImageButton mSelfieButton;
    private ImageView mSelfieView;
    private File mSelfieFile;
    private final int REQUEST_SELFIE = 1;

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
        mSelfieFile = getPhotoFile();
        mSelfieView = (ImageView)rootView.findViewById(R.id.profile_pic);
        mSelfieButton = (ImageButton)rootView.findViewById(R.id.profile_camera);
        context = this.getContext();
        mFirstNameText.setText(mProfile.getFirstName());
        mLastNameText.setText(mProfile.getLastName());


        final Intent captureSelfie = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakeSelfie = mSelfieFile != null &&
                captureSelfie.resolveActivity(getActivity().getPackageManager()) != null;
        mSelfieButton.setEnabled(canTakeSelfie);
        if (canTakeSelfie) {
            Uri uri = Uri.fromFile(mSelfieFile);
            captureSelfie.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }


        mSelfieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureSelfie, REQUEST_SELFIE);
            }
        });
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
                    Backendless.Persistence.save(mProfile, new AsyncCallback<Profile>() {
                        @Override
                        public void handleResponse(Profile response) {
                            Log.i(TAG, "thing2");
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.i(TAG, "thing1");
                        }

                            });

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
        updateSelfieView();
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
    public File getPhotoFile() {
        File externalFilesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, mProfile.getPhotoFileName());
    }

    public void updateSelfieView(){
        if (mSelfieFile!=null && mSelfieFile.exists()){
            Bitmap mSelfieBitmap = BitmapFactory.decodeFile(mSelfieFile.getPath());
            mSelfieView.setImageBitmap(mSelfieBitmap);

        }
    }
}
