package com.example.application.data;


import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Formula;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Company extends AbstractEntity {
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "company")
    @Nullable
    private List<Product> employees = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Product> employees) {
        this.employees = employees;
    }

    @Formula("(select count(p.id) from Product p where p.company_id = id)")
    private int productCount;

    public int getProductCount() {
        return productCount;
    }
}
