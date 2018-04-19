package cn.silen_dev.lantransmission.fileBrowser;

import java.io.File;

public interface OnFileBrowserResultListener {
    void selectFile(File file);
    void nothing();
}
