package com.prankishor.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	private int id;
	@NotBlank
	@Size(min=3, message="Minimum length of category name should be 3")
	private String CategoryName;
	@NotBlank
	@Size(min=10, message="Minimum length of description should be 10 characters")
	private String CategoryDescription;
}
