package com.sunyesle.spring_boot_api_versioning.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTOv1 toV1(User user) {
        return new UserDTOv1(
                user.id(),
                user.name(),
                user.email()
        );
    }

    public UserDTOv2 toV2(User user) {
        String[] nameParts = splitName(user.name());
        return new UserDTOv2(
                user.id(),
                nameParts[0],
                nameParts[1],
                user.email()
        );
    }

    private String[] splitName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return new String[]{"", ""};
        }

        String trimmed = fullName.trim();
        int lastSpaceIndex = trimmed.lastIndexOf(" ");

        if (lastSpaceIndex == -1) {
            return new String[]{trimmed, ""};
        }

        return new String[]{
                trimmed.substring(0, lastSpaceIndex),
                trimmed.substring(lastSpaceIndex + 1)
        };
    }
}
