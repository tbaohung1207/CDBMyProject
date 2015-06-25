package com.asiantech.stevenslavin.ntt_cdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.asiantech.stevenslavin.model.User;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener{
    private Button mLogin,mFacebook,mTwitter;
    private TextView mValidation;
    private EditText mUserName,mPassword;
    private CallbackManager mCallbackManager;
    private String TAG = "FACEBOOK";
    private User user;


    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if (oldAccessToken != null) {
                    Log.i(TAG, "oldAccessToken" + oldAccessToken.toString());
                }
                if (currentAccessToken != null) {
                    Log.i(TAG, "currentAccessToken" + currentAccessToken.toString());
                }
                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(
                            Profile oldProfile,
                            Profile currentProfile) {
                        if (oldProfile != null) {
                            Log.i(TAG, "oldProfile" + oldProfile.toString());
                        }
                        if (currentProfile != null) {
                            Log.i(TAG, "cuurentProfile" + currentProfile.toString());
                        }
                    }
                };
            }
        };
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "success");
               GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String gender = object.getString("gender");
                                    String birthday = object.getString("birthday");
                                    String location = object.getString("location");
                                    String city = object.getJSONObject("location").getString("name");
                                    Log.i("Error", "--" + id + "--" + name + "--" + email + "--" + gender + "--"
                                            + birthday + location + city);
                                    user = new User(name, gender, id, "18", "IT", city);
                                    Log.e("XXX", name +email);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("Error", "----------------");
                                }

                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", user);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,gender,birthday,location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "cancel");

            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
            }
        });

        setContentView(R.layout.activity_login);
        init();
        listener();
    }
    private void init() {
        mUserName   = (EditText)  findViewById(R.id.edt_username);
        mPassword   = (EditText)  findViewById(R.id.edt_passworld);
        mLogin      = (Button)    findViewById(R.id.btn_login);
        mValidation = (TextView)  findViewById(R.id.txt_validation);
        mFacebook   = (Button)    findViewById(R.id.btn_facebook);
    }
    private void listener() {
        mLogin.setOnClickListener(this);
        mFacebook.setOnClickListener(this);
    }
    public boolean doValidation(EditText user, EditText password){
        if(user.getText().toString().equals("") || password.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v == mLogin ){
            if(doValidation(mUserName,mPassword)){
                mValidation.setText("Id and Pass word has not been entered");
            }
            else {
                mValidation.setText("Ok");
            }
        }
        else if (v == mFacebook){
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                    Arrays.asList("user_photos", "user_location", "email", "user_birthday", "public_profile"));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // this method finsh , onSuceess will be called
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }
}

