package com.kodo.codewars.scraper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import com.kodo.database.users.scheme.Challenge;

public class CodewarsKata {
    private String id;
    private String name;
    private String slug;
    private String url;
    private String category;
    private String description;
    private List<String> tags;
    private List<String> languages;
    private Rank rank;
    private CreatedBy createdBy;
    private ApprovedBy approvedBy;
    private int totalAttempts;
    private int totalCompleted;
    private int totalStars;
    private int voteScore;
    private String publishedAt;
    private String approvedAt;
    private boolean retired;

    public static CodewarsKata getRetired(Challenge challenge){

        CodewarsKata kata = new CodewarsKata();
        kata.setId(challenge.getId());
        kata.setName(challenge.getName());
        kata.setSlug(challenge.getSlug());
        
        //set the rest to their default values
        kata.setCategory("retired");
        kata.setDescription("This kata has been retired");  
        kata.setTotalAttempts(0);
        kata.setTotalCompleted(0);
        kata.setTotalStars(0);
        kata.setVoteScore(0);
        kata.setPublishedAt("N/A");
        kata.setApprovedAt("N/A");
        
        Rank retiredRank = new Rank();
        retiredRank.setColor("gray");
        retiredRank.setName("retired");
        retiredRank.setId(9);
        kata.setRank(retiredRank);

        kata.setTags(Arrays.asList("retired"));
        kata.setLanguages(Arrays.asList("retired"));

        CreatedBy createdBy = new CreatedBy();
        createdBy.setUsername("retired");
        createdBy.setUrl("https://www.codewars.com/users/retired");
        kata.setCreatedBy(createdBy);

        ApprovedBy approvedBy = new ApprovedBy();
        approvedBy.setUsername("retired");
        approvedBy.setUrl("https://www.codewars.com/users/retired");
        kata.setApprovedBy(approvedBy);

        kata.setRetired(true);
        
        return kata;
    }

    public CodewarsKata(){
        this.retired = false;
    }

    // Getters and Setters

    public boolean isRetired() {
        return retired;
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

    public String getUrl() {
        if(this.url == null){
            return "https://www.codewars.com/kata/" + this.slug;
        }
        return this.url;
        
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public ApprovedBy getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(ApprovedBy approvedBy) {
        this.approvedBy = approvedBy;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public void setTotalAttempts(int totalAttempts) {
        this.totalAttempts = totalAttempts;
    }

    public int getTotalCompleted() {
        return totalCompleted;
    }

    public void setTotalCompleted(int totalCompleted) {
        this.totalCompleted = totalCompleted;
    }

    public int getTotalStars() {
        return totalStars;
    }

    public void setTotalStars(int totalStars) {
        this.totalStars = totalStars;
    }

    public int getVoteScore() {
        return voteScore;
    }

    public void setVoteScore(int voteScore) {
        this.voteScore = voteScore;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(String approvedAt) {
        this.approvedAt = approvedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Get the declared fields of the class
        Field[] fields = getClass().getDeclaredFields();

        // Iterate over each field
        for (Field field : fields) {
            // Set the field as accessible (in case it is private)
            field.setAccessible(true);

            try {
                // Get the name and value of the field
                String name = field.getName();
                Object value = field.get(this);

                // Append the name and value to the StringBuilder
                sb.append(name).append(": ").append(value).append(", ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Remove the trailing comma and space
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }

        // Return the final string representation
        return sb.toString();
    }


}

