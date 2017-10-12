package com.app.gofoodie.adapter.recyclerviewadapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.app.gofoodie.R;
import com.app.gofoodie.customview.RoundedImageView;
import com.app.gofoodie.model.featured.FeaturedCombo;
import com.app.gofoodie.utility.DisplayUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * @class FeaturedCombosRecyclerAdapter
 * @desc {@link RecyclerView.Adapter} adapter class for shortlisted restaurants - recycler view on home dashboard fragment.
 */
public class FeaturedCombosRecyclerAdapter extends RecyclerView.Adapter<FeaturedCombosRecyclerAdapter.ItemHolder> {

    /**
     * @class ItemHolder
     * @desc {@link RecyclerView.ViewHolder} holder static class for Recycler View items.
     */
    public static class ItemHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView txtRestaurantName;
        public RoundedImageView imgRestaurant;

        ItemHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtRestaurantName = (TextView) itemView.findViewById(R.id.name);
            imgRestaurant = (RoundedImageView) itemView.findViewById(R.id.image);
        }
    }

    /**
     * private class Data members.
     */
    private List<FeaturedCombo> mList = null;
    private Activity mActivity = null;
    private int lastPosition;
    private View.OnClickListener onClickListener = null;
    private int mLayoutResourceId = -1;

    /**
     * @constructor CategoryRecyclerAdapter
     * @desc Constructor method for this class.
     */
    public FeaturedCombosRecyclerAdapter(Activity activity, int layoutResourceId, View.OnClickListener onClickListener, List<FeaturedCombo> categories) {
        this.mActivity = activity;
        this.mList = categories;
        this.onClickListener = onClickListener;
        this.mLayoutResourceId = layoutResourceId;
    }

    /**
     * {@link RecyclerView.Adapter} adapter class override methods.
     */
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(this.mLayoutResourceId, parent, false);
        ItemHolder itemHolder = new ItemHolder(itemView);

        // Category images size fixing depending on screen resolution.
        itemHolder.imgRestaurant.getLayoutParams().height = DisplayUtils.getInstance().getResolution(mActivity).x / 5;
        itemHolder.imgRestaurant.getLayoutParams().width = DisplayUtils.getInstance().getResolution(mActivity).x / 5;

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {

        FeaturedCombo data = mList.get(position);

        // Load image into imageView. (LazyLoading).
        try {
            Picasso.with(mActivity).load(data.image).into(holder.imgRestaurant);
            holder.txtRestaurantName.setText(data.comboName);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        holder.cv.setTag(category.getId());
//        holder.cv.setOnClickListener(onClickListener);
        setAnimation(holder.imgRestaurant, position);
        setAnimation(holder.txtRestaurantName, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(this.mActivity, R.anim.anim_rv_item_show);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}