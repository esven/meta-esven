#!/bin/sh

# config part
RRD_HOME=/tmp/openhr20/

if [ ! -f "$RRD_HOME/outside_temp.rrd" ]; then
	echo -n "Creating RRD database for outside temperature... "
	rrdtool create $RRD_HOME/outside_temp.rrd \
	--step 240 \
	DS:outside:GAUGE:480:-500:5000 \
	RRA:AVERAGE:0.5:1:32850 \
	RRA:MAX:0.5:1:32850 \
	RRA:MIN:0.5:1:32850 \
	RRA:AVERAGE:0.5:4:32850 \
	RRA:MAX:0.5:4:32850 \
	RRA:MIN:0.5:4:32850 \
	RRA:AVERAGE:0.5:15:26280 \
	RRA:MAX:0.5:15:26280 \
	RRA:MIN:0.5:15:26280
	echo "done!"
fi

while true; do
	export `cat /proc/$(pidof java)/environ | grep DBUS`
	CURRENTTEMP=`dbus-send --session --print-reply --reply-timeout=10200 --type=method_call --dest=com.in2soft.remote.client.dbus /com/in2soft/remote/client/ComDialogDBusApi com.in2soft.remote.client.dbus.IDBusComDialogApi.readDatapoints string:"LL_BAS00_0262_RFBASE_PR_EBUS" int32:22 int32:0 array:string:"OutsideTemperatureSensor" | sed -n 's/.*string \"\([-0-9]\+[.][0-9]\+\)\"/\1/p'`
	if [ "x${CURRENTTEMP}" != "x" ]; then
		echo "Current Temperature: $CURRENTTEMP"
		rrdtool update "$RRD_HOME/outside_temp.rrd" N:${CURRENTTEMP}
	else
		echo "Could not read current Temperature: ${CURRENTTEMP}"
	fi
	sleep 120
done

