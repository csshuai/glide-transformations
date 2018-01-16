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
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;

/**
 * The threshold at which to apply the edges, default of 0.2.
 * The levels of quantization for the posterization of colors within the scene,
 * with a default of 10.0.
 */
public class ToonFilterTransformation extends GPUFilterTransformation {
  private static final String ID = "jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation";
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private float threshold;
  private float quantizationLevels;

  public ToonFilterTransformation() {
    this(.2f, 10.0f);
  }

  public ToonFilterTransformation(float threshold, float quantizationLevels) {
    super(new GPUImageToonFilter());
    this.threshold = threshold;
    this.quantizationLevels = quantizationLevels;
    GPUImageToonFilter filter = getFilter();
    filter.setThreshold(this.threshold);
    filter.setQuantizationLevels(this.quantizationLevels);
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ToonFilterTransformation that = (ToonFilterTransformation) o;

    if (Float.compare(that.threshold, threshold) != 0) return false;
    return Float.compare(that.quantizationLevels, quantizationLevels) == 0;
  }

  @Override public int hashCode() {
    int result = (threshold != +0.0f ? Float.floatToIntBits(threshold) : 0);
    result =
        31 * result + (quantizationLevels != +0.0f ? Float.floatToIntBits(quantizationLevels) : 0);
    return Util.hashCode(ID.hashCode(), Util.hashCode(result));
  }

  @Override public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);

    byte[] data = ByteBuffer.allocate(4).putFloat(threshold).array();
    messageDigest.update(data);
    data = ByteBuffer.allocate(4).putFloat(quantizationLevels).array();
    messageDigest.update(data);
  }
}
