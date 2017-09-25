package project.demo2.mainpage.presenter;


import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import project.demo2.data.DataManager;
import project.demo2.data.model.Item;
import project.demo2.data.model.ServerResponse;
import project.demo2.mainpage.view.MainView;

/**
 * Created by hassan on 8/31/2017.
 */


public class MainPresenter implements Presenter {

    MainView mainView;
    DataManager dataManager;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        this.dataManager = DataManager.getInstance();
    }


    @Override
    public void onPause() {}

    @Override
    public void onResume() {

        //load item from realm cache
        List<Item> examples = dataManager.getCacheItems();
        //if size not zero than show them in view
        if (examples.size() != 0) {
            mainView.setItems(examples);
        } else {
            mainView.showProgress();
        }

        //add it to compositeDisposable (so we can dispose all observers at once )
        compositeDisposable.add(dataManager.loadData(new DataManager.Actions<List<Item>>() {
            @Override
            public void onFinish(List<Item> itemList) {
                mainView.hideProgress();
                mainView.setItems(itemList);
            }

            @Override
            public void onError() {
                mainView.hideProgress();
                mainView.displayMsgError();
            }
        }));


    }


    @Override
    public void deleteItem(final Item item, final int position) {
        mainView.showProgress();
        //deleting item
        dataManager.deleteItem(item, new DataManager.Actions<ServerResponse>() {
            @Override
            public void onFinish(ServerResponse result) {
                mainView.hideProgress();
                if (result.isSuccess()) {
                    mainView.displaySuccessDeleteMsg();
                    mainView.deleteItem(position);
                } else {
                    mainView.displayFailDeleteMsg();
                }
            }

            @Override
            public void onError() {
                mainView.hideProgress();
                mainView.displayFailDeleteMsg();
            }
        });
    }


    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        this.dataManager.onDestroy();
        mainView = null;
    }

}
