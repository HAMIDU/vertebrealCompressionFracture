package com.karizma.onlineshopping.base;

public interface CompositePasswordEncoderService {

    byte[] encode(String username, String password);

    boolean check(byte[] var1, byte[] var2);
}
