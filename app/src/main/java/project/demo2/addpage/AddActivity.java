package project.demo2.addpage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import project.demo2.R;

import static project.demo2.util.ToastUtil.showMessage;


public class AddActivity extends AppCompatActivity  implements AddView{

    static final int REQUEST_NEW_ITEM = 1;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.add_item_title)
    EditText title;

    @BindView(R.id.add_item_description)
    EditText description;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.image)
    ImageView image;

    Bitmap bitmap;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);

        //setting toolbar as default action bar
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Demo");

        presenter=new AddPresenter(this);

    }

    @OnClick(R.id.add_item_image)
    public void chooseImage(View view){
        presenter.chooseImage();
    }

    @OnClick(R.id.add_Item)
    public void addItem(View view) {
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();

        presenter.saveItem(title,description,this.bitmap);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //called when user finsih selecting image from gallery
        if (requestCode == REQUEST_NEW_ITEM && resultCode == RESULT_OK) {
            try {
                this.bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                this.image.setImageBitmap(this.bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * converting bitmap into base64
     * @param bitmap
     * @return
     */
    private String convertToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }

    @Override
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_NEW_ITEM);
    }

    @Override
    public void navigateToMainPage() {
        finish();
    }

    @Override
    public void showProgress() {
        //disable touch
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        //enable touch
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public String getImage() {
        if(this.bitmap!=null){
            return convertToBase64(this.bitmap);
        }

        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void displayErrorMessage() {showMessage(R.string.error_add_item);}

    @Override
    public void displaySuccessMessage() {showMessage(R.string.success_add_item);}

    @Override
    public void displayInvalidParameter() {showMessage(R.string.error_invalid_parameter);}

}