package ru.dilgorp.java.stats.game.table.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.stats.game.table.domain.AppUser;
import ru.dilgorp.java.stats.game.table.domain.dto.UserDTO;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;
import ru.dilgorp.java.stats.game.table.response.Response;
import ru.dilgorp.java.stats.game.table.response.ResponseType;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Контроллер, обеспечиваюющий работу с пользователями
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final AppUserRepository appUserRepository;
    private final Mapper<AppUser, UserDTO> mapper;

    /**
     * Возвращает список пользователей
     *
     * @return Ответ, содержащий список пользователей
     */
    @GetMapping
    public Response<Collection<UserDTO>> getUsers() {
        return new Response<>(
                ResponseType.SUCCESS,
                null,
                mapper.documentsToTransferObject(appUserRepository.findAll())
        );
    }

    /**
     * Добавляет нового пользователя.
     * Если пользователь с указанным именем уже существует, возвращает ошибку
     *
     * @param user новый пользователь
     * @return Ответ, содержащий данные потзователя
     * SUCCESS - в случае успешного выполнения, ERROR - если произошла ошибка
     */
    @PostMapping
    public Response<UserDTO> postUser(@RequestBody final UserDTO user) {
        AppUser appUser = appUserRepository.findByUsername(user.getUsername());
        if (appUser != null) {
            return new Response<>(
                    ResponseType.ERROR,
                    String.format("Пользователь с именем '%s' уже сущетсвует", user.getUsername()),
                    null
            );
        }

        return new Response<>(
                ResponseType.SUCCESS,
                null,
                mapper.toTransferObject(appUserRepository.save(mapper.toDocument(user)))
        );
    }

    /**
     * Возвращает пользователя по переданному идентификатору
     *
     * @param uuid идентификатор пользователя
     * @return Ответ, содержащий данные потзователя
     * SUCCESS - в случае успешного выполнения, ERROR - если произошла ошибка
     */
    @GetMapping("/{uuid}")
    public Response<UserDTO> getUser(@PathVariable("uuid") final UUID uuid) {
        Optional<AppUser> byId = appUserRepository.findById(uuid);

        return byId.map(appUser -> new Response<>(
                ResponseType.SUCCESS,
                null,
                mapper.toTransferObject(appUser)
        )).orElseGet(() -> new Response<>(
                ResponseType.ERROR,
                String.format("Пользователь не найден по идентификатору '%s'", uuid),
                null
        ));
    }

    /**
     * Обновляет данные пользователя.
     * Если пользователь не существует, возвращает ошибку с соответствующим текстом
     *
     * @param user пользователь для записи
     * @return Ответ, содержащий данные потзователя
     * SUCCESS - в случае успешного выполнения, ERROR - если произошла ошибка
     */
    @PutMapping
    public Response<UserDTO> putUser(@RequestBody final UserDTO user) {
        Optional<AppUser> byId = appUserRepository.findById(user.getUuid());

        return byId.map(appUser -> new Response<>(
                ResponseType.SUCCESS,
                null,
                mapper.toTransferObject(appUserRepository.save(mapper.toDocument(user)))
        )).orElseGet(() -> new Response<>(
                ResponseType.ERROR,
                String.format("Пользователь не найден по идентификатору '%s'", user.getUuid()),
                null
        ));
    }

    /**
     * Удаляет пользователя из системы.
     * Если пользователь не найден, возвращает ошибку
     *
     * @param uuid идентификатор пользователя
     * @return Ответ, SUCCESS - в случае успешного выполнения, ERROR - если произошла ошибка
     */
    @DeleteMapping("/{uuid}")
    public Response<UserDTO> deleteUser(@PathVariable("uuid") final UUID uuid) {
        Optional<AppUser> byId = appUserRepository.findById(uuid);

        Response<UserDTO> response;
        if (byId.isPresent()) {
            appUserRepository.deleteById(uuid);
            response = new Response<>(ResponseType.SUCCESS, null, null);
        } else {
            response = new Response<>(
                    ResponseType.ERROR,
                    String.format("Пользователь не найден по идентификатору '%s'", uuid),
                    null
            );
        }

        return response;
    }

}
