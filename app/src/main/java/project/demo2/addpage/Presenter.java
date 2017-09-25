package project.demo2.addpage;

/**
 * Created by hassan on 9/24/2017.
 */

public interface Presenter {


    void chooseImage();
    void onDestroy();
    void saveItem(String title, String description, Object image);
}
