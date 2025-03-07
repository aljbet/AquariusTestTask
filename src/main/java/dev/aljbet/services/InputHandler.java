package dev.aljbet.services;

import dev.aljbet.models.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class InputHandler {
    /**
     * Проверяет валидность ссылки, и если все ок, то
     * проходит по файлу, находит нужный номер, возвращает рекорд Configuration
     *
     * @param pathToFile путь к конфиг-файлу
     * @param id         порядковый номер конфигурации
     * @return структурированные данные о нужной конфигурации
     * @throws Exception если файла не существует или произошла ошибка чтения
     */
    public Configuration handle(String pathToFile, int id) throws Exception {
        var path = Path.of(pathToFile);
        if (!Files.exists(path)) {
            throw new FileNotFoundException(path.toString());
        }

        BufferedReader reader = new BufferedReader(
                new FileReader(path.toFile().getAbsolutePath(), StandardCharsets.UTF_8));
        String neededId = "#" + id;
        while (reader.ready()) {
            if (neededId.equals(reader.readLine())) {
                break;
            }
        }
        var config = new Configuration();
        config.setFile(path.toFile());
        config.setId(id);
        for (int i = 0; i < ConfigConstants.K_LINES; i++) {
            handleConfigLine(reader.readLine(), config);
        }
        if (config.getMode() == null) {
            throw new InvalidConfigFormatException(
                    "Mode was not specified for configuration #" + id);
        }
        if (config.getPaths() == null || config.getPaths().isEmpty()) {
            throw new InvalidConfigFormatException(
                    "Path was not specified for configuration #" + id);
        }
        if (config.getAction() == null) {
            throw new InvalidConfigFormatException(
                    "Action was not specified for configuration #" + id);
        }
        reader.close();
        return config;
    }

    public void handleConfigLine(String line, Configuration config) {
        var keyValue = line.split(":");
        if (keyValue.length != 2) {
            throw new InvalidConfigFormatException(
                    "Key and value should be separated by ':' symbol");
        }
        switch (keyValue[0]) {
            case ConfigConstants.MODE:
                config.setMode(Mapper.getModeFromString(keyValue[1]));
                break;
            case ConfigConstants.PATH:
                config.setPaths(Arrays.stream(keyValue[1].split(","))
                                        .map(String::strip).toList());
                break;
            case ConfigConstants.ACTION:
                config.setAction(Mapper.getActionFromString(keyValue[1]));
                break;
            default:
                throw new InvalidConfigFormatException("Unknown key: " + keyValue[0]);
        }
    }
}
