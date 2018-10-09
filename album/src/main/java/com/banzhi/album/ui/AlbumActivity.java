package com.banzhi.album.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banzhi.album.Album;
import com.banzhi.album.R;
import com.banzhi.album.bean.FileBean;
import com.banzhi.album.bean.FolderBean;
import com.banzhi.album.task.MediaReaderTask;
import com.banzhi.album.task.ThumbTask;
import com.banzhi.album.ui.adapter.AlbumAdapter;
import com.banzhi.album.ui.widget.AlbumDivider;
import com.banzhi.album.utils.ScreenUtils;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    int spanCount = 3;
    AlbumAdapter albumAdapter;
    MediaReaderTask mediaReaderTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        int dividerWidth = ScreenUtils.dp2px(this,4);
        int itemSize = (ScreenUtils.getScreenWidth(this) - dividerWidth * (spanCount + 1)) / spanCount;
        albumAdapter = new AlbumAdapter(this, false, itemSize);
        mRecyclerView.setAdapter(albumAdapter);
        mRecyclerView.addItemDecoration(new AlbumDivider(dividerWidth));
        mediaReaderTask = new MediaReaderTask(this, Album.MODE_AUDIOS, scanCallback);
        mediaReaderTask.execute();
    }

    MediaReaderTask.ScanCallback scanCallback = new MediaReaderTask.ScanCallback() {
        @Override
        public void onScanCallback(ArrayList<FolderBean> folderBeans) {
            ArrayList<FileBean> fileBeans = folderBeans.get(0).getFileBeans();

            ThumbTask thumbTask = new ThumbTask(AlbumActivity.this, new ThumbTask.ThumbCallback() {
                @Override
                public void onThumbCallback(ArrayList<FileBean> fileBeans) {
                    albumAdapter.notifyDataSetChanged(fileBeans);
                }
            });
            thumbTask.execute(fileBeans);
        }
    };
}
