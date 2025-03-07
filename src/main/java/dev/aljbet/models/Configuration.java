package dev.aljbet.models;

import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class Configuration {
    File file;
    int id;
    Mode mode;
    List<String> paths;
    Action action;
}
