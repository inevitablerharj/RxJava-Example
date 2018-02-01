package com.rharj.githubrx.client;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rharj.githubrx.model.GithubRepo;
import com.rharj.githubrx.service.GithubService;
import com.rharj.githubrx.utils.AppConstants;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by rharj on 2/1/2018.
 */

public class GithubClient {

    private static GithubClient instance;
    private GithubService gitHubService;

    private GithubClient() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(AppConstants.GITHUB_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        gitHubService = retrofit.create(GithubService.class);
    }

    public static GithubClient getInstance() {
        if (instance == null) {
            instance = new GithubClient();
        }
        return instance;
    }

    public Observable<List<GithubRepo>> getStarredRepos(@NonNull String userName) {
        return gitHubService.getStarredRepositories(userName);
    }
}
