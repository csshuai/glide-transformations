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

import com.bumptech.glide.util.Util;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;

/**
 * Applies a Pixelation effect to the image.
 *
 * The pixel with a default of 10.0.
 */
public class PixelationFilterTransformation extends GPUFilterTransformation {
  private static final String ID = "jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation";
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private float pixel;

  public PixelationFilterTransformation() {
    this(10f);
  }

  public PixelationFilterTransformation(float pixel) {
    super(new GPUImagePixelationFilter());
    this.pixel = pixel;
    GPUImagePixelationFilter filter = getFilter();
    filter.setPixel(this.pixel);
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PixelationFilterTransformation that = (PixelationFilterTransformation) o;

    return Float.compare(that.pixel, pixel) == 0;
  }

  @Override public int hashCode() {
    return Util.hashCode(ID.hashCode(), Util.hashCode(pixel));
  }

  @Override public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);

    byte[] data = ByteBuffer.allocate(4).putFloat(pixel).array();
    messageDigest.update(data);
  }
}
