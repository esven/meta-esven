DESCRIPTION = "haveged - A simple entropy daemon"
AUTHOR = "Sven Ebenfeld"
HOMEPAGE = "http://www.issihosts.com/haveged/index.html"

LICENSE = "LGPL"
LIC_FILES_CHKSUM="file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI = "http://www.issihosts.com/haveged/haveged-${PV}.tar.gz \
		file://remove-systemd-unit.patch \
		"
SRC_URI[md5sum] = "036760389b1827a2532e248dd3cc46d3"
SRC_URI[sha256sum] = "40397eded96fc0d624a6a81c8e888ab023e46f3114f1ee699994e645531bf121"

PR = "r0"

inherit autotools systemd

EXTRA_OECONF = "\
			--enable-init=service.redhat \
			--enable-nistest=yes \
			--enable-olt=yes \
			--enable-threads=no \
			"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "haveged.service"
			
do_install_append() {
	mkdir -p ${D}${systemd_unitdir}/system
	install -p -m644 ${S}/init.d/haveged.service ${D}${systemd_unitdir}/system
}