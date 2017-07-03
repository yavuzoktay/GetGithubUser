package com.yavuzoktay.getGithubUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Yavuz on 3.7.2017.
 */

public interface ApiService {

    @GET("search/users")
    Call<User> getGithubUser(@Query("q") String name);

}