package com.jooq.learn.model;

import java.time.LocalDate;

public record Employee(Integer id, String name, LocalDate hireDate, Integer salary) {}
