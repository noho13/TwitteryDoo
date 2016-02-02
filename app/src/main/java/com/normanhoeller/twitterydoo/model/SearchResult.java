package com.normanhoeller.twitterydoo.model;

import java.util.List;

/**
 * Created by norman on 02/02/16.
 */
public class SearchResult {

    public List<Statuses> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Statuses> statuses) {
        this.statuses = statuses;
    }

    private List<Statuses> statuses;

    public static class Statuses {
        private String text;
        private User user;

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
}
