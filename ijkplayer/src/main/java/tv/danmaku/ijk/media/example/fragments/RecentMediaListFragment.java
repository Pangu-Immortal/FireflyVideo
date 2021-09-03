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

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import tv.danmaku.ijk.media.example.R;
import tv.danmaku.ijk.media.example.activities.VideoActivity;
import tv.danmaku.ijk.media.example.content.RecentMediaStorage;

/**
 * @author qihao
 */
public class RecentMediaListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = RecentMediaListFragment.class.getName();
    private ListView mFileListView;
    private RecentMediaAdapter mAdapter;

    public static RecentMediaListFragment newInstance() {
        return new RecentMediaListFragment();
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

        final Activity activity = getActivity();

        mAdapter = new RecentMediaAdapter(activity);
        mFileListView.setAdapter(mAdapter);
        mFileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                String url = mAdapter.getUrl(position);
                String name = mAdapter.getName(position);
                VideoActivity.intentTo(activity, url, name);
            }
        });
        LoaderManager.getInstance(this).initLoader(2, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new RecentMediaStorage.CursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static final class RecentMediaAdapter extends SimpleCursorAdapter {
        private int mIndex_id = -1;
        private int mIndex_url = -1;
        private int mIndex_name = -1;

        public RecentMediaAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_2, null,
                    new String[]{RecentMediaStorage.Entry.COLUMN_NAME_NAME, RecentMediaStorage.Entry.COLUMN_NAME_URL},
                    new int[]{android.R.id.text1, android.R.id.text2}, 0);
        }

        @Override
        public Cursor swapCursor(Cursor c) {
            Cursor res = super.swapCursor(c);

            mIndex_id = c.getColumnIndex(RecentMediaStorage.Entry.COLUMN_NAME_ID);
            mIndex_url = c.getColumnIndex(RecentMediaStorage.Entry.COLUMN_NAME_URL);
            mIndex_name = c.getColumnIndex(RecentMediaStorage.Entry.COLUMN_NAME_NAME);

            return res;
        }

        @Override
        public long getItemId(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null) {
                return 0;
            }

            return cursor.getLong(mIndex_id);
        }

        Cursor moveToPosition(int position) {
            final Cursor cursor = getCursor();
            if (cursor.getCount() == 0 || position >= cursor.getCount()) {
                return null;
            }
            cursor.moveToPosition(position);
            return cursor;
        }

        public String getUrl(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null) {
                return "";
            }

            return cursor.getString(mIndex_url);
        }

        public String getName(int position) {
            final Cursor cursor = moveToPosition(position);
            if (cursor == null) {
                return "";
            }

            return cursor.getString(mIndex_name);
        }
    }
}
