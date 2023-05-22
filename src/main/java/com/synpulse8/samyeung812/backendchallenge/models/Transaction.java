package com.synpulse8.samyeung812.backendchallenge.models;

import lombok.*;

import java.io.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements Serializable {
    private String transactionID;
    private String currency;
    private String accountIBAN;
    private String date;
    private String description;

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(transactionID);
        out.writeUTF(currency);
        out.writeUTF(accountIBAN);
        out.writeUTF(date);
        out.writeUTF(description);
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        transactionID = in.readUTF();
        currency = in.readUTF();
        accountIBAN = in.readUTF();
        date = in.readUTF();
        description = in.readUTF();
    }

    @Serial
    private void readObjectNoData() throws ObjectStreamException {
        transactionID = "";
        currency = "";
        accountIBAN = "";
        date = "";
        description = "";
    }
}
