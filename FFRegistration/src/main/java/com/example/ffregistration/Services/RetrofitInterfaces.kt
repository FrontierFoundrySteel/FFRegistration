package com.ffsteel.ffpractice_app1.Services

import com.ffsteel.ffpractice_app1.Models.UserRegistrationResponseDataModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface RetrofitInterfaces {

    //Not IN USE
    @GET("destination")
    fun getDestinationList(@QueryMap filter: HashMap<String, String>): Call<List<UserRegistrationResponseDataModel>>
    //Not IN USE
    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int): Call<UserRegistrationResponseDataModel>
/*

    @Headers("Content-Type: application/json")
    @POST("UserRegistration.php") //UserRegistration.php
    fun addDestination(@Body newDestination: Destination): Call<Destination>
*/

/*TODO
    //IN USE
    @GET("update_hcm_userid.php")
     fun updateHCMUserID(@Query("password") password: String, @Query("HCMUserID") HCMUserID: String, @Query("DeviceIMEI") DeviceIMEI: String): Call<UserRegistrationResponseDataModel>
 */
    //IN USE
    @GET("update_hcm_userid.php")
    suspend fun updateHCMUserID(@Query("password") password: String, @Query("HCMUserID") HCMUserID: String, @Query("DeviceIMEI") DeviceIMEI: String): Response<UserRegistrationResponseDataModel>

    //IN USE
    @FormUrlEncoded
    @POST("user_registration.php") //UserRegistration.php
     fun addUser(@Field("TokkenID")  TokkenID:String,
                @Field("ShortTokkenID")  ShortTokkenID:String,
                @Field("AppID")  AppID:String,
                @Field("AppName")  AppName:String,
                @Field("AppVersion")  AppVersion:String,
                @Field("UserEmail")  UserEmail:String,
                @Field("UserType")  UserType:String,
                @Field("DeviceName")  DeviceName:String,
                @Field("DeviceType")  DeviceType:String,
                @Field("DeviceIMEI")  DeviceIMEI:String,
                @Field("DeviceStatus")  DeviceStatus:String,
                @Field("StreetAddress")  StreetAddress:String,
                @Field("City")  City:String,
                @Field("State")  State:String,
                @Field("Country")  Country:String,
                @Field("Latitude")  Latitude:String,
                @Field("Longitude")  Longitude:String,
                @Field("OSVersion")  OSVersion:String,
                @Field("Rooted")  Rooted:String,
                @Field("password")  password:String): Call<List<UserRegistrationResponseDataModel>>

    @FormUrlEncoded
    @POST("push_notification01.php") //UserRegistration.php ,
    suspend fun sendNotification(
            @Field("password") password: String
            ,@Field("HCMUserID")  HCMUserID: String,
                         @Field("Body")  Body:String,
                         @Field("HCMName")  HCMName:String): Response<UserRegistrationResponseDataModel>


    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("description") desc: String,
        @Field("country") country: String
    ): Call<UserRegistrationResponseDataModel>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int): Call<Unit>

}