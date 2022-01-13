
package com.mikhail_golovackii.developmentTeams.model;

import com.mikhail_golovackii.developmentTeams.model.Developer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Team {
    private int id;
    private String name;
    private List<Developer> developers;

    public Team() {
    }

    public Team(String name, List<Developer> developers) {
        this.name = name;
        this.developers = developers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
  
    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public void addDeveloper(Developer developer){
        if (developers == null){
            developers = new ArrayList<>();
        }
        developers.add(developer);
    }
    
    public void removeDeveloper(Developer developer){
        if (developers != null){
            developers.remove(developer);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.developers);
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
        final Team other = (Team) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.developers, other.developers);
    }

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", name=" + name + ", developers=" + developers + '}';
    }
    
    
    
}
