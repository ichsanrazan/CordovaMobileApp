package cordova.telkomsel.cordovamobileapp.activityLog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cordova.telkomsel.cordovamobileapp.R
import kotlinx.android.synthetic.main.activity_edit_troubleshoot_log.*
import kotlinx.android.synthetic.main.activity_pdf_viewer.*

class PdfViewer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        setSupportActionBar(toolbar_pdf_viewer)
        supportActionBar?.title = "PDF Viewer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        PDFView.fromAsset("userguide_activitylog.pdf")
            .load()
    }
}