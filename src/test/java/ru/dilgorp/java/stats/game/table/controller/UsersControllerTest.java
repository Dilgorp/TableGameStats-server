package ru.dilgorp.java.stats.game.table.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.config.MappersConfig;
import ru.dilgorp.java.stats.game.table.config.RepositoriesConfig;
import ru.dilgorp.java.stats.game.table.config.TestConfig;
import ru.dilgorp.java.stats.game.table.domain.AppUser;
import ru.dilgorp.java.stats.game.table.domain.dto.UserDTO;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.generator.UserGenerator;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;
import ru.dilgorp.java.stats.game.table.response.Response;
import ru.dilgorp.java.stats.game.table.response.ResponseType;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Класс для тестирования {@link UsersController}
 */
@SpringJUnitConfig({RepositoriesConfig.class, TestConfig.class, MappersConfig.class})
class UsersControllerTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private Mapper<AppUser, UserDTO> mapper;

    @Autowired
    private UserGenerator userGenerator;

    private UsersController usersController;

    /**
     * Перед каждым тестом создаем контроллер, который тестируем
     */
    @BeforeEach
    void setUp() {
        usersController = new UsersController(appUserRepository, mapper);
    }

    /**
     * После каждого теста, очищаем репозиторий
     */
    @AfterEach
    void tearDown() {
        appUserRepository.deleteAll();
    }

    /**
     * Проверяем, что список пользователей возвращается корректный
     */
    @Test
    public void getUsersIsCorrect(){
        // given
        Collection<AppUser> givenUsers = userGenerator.generate(3);
        appUserRepository.saveAll(givenUsers);
        Response<Collection<UserDTO>> givenResponse = new Response<>(
                SUCCESS,
                null,
                mapper.documentsToTransferObject(givenUsers)
        );

        // when
        Response<Collection<UserDTO>> gettingResponse = usersController.getUsers();

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что пользователь добавляется корректно
     */
    @Test
    public void postUserIsCorrect(){
        // given
        UserDTO givenUser = userGenerator.generateDTO(1).get(0);
        Response<UserDTO> givenResponse = new Response<>(
                SUCCESS,
                null,
                mapper.toTransferObject(mapper.toDocument(givenUser))
        );

        // when
        Response<UserDTO> gettingResponse = usersController.postUser(givenUser);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что при попытке вставить уже имеющегося пользователя возвращается ошибка
     */
    @Test
    public void postUserError(){
        // given
        UserDTO givenUser = userGenerator.generateDTO(1).get(0);
        AppUser givenAppUser = mapper.toDocument(givenUser);
        appUserRepository.save(givenAppUser);
        Response<UserDTO> givenResponse = new Response<>(
                ResponseType.ERROR,
                String.format("Пользователь с именем '%s' уже сущетсвует", givenUser.getUsername()),
                null
        );

        // when
        Response<UserDTO> gettingResponse = usersController.postUser(givenUser);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверям, что возвращается корректный пользователь
     */
    @Test
    public void getUserIsCorrect(){
        // given
        AppUser givenUser = userGenerator.generate(1).get(0);
        appUserRepository.save(givenUser);
        Response<UserDTO> givenResponse = new Response<>(
                SUCCESS,
                null,
                mapper.toTransferObject(givenUser)
        );

        // when
        Response<UserDTO> gettingResponse = usersController.getUser(givenUser.getId());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что при попытке получить пользователя по несущствующему id, возвращается ошибка
     */
    @Test
    public void getUserError(){
        // given
        String id = "NotExistId";
        Response<UserDTO> givenResponse = new Response<>(
                ERROR,
                String.format("Пользователь не найден по идентификатору '%s'", id),
                null
        );

        // when
        Response<UserDTO> gettingResponse = usersController.getUser(id);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что данные пользователя обновляются корректно
     */
    @Test
    public void putUserIsCorrect(){
        // given
        UserDTO givenUser = userGenerator.generateDTO(1).get(0);
        AppUser givenAppUser = mapper.toDocument(givenUser);
        appUserRepository.save(givenAppUser);
        givenAppUser.setUsername("new Username");
        Response<UserDTO> givenResponse = new Response<>(
                SUCCESS,
                null,
                mapper.toTransferObject(mapper.toDocument(givenUser))
        );

        // when
        Response<UserDTO> gettingResponse = usersController.putUser(givenUser);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что при попытке обновить несуществующего пользователя, возвращается ошибка
     */
    @Test
    public void putUserError(){
        // given
        UserDTO givenUser = userGenerator.generateDTO(1).get(0);
        Response<UserDTO> givenResponse = new Response<>(
                ERROR,
                String.format("Пользователь не найден по идентификатору '%s'", givenUser.getId()),
                null
        );

        // when
        Response<UserDTO> gettingResponse = usersController.putUser(givenUser);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что пользователь корректно удаляется
     */
    @Test
    public void deleteUserIsCorrect(){
        // given
        AppUser givenUser = userGenerator.generate(1).get(0);
        appUserRepository.save(givenUser);
        Response<UserDTO> givenResponse = new Response<>(SUCCESS, null, null);

        // when
        Response<UserDTO> gettingResponse = usersController.deleteUser(givenUser.getId());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что при попытке удалить несуществующего пользователя, возвращается ошибка
     */
    @Test
    public void deleteUserError(){
        // given
        AppUser givenUser = userGenerator.generate(1).get(0);
        Response<UserDTO> givenResponse = new Response<>(
                ERROR,
                String.format("Пользователь не найден по идентификатору '%s'", givenUser.getId()),
                null
        );

        // when
        Response<UserDTO> gettingResponse = usersController.deleteUser(givenUser.getId());

        // then
        assertEquals(givenResponse, gettingResponse);
    }
}




