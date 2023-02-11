package com.waylau.hmos.shortvideo.dialog;

import java.util.List;
import java.util.Optional;

import com.waylau.hmos.shortvideo.ResourceTable;
import com.waylau.hmos.shortvideo.bean.CommentInfo;
import com.waylau.hmos.shortvideo.bean.UserInfo;
import com.waylau.hmos.shortvideo.bean.VideoInfo;
import com.waylau.hmos.shortvideo.provider.CommentListItemProvider;
import com.waylau.hmos.shortvideo.store.CommentInfoRepository;
import com.waylau.hmos.shortvideo.util.LogUtil;

import ohos.agp.components.*;
import ohos.agp.window.dialog.CommonDialog;
import ohos.agp.window.service.Display;
import ohos.agp.window.service.DisplayManager;
import ohos.app.Context;

/**
 * 评论弹出框
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @since 2023-02-11
 */
public class CommentDialog extends CommonDialog {
    private static final String TAG = CommentDialog.class.getSimpleName();

    private Context context;
    private ListContainer listContainerComment;
    private Text textDataEmpty;
    private TextField textFieldComment;
    private Button buttonCommentSend;
    private CommentListItemProvider commentListItemProvider;
    private List<CommentInfo> commentInfosList;

    public CommentDialog(Context context, UserInfo userInfo, VideoInfo videoInfo, List<CommentInfo> list) {
        super(context);
        this.context = context;
        this.commentInfosList = list;
        this.setAutoClosable(true); // 自动关闭

        Component container = LayoutScatter.getInstance(context).parse(ResourceTable.Layout_comment_dialog, null, false);
        setContentCustomComponent(container);

        Optional<Display> display = DisplayManager.getInstance().getDefaultDisplay(context);
        int height = (int) (display.get().getAttributes().height * 0.5);
        setSize(ComponentContainer.LayoutConfig.MATCH_PARENT, height);

        listContainerComment = (ListContainer) container.findComponentById(ResourceTable.Id_lc_comment_list);
        textDataEmpty = (Text) container.findComponentById(ResourceTable.Id_text_comment_dataEmpty);
        textFieldComment = (TextField) container.findComponentById(ResourceTable.Id_textfield_comment);
        textFieldComment.setAdjustInputPanel(true);
        buttonCommentSend = (Button) container.findComponentById(ResourceTable.Id_button_comment_send);

        commentListItemProvider = new CommentListItemProvider(commentInfosList, context,null);
        listContainerComment.setItemProvider(commentListItemProvider);

        refreashView();

        buttonCommentSend.setClickedListener(component -> {
            LogUtil.info(TAG, "buttonCommentSend Clicked");
            CommentInfo commentInfo = new CommentInfo();
            commentInfo.copy(userInfo, videoInfo);
            commentInfo.setContent(textFieldComment.getText());

            CommentInfoRepository.insert(commentInfo);

            commentInfosList.add(commentInfo);

            commentListItemProvider.notifyDataChanged();

            refreashView();
        });
    }

    private void refreashView() {
        if(commentInfosList.isEmpty()){
            listContainerComment.setVisibility(Component.HIDE);
            textDataEmpty.setVisibility(Component.VISIBLE);
        }else{
            listContainerComment.setVisibility(Component.VISIBLE);
            textDataEmpty.setVisibility(Component.HIDE);
        }

        textFieldComment.setText("");
    }
 }
