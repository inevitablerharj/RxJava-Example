package com.rharj.githubrx.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rharj.githubrx.R;
import com.rharj.githubrx.model.GithubRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rharj on 2/1/2018.
 */

public class GithubRepoAdapter extends BaseAdapter {

    private List<GithubRepo> gitHubRepos = new ArrayList<>();

    @Override public int getCount() {
        return gitHubRepos.size();
    }

    @Override public GithubRepo getItem(int position) {
        if (position < 0 || position >= gitHubRepos.size()) {
            return null;
        } else {
            return gitHubRepos.get(position);
        }
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final GithubRepoViewHolder viewHolder = (GithubRepoViewHolder) view.getTag();
        viewHolder.setGithubRepo(getItem(position));
        return view;
    }

    public void setGitHubRepos(@Nullable List<GithubRepo> repos) {
        if (repos == null) {
            return;
        }
        gitHubRepos.clear();
        gitHubRepos.addAll(repos);
        notifyDataSetChanged();
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_github_repo, parent, false);
        final GithubRepoViewHolder viewHolder = new GithubRepoViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static class GithubRepoViewHolder {

        private TextView textRepoName;
        private TextView textRepoDescription;
        private TextView textLanguage;
        private TextView textStars;

        public GithubRepoViewHolder(View view) {
            textRepoName = (TextView) view.findViewById(R.id.text_repo_name);
            textRepoDescription = (TextView) view.findViewById(R.id.text_repo_description);
            textLanguage = (TextView) view.findViewById(R.id.text_language);
            textStars = (TextView) view.findViewById(R.id.text_stars);
        }

        public void setGithubRepo(GithubRepo gitHubRepo) {
            textRepoName.setText(gitHubRepo.name);
            textRepoDescription.setText(gitHubRepo.description);
            textLanguage.setText("Language: " + gitHubRepo.language);
            textStars.setText("Stars: " + gitHubRepo.stargazersCount);
        }
    }
}
