package top.smokeydays.web.daysmogserver.datatype;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DSUser {
    private String name;
    private String password;
    private String id;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

}
