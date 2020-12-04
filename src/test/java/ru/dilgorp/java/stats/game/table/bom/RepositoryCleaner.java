package ru.dilgorp.java.stats.game.table.bom;

import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.repository.*;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;
import ru.dilgorp.java.stats.game.table.repository.PlayerRepository;

@Service
public class RepositoryCleaner {
    private final GameRepository gameRepository;
    private final RoundRepository roundRepository;
    private final MurderEventRepository murderEventRepository;
    private final ParticipantRepository participantRepository;
    private final MagicianRepository magicianRepository;
    private final PlayerRepository playerRepository;
    private final AppUserRepository appUserRepository;

    public RepositoryCleaner(
            GameRepository gameRepository,
            RoundRepository roundRepository,
            MurderEventRepository murderEventRepository,
            ParticipantRepository participantRepository,
            MagicianRepository magicianRepository,
            PlayerRepository playerRepository,
            AppUserRepository appUserRepository
    ) {
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
        this.murderEventRepository = murderEventRepository;
        this.participantRepository = participantRepository;
        this.magicianRepository = magicianRepository;
        this.playerRepository = playerRepository;
        this.appUserRepository = appUserRepository;
    }

    public void clean(){
        gameRepository.deleteAll();
        roundRepository.deleteAll();
        murderEventRepository.deleteAll();
        participantRepository.deleteAll();
        magicianRepository.deleteAll();
        playerRepository.deleteAll();
        appUserRepository.deleteAll();
    }
}
