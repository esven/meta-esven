#!/bin/sh

IDS="1 2 3 4 5 6"

if [ ! -d "/var/backups/openhr20" ]; then
  mkdir -p "/var/backups/openhr20"
fi

sqlite3 /tmp/openhr20.sqlite ".dump" | gzip > /var/backups/openhr20/openhr20.sql.gz
for id in `echo $IDS`; do
  rrdtool dump /tmp/openhr20/openhr20_${id}.rrd | gzip > /var/backups/openhr20/openhr20_${id}.rrd.gz
done
rrdtool dump /tmp/openhr20/outside_temp.rrd | gzip > /var/backups/openhr20/outside_temp.rrd.gz
sync
