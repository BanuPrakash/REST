package com.adobe.asyncexample.dto;

// record is a DTO with constructor, and getters: readonly
public record Post(int id, String title, String body, int userId) {
}
