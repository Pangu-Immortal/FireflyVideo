# FireflyVideo
🔥在ijkplayer的基础上借鉴大量播放器内核优势进行封装

**注🌈**：
1. 该项目仅供学习和参考，在真机上已经全面测试。
2. 对于自研轻量定制的 Android系统，对一些系统应用的编解码，这个方案还是很有优势的。资源占用少，用户无感知，成功率高。
3. 可用于C端产品，不建议在2B产品上使用。
4. 可作为学习音视频播放器框架的一个案例。

## 🔗实现原理参考🍎

- [FFmpeg源码 ffplay](http://ffmpeg.org/)
- [哔哩哔哩开源的 bilibili/ijkplayer](https://github.com/bilibili/ijkplayer)
- [ArtPlayer Kotlin实现的视频播放器，将MediaPlayer与VideoView解耦合，支持切换播放器内核（如ExoPlayer和ijkPlayer）](https://github.com/maiwenchang/ArtPlayer)
- [谷歌推出的 google/ExoPlayer](https://github.com/google/ExoPlayer)
- [VLC 是一款自由、开源的跨平台多媒体播放器及框架](https://www.videolan.org/vlc/index.zh_CN.html)
- [金山云Android播放SDK，支持RTMP HTTP-FLV HLS 协议](https://github.com/FirePrayer/KSYMediaPlayer_Android)
- [PLDroidPlayer 是七牛推出的一款免费的适用于 Android 平台的播放器 SDK](https://github.com/pili-engineering/PLDroidPlayer)
- [qplayer 七牛播放器SDK支持点播，直播，首开快，延迟低，接口简单，易于使用。](https://github.com/qiniu/qplayer-sdk)
- [业内为数不多致力于极致体验的超强全自研跨平台(windows/android/iOS)流媒体内核](https://github.com/daniulive/SmarterStreaming)
- [控制 Android MediaPlayer 类的项目。它可以更轻松地使用 MediaPlayer ListView 和 RecyclerView。](https://github.com/danylovolokh/VideoPlayerManager)
- [Android 纯rtmp播放器, 不依赖ffmpeg](https://github.com/qingkouwei/oarplayer)

## 在ijkplayer的基础上，结合AndroidVideoCache，封装的第三方播放器。
- [饺子播放器 -> 原名：节操播放器](https://github.com/Jzvd/JZVideo)
- [CarGuo/GSYVideoPlayer](https://github.com/CarGuo/GSYVideoPlayer)
- [边播边缓存 AndroidVideoCache](https://github.com/danikula/AndroidVideoCache)

## 许可(LICENSE)✏️

    Copyright 2021 @yugu88, FireflyVideo Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.