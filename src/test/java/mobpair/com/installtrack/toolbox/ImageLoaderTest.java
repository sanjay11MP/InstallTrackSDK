/*
 * Copyright (C) 2015 The Android Open Source Project
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

package mobpair.com.installtrack.toolbox;

import android.graphics.Bitmap;
import android.widget.ImageView;

import mobpair.com.installtrack.Request;
import mobpair.com.installtrack.RequestQueue;
import mobpair.com.installtrack.toolbox.ImageLoader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class ImageLoaderTest {
    private RequestQueue mRequestQueue;
    private mobpair.com.installtrack.toolbox.ImageLoader.ImageCache mImageCache;
    private mobpair.com.installtrack.toolbox.ImageLoader mImageLoader;

    @Before
    public void setUp() {
        mRequestQueue = mock(RequestQueue.class);
        mImageCache = mock(mobpair.com.installtrack.toolbox.ImageLoader.ImageCache.class);
        mImageLoader = new mobpair.com.installtrack.toolbox.ImageLoader(mRequestQueue, mImageCache);
    }

    @Test
    public void isCachedChecksCache() throws Exception {
        when(mImageCache.getBitmap(anyString())).thenReturn(null);
        Assert.assertFalse(mImageLoader.isCached("http://foo", 0, 0));
    }

    @Test
    public void getWithCacheHit() throws Exception {
        Bitmap bitmap = Bitmap.createBitmap(1, 1, null);
        mobpair.com.installtrack.toolbox.ImageLoader.ImageListener listener = mock(mobpair.com.installtrack.toolbox.ImageLoader.ImageListener.class);
        when(mImageCache.getBitmap(anyString())).thenReturn(bitmap);
        mobpair.com.installtrack.toolbox.ImageLoader.ImageContainer ic = mImageLoader.get("http://foo", listener);
        Assert.assertSame(bitmap, ic.getBitmap());
        verify(listener).onResponse(ic, true);
    }

    @Test
    public void getWithCacheMiss() throws Exception {
        when(mImageCache.getBitmap(anyString())).thenReturn(null);
        mobpair.com.installtrack.toolbox.ImageLoader.ImageListener listener = mock(mobpair.com.installtrack.toolbox.ImageLoader.ImageListener.class);
        // Ask for the image to be loaded.
        mImageLoader.get("http://foo", listener);
        // Second pass to test deduping logic.
        mImageLoader.get("http://foo", listener);
        // Response callback should be called both times.
        verify(listener, times(2)).onResponse(any(mobpair.com.installtrack.toolbox.ImageLoader.ImageContainer.class), eq(true));
        // But request should be enqueued only once.
        verify(mRequestQueue, times(1)).add(any(Request.class));
    }

    @Test
    public void publicMethods() throws Exception {
        // Catch API breaking changes.
        mobpair.com.installtrack.toolbox.ImageLoader.getImageListener(null, -1, -1);
        mImageLoader.setBatchedResponseDelay(1000);

        assertNotNull(mobpair.com.installtrack.toolbox.ImageLoader.class.getConstructor(RequestQueue.class,
                mobpair.com.installtrack.toolbox.ImageLoader.ImageCache.class));

        assertNotNull(mobpair.com.installtrack.toolbox.ImageLoader.class.getMethod("getImageListener", ImageView.class,
                int.class, int.class));
        assertNotNull(mobpair.com.installtrack.toolbox.ImageLoader.class.getMethod("isCached", String.class, int.class, int.class));
        assertNotNull(mobpair.com.installtrack.toolbox.ImageLoader.class.getMethod("isCached", String.class, int.class, int.class,
                ImageView.ScaleType.class));
        assertNotNull(mobpair.com.installtrack.toolbox.ImageLoader.class.getMethod("get", String.class,
                mobpair.com.installtrack.toolbox.ImageLoader.ImageListener.class));
        assertNotNull(mobpair.com.installtrack.toolbox.ImageLoader.class.getMethod("get", String.class,
                mobpair.com.installtrack.toolbox.ImageLoader.ImageListener.class, int.class, int.class));
        assertNotNull(mobpair.com.installtrack.toolbox.ImageLoader.class.getMethod("get", String.class,
                mobpair.com.installtrack.toolbox.ImageLoader.ImageListener.class, int.class, int.class, ImageView.ScaleType.class));
        assertNotNull(mobpair.com.installtrack.toolbox.ImageLoader.class.getMethod("setBatchedResponseDelay", int.class));

        assertNotNull(mobpair.com.installtrack.toolbox.ImageLoader.ImageListener.class.getMethod("onResponse",
                ImageLoader.ImageContainer.class, boolean.class));
    }
}

