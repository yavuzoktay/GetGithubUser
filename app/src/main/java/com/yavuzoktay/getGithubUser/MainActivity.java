package com.yavuzoktay.getGithubUser;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;
    List<User.İtemsBean> Users;
    private RecyclerView.LayoutManager mLayoutManager;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressDialog = new ProgressDialog(this);


        if (checkConnection()) {


            progressDialog.setTitle("Connecting");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            ApiService myApi = RetroClient.getApiService();

            Call<User> call = myApi.getGithubUser("yavuzoktay");

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {

                    Log.v("Response Code", "Response Code is : " + response.code());
                    progressDialog.dismiss();

                    if (response.isSuccessful()) {

                        Users = new ArrayList<User.İtemsBean>();
                        Users = response.body().getİtems();

                        mAdapter = new UserAdapter(Users, getApplicationContext());

                        mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        mRecyclerView.setLayoutManager(mLayoutManager);

                        mRecyclerView.setAdapter(mAdapter);

                    } else {

                        Toast.makeText(getApplicationContext(), "Bazı şeyler yanlış gitti", Toast.LENGTH_LONG).show();

                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), "On Failure", Toast.LENGTH_LONG).show();

                }
            });

        } else {

            Toast.makeText(getApplicationContext(), "İnternet bağlantınızı tekrar deneyiniz", Toast.LENGTH_LONG).show();

        }
    }


    public boolean checkConnection() {

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null

                && conMgr.getActiveNetworkInfo().isAvailable()

                && conMgr.getActiveNetworkInfo().isConnected()) {

            return true;

        } else {

            return false;

        }

    }
}