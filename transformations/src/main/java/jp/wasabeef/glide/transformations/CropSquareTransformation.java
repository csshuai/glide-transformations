package jp.wasabeef.glide.transformations;

/**
 * Copyright (C) 2017 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.util.Util;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class CropSquareTransformation extends BitmapTransformation {
  private static final String ID = "jp.wasabeef.glide.transformations.CropSquareTransformation";
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private int size;

  @Override protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
      @NonNull Bitmap toTransform, int outWidth, int outHeight) {
    this.size = Math.max(outWidth, outHeight);
    return TransformationUtils.centerCrop(pool, toTransform, size, size);
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CropSquareTransformation that = (CropSquareTransformation) o;

    return size == that.size;
  }

  @Override public int hashCode() {
    return Util.hashCode(ID.hashCode(), Util.hashCode(size));
  }

  @Override public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);

    byte[] data = ByteBuffer.allocate(4).putInt(size).array();
    messageDigest.update(data);
  }
}
