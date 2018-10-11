package com.banzhi.album.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.banzhi.album.R;
import com.banzhi.album.bean.FolderBean;
import com.banzhi.album.interfaces.OnItemClickListener;
import com.banzhi.album.ui.adapter.AlbumFolderAdapter;

import java.util.ArrayList;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/10/11.
 * @desciption :
 * @version :
 * </pre>
 */

public class AlbumFolderDialog extends BottomSheetDialog {
    AlbumFolderAdapter adapter;
    OnItemClickListener mOnItemClickListener;

    public AlbumFolderDialog(@NonNull Context context, ArrayList<FolderBean> folderBeans, OnItemClickListener onItemClickListener) {
        super(context);
        this.mOnItemClickListener = onItemClickListener;
        setContentView(R.layout.dialog_folder);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_folder);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new AlbumFolderAdapter(context, folderBeans);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                dismiss();
                mOnItemClickListener.onItemClick(v, position);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
