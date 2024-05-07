package com.springboot.cli.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AdminAddUser {
    private Long userId;
    private List<String> endpoint;
}
