package com.rharj.githubrx.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.rharj.githubrx.R;
import com.rharj.githubrx.adapter.GithubRepoAdapter;
import com.rharj.githubrx.client.GithubClient;
import com.rharj.githubrx.model.GithubRepo;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GithubRepoAdapter adapter = new GithubRepoAdapter();
    private Subscription subscription;
    ListView listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = (ListView) findViewById(R.id.list_view_repos);
        final EditText editTextUsername = (EditText) findViewById(R.id.edit_text_username);
        final Button buttonSearch = (Button) findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final String username = editTextUsername.getText().toString();
                if (!TextUtils.isEmpty(username)) {
                    getStarredRepos(username);
                }
            }
        });
    }

    @Override protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    private void getStarredRepos(String username) {
        subscription = GithubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GithubRepo>>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override public void onNext(List<GithubRepo> gitHubRepos) {
                        Log.d(TAG, "In onNext()");
                        adapter.setGitHubRepos(gitHubRepos);
                        listView.setAdapter(adapter);
                    }
                });
    }
}
