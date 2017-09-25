package project.demo2.addpage;

import android.util.Log;

import project.demo2.data.DataManager;
import project.demo2.data.model.Item;


/**
 * Created by hassan on 9/24/2017.
 */

class AddPresenter implements Presenter {

    private DataManager dataManager;
    private AddView addView;


    AddPresenter(AddView view){
        this.dataManager=DataManager.getInstance();
        this.addView =view;
    }


    @Override
    public void chooseImage() {
        addView.openGallery();
    }

    @Override
    public void onDestroy() {
        addView =null;
        this.dataManager.onDestroy();
    }

    /**
     * adding item
     * @param title
     * @param description
     */
    @Override
    public void saveItem(final String title, final String description,Object image) {
        if(!isValidInput(title,description,image)){
            addView.displayInvalidParameter();
            return;
        }
        addView.showProgress();

        //start another thread to encode image before calling api to add new item
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                String bitmapBytes= addView.getImage();
                dataManager.saveItem(new DataManager.Actions<Item>() {
                    @Override
                    public void onFinish(Item result) {
                        Log.d("AddPrseneter"," onFinish() saveItem and view is " + addView);
                        addView.hideProgress();
                        addView.displaySuccessMessage();
                        addView.navigateToMainPage();
                    }

                    @Override
                    public void onError() {
                        Log.d("AddPresenter"," onError() saveItem and view is " + addView);
                        if(addView!=null) {
                            addView.hideProgress();
                            addView.displayErrorMessage();
                        }
                    }
                },title,description,bitmapBytes);
            }
        });

        thread.start();


    }


    /**
     * check if title, description ,image valid parameter before adding item
     * @param title
     * @param description
     * @param image
     * @return
     */
    private boolean isValidInput(String title, String description, Object image) {
        boolean isValid= !title.trim().isEmpty() && !description.trim().isEmpty() && image!=null;

        if(!isValid){
            addView.displayErrorMessage();
        }
        return isValid;
    }
}
