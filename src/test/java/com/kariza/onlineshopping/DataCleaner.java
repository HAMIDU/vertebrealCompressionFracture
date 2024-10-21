package com.kariza.onlineshopping;

import com.karizma.onlineshopping.iam.repository.SecurityUserRepository;
import com.karizma.onlineshopping.iam.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataCleaner {
    private final SecurityUserRepository securityUserRepository;
    private final UserProfileRepository userProfileRepository;

    public void deleteAllData() {
        userProfileRepository.deleteAll();
        securityUserRepository.deleteAll();
    }
}
