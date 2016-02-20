package com.jimtang.saver.executor;

import com.jimtang.saver.files.FilePredicates;
import com.jimtang.saver.settings.DirectorySource;
import com.jimtang.saver.settings.DirectoryTarget;
import com.jimtang.saver.settings.FileTypeFilterable;
import com.jimtang.saver.settings.SaveFilterable;
import com.jimtang.saver.util.ArgumentChecker;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by tangz on 10/4/2015.
 */
public class DirectoryListingSaveExecutor
        implements SaveFilterable, FileTypeFilterable, DirectorySource, DirectoryTarget {

    private String onlineDirectory;
    private Collection<Predicate<String>> filters = new HashSet<>();
    private String saveLocation;

    public DirectoryListingSaveExecutor(String onlineDirectory) {
        this.onlineDirectory = onlineDirectory;
        filters.add(FilePredicates.forIsFile());
    }

    @Override
    public void setAllowedFileTypes(String... fileTypes) {
        if (fileTypes.length > 0) {
            filters.add(FilePredicates.forAllowedFileTypes(fileTypes));
        }
    }

    @Override
    public void addFilter(Predicate<String> additionalFilter) {
        filters.add(additionalFilter);
    }

    protected Predicate<String> getFilter() {
        Predicate<String> filterToReturn = x -> true;
        for (Predicate<String> filter: filters) {
            filterToReturn = filterToReturn.and(filter);
        }
        return filterToReturn;
    }

    List<String> getAndFilterURLs() {
        try {
            Document document = Jsoup.connect(onlineDirectory).get();
            Elements linkElements = document.getElementsByTag("a");

            return linkElements
                    .stream()
                    .map(elem -> getFullUrl(elem.attr("href")))
                    .filter(getFilter())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new ImageRetrievalException(e);
        }
    }

    static String getFileName(String url) {
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

    static String getFullFileLocation(String saveDir, String fileName) {
        File file1 = new File(saveDir);
        File file2 = new File(file1, fileName);
        return file2.getPath();
    }

    protected void saveOne(String url, String saveLocation) {
        StaticURLSaveExecutor exec = new StaticURLSaveExecutor();
        String actualSaveLocation = getFullFileLocation(saveLocation, getFileName(url));
        exec.setSourceFile(url);
        exec.setTargetFile(actualSaveLocation);
        exec.doSave();

        // for security reasons, pause 0.2 sec between saves, so as we don't overwhelm the server.
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void doSave() {
        ArgumentChecker.checkNotNull(onlineDirectory, saveLocation);
        Collection<String> urls = getAndFilterURLs();
        for (String url: urls) {
            saveOne(url, onlineDirectory);
        }
    }

    @Override
    public void setSourceDirectory(String urlToSave) {
        this.onlineDirectory = urlToSave;
    }

    @Override
    public String getSourceDirectory() {
        return onlineDirectory;
    }

    @Override
    public void setTargetDirectory(String saveLocation) {
        this.saveLocation = saveLocation;
    }

    @Override
    public String getTargetDirectory() {
        return saveLocation;
    }
}
