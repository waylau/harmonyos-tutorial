package com.waylau.hmos.distributedfile.slice;

import com.waylau.hmos.distributedfile.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.agp.components.TextField;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;


import java.io.*;

public class MainAbilitySlice extends AbilitySlice {
    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private static final HiLogLabel LABEL_LOG =
            new HiLogLabel(HiLog.LOG_APP, 0x00001, TAG);

    private Text textRead;
    private TextField textWrite;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);


        // 添加点击事件来触发请求
        textRead = (Text) findComponentById(ResourceTable.Id_text_read);
        textWrite = (TextField) findComponentById(ResourceTable.Id_text_write);
        Button buttonWrite = (Button) findComponentById(ResourceTable.Id_button_write);

        // 为按钮设置点击事件回调
        buttonWrite.setClickedListener(listener -> {
            try {
                writeFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button buttonRead = (Button) findComponentById(ResourceTable.Id_button_read);

        // 为按钮设置点击事件回调
        buttonRead.setClickedListener(listener -> {
            try {
                readFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void writeFile() throws IOException {
        HiLog.info(LABEL_LOG, "Before initFile");
        File distDir = this.getDistributedDir();
        String distributedfilePath = distDir + File.separator + "hello.txt";

        try {
            File writeName = new File(distributedfilePath); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(textWrite.getText());
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        HiLog.info(LABEL_LOG, "After initFile");
    }

    private void readFile() throws IOException {
        HiLog.info(LABEL_LOG, "Before getFile");
        File distDir = this.getDistributedDir();
        String distributedfilePath = distDir + File.separator + "hello.txt";
        String content = "";

        try (FileReader reader = new FileReader(distributedfilePath);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                HiLog.info(LABEL_LOG, "getFile line %{public}s", line);
                content = content + line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        textRead.setText(content);
        HiLog.info(LABEL_LOG, "getFile %{public}s", content);
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