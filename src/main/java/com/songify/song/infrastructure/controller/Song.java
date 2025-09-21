package com.songify.song.infrastructure.controller;

import lombok.Builder;

//@Builder(builderClassName = "Builder", access = lombok.AccessLevel.PUBLIC)
@Builder
public record Song(String name, String artist) {
}
