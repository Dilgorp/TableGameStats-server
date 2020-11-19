package ru.dilgorp.java.stats.game.table.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.dilgorp.java.stats.game.table.domain.AppUser;
import ru.dilgorp.java.stats.game.table.domain.dto.UserDTO;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;

import java.util.Optional;

@Component
@Qualifier("UserMapper")
@RequiredArgsConstructor
public class UserMapper implements Mapper<AppUser, UserDTO> {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * Преобразовывает {@link UserDTO} в {@link AppUser}
     *
     * @param transferObject {@link UserDTO}, пользователь передаваемый клиенту (получаемый от клиента)
     * @return {@link AppUser}, пользователь системы
     */
    @Override
    public AppUser toDocument(UserDTO transferObject) {
        if (transferObject.getUuid() == null) {
            return newAppUser(transferObject);
        }

        Optional<AppUser> byId = appUserRepository.findById(transferObject.getUuid());
        return byId.map(appUser -> {
            AppUser newAppUser = new AppUser();
            newAppUser.setUuid(appUser.getUuid());
            newAppUser.setPassword(encoder.encode(transferObject.getPassword()));
            newAppUser.setUsername(transferObject.getUsername());
            newAppUser.setAuthorities(transferObject.getAuthorities());
            return newAppUser;
        }).orElseGet(() -> newAppUser(transferObject));
    }

    /**
     * Преобразовывет {@link AppUser} в {@link UserDTO}
     *
     * @param document {@link AppUser}, пользователь системы
     * @return {@link UserDTO}, пользователь передаваемый клиенту (получаемый от клиента)
     */
    @Override
    public UserDTO toTransferObject(AppUser document) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(document.getUuid());
        userDTO.setUsername(document.getUsername());
        userDTO.setAuthorities(document.getAuthorities());
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
