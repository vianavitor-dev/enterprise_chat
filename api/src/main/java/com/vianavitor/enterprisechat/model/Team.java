package com.vianavitor.enterprisechat.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("team")
public class Team extends Group {
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department; // indicate which department it belongs

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
