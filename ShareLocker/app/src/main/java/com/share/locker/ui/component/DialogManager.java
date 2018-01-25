package com.share.locker.ui.component;

import android.view.LayoutInflater;
import android.view.View;

import com.share.locker.R;
import com.share.locker.common.GlobalManager;

import me.drakeet.materialdialog.MaterialDialog;


/**
 * Created by Jordan on 25/01/2018.
 */

public class DialogManager {
    private MaterialDialog currentDialog = null;

    /**
     * 弹出等待框，全局只能有一个
     */
    public void showLoopDialog() {
        View view = LayoutInflater.from(GlobalManager.currentActivity).inflate(R.layout.dialog_waiting, null);
        currentDialog = new MaterialDialog(GlobalManager.currentActivity).setView(view);
        currentDialog.show();
    }

    /**
     * 弹出等待框，全局只能有一个
     */
    public void showLoopDialogInUiThread() {
        GlobalManager.currentActivity.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            showLoopDialog();
                                                        }
                                                    }
        );
    }

    /**
     * 取消等待框
     */
    public void removeCurrentLoopDialog() {
        currentDialog.dismiss();
    }

    /**
     * 取消等待框
     */
    public void removeCurrentLoopDialogInUiThread() {
        GlobalManager.currentActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        removeCurrentLoopDialog();
                    }
                }
        );
    }

    /**
     * 正常提示
     *
     * @param tipMsg
     */
    public void showTipDialog(String tipMsg) {
        final MaterialDialog dialog = new MaterialDialog(GlobalManager.currentActivity).setMessage(tipMsg);
        dialog.setPositiveButton("好的", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 正常提示
     *
     * @param tipMsg
     */
    public void showTipDialogInUiThread(final String tipMsg) {
        GlobalManager.currentActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        showTipDialog(tipMsg);
                    }
                }
        );
    }

    /**
     * 错误提示
     *
     * @param errorMsg
     */
    public void showErrorDialog(String errorMsg) {
        final MaterialDialog dialog = new MaterialDialog(GlobalManager.currentActivity).setMessage(errorMsg);
        dialog.setTitle("请注意");
        dialog.setPositiveButton("知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 错误提示
     *
     * @param errorMsg
     */
    public void showErrorDialogInUiThread(final String errorMsg) {
        GlobalManager.currentActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        showErrorDialog(errorMsg);
                    }
                }
        );
    }

    /**
     * title+确定+取消
     *
     * @param title
     * @param confirmListener
     * @param cancelListener
     */
    public void showConfirmDialog(String title,
                                  final View.OnClickListener confirmListener,
                                  final View.OnClickListener cancelListener) {
        final MaterialDialog dialog = new MaterialDialog(GlobalManager.currentActivity);
        dialog.setTitle(title);
        dialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmListener.onClick(v);
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelListener.onClick(v);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * title+确定+取消
     *
     * @param title
     * @param confirmListener
     * @param cancelListener
     */
    public void showConfirmDialogInUiThread(final String title,
                                            final View.OnClickListener confirmListener,
                                            final View.OnClickListener cancelListener) {
        GlobalManager.currentActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        showConfirmDialog(title, confirmListener, cancelListener);
                    }
                }
        );
    }

}
