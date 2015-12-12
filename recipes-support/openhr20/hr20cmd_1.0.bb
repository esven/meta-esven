DESCRIPTION = "Open firmware for Honeywell Rondostat HR20 radiator thermostats"
AUTHOR = "Sven Ebenfeld"
HOMEPAGE = "http://www.mikrocontroller.net/articles/Heizungssteuerung_mit_Honeywell_HR20"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM="file://COPYING;md5=59530bdf33659b29e73d4adb9f9f6552"

SRC_URI = "svn://svn.code.sf.net/p/openhr20/code;module=tools/hr20cmd"
SRCREV = "368"

PR = "r0"

S = "${WORKDIR}/tools/hr20cmd"

inherit cmake

PACKAGES = "${PN}-dev ${PN}-dbg ${PN}"

FILES_${PN} = "${bindir}"
FILES_${PN}-dev = "/usr/src"
FILES_${PN}-dbg = "${bindir}/.debug"

do_install () {
    mkdir -p ${D}${bindir}
	install -m 0755 ${B}/hr20cmd ${D}${bindir}
}
