package entities;

import java.util.List;

public class Role {

    private int id;
    private String name;
    private List<Permission> permissions;

    public Role() {
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    } 

    public Role(int id, String name, List<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
