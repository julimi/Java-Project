package com.example.kuilikmi.curiomobile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuilikmi.curiomobile.dummy.DummyContent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    ProgressDialog mDialog;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            ImageView imageView = (ImageView) activity.findViewById(R.id.backgroundImage);
            new DownloadImageTask(imageView).execute(mItem.url);

            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
           // mDialog = new ProgressDialog(getActivity().getApplicationContext());
           // mDialog.setMessage("Please wait...");
           // mDialog.setCancelable(false);
           // mDialog.show();
        }
        new ProgressTask(getActivity()).execute();
    }

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;
        //List<Message> titles;
        private FragmentActivity activity;
        //private List<Message> messages;
        public ProgressTask(FragmentActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }



        /** progress dialog to show user that the backup is processing. */

        /** application context. */
        private Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (success) {
                Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
            }
        }

        protected Boolean doInBackground(final String... args) {
            try{
                this.dialog.setMessage("Loading...");
                this.dialog.show();
                return true;
            } catch (Exception e){
                Log.e("tag", "error", e);
                return false;
            }
        }


    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public ProgressDialog mDialog;

        public DownloadImageTask(ImageView imageView) {
            this.bmImage = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bm = null;
            try {
                //mDialog = new ProgressDialog(getActivity().getApplicationContext());
                //mDialog.setMessage("Please wait...");
                //mDialog.setCancelable(false);
                //mDialog.show();
                InputStream is = new java.net.URL(urldisplay).openStream();
                bm = BitmapFactory.decodeStream(is);
                //mDialog.dismiss();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bm;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            //mDialog.cancel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }

        return rootView;
    }
}
