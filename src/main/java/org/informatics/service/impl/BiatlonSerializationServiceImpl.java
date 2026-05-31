package org.informatics.service.impl;

import org.informatics.data.Biatlon;
import org.informatics.exceptions.BiatlonSerializationException;
import org.informatics.service.BiatlonSerializationService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BiatlonSerializationServiceImpl implements BiatlonSerializationService {

    @Override
    public void serializeBiatlon(Biatlon biatlon, String fileName) {
        validateBiatlon(biatlon);
        validateFileName(fileName);

        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            outputStream.writeObject(biatlon);

            System.out.println("Biatlon serialized successfully to file: " + fileName);

        } catch (IOException e) {
            throw new BiatlonSerializationException(
                    "Error while serializing biatlon to file: " + fileName,
                    e
            );
        }
    }

    @Override
    public Biatlon deserializeBiatlon(String fileName) {
        validateFileName(fileName);

        try (ObjectInputStream inputStream =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            Biatlon biatlon = (Biatlon) inputStream.readObject();

            System.out.println("Biatlon deserialized successfully from file: " + fileName);

            return biatlon;

        } catch (IOException e) {
            throw new BiatlonSerializationException(
                    "Error while reading biatlon from file: " + fileName,
                    e
            );

        } catch (ClassNotFoundException e) {
            throw new BiatlonSerializationException(
                    "Class Biatlon was not found during deserialization.",
                    e
            );

        } catch (ClassCastException e) {
            throw new BiatlonSerializationException(
                    "The file does not contain a valid Biatlon object.",
                    e
            );
        }
    }

    private void validateBiatlon(Biatlon biatlon) {
        if (biatlon == null) {
            throw new BiatlonSerializationException("Biatlon cannot be null.");
        }
    }

    private void validateFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new BiatlonSerializationException("File name cannot be empty.");
        }
    }
}