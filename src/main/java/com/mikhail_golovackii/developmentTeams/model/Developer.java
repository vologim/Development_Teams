
package com.mikhail_golovackii.developmentTeams.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private List<Skill> skills;

    public Developer() {
    }

    public Developer(String firstName, String lastName, List<Skill> skills) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
    
    public void addSkill(Skill skill){
        if (skills == null){
            skills = new ArrayList<>();
        }
        skills.add(skill);
    }
    
    public void removeSkill(Skill skill){
        if (skills != null){
            skills.remove(skill);
        }
    }

    @Override
    public String toString() {
        return "Developer{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", skills=" + skills + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.firstName);
        hash = 97 * hash + Objects.hashCode(this.lastName);
        hash = 97 * hash + Objects.hashCode(this.skills);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Developer other = (Developer) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        return Objects.equals(this.skills, other.skills);
    }

}
