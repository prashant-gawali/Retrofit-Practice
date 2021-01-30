package com.example.workingwithretrofit;

import com.example.workingwithretrofit.model.Comment;
import com.example.workingwithretrofit.model.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface MyWebService {
    String BASE_URL = "https://jsonplaceholder.typicode.com/";
    String FEED = "posts";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

//                                            *** @GET Requests ***
//    @GET used for GET Request
//    @Path used to get url path at Runtime
//    @Query() used to define Query parameters for Request
//    @QueryMap used to Query POarameters in Key-Value pairs
//    @Url used to pass Url directly instead of parameters
//    @Body used to pass objects within the body..
//    @FormUrlEncoded it sends data in url encoded formats


    //    get list of Posts
    @GET(FEED)
    Call<List<Post>> getPosts();

    //  dynamic url parameters with path

//    @GET("posts/{id}/comments")
//    Call<List<Comment>> getComments(@Path("id") int userId);


    //    Query Parameters
    @GET("comments")
    Call<List<Comment>> getComments(@Query("postId") int postId,
                                    @Query("_sort") String sortBy,
                                    @Query("_order") String orderBy);


    //    Query Map(Query Parameters in Map)
    @GET("comments")
    Call<List<Comment>> getComment(@QueryMap Map<String, String> params);

    //    Passing Url
    @GET()
    Call<List<Comment>> getUrl(@Url String url);

//                                          *** @POST Requets ***

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createFieldPost(@Field("userId") int userId,
                               @Field("title") String title,
                               @Field("body") String body);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createFieldMapPost(@FieldMap Map<String, String> postMap);

//                                           *** @PUT Requests ***

    //    replaces all objects if we pass only one key's value..
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

//                                           *** @PATCH Requests ***

    //    replaces only key's value which we r passing..
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);

    //                                           *** @DELETE Request ***
    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);


}