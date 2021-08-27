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

package tv.danmaku.ijk.media.example.activities;

import static com.google.android.exoplayer2.mediacodec.MediaCodecInfo.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;

import com.squareup.otto.Subscribe;

import java.io.File;
import java.io.IOException;

import tv.danmaku.ijk.media.example.R;
import tv.danmaku.ijk.media.example.application.AppActivity;
import tv.danmaku.ijk.media.example.application.Settings;
import tv.danmaku.ijk.media.example.eventbus.FileExplorerEvents;
import tv.danmaku.ijk.media.example.fragments.FileListFragment;

/**
 * @author qihao
 */
public class FileExplorerActivity extends AppActivity {
    private Settings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FileOperator.INSTANCE.init(getApplication(), BuildConfig.DEBUG);
        if (mSettings == null) {
            mSettings = new Settings(this);
        }

        String lastDirectory = mSettings.getLastDirectory();
        if (lastDirectory.length() > 5 && !TextUtils.isEmpty(lastDirectory) && new File(lastDirectory).isDirectory()) {
            doOpenDirectory(lastDirectory, false);
        } else {
            doOpenDirectory("http://vcdnb.huoying666.com/new_video/2020/0401/10586387113e7146f976e41f72878ad5/v1080/1058638711_5166298_fhd.mp4", false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        FileExplorerEvents.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        FileExplorerEvents.getBus().unregister(this);
    }

    private void doOpenDirectory(String path, boolean addToBackStack) {
        Fragment newFragment = FileListFragment.newInstance(path);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.body, newFragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Subscribe
    public void onClickFile(FileExplorerEvents.OnClickFile event) {
        File f = event.mFile;
        try {
            f = f.getAbsoluteFile();
            f = f.getCanonicalFile();
            Log.d(TAG, "onClickFile: f=" + f.toString());
            if (TextUtils.isEmpty(f.toString())) {
                f = new File("/");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (f.isDirectory()) {
            String path = f.toString();
            mSettings.setLastDirectory(path);
            doOpenDirectory(path, true);
        } else if (f.exists()) {
            VideoActivity.intentTo(this, f.getPath(), f.getName());
        }
    }
}
