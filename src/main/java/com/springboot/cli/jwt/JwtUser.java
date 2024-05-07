package com.springboot.cli.jwt;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author l
 */
@Data
@Accessors(chain = true)
public class JwtUser {

    private boolean valid;
    private Long id;
    private String accountName;
    private String role;

    public JwtUser() {
        this.valid = false;
    }


}
