package org.informatics.service.impl;

import org.informatics.data.Biatlon;
import org.informatics.service.BiatlonSerializationService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BiatlonSerializationServiceImpl implements BiatlonSerializationService {

    @Override
    public void serializeBiatlon(Biatlon biatlon, String fileName) {
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream(fileName));

            outputStream.writeObject(biatlon);
            outputStream.close();

            System.out.println("Biatlon serialized successfully to file: " + fileName);

        } catch (IOException e) {
            System.out.println("Error while serializing biatlon.");
        }
    }

    @Override
    public Biatlon deserializeBiatlon(String fileName) {
        try {
            ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream(fileName));

            Biatlon biatlon = (Biatlon) inputStream.readObject();
            inputStream.close();

            System.out.println("Biatlon deserialized successfully from file: " + fileName);

            return biatlon;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while deserializing biatlon.");
            return null;
        }
    }
}