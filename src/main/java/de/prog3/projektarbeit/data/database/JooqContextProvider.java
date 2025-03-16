package de.prog3.projektarbeit.data.database;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import javax.sql.DataSource;

import org.jooq.SQLDialect;

public class JooqContextProvider {

    private static DSLContext dslContext;

    public static void init(){
        DataSourceProvider.init();
        DataSource ds = DataSourceProvider.getDataSource();

        dslContext = DSL.using(ds, SQLDialect.SQLITE);
    }

    public static DSLContext getDSLContext() {
        if (dslContext == null) {
            DataSource ds = DataSourceProvider.getDataSource();

            dslContext = DSL.using(ds, SQLDialect.SQLITE);
        }
        return dslContext;
    }
}