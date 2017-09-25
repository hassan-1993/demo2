package project.demo2.mainpage.view;


import java.util.List;

import project.demo2.data.model.Item;


/**
 * Created by hassan on 8/31/2017.
 */

public interface MainView {

    interface UserClickListener {
        void onDeleteClicked(int position, Item item);
    }

    void showProgress();
    void hideProgress();
    void setItems(List<Item> list);
    void deleteItem(int position);

    void displayFailDeleteMsg();
    void displaySuccessDeleteMsg();
    void displayMsgError();
}