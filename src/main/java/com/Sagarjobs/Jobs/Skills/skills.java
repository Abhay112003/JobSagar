package com.Sagarjobs.Jobs;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class skills {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
int skills_id;

int job_seekerid;

@Size(min = 4 ,message = "Enter a Valid Skills")
String name;
@Size(min = 4 ,message = "Enter a valid Category")
String category;

    public skills(int skills_id, int job_seekerid, String name, String category) {
        this.skills_id = skills_id;
        this.job_seekerid = job_seekerid;
        this.name = name;
        this.category = category;
    }

    public skills() {
    }

    public int getSkills_id() {
        return skills_id;
    }

    public void setSkills_id(int skills_id) {
        this.skills_id = skills_id;
    }

    public int getJob_seekerid() {
        return job_seekerid;
    }

    public void setJob_seekerid(int job_seekerid) {
        this.job_seekerid = job_seekerid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
