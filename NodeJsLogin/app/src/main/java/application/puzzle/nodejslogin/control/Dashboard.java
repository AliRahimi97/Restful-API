package application.puzzle.nodejslogin.control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import application.puzzle.nodejslogin.R;
import application.puzzle.nodejslogin.model.user;
import application.puzzle.nodejslogin.control.retrofit.INodeJs;
import application.puzzle.nodejslogin.control.retrofit.RetrofitClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Dashboard extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    INodeJs myAPI;
    user user;

    TextView firstname, lastname, userCode, age, gender, marital_status, education_degree, emailUser, password;

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJs.class);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        userCode = findViewById(R.id.userCode);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        marital_status = findViewById(R.id.marital_status);
        education_degree = findViewById(R.id.education_degree);
        emailUser = findViewById(R.id.email);
        password = findViewById(R.id.password);

        
        String email = getIntent().getStringExtra("email");
        user_dashboard(email);
    }

    private void user_dashboard(String email) {

        compositeDisposable.add(myAPI.getUserInfo(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        user = new user();
                        JSONArray array = new JSONArray(s);
                        JSONObject info = array.getJSONObject(0);
                        user.setUser_code(info.getInt("user_code"));
                        user.setFirst_name(info.getString("first_name"));
                        user.setLast_name(info.getString("last_name"));
                        user.setAge(info.getInt("age"));
                        user.setEmail(info.getString("email"));
                        user.setEducation_degree(info.getString("education_degree"));
                        user.setMarital_status(info.getString("marital_status"));
                        user.setGender(info.getString("gender"));

                        firstname.setText(String.format("first name : %s", user.getFirst_name()));
                        lastname.setText(String.format("last name : %s", user.getLast_name()));
                        userCode.setText(String.format("user code : %s", user.getUser_code()));
                        age.setText(String.format("age : %s", user.getAge()));
                        gender.setText(String.format("gender : %s", user.getGender()));
                        marital_status.setText(String.format("marital status : %s", user.getMarital_status()));
                        education_degree.setText(String.format("education degree : %s", user.getEducation_degree()));
                        emailUser.setText(String.format("email : %s", user.getEmail()));
                        password.setText(String.format("password : %s", "*****"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(Dashboard.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}