package com.qarepo.actions;

/*
 * @since 1.0.0
 */
public interface UserAccessActions {

    /**
     * @param registrationInfo credentials for registration name, email etc
     */
    void register(String... registrationInfo);

    /**
     * @param username login username String
     * @param password login password String
     */
    void login(String username, String password);

    void logout();
}
