package net.terut.twitter;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.terut.twitter.lib.TwitterClient;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetActivity extends Activity {

    private EditText mInputText;
    private Twitter mTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        mTwitter = TwitterClient.getTwitterInstance(this);

        mInputText = (EditText) findViewById(R.id.input_text);

        findViewById(R.id.action_tweet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweet();
            }
        });
    }

    private void tweet() {
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... args) {
                try {
                    mTwitter.updateStatus(args[0]);
                    return true;
                } catch (TwitterException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    showToast("ツイート完了");
                    finish();
                } else {
                    showToast("ツイート失敗");
                }
            }
        };
        task.execute(mInputText.getText().toString());
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
