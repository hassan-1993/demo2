package project.demo2.data.service;



import java.util.List;

import io.reactivex.Observable;
import project.demo2.data.model.Item;
import project.demo2.data.model.ServerResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by hassan on 9/16/2017.
 */

public interface ApiInterface {



    @GET("item")
    Observable<List<Item>> getItems();


    //the @GET should be @DELETE but i couldn't use it since not supported from php (only get and post supported ,i have to buy it to support it)
    @GET("item")
    Call<ServerResponse> deleteItem(@Query("id") int id);




    @FormUrlEncoded
    @POST("item")
    Call<Item> addItem(@Field("title") String title, @Field("description") String description, @Field("image") String image);

}
