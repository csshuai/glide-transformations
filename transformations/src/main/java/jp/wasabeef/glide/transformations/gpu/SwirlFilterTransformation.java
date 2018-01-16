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
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;

/**
 * Creates a swirl distortion on the image.
 */
public class SwirlFilterTransformation extends GPUFilterTransformation {
  private static final String ID = "jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation";
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private float radius;
  private float angle;
  private PointF center;

  public SwirlFilterTransformation() {
    this(.5f, 1.0f, new PointF(0.5f, 0.5f));
  }

  /**
   * @param radius from 0.0 to 1.0, default 0.5
   * @param angle minimum 0.0, default 1.0
   * @param center default (0.5, 0.5)
   */
  public SwirlFilterTransformation(float radius, float angle, PointF center) {
    super(new GPUImageSwirlFilter());
    this.radius = radius;
    this.angle = angle;
    this.center = center;
    GPUImageSwirlFilter filter = getFilter();
    filter.setRadius(this.radius);
    filter.setAngle(this.angle);
    filter.setCenter(this.center);
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SwirlFilterTransformation that = (SwirlFilterTransformation) o;

    if (Float.compare(that.radius, radius) != 0) return false;
    if (Float.compare(that.angle, angle) != 0) return false;
    return center != null ? center.equals(that.center) : that.center == null;
  }

  @Override public int hashCode() {
    int result = (radius != +0.0f ? Float.floatToIntBits(radius) : 0);
    result = 31 * result + (angle != +0.0f ? Float.floatToIntBits(angle) : 0);
    result = 31 * result + (center != null ? center.hashCode() : 0);
    return Util.hashCode(ID.hashCode(), Util.hashCode(result));
  }

  @Override public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);

    byte[] data = ByteBuffer.allocate(4).putFloat(radius).array();
    messageDigest.update(data);
    data = ByteBuffer.allocate(4).putFloat(angle).array();
    messageDigest.update(data);
    data = ByteBuffer.allocate(4).putFloat(center.x).array();
    messageDigest.update(data);
    data = ByteBuffer.allocate(4).putFloat(center.y).array();
    messageDigest.update(data);
  }
}
