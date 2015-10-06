package com.jimtang.saver.executor;

import com.jimtang.saver.filters.FilePredicates;
import com.jimtang.saver.filters.FileTypeFilterable;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by tangz on 10/4/2015.
 */
public class DirectoryListingSaveExecutor implements SaveExecutor, FileTypeFilterable {

    private String onlineDirectory;
    private Predicate<String> filter;

    public DirectoryListingSaveExecutor(String onlineDirectory) {
        this.onlineDirectory = onlineDirectory;
        filter = FilePredicates.forIsFile();
    }

    @Override
    public void setAllowedFileTypes(String... fileTypes) {
        if (fileTypes.length > 0) {
            filter = FilePredicates.forAllowedFileTypes(fileTypes);
        }
    }

    @Override
    public void addFilter(Predicate<String> additionalFilter) {
        filter = filter.and(additionalFilter);
    }

    private List<String> getAndFilterURLs() {
        try {
            Document document = Jsoup.connect(onlineDirectory).get();
            Elements linkElements = document.getElementsByTag("a");

            return linkElements
                    .stream()
                    .map(elem -> getFullUrl(elem.attr("href")))
                    .filter(filter)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new ImageRetrievalException(e);
        }
    }

    private static String getFileName(String url) {
        String baseName = FilenameUtils.getBaseName(url);
        String extension = FilenameUtils.getExtension(url);
        return baseName + "." + extension;
    }

    private String getFullUrl(String fileName) {
        // this is a shameful hack, we'll need to use some Path-forming API to make this platform-agnostic
        if (onlineDirectory.endsWith("/")) {
            return onlineDirectory + fileName;
        } else {
            return onlineDirectory + "/" + fileName;
        }
    }

    private static String getFullFileLocation(String saveDir, String fileName) {
        File file1 = new File(saveDir);
        File file2 = new File(file1, fileName);
        return file2.getPath();
    }

    /**
     *
     * @param saveLocation the directory that we want to save images to.
     */
    @Override
    public void doSave(String saveLocation) {
        Collection<String> urls = getAndFilterURLs();

        for (String url: urls) {
            StaticURLSaveExecutor exec = new StaticURLSaveExecutor(url);
            String actualSaveLocation = getFullFileLocation(saveLocation, getFileName(url));
            exec.doSave(actualSaveLocation);

            // for security reasons, pause 0.5 sec between saves, so as we don't overwhelm the host.
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
            }
        }
    }
}
