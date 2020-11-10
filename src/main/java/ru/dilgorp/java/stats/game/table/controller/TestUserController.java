package ru.dilgorp.java.stats.game.table.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.stats.game.table.domain.AppUser;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;
import ru.dilgorp.java.stats.game.table.response.Response;
import ru.dilgorp.java.stats.game.table.response.ResponseType;

import java.util.Collections;
import java.util.List;

import static ru.dilgorp.java.stats.game.table.domain.auth.Authority.ALL;

@RestController
@RequiredArgsConstructor
public class TestUserController {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/")
    public Response<AppUser> getTestUser() {
        AppUser admin = new AppUser(
                "randomUser",
                encoder.encode("randomUser"),
                Collections.emptyList()
        );


        return new Response<>(
                ResponseType.SUCCESS,
                null,
                admin
        );
    }

    @GetMapping("/admin")
    public Response<AppUser> getAdmin() {
        AppUser admin = new AppUser(
                "admin",
                encoder.encode("admin"),
                List.of(ALL)
        );


        return new Response<>(
                ResponseType.SUCCESS,
                null,
                admin
        );
    }
}
