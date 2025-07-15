package com.vianavitor.enterprisechat.util.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/*
An interface designed to be used by classes/entities that need to be managed.
It provides functions to manage time, such as: created at, and updated at. And
function to handle with account lifetime: active
 */
public interface Manageable {
    LocalDate getCreatedAt();
    void setCreatedAt();
    LocalDate getUpdatedAt();
    void setUpdatedAt();
    boolean isActive();
    void setActive(boolean active);
}
