package com.wht.rishiherherbocare.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wht.rishiherherbocare.Helper.Helper_Method;
import com.wht.rishiherherbocare.Object.TipsObject;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.inside_user_pages.TipsDetailsActivity;
import com.wht.rishiherherbocare.my_library.Constants;

import java.util.ArrayList;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.SingleItemRowHolder> {

    private ArrayList<TipsObject> itemsList;
    private Context mContext;
    private TipsObject singleItem;
    View v;

    public TipsAdapter(Context context, ArrayList<TipsObject> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tips_adapter_row_item, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, final int i) {

        singleItem = new TipsObject();
        singleItem = itemsList.get(i);

        holder.tvBlogTitle.setText(Helper_Method.toTitleCase(singleItem.tips));
        holder.tvShotDesc.setText(singleItem.description);

        DisplayMetrics metricscard = mContext.getResources().getDisplayMetrics();
        int cardwidth = metricscard.widthPixels;
        int cardheight = metricscard.heightPixels;
       // holder.ivImgCard.getLayoutParams().width = (int) (cardwidth / 1.5);
       // holder.ivImgCard.getLayoutParams().height = (int) (cardheight / 3);

        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TipsDetailsActivity.class);
                intent.putExtra(Constants.Position, i);
                intent.putParcelableArrayListExtra(Constants.TipsList, itemsList);
                mContext.startActivity(intent);
            }
        });

        holder.tvBlogTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TipsDetailsActivity.class);
                intent.putExtra(Constants.Position, i);
                intent.putParcelableArrayListExtra(Constants.TipsList, itemsList);
                mContext.startActivity(intent);
            }
        });

        holder.tvShotDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TipsDetailsActivity.class);
                intent.putExtra(Constants.Position, i);
                intent.putParcelableArrayListExtra(Constants.TipsList, itemsList);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvBlogTitle;
        protected TextView tvShotDesc;
        protected CardView ivImgCard;
        private LinearLayout ll_main;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvBlogTitle = view.findViewById(R.id.tvBlogTitle);
            this.tvShotDesc = view.findViewById(R.id.tvShotDesc);
            this.ivImgCard = view.findViewById(R.id.ivImgCard);
            this.ll_main = view.findViewById(R.id.ll_main);

        }

    }
}