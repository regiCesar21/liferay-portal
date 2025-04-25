# Oracle Data Partitioning SQL Builder

The Oracle Data Partitioning SQL Builder tool lets you physically separate a
Liferay Portal instance from an already existing *logically partitioned* Liferay
Portal instance. An existing installation is considered logically partitioned
when several companies are defined in its database (i.e., the database's
`Company` table has multiple rows). When using the partitioning tool, you can
move a subset of the existing companies to another database to create a
completely new and isolated Liferay Portal instance.

For more information, read the [Data Partitioning tool's README](https://github.com/liferay/liferay-portal/blob/master/modules/util/portal-tools-data-partitioning-sql-builder/README.markdown).

## Oracle Special Considerations

Due to Oracle's CLOBs management nature, you're forced to follow a different
approach to the one showed by other providers like MySQL.

Instead of contributing to the template algorithm, you'll use the native tools
provided by the database. Note, one of the design goals of this tool is allowing
users of the tool to contribute a custom provider (usually based on native
database tools) which could outperform the default approach.

### CLOBs

Oracle databases cannot import a String with more than 4000 characters, as
described here:

    SQL Error: ORA-01704: string literal too long
    01704. 00000 -  "string literal too long"
    *Cause:    The string literal is longer than 4000 characters.
    *Action:   Use a string literal of at most 4000 characters.
               Longer values may only be entered using bind variables.

You must also use Oracle database native commands to export the data present on
the initial database to import it into the target database.

### Exporting Tables

The tool produces one or more files for you to execute on a target database.

For all tables, with or without CLOB columns, the files will contain commands
similar to the following one:

    expdp system/oracle@xe tables=ASSETENTRY directory=DATA_PUMP_DIR dumpfile=ASSETENTRY.dmp logfile=ASSETENTRY.log

To better understand the export command above, you can examine what each part
means:

- `system`: the Oracle user to create the connection.

- `oracle`: the Oracle user's password.

- `xe`: the Oracle SID of the schema.

- `tables`: the tables (one or more).

- `directory`<a name="exportingDir"></a>: the data pump directory defined by the Oracle database. The
default directory for data pump commands is obtained by executing the following
query on Oracle's SQL*Plus tool:

            select * from dba_directories;

    The default result for that query is `DATA_PUMP_DIR`. To execute this tool,
    however, database administrator must confirm that the data pump directory
    has not been changed, because it's common to have several different data
    pump directories for performance purposes.

    If you want to change the data pump directory, or want to use a different
    one than the default, the generated script must be edited by replacing
    `DATA_PUMP_DIR` with the SQL result of the field `DIRECTORY_NAME` from the
    previous SQL query.

- `dumpfile`: the name of the dump file where the table is exported.

- `logfile`: the name of the log file generated during the export process.

## Importing Tables

The tool produces one or more files for you to execute against the target
database of your choice.

    impdp system/oracle@xe directory=DATA_PUMP_DIR dumpfile=ASSETENTRY.dmp full=yes logfile=impdp_ASSETENTRY.log

To better understand the import command above, you can examine what each part
means:

- `system`: the Oracle user to create the connection.

- `oracle`: the Oracle user's password.

- `xe`: the schema's Oracle SID.

- `directory`: See the Exporting Tables section's [directory](#exportingDir)
parameter.

- `dumpfile`: the name of the dump file where the table is imported.

- `logfile`: the name of the log file generated during the import process.

## Oracle Database Environment

The following environment variables must be set in the host:

- `ORACLE_HOME`: the path to Oracle's home folder.
- `ORACLE_SID`: the SID of the Oracle schema (e.g., `xe`).
- `TNS_ADMIN`: the path to the `TNS_NAMES.ora`, which is usually located in
`$ORACLE_HOME/network/admin`.