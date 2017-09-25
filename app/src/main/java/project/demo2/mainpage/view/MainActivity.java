package project.demo2.mainpage.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import project.demo2.R;
import project.demo2.addpage.AddActivity;
import project.demo2.data.model.Item;
import project.demo2.mainpage.presenter.MainPresenter;
import project.demo2.mainpage.presenter.Presenter;

import static project.demo2.util.ToastUtil.showMessage;


public class MainActivity extends  AppCompatActivity implements MainView,MainView.UserClickListener {

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Adapter adapter;

    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Demo");

        //initialize recyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter=new Adapter(null,this);
        recyclerView.setAdapter(adapter);

        //initialize presenter
        setPresenter(new MainPresenter(this));
    }

    public void setPresenter(Presenter presenter){
        this.presenter=presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
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
    public void setItems(List<Item> list) {
      adapter.setItemList(list);
    }

    @Override
    public void deleteItem(int position) {
        if(adapter!=null){
            adapter.remove(position);
        }
    }



    @OnClick(R.id.add_Item)
    public void navigateToAddPage(View view){startActivity(new Intent(this,AddActivity.class));}

    @Override
    public void onDeleteClicked(int position, Item item) {
        presenter.deleteItem(item,position);
    }

    @Override
    public void displayFailDeleteMsg() {showMessage(R.string.error_delete_item);}

    @Override
    public void displaySuccessDeleteMsg() {showMessage(R.string.success_delete_item);}

    @Override
    public void displayMsgError() {showMessage(R.string.error_connection);}
}
