package com.banzhi.album.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.banzhi.album.Album;
import com.banzhi.album.R;
import com.banzhi.album.bean.FileBean;
import com.banzhi.album.bean.FolderBean;
import com.banzhi.album.interfaces.OnItemClickListener;
import com.banzhi.album.task.MediaReaderTask;
import com.banzhi.album.task.ThumbTask;
import com.banzhi.album.ui.adapter.AlbumAdapter;
import com.banzhi.album.ui.dialog.AlbumFolderDialog;
import com.banzhi.album.ui.widget.AlbumDivider;
import com.banzhi.album.utils.ScreenUtils;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    Button folderToggleBtn;
    Button previewBtn;
    int spanCount = 3;
    AlbumAdapter albumAdapter;
    MediaReaderTask mediaReaderTask;
    ArrayList<FolderBean> folderBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initView();
        int dividerWidth = ScreenUtils.dp2px(this, 4);
        int itemSize = (ScreenUtils.getScreenWidth(this) - dividerWidth * (spanCount + 1)) / spanCount;
        albumAdapter = new AlbumAdapter(this, false, itemSize);
        mRecyclerView.setAdapter(albumAdapter);
        mRecyclerView.addItemDecoration(new AlbumDivider(dividerWidth));
        mediaReaderTask = new MediaReaderTask(this, Album.MODE_IMAGES_VIDEOS, scanCallback);
        mediaReaderTask.execute();
    }

    private void initView() {
        folderToggleBtn = findViewById(R.id.btn_toggle);
        previewBtn = findViewById(R.id.btn_preview);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initListener();
    }

    private void initListener() {
        folderToggleBtn.setOnClickListener(this);
        previewBtn.setOnClickListener(this);
    }

    MediaReaderTask.ScanCallback scanCallback = new MediaReaderTask.ScanCallback() {
        @Override
        public void onScanCallback(ArrayList<FolderBean> folders) {
            folderBeans = folders;
            FolderBean folderBean = folderBeans.get(0);
            ArrayList<FileBean> fileBeans = folderBean.getFileBeans();
            folderToggleBtn.setText(folderBean.getName());
            ThumbTask thumbTask = new ThumbTask(AlbumActivity.this, new ThumbTask.ThumbCallback() {
                @Override
                public void onThumbCallback(ArrayList<FileBean> fileBeans) {
                    albumAdapter.notifyDataSetChanged(fileBeans);
                }
            });
            thumbTask.execute(fileBeans);
        }
    };
    AlbumFolderDialog dialog;

    @Override
    public void onClick(View v) {
        if (v == folderToggleBtn) {
            if (dialog == null) {
                dialog = new AlbumFolderDialog(this, folderBeans, new OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        updateAlbumFile(position);
                    }
                });
            }
            dialog.show();
        } else if (v == previewBtn) {

        }
    }

    private void updateAlbumFile(int position) {
        FolderBean folderBean = folderBeans.get(position);
        folderToggleBtn.setText(folderBean.getName());
        albumAdapter.notifyDataSetChanged(folderBean.getFileBeans());
    }
}
