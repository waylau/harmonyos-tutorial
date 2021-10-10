package com.waylau.hmos.douyin.player.view;

import ohos.agp.components.AttrSet;
import ohos.agp.components.RoundProgressBar;
import ohos.app.Context;

/**
 * Loading component.
 */
public class LoadingComp extends RoundProgressBar {
    /**
     * RoundProgressBar rotate angle.
     */
    public static final int ROTATE_ANGLE = 360;
    /**
     * Animation duration
     */
    public static final long ANIM_DURATION = 1500;
    /**
     * Animation loop count.
     */
    public static final int LOOPED_COUNT = Integer.MAX_VALUE;
    /**
     * Time when the animation delay starts.
     */
    public static final int ANIM_DELAY_TIME = 500;

    public LoadingComp(Context context) {
        this(context, null);
    }

    public LoadingComp(Context context, AttrSet attrSet) {
        this(context, attrSet, null);
    }

    public LoadingComp(Context context, AttrSet attrSet, String styleName) {
        super(context, attrSet, styleName);
        init(context);
    }

    private void init(Context context) {
        context.getUITaskDispatcher()
                .delayDispatch(
                        () -> {
                            createAnimatorProperty()
                                    .rotate(ROTATE_ANGLE)
                                    .setDuration(ANIM_DURATION)
                                    .setLoopedCount(LOOPED_COUNT)
                                    .start();
                        },
                        ANIM_DELAY_TIME);
    }
}
