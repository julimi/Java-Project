package com.example.kuilikmi.curiomobile.dummy;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 7;

    private static final String USER_AGENT = "Marshmallow/6.0";

    private static final String GET_URL = "http://test.crowdcurio.com/api/project";

    public static JSONArray jsonArray = new JSONArray();
    public static StringBuilder responseStrBuilder;

    public static void sendGET() throws IOException {
        URL obj = new URL(GET_URL);

        // obtain a new connection
        HttpURLConnection urlConnection = (HttpURLConnection) obj.openConnection();

        // prepare the request
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("User-Agent", USER_AGENT);
        int response = urlConnection.getResponseCode();
        System.out.println("Connection response code: " + response);

        // read the request
        try {
            //urlConnection.connect();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            responseStrBuilder = new StringBuilder();

            String inStr;
            while ((inStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inStr);

            jsonArray = new JSONArray(responseStrBuilder.toString());
            //JSONObject testJ = new JSONObject(responseStrBuilder.toString());
            Log.d("","JSON value: " + jsonArray.getJSONObject(0).getString("slug"));
            in.close();
        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println("Fail to convert to JSONObject!");
        } finally {
            urlConnection.disconnect();
        }
    }

    private static class BackgroundTask extends AsyncTask<String,Void,String> {
        public ProgressDialog mDialog;
        @Override
        protected String doInBackground(String... strings) {
            try {

                DummyContent.sendGET();
                for (int i = 1; i <= COUNT; i++) {
                    setItem(setDummyItem(i), i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Order: 11");
            return responseStrBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    public static void getResponse() {
        new BackgroundTask().execute(GET_URL);

    }

    static {
        // Add some sample items.
        getResponse();
        System.out.println("Order: 1");
        for (int i = 1; i <= COUNT; i++) {
            System.out.println("Order: 111");
            addItem(createDummyItem(i));
            System.out.println("Order: 1111");
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        //try {
        //    System.err.println("test: " + jsonObject.getString("dive4oceanography"));
        //} catch (JSONException e) {
        //    e.printStackTrace();
        //}
    }

    private static void setItem(DummyItem item, int position) {
        ITEMS.set(position-1, item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        String content;
        if (position == 1) content = "thoreaus-field-notes";
        else if (position == 2) content = "dive4oceanography";
        else if (position == 3) content = "typewriter-girl";
        else if (position == 4) content = "crowdeeg";
        else if (position == 5) content = "ensemble";
        else if (position == 6) content = "urban-ears";
        else content = "on-target";
        return new DummyItem(String.valueOf(position), content, makeDetails(position));
    }

    private static DummyItem setDummyItem(int position) {
        String content = "";
        int id = 8;
        StringBuilder builder = new StringBuilder();
        String details = "";
        String url = "";
        try {

            id = jsonArray.getJSONObject(position-1).getInt("id");
            content = jsonArray.getJSONObject(position-1).getString("slug");
            builder.append("About:\n\n");
            details = jsonArray.getJSONObject(position-1).getString("description");
            builder.append(details+"\n\n\nResearch Question:\n\n");
            details = jsonArray.getJSONObject(position-1).getString("short_description");
            builder.append(details+"\n\n\nTeam:\n\n");
            for (int i = 0; i < jsonArray.getJSONObject(position-1).getJSONArray("team").length(); i++) {
                details = jsonArray.getJSONObject(position-1).getJSONArray("team").getJSONObject(i).getString("owner");
                builder.append(details+"\n");
            }
            url = jsonArray.getJSONObject(position-1).getString("avatar");

        } catch (JSONException e) {
            e.printStackTrace();
            System.err.println("cao ni da ye");
        }
        return new DummyItem(String.valueOf(id), content, builder.toString(), url);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String content;
        public String details;
        public String url;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        public DummyItem(String id, String content, String details, String url) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.url = url;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
