package com.ist.educloud.integrationexample.dtos;

public class DiplomaProjectDTO {
    private final String title;
    private final String description;
    private final String titleEnglish;
    private final String descriptionEnglish;

    public DiplomaProjectDTO(String title, String description, String titleEnglish, String descriptionEnglish) {
        this.title = title;
        this.description = description;
        this.titleEnglish = titleEnglish;
        this.descriptionEnglish = descriptionEnglish;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTitleEnglish() {
        return titleEnglish;
    }

    public String getDescriptionEnglish() {
        return descriptionEnglish;
    }
}
