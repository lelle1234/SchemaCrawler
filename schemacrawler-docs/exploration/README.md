<!-- markdownlint-disable MD024 -->
# Explore a New Database with SchemaCrawler

### Setup

- Download an SQLite version of the [Sakila database](https://dev.mysql.com/doc/sakila/en/)
  `wget -N -q --show-progress https://github.com/ivanceras/sakila/raw/master/sqlite-sakila-db/sakila.db`
- To start SchemaCrawler, run
  `docker run -v "$(pwd)":/home/schcrwlr/share --name schemacrawler --rm -i -t --entrypoint=/bin/bash schemacrawler/schemacrawler:v16.15.7`


## Tutorial

- List all the tables in the database
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --info-level minimum --command list`
- List only film related tables in the database
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --info-level minimum --command list --grep-tables film.*`
- Explore the "film" table in detail
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --no-info --info-level maximum --command details --grep-tables film`
- See table relationships in a diagram
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --no-info --info-level standard --command schema --grep-tables film --output-file share/film-table.pdf`
- See child table relationships in a diagram
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --no-info --info-level standard --command schema --grep-tables film --children 1 --output-file share/film-table.pdf`
- Find all tables with a "film_id" column in a diagram
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --no-info --info-level standard --command schema --grep-columns .*\.film_id --output-file share/film-table.pdf`
- Guess at weak associations in a diagram
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --no-info --info-level standard --command schema --grep-columns .*\.film_id --weak-associations --output-file share/film-table.pdf`
- Find schema design problems with lint
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --no-info --info-level standard --command lint --grep-columns .*\.film_id`
- See row counts film related tables in the database
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --info-level minimum --command count --grep-tables film.*`
- See data in "film" table
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --info-level standard --command dump --grep-tables film --output-file share/film-data.html`
- Output schema to HTML, with diagram, in a single file
  `schemacrawler --url "jdbc:sqlite:share/sakila.db" --no-info --info-level maximum --command details --grep-tables film --output-format htmlx --output-file share/film-table.html`
- *TODO*: Diff with another version of Sakila
- *TODO*: Open in dbdiagram.io and edit the film text table schema, and generate new scripts

### Tear Down

- To stop the SchemaCrawler Docker container, run
  `docker--commandompose -f schemacrawler.yml down -t0`
