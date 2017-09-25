package project.demo2.data;




import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import project.demo2.data.model.Item;
import project.demo2.data.model.ServerResponse;
import project.demo2.data.service.ApiInterface;
import project.demo2.data.service.RealmController;
import project.demo2.data.service.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by hassan on 8/31/2017.
 */

public class DataManager {

    public interface Actions<T> {
        void onFinish(T result);
        void onError();
    }

    private static DataManager dataManager;
    private RealmController realmController;

    private ApiInterface apiInterface;



    private retrofit2.Call<Item> callAddItem;
    private retrofit2.Call<ServerResponse> callDeleteItem;

    private DataManager(){
        realmController=new RealmController();
        apiInterface= RestClient.getClient().create(ApiInterface.class);
    }

    /**
     * get items using rxAndroid and retrofit
     *
     * @param actions
     */
    public Disposable loadData(final Actions actions) {
        //subscribe on different thread
        return apiInterface.getItems().subscribeOn(Schedulers.newThread())
                //order matter so it execute on another thread
                .map(new Function<List<Item>, List<Item>>() {
                    @Override
                    public List<Item> apply(@NonNull List<Item> examples) throws Exception {
                        realmController.saveItems(examples);
                        return examples;
                    }
                })
                //observe on the main thread
                .observeOn(AndroidSchedulers.mainThread()).
                //subscribe an observer
                  subscribeWith(new DisposableObserver<List<Item>>() {
                    @Override
                    public void onNext(@NonNull List<Item> examples) {
                        actions.onFinish(examples);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        actions.onError();
                    }

                    @Override
                    public void onComplete() {
                    }
                });



    }

    /**
     * get items cached in realm
     * @return
     */
    public List<Item> getCacheItems() {
        return realmController.getItems();
    }


    public void deleteItem(final Item item, final Actions<ServerResponse> actions){
        this.callDeleteItem= apiInterface.deleteItem(item.getId());
        apiInterface.deleteItem(item.getId()).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                //delete from database
                realmController.deleteItem(item);
                actions.onFinish(response.body());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                actions.onError();
            }
        });
    }


    public void saveItem(final Actions<Item> actions, String title, String description, String imageBytes){

        this.callAddItem=apiInterface.addItem(title,description,imageBytes);
        //add the new item
        this.callAddItem.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                realmController.addItem(response.body());
                actions.onFinish(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<Item> call, Throwable t) {
                actions.onError();
            }
        });
    }

    public void onDestroy(){
        if(this.callAddItem!=null)
            this.callAddItem.cancel();

        if(this.callDeleteItem!=null)
            this.callDeleteItem.cancel();

    }

    public static DataManager getInstance(){
        if(dataManager==null)
            dataManager=new DataManager();
        return dataManager;
    }
}