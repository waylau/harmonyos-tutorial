package com.waylau.hmos.dataabilityhelperaccessfile;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.data.resultset.ResultSet;
import ohos.data.rdb.ValuesBucket;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.global.resource.RawFileEntry;
import ohos.global.resource.Resource;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.MessageParcel;
import ohos.utils.net.Uri;
import ohos.utils.PacMap;

import java.io.*;
import java.nio.file.Paths;

public class UserDataAbility extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.LOG_APP, 0x00001, "UserDataAbility");

    private File targetFile;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "UserDataAbility onStart");

        try {
            // 初始化目标文件数据
            initFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFile() throws IOException {
        // 获取数据目录
        File dataDir = new File(this.getDataDir().toString());
        if(!dataDir.exists()){
            dataDir.mkdirs();
        }

        // 构建目标文件
        targetFile = new File(Paths.get(dataDir.toString(),"users.txt").toString());

        // 获取源文件
        RawFileEntry rawFileEntry = this.getResourceManager().getRawFileEntry("resources/rawfile/users.txt");
        Resource resource = rawFileEntry.openRawFile();

        // 新建目标文件
        FileOutputStream fos = new FileOutputStream(targetFile);

        byte[] buffer = new byte[4096];
        int count = 0;

        // 源文件内容写入目标文件
        while((count = resource.read(buffer)) >= 0){
            fos.write(buffer,0,count);
        }

        resource.close();
        fos.close();
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
        FileDescriptor fd = null;

        try {
            // 获取目标文件FileDescriptor
            FileInputStream fileIs = new FileInputStream(targetFile);
            fd = fileIs.getFD();

            HiLog.info(LABEL_LOG, "fd: %{public}s", fd);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建MessageParcel
        MessageParcel messageParcel = MessageParcel.obtain();

        // 复制FileDescriptor
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