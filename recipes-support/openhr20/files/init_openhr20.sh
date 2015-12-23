#!/bin/sh

IDS="1 2 3 4 5 6"

if [ -f "/var/backups/openhr20/openhr20.sql.gz" ]; then
  zcat /var/backups/openhr20/openhr20.sql.gz | sqlite3 /tmp/openhr20.sqlite
else
  /usr/bin/php /home/hr20/create_db.php
fi

/bin/mkdir -p /tmp/openhr20/plots

for id in `echo $IDS`; do
  if [ -f "/var/backups/openhr20/openhr20_${id}.rrd.gz" ]; then
    zcat /var/backups/openhr20/openhr20_${id}.rrd.gz | rrdtool restore - /tmp/openhr20/openhr20_${id}.rrd -f
  else
    /home/hr20/create_rrd ${id}
  fi
done

