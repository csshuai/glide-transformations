package jp.wasabeef.glide.transformations.gpu;

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

import android.graphics.PointF;
import com.bumptech.glide.util.Util;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;

/**
 * Performs a vignetting effect, fading out the image at the edges
 * The directional intensity of the vignetting,
 * with a default of x = 0.5, y = 0.5, start = 0, end = 0.75
 */
public class VignetteFilterTransformation extends GPUFilterTransformation {
  private static final String ID = "jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation";
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private PointF center;
  private float[] vignetteColor;
  private float vignetteStart;
  private float vignetteEnd;

  public VignetteFilterTransformation() {
    this(new PointF(0.5f, 0.5f), new float[] { 0.0f, 0.0f, 0.0f }, 0.0f, 0.75f);
  }

  public VignetteFilterTransformation(PointF center, float[] color, float start, float end) {
    super(new GPUImageVignetteFilter());
    this.center = center;
    vignetteColor = color;
    vignetteStart = start;
    vignetteEnd = end;
    GPUImageVignetteFilter filter = getFilter();
    filter.setVignetteCenter(this.center);
    filter.setVignetteColor(vignetteColor);
    filter.setVignetteStart(vignetteStart);
    filter.setVignetteEnd(vignetteEnd);
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    VignetteFilterTransformation that = (VignetteFilterTransformation) o;

    if (Float.compare(that.vignetteStart, vignetteStart) != 0) return false;
    if (Float.compare(that.vignetteEnd, vignetteEnd) != 0) return false;
    if (center != null ? !center.equals(that.center) : that.center != null) return false;
    return Arrays.equals(vignetteColor, that.vignetteColor);
  }

  @Override public int hashCode() {
    int result = center != null ? center.hashCode() : 0;
    result = 31 * result + Arrays.hashCode(vignetteColor);
    result = 31 * result + (vignetteStart != +0.0f ? Float.floatToIntBits(vignetteStart) : 0);
    result = 31 * result + (vignetteEnd != +0.0f ? Float.floatToIntBits(vignetteEnd) : 0);
    return Util.hashCode(ID.hashCode(), Util.hashCode(result));
  }

  @Override public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);

    byte[] data = ByteBuffer.allocate(4).putFloat(center.x).array();
    messageDigest.update(data);
    data = ByteBuffer.allocate(4).putFloat(center.y).array();
    messageDigest.update(data);
    for (float v : vignetteColor) {
      data = ByteBuffer.allocate(4).putFloat(v).array();
      messageDigest.update(data);
    }
    data = ByteBuffer.allocate(4).putFloat(vignetteStart).array();
    messageDigest.update(data);
    data = ByteBuffer.allocate(4).putFloat(vignetteEnd).array();
    messageDigest.update(data);
  }
}
