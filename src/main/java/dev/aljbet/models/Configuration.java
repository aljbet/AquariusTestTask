package dev.aljbet.models;

import lombok.Data;
import java.util.List;

@Data
public class Configuration {
    String fileName;
    int id;
    Mode mode;
    List<String> paths;
    Action action;
}
