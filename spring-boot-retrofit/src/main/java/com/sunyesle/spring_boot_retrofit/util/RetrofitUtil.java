package com.sunyesle.spring_boot_retrofit.util;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class RetrofitUtil {
    public static <T> Optional<T> requestSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            log.info("execute: {}", response);
            
            if (response.isSuccessful()) {
                return Optional.ofNullable(response.body());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
}
