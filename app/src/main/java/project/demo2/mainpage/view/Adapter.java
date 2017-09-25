package project.demo2.mainpage.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.demo2.R;
import project.demo2.data.model.Item;
import project.demo2.util.UnitConverter;

/**
 * Created by hassan on 8/31/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    //just as an example
    private int dpSize= (int) UnitConverter.dpToPx(130);

    List<Item> itemList;
    MainView.UserClickListener clickListener;

    public Adapter(List<Item> newsList, MainView.UserClickListener clickListener){
        this.itemList=newsList;
        this.clickListener=clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //build view from xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Item item=itemList.get(position);

        holder.title.setText(item.getTitle());
        holder.deleteButton.setImageResource(R.drawable.ic_delete_black_24dp);
        holder.description.setText(item.getDescription());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              clickListener.onDeleteClicked(holder.getLayoutPosition(),itemList.get(holder.getLayoutPosition()));
            }
        });

        //load image from url using picasso library
        Picasso.with(holder.imageView.getContext())
                .load(itemList.get(position).getImageUrl())
        .placeholder(R.drawable.placeholder).resize(dpSize,dpSize).centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {return itemList==null?0:itemList.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.itemImage)
        public ImageView imageView;
        @BindView(R.id.title)
        public TextView title;
        @BindView(R.id.delete_item)
        public ImageButton deleteButton;
        @BindView(R.id.description)
        public TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public void remove(int position){
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,itemList.size());
    }


    public void setItemList(List<Item> itemList){
        this.itemList=itemList;
        notifyDataSetChanged();
    }
}
