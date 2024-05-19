package com.mzopensource.examease.webexam;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.mzopensource.examease.R;

public class WebExamUpload {
    Context context;
    public WebExamUpload(ConstraintLayout container, Context context) {
        this.context = context;
        Button upload_class = container.findViewById(R.id.upload_class);
        Button upload_proctor = container.findViewById(R.id.upload_proctor);

        upload_class.setOnClickListener(view -> classUploadDialog());
        upload_proctor.setOnClickListener(view -> {});
    }

    private void classUploadDialog(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_upload_chooser);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        LinearLayout excel_upload = dialog.findViewById(R.id.upload_container).findViewById(R.id.excel_upload);
        excel_upload.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }
}
