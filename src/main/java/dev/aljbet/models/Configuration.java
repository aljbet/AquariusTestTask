package dev.aljbet.models;

import java.util.List;

public record Configuration(Mode mode, List<String> paths, Action action) {
}
