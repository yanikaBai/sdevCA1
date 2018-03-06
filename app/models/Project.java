package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Project extends Model {
    @Id
    public Long id;
    
    @Constraints.Required
    public String name;

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Employee> employees;

    public static final Finder<Long, Project> find = new Finder<>(Project.class);


    public Project() {
    }

    public Project(Long id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setProjects(List<Employee> employees) {
        this.employees = employees;
    }

    public static List<Project> findAll() {
        return Project.find.query().where().orderBy("name asc").findList();
    }

    public static Map<String, String> options() {
        LinkedHashMap<String, String> options = new LinkedHashMap();

        for (Project c: Project.findAll()) {
            options.put(c.getId().toString(), c.getName());
        }
        
        return options;
    }

    public static boolean inProject(Long emp, Long project) {
        return find.query().where()
        .eq("projects.id", project)
        .eq("id", emp)
        .findCount() > 0;
    }

}