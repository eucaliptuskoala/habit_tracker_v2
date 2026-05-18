package org.solen.controller.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoryRequest {
    private String name;
    private Long parentId;
    private boolean parentExplicitlyNull;

    public static UpdateCategoryRequest withParentNull() {
        return UpdateCategoryRequest.builder().parentExplicitlyNull(true).build();
    }
}
