package org.agh.edu.pl.carrentalrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "UserRoles")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;
    @JsonProperty("type")
    @Column(name = "Type", columnDefinition = "varchar(30) default 'Customer'")
    @Size(min = 1, max = 30, message = "Type must be between 1 and 30 characters long")
    private String type;

    @ManyToMany(mappedBy = "userRoles")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public UserRole() {
        super();
    }

    public UserRole(Long id,
                    String type) {
        super();
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
