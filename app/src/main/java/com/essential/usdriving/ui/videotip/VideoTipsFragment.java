package com.essential.usdriving.ui.videotip;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.usdriving.R;
import com.essential.usdriving.app.BaseFragment;

import java.util.ArrayList;

/**
 * Created by the_e_000 on 8/18/2015.
 */
public class VideoTipsFragment extends BaseFragment {

    private ArrayList<VideoTipsListItem> videos;
    private VideoTipsListAdapter adapter;
    private RecyclerView videoTipsList;

    @Override
    protected boolean enableBackButton() {
        return true;
    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_video_tips;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {


        videoTipsList = (RecyclerView) rootView.findViewById(R.id.videoTipsList);

        if (videos == null) {
            videos = new ArrayList<>();
            getVideos();
        }

        adapter = new VideoTipsListAdapter(getActivity(), videos);
        videoTipsList.setAdapter(adapter);
        videoTipsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        videoTipsList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    View view = rv.findChildViewUnder(e.getX(), e.getY());
                    int position = rv.getChildLayoutPosition(view);
                    VideoTipsListItem item = videos.get(position);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.link));
                    startActivity(browserIntent);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }
    @Override
    protected boolean enableToolbar() {
        return true;
    }

    @Override
    protected String setTitle() {
        return getString(R.string.title_video_tips);
    }

    private void getVideos() {
        videos.add(new VideoTipsListItem(getString(R.string.dmv_driving_test_tips), ContextCompat.getDrawable(getActivity(), R.drawable.dmv_driving_test_tips), "https://www.youtube.com/watch?v=5M-Kcq2U6CI"));
        videos.add(new VideoTipsListItem(getString(R.string.ten_tips), ContextCompat.getDrawable(getActivity(), R.drawable.ten_tips), "https://www.youtube.com/watch?v=7ClH977D0Yg"));
        videos.add(new VideoTipsListItem(getString(R.string.confession), ContextCompat.getDrawable(getActivity(), R.drawable.confession), "https://www.youtube.com/watch?v=RhBMk-na5Q0"));
        videos.add(new VideoTipsListItem(getString(R.string.ten_reasons_for_failing_driving_test), ContextCompat.getDrawable(getActivity(), R.drawable.ten_reasons), "https://www.youtube.com/watch?list=PL297E87DA9A1025B2&v=xkf_NfEwZls"));
        videos.add(new VideoTipsListItem(getString(R.string.rules_of_the_road), ContextCompat.getDrawable(getActivity(), R.drawable.rules_of_the_road), "https://www.youtube.com/watch?list=PL2F4E872DBDFF6AFA&v=E9H0yON1Xf0"));
        videos.add(new VideoTipsListItem(getString(R.string.california_driver_license_info), ContextCompat.getDrawable(getActivity(), R.drawable.california_driver_license), "https://www.youtube.com/watch?list=PL5308680B569DFA20&v=TcFGlHUbKgM"));
    }

    private static class VideoTipsListItem {
        public String sectionTitle;
        public Drawable image;
        public String link;

        public VideoTipsListItem(String sectionTitle, Drawable image, String link) {
            this.sectionTitle = sectionTitle;
            this.image = image;
            this.link = link;
        }
    }

    private static class VideoTipsListAdapter extends RecyclerView.Adapter<VideoTipsListAdapter.ViewHolder> {
        private LayoutInflater inflater;
        private ArrayList<VideoTipsListItem> items;

        public VideoTipsListAdapter(Context context, ArrayList<VideoTipsListItem> items) {
            inflater = LayoutInflater.from(context);
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_video_tips, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            VideoTipsListItem item = items.get(position);
            holder.setText(item.sectionTitle);
            holder.setImage(item.image);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView imgVideoTipsItem;
            private TextView txtVideoTipsItem;
            private LinearLayout layout;

            public ViewHolder(View itemView) {
                super(itemView);
                imgVideoTipsItem = (ImageView) itemView.findViewById(R.id.imgVideoTipsItem);
                txtVideoTipsItem = (TextView) itemView.findViewById(R.id.txtVideoTipsItem);

            }

            public void setText(String text) {
                txtVideoTipsItem.setText(text);
            }

            public void setImage(Drawable image) {
                imgVideoTipsItem.setImageDrawable(image);
            }
        }
    }
}
