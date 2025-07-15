package com.vianavitor.enterprisechat.model;

import jakarta.persistence.*;

enum Type {
    DEPARTMENT(0) , TEAM(1);

    private final int value;

    Type(int value) {
        this.value = value;
    }

    public boolean compare(int value) {
        return this.value == value;
    }

    public int getValue() {
        return value;
    }
}

/*
The group entity represents a father entity for all groups presents in this system,
such as: team, and department.
 */

@Entity
@Table(name = "`group`")
@Inheritance(strategy = InheritanceType.JOINED)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private int members_count = 0;


    public int getType() {
        return type.getValue();
    }

    public void setType(int type) {
        switch (type) {
            case 0:
                this.type = Type.DEPARTMENT;
                break;
            case 1:
                this.type = Type.TEAM;
                break;
            default:
                throw new IllegalArgumentException(type+" is a invalid grop type");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMembers_count() {
        return members_count;
    }

    public void setMembers_count(int members_count) {
        this.members_count = members_count;
    }
}
