package com.first.challenge.api.dto;

public class UserExistsResponseDto {
    private boolean exists;

    public UserExistsResponseDto() {}
    public UserExistsResponseDto(boolean exists) { this.exists = exists; }

    public boolean isExists() { return exists; }
    public void setExists(boolean exists) { this.exists = exists; }
}
