package com.rharj.githubrx.service;

import com.rharj.githubrx.model.GithubRepo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by rharj on 2/1/2018.
 */

public interface GithubService {

    @GET("users/{user}/starred")
    Observable<List<GithubRepo>> getStarredRepositories(@Path("user") String userName);
}
