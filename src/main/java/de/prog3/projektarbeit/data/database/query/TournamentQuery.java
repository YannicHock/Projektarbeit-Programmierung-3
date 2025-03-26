package de.prog3.projektarbeit.data.database.query;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.objects.Tournament;
import de.prog3.projektarbeit.exceptions.TournamentNotFoundException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static de.prog3.projektarbeit.data.jooq.tables.Player.PLAYER;
import static de.prog3.projektarbeit.data.jooq.tables.Tournament.TOURNAMENT;

public class TournamentQuery {
    private static final Logger logger = LoggerFactory.getLogger(TournamentQuery.class);

    public List<Tournament> getAllTournaments() {
        logger.info("Lade alle Turniere");
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> result = ctx.select().from(TOURNAMENT).fetch();

        List<Tournament> tournaments = new ArrayList<>();
        result.forEach(record -> {
            Tournament tournament = new Tournament(
                    record.get(TOURNAMENT.NAME),
                    record.get(DSL.count(TOURNAMENT.ID))
            );
            tournaments.add(tournament);
            logger.debug("Gefundenes Turnier: {}", tournament.getName());
        });
        return tournaments;
    }

    public Tournament getTournamentById(int id) throws TournamentNotFoundException {
        logger.info("Lade Turnier mit ID: {}", id);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Record record = ctx.select().from(TOURNAMENT).where(TOURNAMENT.ID.eq(id)).fetchOne();

        if (record == null) {
            logger.warn("Turnier mit ID {} nicht gefunden", id);
            throw new TournamentNotFoundException("Turnier mit der ID " + id + " nicht gefunden");
        }

        return new Tournament(
                record.get(TOURNAMENT.ID),
                record.get(TOURNAMENT.NAME)
        );
    }
}