package com.michalkarmelita.hashtagtracker.view.tweets.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.michalkarmelita.hashtagtracker.R;
import com.michalkarmelita.hashtagtracker.dagger.daggerqualifiers.ForActivity;
import com.michalkarmelita.hashtagtracker.model.adaptermodels.TweetAdapterItem;
import com.michalkarmelita.hashtagtracker.util.RoundedTransformation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnLoadMoreListener {

        void onLoadMore();
    }
    private final int VIEW_TYPE_TWEET = 0;

    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;

    private final Context context;

    private final LayoutInflater inflater;
    private List<TweetAdapterItem> items = new ArrayList<>();

    private boolean isLoading;
    private boolean loadComplete;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    @Inject
    public TweetsAdapter(@ForActivity Context context,
                         LayoutInflater inflater) {
        this.context = context;
        this.inflater = inflater;
        this.isLoading = false;
        this.loadComplete = false;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_TWEET;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TWEET: {
                return new TweetViewHolder(inflater.inflate(R.layout.tweets_row, parent, false), context);
            }
            case VIEW_TYPE_LOADING: {
                return new LoadingViewHolder(inflater.inflate(R.layout.loadmore_row, parent, false));
            }
            default: {
                throw new RuntimeException("Unsupported type");
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TweetViewHolder){
            ((TweetViewHolder) holder).bind(items.get(position));
        } else {
            ((LoadingViewHolder) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void allItemsLoaded() {
        loadComplete = true;
        isLoading = false;
        this.items.remove(null);
        notifyItemRemoved(items.size() - 1);
    }

    public void clearItems() {
        this.items.clear();
        loadComplete = false;
        notifyDataSetChanged();
    }

    public void addItems(List<TweetAdapterItem> items) {
        this.items.remove(null);
        this.items.addAll(items);
        isLoading = false;
        notifyItemRangeChanged(items.size() - 1, items.size());
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void setupLoadMore(RecyclerView recyclerView) {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold) && !loadComplete) {
                    if (mOnLoadMoreListener != null && items.size() > 0) {
                        mOnLoadMoreListener.onLoadMore();
                        items.add(null);
                        notifyItemInserted(items.size() - 1);
                    }
                    isLoading = true;
                }
            }
        });
    }

    public static class TweetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_avatar)
        ImageView userAvatar;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.tweet_text)
        TextView tweetText;

        private final Context context;

        public TweetViewHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;
        }

        public void bind(TweetAdapterItem tweetAdapterItem) {
            Glide.with(context)
                    .load(tweetAdapterItem.getProfileImageUrl())
                    .bitmapTransform(new RoundedTransformation(context))
                    .into(userAvatar);
            userName.setText(tweetAdapterItem.getName());
            tweetText.setText(tweetAdapterItem.getText());
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind() {
            progressBar.setIndeterminate(true);
        }
    }
}
