DESCRIPTION = "Open firmware for Honeywell Rondostat HR20 radiator thermostats"
AUTHOR = "Sven Ebenfeld"
HOMEPAGE = "http://www.mikrocontroller.net/articles/Heizungssteuerung_mit_Honeywell_HR20"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

SRC_URI = "svn://svn.code.sf.net/p/openhr20/code;module=rfmsrc/frontend \
	file://httpd_openhr20.conf \
	file://openhr20.service \
	file://openhr20-init.service \
	file://openhr20-httpd.service \
	"
SRCREV = "368"

PR = "r0"

S = "${WORKDIR}/rfmsrc/frontend"

USERADD_PACKAGES = "${PN}"

USERADD_PARAM_${PN} = " -g hr20 -d /home/hr20 -r -s /dev/null hr20"
GROUPADD_PARAM_${PN} = " hr20 "

inherit allarch useradd systemd

SYSTEMD_SERVICE_${PN} = "openhr20.service openhr20-init.service"

PACKAGES = "${PN}"

FILES_${PN} = " \
	/home/hr20 \
	${sysconfdir}/apache2 \
	${systemd_unitdir}/system \
	"

RDEPENDS_${PN} = "apache2 php php-modphp php-cli php-cgi sqlite sqlite3 rrdtool"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
  install -m 0755 -d ${D}${sysconfdir}/apache2
  install ${WORKDIR}/httpd_openhr20.conf ${D}${sysconfdir}/apache2
  install -o hr20 -g hr20 -m 0755 -d ${D}/home/hr20
  install -o hr20 -g hr20 -m 0644 ${S}/tools/daemon.php ${D}/home/hr20
  install -o hr20 -g hr20 -m 0644 ${S}/tools/create_db.php ${D}/home/hr20
  cp -r ${S}/www ${D}/home/hr20
  chown -R hr20:hr20 ${D}/home/hr20/www
  sed -i 's/\$db = new SQLiteDatabase(\".*/\$db = new SQLiteDatabase(\"\/tmp\/hr20db.sqlite\")\;/' ${D}/home/hr20/daemon.php
  sed -i 's/\$db = new SQLiteDatabase(\".*/\$db = new SQLiteDatabase(\"\/tmp\/hr20db.sqlite\")\;/' ${D}/home/hr20/create_db.php
  sed -i 's/\$db = new SQLiteDatabase(\".*/\$db = new SQLiteDatabase(\"\/tmp\/hr20db.sqlite\")\;/' ${D}/home/hr20/www/config.php
  
  install -m 0755 -d ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-init.service ${D}${systemd_unitdir}/system
  install -m 0644 ${WORKDIR}/openhr20-httpd.service ${D}${systemd_unitdir}/system
}