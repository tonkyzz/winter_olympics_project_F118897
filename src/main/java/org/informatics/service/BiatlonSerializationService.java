package org.informatics.service;

import org.informatics.data.Biatlon;

public interface BiatlonSerializationService {

    void serializeBiatlon(Biatlon biatlon, String fileName);

    Biatlon deserializeBiatlon(String fileName);
}