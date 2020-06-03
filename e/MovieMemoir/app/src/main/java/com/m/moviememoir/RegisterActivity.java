package com.m.moviememoir;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.m.moviememoir.Bean.Credential;
import com.m.moviememoir.Bean.Person;
import com.m.moviememoir.Utils.HttpClient;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_firstname)
    EditText etFirstname;
    @BindView(R.id.et_surname)
    EditText etSurname;
    @BindView(R.id.radio_F)
    RadioButton radioF;
    @BindView(R.id.radio_M)
    RadioButton radioM;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.btnDob)
    Button btnDob;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etPassword2)
    EditText etPassword2;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.etState)
    EditText etState;
    @BindView(R.id.etPostCode)
    EditText etPostCode;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.btnSign)
    Button btnSign;
    @BindView(R.id.tvLogin)
    TextView tvLogin;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private String datePicked = 2000 + "-" + 01 + "-" + 01 + "T00:00:00+10:00";
    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear, int newMonth, int newDate) {
            String displayDate = newDate + "/" + Integer.toString(newMonth + 1) + "/" + newYear;
            String strDay = "";
            String strMonth = "";
            strDay = (newDate > 10 ? "" : "0") + newDate;
            strMonth = (newMonth + 1 > 10 ? "" : "0") + (newMonth + 1);
            datePicked = newYear + "-" + strMonth + "-" + strDay + "T00:00:00+10:00";
            btnDob.setText(displayDate);
        }
    };

    public static String getTime() {
        return DateFormat.format("yyyy-MM-dd", System.currentTimeMillis()).toString();
    }

    public void initView() {
        btnDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, dateListener, 2000, 1, 1).show();
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstname = etFirstname.getText().toString();
                final String surname = etSurname.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String password2 = etPassword2.getText().toString();
                final String state = etState.getText().toString();
                final String address = etAddress.getText().toString();
                final String postcode = etPostCode.getText().toString();
                final String email = etEmail.getText().toString();
                final String dob = datePicked;
                final String gender = radioF.isChecked() ? "f" : "m";
                if (username.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please input username", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please input password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password2.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please input password2", Toast.LENGTH_LONG).show();
                    return;
                }
                if (firstname.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please input height", Toast.LENGTH_LONG).show();
                    return;
                }
                if (surname.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please input weight", Toast.LENGTH_LONG).show();
                    return;
                }
                if (state.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please input street", Toast.LENGTH_LONG).show();
                    return;
                }
                if (postcode.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please input postcode", Toast.LENGTH_LONG).show();
                    return;
                }
                if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please input email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (address.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please input levelOfActivity", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!isNumber(postcode)) {
                    Toast.makeText(RegisterActivity.this, "Format error please enter number", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!isEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Format error please enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!password.equals(password2)) {
                    Toast.makeText(RegisterActivity.this, "Password mismatch", Toast.LENGTH_LONG).show();
                    return;
                }
                final Credential credential = new Credential();
                credential.setPassword(password);
                credential.setSignupdate(getTime());
                credential.setUsername(email);

                Person person = new Person();
                person.setAddress(address);
                person.setDob(dob);
                person.setEmail(email);
                person.setFirstname(firstname);
                person.setSurname(surname);
                person.setGender(gender);
                person.setState(state);
                person.setPostcode(Integer.parseInt(postcode));
                Log.d("xbw12138", new Gson().toJson(person));
                Log.d("xbw12138", new Gson().toJson(credential));
                HttpClient.getInstance().asyncPost(Constant.API_PERSON, RequestBody.create(JSON, new Gson().toJson(person)), new HttpClient.HttpCallBack() {
                    @Override
                    public void onError(Request request, IOException e) {

                    }

                    @Override
                    public void onSuccess(Request request, String result, Headers headers) {
                        Log.d("xbw12138", result);
                        HttpClient.getInstance().asyncPost(Constant.API_CREDENTIAL, RequestBody.create(JSON, new Gson().toJson(credential)), new HttpClient.HttpCallBack() {
                            @Override
                            public void onError(Request request, IOException e) {

                            }

                            @Override
                            public void onSuccess(Request request, String result, Headers headers) {
                                Log.d("xbw12138", result);
                                finish();
                            }
                        });
                    }
                });
            }

        });
    }

    public boolean isNumber(String str) {
        final String pattern = "^[1-9]\\d*$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.find();
    }

    public boolean isEmail(String str) {
        final String pattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.find();
    }
}
