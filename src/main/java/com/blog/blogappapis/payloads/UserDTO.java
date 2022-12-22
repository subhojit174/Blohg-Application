package com.blog.blogappapis.payloads;

import com.blog.blogappapis.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    @NotEmpty
    @Size(min = 4,message = "User Name must be atleast 4 charecters")
    private String name;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
   // @JsonIgnore
    private String password;
    @NotEmpty
    private String about;
    private List<RoleDTO> roles=new ArrayList();
}
