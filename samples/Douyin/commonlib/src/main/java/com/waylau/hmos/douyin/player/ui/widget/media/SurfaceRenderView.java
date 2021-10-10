package com.waylau.hmos.douyin.player.ui.widget.media;

import com.waylau.hmos.douyin.player.core.VideoPlayer.IVideoPlayer;

import ohos.agp.components.AttrSet;
import ohos.agp.components.Component;
import ohos.agp.components.surfaceprovider.SurfaceProvider;
import ohos.agp.graphics.Surface;
import ohos.agp.graphics.SurfaceOps;
import ohos.app.Context;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A SurfaceProvider for player to render Images and be displayed on.
 */
public class SurfaceRenderView extends SurfaceProvider implements IRenderView {
    private MeasureHelper measureHelper;
    private SurfaceCallback surfaceCallback;

    public SurfaceRenderView(Context context) {
        super(context);
        initView();
    }

    public SurfaceRenderView(Context context, AttrSet attrSet) {
        super(context, attrSet);
        initView();
    }

    public SurfaceRenderView(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        initView();
    }

    private void initView() {
        measureHelper = new MeasureHelper(this);
        surfaceCallback = new SurfaceCallback(this);
        getSurfaceOps().ifPresent(ops -> ops.addCallback(surfaceCallback));
    }

    @Override
    public Component getView() {
        return this;
    }

    @Override
    public void setVideoSize(int videoWidth, int videoHeight) {
        if (videoWidth > 0 && videoHeight > 0) {
            measureHelper.setVideoSize(videoWidth, videoHeight);
            measureHelper.doMeasure(getContext());
            getContext()
                    .getUITaskDispatcher()
                    .asyncDispatch(
                            () -> {
                                setWidth(measureHelper.getMeasuredWidth());
                                setHeight(measureHelper.getMeasuredHeight());
                            });
        }
    }

    @Override
    public void setPlaybackWindowSize(int width, int height) {
        if (width * height != 0) {
            measureHelper.setPlaybackWindowSize(width, height);
        }
    }

    @Override
    public void addRenderCallback(IRenderCallback callback) {
        surfaceCallback.addRenderCallback(callback);
    }

    @Override
    public void removeRenderCallback(IRenderCallback callback) {
        surfaceCallback.removeRenderCallback(callback);
    }

    private static final class InternalSurfaceHolder implements ISurfaceHolder {
        private final SurfaceRenderView surfaceView;
        private final SurfaceOps surfaceHolder;

        InternalSurfaceHolder(SurfaceRenderView surfaceView, SurfaceOps surfaceHolder) {
            this.surfaceView = surfaceView;
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void bindToMediaPlayer(IVideoPlayer vp) {
            if (vp != null) {
                vp.setSurface(surfaceHolder);
            }
        }

        @Override
        public IRenderView getRenderView() {
            return surfaceView;
        }

        @Override
        public SurfaceOps getSurfaceHolder() {
            return surfaceHolder;
        }

        @Override
        public Surface openSurface() {
            if (surfaceHolder == null) {
                return null;
            }

            return surfaceHolder.getSurface();
        }
    }

    private static final class SurfaceCallback implements SurfaceOps.Callback {
        private final WeakReference<SurfaceRenderView> weakSurfaceView;
        private final Map<IRenderCallback, Object> renderCallbackMap = new ConcurrentHashMap<>();
        private SurfaceOps surfaceHolder;
        private boolean isFormatChanged;
        private int format;
        private int width;
        private int height;

        /**
         * constructor for surfaceView.
         *
         * @param surfaceView surfaceView for player.
         */
        SurfaceCallback(SurfaceRenderView surfaceView) {
            weakSurfaceView = new WeakReference<>(surfaceView);
        }

        /**
         * add customized render callback.
         *
         * @param callback render callback.
         */
        public void addRenderCallback(IRenderCallback callback) {
            renderCallbackMap.put(callback, callback);

            ISurfaceHolder tempSurfaceHolder = null;
            if (this.surfaceHolder != null) {
                tempSurfaceHolder = new InternalSurfaceHolder(weakSurfaceView.get(), this.surfaceHolder);
                callback.onSurfaceCreated(tempSurfaceHolder, width, height);
            }

            if (isFormatChanged) {
                if (tempSurfaceHolder == null) {
                    tempSurfaceHolder = new InternalSurfaceHolder(weakSurfaceView.get(), this.surfaceHolder);
                }
                callback.onSurfaceChanged(tempSurfaceHolder, format, width, height);
            }
        }

        /**
         * remove render callback
         *
         * @param callback instance of IRenderCallback
         */
        public void removeRenderCallback(IRenderCallback callback) {
            renderCallbackMap.remove(callback);
        }

        @Override
        public void surfaceCreated(SurfaceOps surfaceOps) {
            surfaceHolder = surfaceOps;
            isFormatChanged = false;
            format = 0;
            width = 0;
            height = 0;

            ISurfaceHolder tempSurfaceHolder = new InternalSurfaceHolder(weakSurfaceView.get(), this.surfaceHolder);
            for (IRenderCallback renderCallback : renderCallbackMap.keySet()) {
                renderCallback.onSurfaceCreated(tempSurfaceHolder, 0, 0);
            }
        }

        @Override
        public void surfaceChanged(SurfaceOps surfaceOps, int format, int width, int height) {
            surfaceHolder = surfaceOps;
            isFormatChanged = true;
            this.format = format;
            this.width = width;
            this.height = height;

            ISurfaceHolder tempSurfaceHolder = new InternalSurfaceHolder(weakSurfaceView.get(), this.surfaceHolder);
            for (IRenderCallback renderCallback : renderCallbackMap.keySet()) {
                renderCallback.onSurfaceChanged(tempSurfaceHolder, format, width, height);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceOps surfaceOps) {
            surfaceHolder = null;
            isFormatChanged = false;
            format = 0;
            width = 0;
            height = 0;

            ISurfaceHolder tempSurfaceHolder = new InternalSurfaceHolder(weakSurfaceView.get(), this.surfaceHolder);
            for (IRenderCallback renderCallback : renderCallbackMap.keySet()) {
                renderCallback.onSurfaceDestroyed(tempSurfaceHolder);
            }
        }
    }
}
