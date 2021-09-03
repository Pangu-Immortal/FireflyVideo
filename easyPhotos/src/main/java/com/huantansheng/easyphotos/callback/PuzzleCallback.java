package com.huantansheng.easyphotos.callback;

import com.huantansheng.easyphotos.models.album.entity.Photo;

/**
 * PuzzleCallback
 *
 * @author joker
 * @date 2019/4/9.
 */
public abstract class PuzzleCallback {
    /**
     * 选择结果回调
     *
     * @param photo 返回对象：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
     */
    public abstract void onResult(Photo photo);
    /**
     * 什么都没选，取消选择回调
     */
    public abstract void onCancel();
}
