/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tv.danmaku.ijk.media.example.fragments;

import static com.google.android.exoplayer2.mediacodec.MediaCodecInfo.TAG;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

import tv.danmaku.ijk.media.example.R;
import tv.danmaku.ijk.media.example.activities.VideoActivity;
import tv.danmaku.ijk.media.example.content.PathCursor;
import tv.danmaku.ijk.media.example.content.PathCursorLoader;
import tv.danmaku.ijk.media.example.eventbus.FileExplorerEvents;

/**
 * @author qihao
 */
public class FileListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_PATH = "path";

    private ListView mFileListView;
    private VideoAdapter mAdapter;
    private String mPath;

    public static FileListFragment newInstance(String path) {
        FileListFragment f = new FileListFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString(ARG_PATH, path);
        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_file_list, container, false);
        mFileListView = (ListView) viewGroup.findViewById(R.id.file_list_view);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();

        Bundle bundle = getArguments();
        if (bundle != null) {
            mPath = bundle.getString(ARG_PATH);
            Log.d(TAG, "onViewCreated: mPath=" + mPath);
        }

        mAdapter = new VideoAdapter(activity);
        mFileListView.setAdapter(mAdapter);
        mFileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                String path = mAdapter.getFilePath(position);
                if (TextUtils.isEmpty(path)) {
                    return;
                }
                FileExplorerEvents.getBus().post(new FileExplorerEvents.OnClickFile(path));
            }
        });

        LoaderManager.getInstance(this).initLoader(1, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (TextUtils.isEmpty(mPath)) {
            return null;
        }
        return new PathCursorLoader(getActivity(), mPath);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    static final class VideoAdapter extends SimpleCursorAdapter {
        static final class ViewHolder {
            public ImageView iconImageView;
            public TextView nameTextView;
        }

        public VideoAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_2, null,
                    new String[]{PathCursor.CN_FILE_NAME, PathCursor.CN_FILE_PATH},
                    new int[]{android.R.id.text1, android.R.id.text2}, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(R.layout.fragment_file_list_item, parent, false);
            }

            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
                viewHolder.iconImageView = (ImageView) view.findViewById(R.id.icon);
                viewHolder.nameTextView = (TextView) view.findViewById(R.id.name);
            }

            if (isDirectory(position)) {
                viewHolder.iconImageView.setImageResource(R.drawable.ic_theme_folder);
            } else if (isVideo(position)) {
                viewHolder.iconImageView.setImageResource(R.drawable.ic_theme_play_arrow);
            } else {
                viewHolder.iconImageView.setImageResource(R.drawable.ic_theme_description);
            }
            viewHolder.nameTextView.setText(getFileName(position));

            return view;
        }

        @Override
        public long getItemId(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null) {
                return 0;
            }

            return cursor.getLong(PathCursor.CI_ID);
        }

        Cursor moveToPosition(int position) {
            final Cursor cursor = getCursor();
            if (cursor.getCount() == 0 || position >= cursor.getCount()) {
                return null;
            }
            cursor.moveToPosition(position);
            return cursor;
        }

        public boolean isDirectory(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null) {
                return true;
            }

            return cursor.getInt(PathCursor.CI_IS_DIRECTORY) != 0;
        }

        public boolean isVideo(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null) {
                return true;
            }

            return cursor.getInt(PathCursor.CI_IS_VIDEO) != 0;
        }

        public String getFileName(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null) {
                return "";
            }

            return cursor.getString(PathCursor.CI_FILE_NAME);
        }

        public String getFilePath(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null) {
                return "";
            }

            return cursor.getString(PathCursor.CI_FILE_PATH);
        }
    }
}
