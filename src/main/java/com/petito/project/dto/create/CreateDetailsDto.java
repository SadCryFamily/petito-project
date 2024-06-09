package com.petito.project.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateDetailsDto
{
    private String firstName;
    private String lastName;
    private Integer numberPhone;
}
