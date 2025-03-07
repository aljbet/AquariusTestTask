package dev.aljbet.services;

import dev.aljbet.models.Configuration;
import dev.aljbet.models.InvalidConfigFormatException;
import dev.aljbet.models.Mapper;
import dev.aljbet.models.Mode;
import dev.aljbet.services.linehandlers.ILineHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FilesHandler {
    /**
     * читает поля, проверяет согласованность (при mode=dir в path передаются только директории)
     * проходится по всем файлам и для каждой строки из него вызывает очередной обработчик,
     * который зависит от поля action. тот возвращает строку, из которых составляем массив.
     *
     * @param config данные конфигурации
     * @return список строк файлов, обработанных согласно action
     */
    public List<List<String>> handle(Configuration config) throws Exception {
        List<List<String>> resultList = new ArrayList<>();
        ILineHandler lineHandler = Mapper.getLineHandlerFromAction(config.getAction());
        if (config.getMode().equals(Mode.FILES)) {
            var files = config.getPaths();
            for (int i = 0; i < files.size(); ++i) {
                visitFile(new File(files.get(i)), resultList, lineHandler, i + 1);
            }
            return resultList;
        }
        for (var path : config.getPaths()) {
            var dir = new File(path);
            if (!dir.isDirectory()) {
                throw new InvalidConfigFormatException(
                        "Path " + path + " is not a directory, however mode is 'dir'.");
            }
            var files = dir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; ++i) {
                    visitFile(files[i], resultList, lineHandler, i + 1);
                }
            }
        }

        return resultList;
    }

    private void visitFile(File file,
                           List<List<String>> resultList,
                           ILineHandler lineHandler,
                           int fileNumber) throws Exception {
        if (!file.isFile()) {
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
        int index = 0;
        while (reader.ready()) {
            if (resultList.size() <= index) {
                resultList.add(new ArrayList<>());
            }
            resultList.get(index).add(lineHandler.handleLine(reader.readLine(), fileNumber));
            index++;
        }
    }
}
