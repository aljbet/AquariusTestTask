package dev.aljbet.models;

import java.util.List;

public record Configuration(String fileName,
                            int id,
                            Mode mode,
                            List<String> paths,
                            Action action) {
}
