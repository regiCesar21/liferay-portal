# DB2 Data Partitioning SQL Builder

The DB2 Data Partitioning SQL Builder tool lets you physically separate a
Liferay Portal instance from an already existing *logically partitioned* Liferay
Portal instance. An existing installation is considered logically partitioned
when several companies are defined in its database (i.e., the database's
`Company` table has multiple rows). When using the partitioning tool, you can
move a subset of the existing companies to another database to create a
completely new and isolated Liferay Portal instance.

For more information, read the
[Data Partitioning tool's README](https://github.com/liferay/liferay-portal/blob/master/modules/util/portal-tools-data-partitioning-sql-builder/README.markdown).

## DB2 Special Considerations

Due to DB2's CLOBs management nature, you're forced to follow a different
approach to the one showed by other providers like Oracle or MySQL.

Instead of contributing to the template algorithm, you'll use the native tools
provided by the database. Note, one of the design goals of this tool is allowing
users of the tool to contribute a custom provider (usually based on native
database tools) which could outperform the default approach.

### CLOBs

DB2 cannot import a String with more than 16336 characters, as described
here:

    the string constant beginning with string has a length greater than
    32672 bytes. Character strings with lengths greater than 32672 bytes
    or graphic strings with lengths greater than 16336 characters can be
    specified only through assignment from host variables. Note that other
    servers in the DB2 family of products may specify a different size
    limit for character strings

Therefore, you must use DB2 native commands to export the data present on the
initial database to import it into the target database. For more information,
see the [error explanation](https://www.ibm.com/support/knowledgecenter/SSEPGG_10.5.0/com.ibm.db2.luw.messages.sql.doc/doc/msql00102n.html).

### Exporting Tables

The tool produces one or more files for you to execute on a target database.

For tables **without** CLOB columns, the files will contain commands similar
to the following one:

    db2 export to CLASSNAME_.ixf of ixf messages CLASSNAME_.txt select * from CLASSNAME_

For tables **with** CLOB columns, the files will contain commands similar
to the following one:

    db2 export to ASSETENTRY.del of del lobs to /tmp/lobexport/ lobfile lobs1, lobs2 modified by lobsinfile select * from ASSETENTRY

To better understand the export command above, you can examine what each part
means:

- `/tmp/lobexport/`: specifies one or more paths to directories where the LOB
files are to be stored. There will be at least one file per LOB path, and each
file will contain at least one LOB. The maximum number of paths that can be
specified is 999.

- `lobs1` and `lobs2`: the base file names for the LOB files. When the namespace
is exhausted for the first name, the second name is used, and so on.

When creating LOB files during an export operation, file names are constructed
by appending the current base name from this list to the current path (from
lob-path), and then appending a 3-digit sequence number to start and the three
character identifier LOB. For example, if the current LOB path is the directory
`/u/foo/lob/path/`, and the current LOB file name is `bar`, the LOB files
created will be `/u/foo/lob/path/bar.001.lob`, `/u/foo/lob/path/bar.002.lob`,
etc. The 3-digit sequence number in the LOB file name will grow to 4-digits once
999 is used, 4-digits will grow to 5-digits once 9999 is used, etc.

For more reference about exporting data on DB2, read
[http://www.ibm.com/support/knowledgecenter/SSEPGG_10.5.0/com.ibm.db2.luw.admin.cmd.doc/doc/r0008303.html](http://www.ibm.com/support/knowledgecenter/SSEPGG_10.5.0/com.ibm.db2.luw.admin.cmd.doc/doc/r0008303.html)

### Importing Tables

The tool produces one or more files for you to execute against the target
database of your choice.

For tables **without** CLOB columns, the files will contain commands similar
to the following one:

    db2 import from CLASSNAME_.ixf of ixf messages CLASSNAME_.txt insert into CLASSNAME_

For tables **with** CLOB columns, the files will contain something similar
to the following command:

    db2 import ASSETENTRY.del of del lobs from /tmp/data modified by lobsinfile insert inyo ASSETENTRY

For more information about importing CLOB data in DB2, please read
[http://www.ibm.com/support/knowledgecenter/SSEPGG_10.5.0/com.ibm.db2.luw.admin.dm.doc/doc/c0004581.html](http://www.ibm.com/support/knowledgecenter/SSEPGG_10.5.0/com.ibm.db2.luw.admin.dm.doc/doc/c0004581.html).