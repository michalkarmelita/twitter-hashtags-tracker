package com.michalkarmelita.hashtagtracker.model.adaptermodels;

public class TweetAdapterItem {

    private final String text;
    private final String name;
    private final String profileImageUrl;

    public TweetAdapterItem(String text, String name, String profileImageUrl) {
        this.text = text;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
