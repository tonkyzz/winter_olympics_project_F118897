package org.informatics.data;

import javax.xml.crypto.Data;
import java.util.List;

public class Biatlon extends Competition {

    public Biatlon(String name, Gender gender, int minimumAge, List<Participant> participants) {
        super(name, gender, minimumAge, participants);
    }

    @Override
    public void finalResults() {

    }
}
