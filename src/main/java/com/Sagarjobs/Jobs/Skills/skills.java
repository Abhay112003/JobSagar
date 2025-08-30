package com.Sagarjobs.Jobs.Skills;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class skills {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
int skills_id;

@Column(name = "seeker_id")
int jobseekerid;

@Size(min = 4 ,message = "Enter a Valid Skills")
String skill;
@Size(min = 4 ,message = "Enter a valid Category")
String category;

char isenabled;

    public skills(int skills_id, int jobseekerid, String skill, String category, char isenabled) {
        this.skills_id = skills_id;
        this.jobseekerid = jobseekerid;
        this.skill = skill;
        this.category = category;
        this.isenabled = isenabled;
    }

    public skills() {
    }

    public int getSkills_id() {
        return skills_id;
    }

    public void setSkills_id(int skills_id) {
        this.skills_id = skills_id;
    }

    public int getJobseekerid() {
        return jobseekerid;
    }

    public void setJobseekerid(int jobseekerid) {
        this.jobseekerid = jobseekerid;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public char getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(char isenabled) {
        this.isenabled = isenabled;
    }
}
