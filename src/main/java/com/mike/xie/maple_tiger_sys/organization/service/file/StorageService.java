package com.mike.xie.maple_tiger_sys.organization.service.file;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	
	void init();
	void initWithPath(String path);

    String store(MultipartFile file);
    String storeWithNewFileName(MultipartFile file, String newFileName);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
    void deleteFile(String filePath);
}
