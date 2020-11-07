package ru.dilgorp.java.stats.game.table.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.stats.game.table.domain.AppUser;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;
import ru.dilgorp.java.stats.game.table.response.Response;
import ru.dilgorp.java.stats.game.table.response.ResponseType;

import java.util.List;

import static ru.dilgorp.java.stats.game.table.domain.auth.Authority.ALL;

@RestController
@RequiredArgsConstructor
public class TestUserController {

    private final AppUserRepository appUserRepository;

    @GetMapping("/")
    public Response<AppUser> getTestUser() {
        AppUser admin = new AppUser("admin", "admin", List.of(ALL));

        return new Response<>(
                ResponseType.SUCCESS,
                null,
                admin
        );
    }
}
