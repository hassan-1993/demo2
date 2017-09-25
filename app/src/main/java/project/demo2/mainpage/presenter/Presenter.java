package project.demo2.mainpage.presenter;

import project.demo2.data.model.Item;

/**
 * Created by hassan on 8/31/2017.
 */


public interface Presenter {
    void onPause();
    void onResume();
    void onDestroy();
    void deleteItem(Item item, int position);
}
