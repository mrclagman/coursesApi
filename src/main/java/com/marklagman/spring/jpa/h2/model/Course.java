package com.marklagman.spring.jpa.h2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="courses")
public class Course {

    private long id;
    private String title;
    private String description;

    private boolean full;

    public Course() {

    }

    @Id
    @SequenceGenerator(name="course_seq", initialValue = 3, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "course_seq")
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "is_full", nullable = false)
    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", title=" + title + ", description=" + description + ", full=" + full + "]";
    }

    public Course(String title, String description, boolean full) {
        this.title = title;
        this.description = description;
        this.full = full;
    }

    
    
}
