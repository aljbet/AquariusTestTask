package dev.aljbet.services;

import dev.aljbet.models.Configuration;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JsonFormatter {
    /**
     * форматирует, записывает в файл, путь к которому возвращается
     *
     * @param config  данные конфигурации
     * @param strings строки файлов
     * @return путь к файлы с Json
     */
    public String format(Configuration config, List<List<String>> strings)
            throws Exception {
        var json = new JSONObject();
        json.put("configFile", config.getFile());
        json.put("configurationID", config.getId());

        var configData = new JSONObject();
        configData.put("mode", config.getMode().toString().toLowerCase());
        var pathString = config.getPaths().toString();
        configData.put("path", pathString.substring(1, pathString.length() - 1));
        configData.put("action", config.getAction().toString().toLowerCase());

        json.put("configurationData", configData);

        // обработка строк
        var out = new JSONObject();
        for (int i = 0; i < strings.size(); ++i) {
            var curObj = new JSONObject();
            for (int j = 0; j < strings.get(i).size(); ++j) {
                curObj.put(Integer.toString(j + 1), strings.get(i).get(j));
            }
            out.put(Integer.toString(i + 1), curObj);
        }

        json.put("out", out);

        // запись в файл
        var jsonPath = Path.of(
                config.getFile().getAbsoluteFile().getParent() + "\\"
                + config.getFile().getName().split("\\.")[0]
                + config.getId() + ".json");
        if (!Files.exists(jsonPath)) {
            Files.createFile(jsonPath);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(jsonPath.toFile()));
        writer.write(json.toString());
        writer.close();
        return jsonPath.toString();
    }
}
