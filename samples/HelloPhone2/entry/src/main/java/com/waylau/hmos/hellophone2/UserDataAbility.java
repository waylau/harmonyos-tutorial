package com.waylau.hmos.hellophone2;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.data.resultset.ResultSet;
import ohos.data.rdb.ValuesBucket;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.global.resource.RawFileEntry;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.MessageParcel;
import ohos.utils.net.Uri;
import ohos.utils.PacMap;

import java.io.*;

public class UserDataAbility extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "Demo");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "UserDataAbility onStart");
    }

    @Override
    public ResultSet query(Uri uri, String[] columns, DataAbilityPredicates predicates) {
        return null;
    }

    @Override
    public int insert(Uri uri, ValuesBucket value) {
        HiLog.info(LABEL_LOG, "UserDataAbility insert");
        return 999;
    }

    @Override
    public int delete(Uri uri, DataAbilityPredicates predicates) {
        return 0;
    }

    @Override
    public int update(Uri uri, ValuesBucket value, DataAbilityPredicates predicates) {
        return 0;
    }

    @Override
    public FileDescriptor openFile(Uri uri, String mode) {
        RawFileEntry rawFileEntry = this.getResourceManager().getRawFileEntry("resources/rawfile/users.txt");
        FileDescriptor fd = null;
        try {
            StringBuilder sb = new StringBuilder();
            InputStream inputStream = rawFileEntry.openRawFile();

            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            String str = sb.toString();

            HiLog.info(LABEL_LOG, "openFile content: %{public}s", str);

            fd = rawFileEntry.openRawFileDescriptor().getFileDescriptor();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HiLog.info(LABEL_LOG, "after openFile fd: %{public}s", fd);

        // 创建messageParcel
        MessageParcel messageParcel = MessageParcel.obtain();

        fd = messageParcel.dupFileDescriptor(fd);

        return fd;
    }

    @Override
    public String[] getFileTypes(Uri uri, String mimeTypeFilter) {
        return new String[0];
    }

    @Override
    public PacMap call(String method, String arg, PacMap extras) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}