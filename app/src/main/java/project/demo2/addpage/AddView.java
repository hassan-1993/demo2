package project.demo2.addpage;

/**
 * Created by hassan on 9/24/2017.
 */

public interface AddView {

    void navigateToMainPage();
    void showProgress();
    void hideProgress();
    void displayErrorMessage();
    void displaySuccessMessage();
    void displayInvalidParameter();
    void openGallery();
    String getImage();

}
