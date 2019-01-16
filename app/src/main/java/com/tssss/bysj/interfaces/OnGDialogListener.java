package com.tssss.bysj.interfaces;

public interface OnGDialogListener {
    /*
    取消、反对等相似操作。
     */
    void onPassive();

    /*
    确认、同意等相似操作。
     */
    void onPositive();
}
