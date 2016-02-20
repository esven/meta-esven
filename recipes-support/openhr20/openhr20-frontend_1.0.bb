DESCRIPTION = "Open firmware for Honeywell Rondostat HR20 radiator thermostats"
AUTHOR = "Sven Ebenfeld"
HOMEPAGE = "http://www.mikrocontroller.net/articles/Heizungssteuerung_mit_Honeywell_HR20"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://trunk/source/license.txt;md5=59530bdf33659b29e73d4adb9f9f6552"

SRC_URI = "git://github.com/OpenHR20/OpenHR20.git;protocol=https;branch=master \
	file://0001-Add-outside-temperature-to-all-graphs.patch \
	file://read_outside_temp.sh \
	file://read_outside_temp.service \
	file://httpd_openhr20.conf \
	file://openhr20.service \
	file://openhr20-init.service \
	file://openhr20-setttyspeed.service \
	file://openhr20-httpd.service \
	file://openhr20-plot-90days.service \
	file://openhr20-plot-90days.timer \
	file://openhr20-plot-30days.service \
	file://openhr20-plot-30days.timer \
	file://openhr20-plot-7days.service \
	file://openhr20-plot-7days.timer \
	file://openhr20-plot-3days.service \
	file://openhr20-plot-3days.timer \
	file://openhr20-backup.service \
	file://openhr20-backup.timer \
	file://config.php \
	file://backup_openhr20.sh \
	file://init_openhr20.sh \
	file://openhr20-volatile.conf \
	"
SRCREV = "0092ef00b1d0f3046a658416e626b54b4b60f744"

PR = "r0"

S = "${WORKDIR}/git"

USERADD_PACKAGES = "${PN}"

USERADD_PARAM_${PN} = " -g hr20 -G dialout -d /home/hr20 -r -s /dev/null hr20"
GROUPADD_PARAM_${PN} = " hr20 "

inherit allarch useradd systemd

SYSTEMD_SERVICE_${PN} = "\
	openhr20.service \
	read_outside_temp.service \
	openhr20-init.service \
	openhr20-setttyspeed.service \
	openhr20-httpd.service \
	openhr20-plot-90days.timer \
	openhr20-plot-30days.timer \
	openhr20-plot-7days.timer \
	openhr20-plot-3days.timer \
	openhr20-backup.timer"

PACKAGES = "${PN}"

FILES_${PN} = " \
	/home/hr20 \
	${sysconfdir} \
	${systemd_unitdir}/system \
	"

RDEPENDS_${PN} = "apache2 php php-modphp php-cli php-cgi sqlite3 rrdtool xorg-minimal-fonts"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
  install -m 0755 -d ${D}${sysconfdir}/tmpfiles.d
  install ${WORKDIR}/openhr20-volatile.conf ${D}${sysconfdir}/tmpfiles.d
  install -m 0755 -d ${D}${sysconfdir}/apache2
  install ${WORKDIR}/httpd_openhr20.conf ${D}${sysconfdir}/apache2
  install -o hr20 -g hr20 -m 0755 -d ${D}/home/hr20
  install -o hr20 -g hr20 -m 0755 ${WORKDIR}/backup_openhr20.sh ${D}/home/hr20
  install -o hr20 -g hr20 -m 0755 ${WORKDIR}/init_openhr20.sh ${D}/home/hr20
  install -o root -g root -m 0755 ${WORKDIR}/read_outside_temp.sh ${D}/home/hr20
  install -o hr20 -g hr20 -m 0644 ${S}/rfmsrc/frontend/tools/daemon.php ${D}/home/hr20
  install -o hr20 -g hr20 -m 0644 ${S}/rfmsrc/frontend/tools/create_db.php ${D}/home/hr20
  install -o hr20 -g hr20 -m 0755 ${S}/rfmsrc/frontend/tools/create_rrd ${D}/home/hr20
  install -o hr20 -g hr20 -m 0755 ${S}/rfmsrc/frontend/tools/plot ${D}/home/hr20
  
  sed -i 's/^IDS=.*/IDS=\"1 2 3 4 5 6\"/' ${D}/home/hr20/plot
  
  cp -r ${S}/rfmsrc/frontend/www ${D}/home/hr20
  rm ${D}/home/hr20/www/config.php
  install -o hr20 -g hr20 -m 0644 ${WORKDIR}/config.php ${D}/home/hr20/www/
  (cd ${D}/home/hr20/www && ln -s /tmp/openhr20/plots plots)
  chown -R hr20:hr20 ${D}/home/hr20/www
  
  install -m 0755 -d ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/read_outside_temp.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-init.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-setttyspeed.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-httpd.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-plot-90days.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-plot-90days.timer ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-plot-30days.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-plot-30days.timer ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-plot-7days.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-plot-7days.timer ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-plot-3days.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-plot-3days.timer ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-backup.timer ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-backup.service ${D}${systemd_unitdir}/system
}