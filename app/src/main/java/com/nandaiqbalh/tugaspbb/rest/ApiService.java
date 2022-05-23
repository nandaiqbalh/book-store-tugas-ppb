package com.nandaiqbalh.tugaspbb.rest;

import com.nandaiqbalh.tugaspbb.utils.book.BookResponse;
import com.nandaiqbalh.tugaspbb.utils.login.LoginRequest;
import com.nandaiqbalh.tugaspbb.utils.login.LoginResponse;
import com.nandaiqbalh.tugaspbb.utils.register.RegisterRequest;
import com.nandaiqbalh.tugaspbb.utils.register.RegisterResponse;
import com.nandaiqbalh.tugaspbb.utils.userprofile.UserProfileRequest;
import com.nandaiqbalh.tugaspbb.utils.userprofile.UserProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("user/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("user/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("user/get-profile")
    Call<UserProfileResponse> getProfile(@Body UserProfileRequest userProfileRequest);

    @POST("user/update-profile")
    Call<UserProfileResponse> updateUserProfile(@Body UserProfileRequest userProfileRequest);

    @GET("book/get-featured")
    Call<BookResponse> latestBooks();
}
