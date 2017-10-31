package com.example.cspeir.collegeapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    public View onCreateView(LayoutInflater inflater , ViewGroup v, Bundle bundle){
        super.onCreateView(inflater, v , bundle);
        mSibling = new Sibling("Bishop", "Hunt");

        View rootView = inflater.inflate(R.layout.fragment_profile, v, false);
        ssubmitButton=(Button)rootView.findViewById(R.id.ssubmit_button);
        sFirstNameEditText = (EditText)rootView.findViewById(R.id.sfirst_name_edit);
        sLastNameEditText = (EditText)rootView.findViewById(R.id.slast_name_edit);
        sLastNameText = (TextView)rootView.findViewById(R.id.slast_name_text);
        sFirstNameText= (TextView)rootView.findViewById(R.id.sfirst_name_text);
        sFirstNameText.setText(mSibling.getFirstName());
        sLastNameText.setText(mSibling.getLastName());
        ssubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = "";
                String last = "";
                first = sFirstNameEditText.getText().toString().trim();
                last = sLastNameEditText.getText().toString().trim();
                if ((first != null && !first.equals("")) && (last != null && !last.equals(""))) {
                    sFirstNameText.setText(first);
                    sLastNameText.setText(last);
                }
            }
        });
        return rootView;

    }
}
