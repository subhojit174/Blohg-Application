package com.blog.blogappapis.payloads;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private Integer categoryId;
    @NotEmpty
    private String categoryTitle;
    @NotEmpty
    private String categoryDescription;
}
