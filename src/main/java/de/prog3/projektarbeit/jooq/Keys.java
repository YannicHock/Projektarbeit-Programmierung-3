/*
 * This file is generated by jOOQ.
 */
package de.prog3.projektarbeit.jooq;


import de.prog3.projektarbeit.jooq.tables.Player;
import de.prog3.projektarbeit.jooq.tables.records.PlayerRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<PlayerRecord> PLAYER__PK_PLAYER = Internal.createUniqueKey(Player.PLAYER, DSL.name("pk_player"), new TableField[] { Player.PLAYER.ID }, true);
}
