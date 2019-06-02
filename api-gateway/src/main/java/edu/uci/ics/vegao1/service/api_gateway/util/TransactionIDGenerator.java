package edu.uci.ics.vegao1.service.api_gateway.util;

import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

public class TransactionIDGenerator {

    private static final int ID_SIZE = 64;

    private TransactionIDGenerator() {
    }

    public static String generateTransactionID() {
        SecureRandom rngesus = new SecureRandom();
        byte[] id = new byte[ID_SIZE];
        rngesus.nextBytes(id);
        return Hex.encodeHexString(id);
    }
}
