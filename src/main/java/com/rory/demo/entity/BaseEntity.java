package com.rory.demo.entity;

import java.io.Serializable;

public interface BaseEntity extends Serializable {

    Long getId();

    void setId(Long id);
}
