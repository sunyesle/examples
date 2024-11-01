package com.sunyesle.spring_boot_retrofit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sunyesle.spring_boot_retrofit.user.UserAPI;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
public class RetrofitConfig {
    private static final String BASE_URL = "https://reqres.in/";

    @Bean
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Bean
    public OkHttpClient okHttpClient(Interceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public Retrofit retrofit(OkHttpClient client) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        String baseURL = BASE_URL + "api/";
        return new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
    }

    @Bean
    public UserAPI userAPI(Retrofit retrofit) {
        return retrofit.create(UserAPI.class);
    }
}
