package com.promel.api.web.controller.association.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InviteCodeUpdateRequest {
    @NotBlank
    private String inviteCode;
}
