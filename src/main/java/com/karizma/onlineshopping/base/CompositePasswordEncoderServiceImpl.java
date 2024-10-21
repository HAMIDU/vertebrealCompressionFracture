package com.karizma.onlineshopping.base;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class CompositePasswordEncoderServiceImpl implements CompositePasswordEncoderService {


    @Override
    public byte[] encode(String username, String password) {
        byte[] usernameBytes = username.toLowerCase().getBytes();
        byte[] passwordBytes = password.getBytes();
        return DigestUtils.sha256(concatenate(usernameBytes, passwordBytes));
    }

    /**
     * return concatenated two bytes array together.
     *
     * @param firstByteArray  it's fist input byte array.
     * @param secondByteArray it's second input byte array.
     * @return byte[] of concatenated two bytes array together.
     */
    private byte[] concatenate(byte[] firstByteArray, byte[] secondByteArray) {
        var concatResult = new byte[firstByteArray.length + secondByteArray.length];

        System.arraycopy(firstByteArray, 0, concatResult, 0, firstByteArray.length);
        System.arraycopy(secondByteArray, 0, concatResult, firstByteArray.length, secondByteArray.length);

        return concatResult;
    }

    public boolean check(byte[] oldHash, byte[] newHash) {
        return Arrays.equals(oldHash, newHash);
    }
}
