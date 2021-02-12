package com.waylau.hmos.avstorage.slice;

import com.waylau.hmos.avstorage.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.agp.components.TableLayout;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.data.resultset.ResultSet;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.common.Source;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.photokit.metadata.AVStorage;
import ohos.media.player.Player;
import ohos.utils.net.Uri;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private DataAbilityHelper helper;

    private TableLayout tableLayout;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        helper = DataAbilityHelper.creator(this);

        // 为按钮设置点击事件回调
        Button buttonGet =
                (Button) findComponentById(ResourceTable.Id_button_get);
        buttonGet.setClickedListener(listener -> {
            try {
                getInfo();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DataAbilityRemoteException e) {
                e.printStackTrace();
            }
        });

        tableLayout = (TableLayout) findComponentById(ResourceTable.Id_layout_table);
        tableLayout.setColumnCount(4);
    }

    @Override
    public void onStop() {
        super.onStop();

        // 关闭、释放资源
        release();
    }

    private void getInfo() throws FileNotFoundException, DataAbilityRemoteException {
        HiLog.info(LABEL_LOG, "before getInfo");

        // 查询条件为null，即为查询所有记录
        ResultSet result =
                helper.query(AVStorage.Images.Media.EXTERNAL_DATA_ABILITY_URI,
                        null, null);

        if (result == null) {
            HiLog.info(LABEL_LOG, "result is null");
            return;
        }

        while (result.goToNextRow()) {
            // 获取id字段的值
            String mediaId = result.getString(result.getColumnIndexForName(AVStorage.Images.Media.ID));
            HiLog.info(LABEL_LOG, "mediaId: %{public}s", mediaId);

            showImage(mediaId);
        }


        result.close();

        HiLog.info(LABEL_LOG, "end getInfo");
    }

    private void showImage(String mediaId) throws DataAbilityRemoteException, FileNotFoundException {
        HiLog.info(LABEL_LOG, "before showImage, mediaId: %{public}s", mediaId);

        PixelMap pixelMap = null;
        ImageSource imageSource = null;
        Image image = new Image(this);
        image.setWidth(250);
        image.setHeight(250);
        image.setMarginsLeftAndRight(10, 10);
        image.setMarginsTopAndBottom(10, 10);
        image.setScaleMode(Image.ScaleMode.CLIP_CENTER);

        Uri uri = Uri.appendEncodedPathToUri(AVStorage.Images.Media.EXTERNAL_DATA_ABILITY_URI, mediaId);
        FileDescriptor fd = helper.openFile(uri, "r");
        try {
            imageSource = ImageSource.create(fd, null);
            pixelMap = imageSource.createPixelmap(null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageSource != null) {
                imageSource.release();
            }
        }

        image.setPixelMap(pixelMap);
        tableLayout.addComponent(image);

        HiLog.info(LABEL_LOG, "end play");
    }

    private void release() {
        HiLog.info(LABEL_LOG, "release");

        if (helper != null) {
            // 关闭、释放资源
            helper.release();
            helper = null;
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
