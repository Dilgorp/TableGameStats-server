package ru.dilgorp.java.stats.game.table.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.dilgorp.java.stats.game.table.domain.AppUser;
import ru.dilgorp.java.stats.game.table.domain.dto.UserDTO;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;

import java.util.Optional;

/**
 * Отвечает за преобразование пользователей
 */
@Component
@Qualifier("UserMapper")
@RequiredArgsConstructor
public class UserMapper implements Mapper<AppUser, UserDTO> {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * Преобразовывает {@link UserDTO} в {@link AppUser}
     *
     * @param model {@link UserDTO}, пользователь передаваемый клиенту (получаемый от клиента)
     * @return {@link AppUser}, пользователь системы
     */
    @Override
    public AppUser toEntity(UserDTO model) {
        if (model.getUuid() == null) {
            return newAppUser(model);
        }

        Optional<AppUser> byId = appUserRepository.findById(model.getUuid());
        return byId.map(appUser -> {
            AppUser newAppUser = new AppUser();
            newAppUser.setUuid(appUser.getUuid());
            newAppUser.setPassword(encoder.encode(model.getPassword()));
            newAppUser.setUsername(model.getUsername());
            newAppUser.setAuthorities(model.getAuthorities());
            return newAppUser;
        }).orElseGet(() -> newAppUser(model));
    }

    /**
     * Преобразовывет {@link AppUser} в {@link UserDTO}
     *
     * @param entity {@link AppUser}, пользователь системы
     * @return {@link UserDTO}, пользователь передаваемый клиенту (получаемый от клиента)
     */
    @Override
    public UserDTO toModel(AppUser entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(entity.getUuid());
        userDTO.setUsername(entity.getUsername());
        userDTO.setAuthorities(entity.getAuthorities());
        return userDTO;
    }

    private AppUser newAppUser(UserDTO transferObject) {
        AppUser appUser = new AppUser();
        appUser.setUuid(transferObject.getUuid());
        appUser.setUsername(transferObject.getUsername());
        appUser.setPassword(encoder.encode(transferObject.getPassword()));
        appUser.setAuthorities(transferObject.getAuthorities());
        return appUser;
    }
}
