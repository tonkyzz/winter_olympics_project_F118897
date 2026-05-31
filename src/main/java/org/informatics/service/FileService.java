package org.informatics.service;

import org.informatics.data.Olympics;

public interface FileService {

    void saveFinalResultsToFile(Olympics olympics, String fileName);
}
