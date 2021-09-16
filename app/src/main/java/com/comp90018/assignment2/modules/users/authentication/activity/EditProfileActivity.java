package com.comp90018.assignment2.modules.users.authentication.activity;
/**
 * @author Gengchang Xu
 */

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


import com.comp90018.assignment2.R;
import com.comp90018.assignment2.databinding.ActivityEditUserProfileBinding;
import com.comp90018.assignment2.databinding.ActivityRegisterBinding;
import com.comp90018.assignment2.databinding.ActivitySearchProductBinding;
import com.comp90018.assignment2.dto.UserDTO;
import com.comp90018.assignment2.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;



public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity DEBUG";
    private ActivityEditUserProfileBinding binding;

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        binding = ActivityEditUserProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        // attach to layout file
        setContentView(view);

        // init db
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        //set edit profile listener
        //back button
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RadioGroup genderGroup = binding.genderRadioGroup;
        final Integer[] gender = {Constants.FEMALE};

        // get gender info
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == binding.profileFemale.getId()) {
                    gender[0] = Constants.FEMALE;
                } else if (checkedId == binding.profileMale.getId()) {
                    gender[0] = Constants.MALE;
                } else {
                    gender[0] = Constants.GENDER_UNKNOWN;
                }
            }
        });

        genderGroup.check(binding.profileFemale.getId());

        binding.btnProfile.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String nickname = binding.profileName.getText().toString();
                String description = binding.profileDesc.getText().toString();
                String address = binding.profileAddress.getText().toString();
                // get genderType
                Integer genderType = gender[0];



                if (nickname == null || description == null || address == null || nickname.length() >= 20 || description.length() >= 60 || address.length() >= 100) {
                    new AlertDialog.Builder(EditProfileActivity.this).setMessage("Please input all information correctly.").setPositiveButton("ok", null).show();
                    return;
                }

                String nicknameRegex = "^[a-zA-Z0-9_-]{0,20}$";
                // No password regex, because it is login
                if (!nickname.matches(nicknameRegex)) {
                    new AlertDialog.Builder(EditProfileActivity.this).setMessage("Nickname should be letters, numbers, underscores and dashes.").setPositiveButton("ok", null).show();
                    return;
                }

                String addressRegex = "^[a-zA-Z0-9_-]{0,100}$";
                // No password regex, because it is login
                if (!address.matches(addressRegex)) {
                    new AlertDialog.Builder(EditProfileActivity.this).setMessage("address should be letters, numbers, underscores and dashes.").setPositiveButton("ok", null).show();
                    return;
                }


                String descRegex = "^[a-zA-Z0-9_-]{0,60}$";
                // No password regex, because it is login
                if (!description.matches(descRegex)) {
                    new AlertDialog.Builder(EditProfileActivity.this).setMessage("Description should be letters, numbers, underscores and dashes.").setPositiveButton("ok", null).show();
                    return;
                }


            }
        });





    }












}
