package com.kodo.database.users.scheme;

import java.util.List;

public class Challenge {
    private String id;
    private String name;
    private String slug;
    private String completedAt;
    private List<String> completedLanguages;

    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public List<String> getCompletedLanguages() {
        return completedLanguages;
    }

    public void setCompletedLanguages(List<String> completedLanguages) {
        this.completedLanguages = completedLanguages;
    }
}
