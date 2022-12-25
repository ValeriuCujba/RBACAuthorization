package entities;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int id;
    private String name;
    private Role role;
    private List<User> users = new ArrayList<>();

    public Group() {
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    
    public Group(int id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Group(int id, String name, Role role, List<User> users) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.users = users;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    
}
