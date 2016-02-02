package com.normanhoeller.twitterydoo.model;

import java.util.List;

/**
 * Created by norman on 02/02/16.
 */
public class SearchResult {

    private List<Statuses> statuses;
    private SearchMetaData search_metadata;

    public SearchMetaData getSearch_metadata() {
        return search_metadata;
    }

    public void setSearch_metadata(SearchMetaData search_metadata) {
        this.search_metadata = search_metadata;
    }

    public List<Statuses> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Statuses> statuses) {
        this.statuses = statuses;
    }

    public static class Statuses {
        private String text;
        private User user;
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public static class User {
        private String profile_image_url;
        private String created_at;

        public String getProfile_image_url() {
            return profile_image_url;
        }

        public void setProfile_image_url(String profile_image_url) {
            this.profile_image_url = profile_image_url;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

    public static class SearchMetaData {
        private String next_results;
        private String query;
        private long max_id;

        public String getNext_results() {
            return next_results;
        }

        public void setNext_results(String next_results) {
            this.next_results = next_results;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public long getMax_id() {
            return max_id;
        }

        public void setMax_id(long max_id) {
            this.max_id = max_id;
        }
    }
}
