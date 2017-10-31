package com.example.cspeir.collegeapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by shunt on 10/5/2017.
 */

public class GuardianFragment extends Fragment {
    TextView mFirstNameText;
    TextView mLastNameText;
    EditText mFirstNameEditText;
    EditText mLastNameEditText;
    Guardian mGuardian;
    Button mSubmitButton;

    @Override
        public View onCreateView(LayoutInflater inflater , ViewGroup v, Bundle bundle){
        super.onCreateView(inflater, v , bundle);
            mGuardian= new Guardian("John", "Cena");
            View rootView = inflater.inflate(R.layout.fragment_guardian, v, false);
            mSubmitButton= (Button)rootView.findViewById(R.id.gsubmit_button);
            mFirstNameEditText = (EditText)rootView.findViewById(R.id.gfirst_name_edit);
            mLastNameEditText = (EditText)rootView.findViewById(R.id.glast_name_edit);
            mLastNameText = (TextView)rootView.findViewById(R.id.glast_name_text);
            mFirstNameText= (TextView)rootView.findViewById(R.id.gfirst_name_text);
            mFirstNameText.setText(mGuardian.getFirstName());
            mLastNameText.setText(mGuardian.getLastName());
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = "";
                String last = "";
                first = mFirstNameEditText.getText().toString().trim();
                last= mLastNameEditText.getText().toString().trim();
                if((first != null && !first.equals(""))&& (last!=null && !last.equals(""))){
                    mFirstNameText.setText(first);
                    mLastNameText.setText(last);
                }



            }
        });
        return rootView;
    }
}
