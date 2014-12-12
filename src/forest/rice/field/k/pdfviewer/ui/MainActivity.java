package forest.rice.field.k.pdfviewer.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import forest.rice.field.k.pdfviewer.R;
import forest.rice.field.k.pdfviewer.view.FloatingActionButton;
import forest.rice.field.k.pdfviewer.view.FloatingActionButton.OnCheckedChangeListener;

public class MainActivity extends Activity implements OnCheckedChangeListener {

	// A request code's purpose is to match the result of a
	// "startActivityForResult" with
	// the type of the original request. Choose any value.
	private static final int READ_REQUEST_CODE = 1337;

	public static final String FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic";

	FloatingActionButton fab = null;

	Fragment pdfRendererBasicFragment = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnCheckedChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// メニューの要素を追加して取得
		MenuItem actionItem = menu.add("Source");
		// SHOW_AS_ACTION_IF_ROOM:余裕があれば表示
		actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://github.com/moritalous/PdfViewer"));
		startActivity(intent);

		return true;
	}

	@Override
	public void onCheckedChanged(FloatingActionButton fabView, boolean isChecked) {
		// TODO Auto-generated method stub

		if (isChecked) {
			// BEGIN_INCLUDE (use_open_document_intent)
			// ACTION_OPEN_DOCUMENT is the intent to choose a file via the
			// system's file browser.
			Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

			// Filter to only show results that can be "opened", such as a file
			// (as opposed to a list
			// of contacts or timezones)
			intent.addCategory(Intent.CATEGORY_OPENABLE);

			// Filter to show only images, using the image MIME data type.
			// If one wanted to search for ogg vorbis files, the type would be
			// "audio/ogg".
			// To search for all documents available via installed storage
			// providers, it would be
			// "*/*".
			intent.setType("application/pdf");

			startActivityForResult(intent, READ_REQUEST_CODE);
			// END_INCLUDE (use_open_document_intent)

		} else {
			if (pdfRendererBasicFragment != null) {
				getFragmentManager().beginTransaction()
						.remove(pdfRendererBasicFragment).commit();
			}

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode,
			Intent resultData) {

		if (requestCode == READ_REQUEST_CODE
				&& resultCode == Activity.RESULT_OK) {

			Uri uri = null;
			if (resultData != null) {
				uri = resultData.getData();
			}

			pdfRendererBasicFragment = new PdfRendererBasicFragment(uri);

			getFragmentManager()
					.beginTransaction()
					.add(R.id.container, pdfRendererBasicFragment,
							FRAGMENT_PDF_RENDERER_BASIC).commit();

		} else {
			fab.setChecked(false);
		}
	}

}
