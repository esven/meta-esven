DESCRIPTION = "Python Jinja2: A small but fast and easy to use stand-alone template engine written in pure python."
HOMEPAGE = "http://jinja.pocoo.org/"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=20c831f91dd3bd486020f672ba2be386"
PR = "r0"

SRC_URI = "https://pypi.python.org/packages/source/J/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"
SRC_URI[md5sum] = "edb51693fe22c53cee5403775c71a99e"
SRC_URI[sha256sum] = "bc1ff2ff88dbfacefde4ddde471d1417d3b304e8df103a7a9437d47269201bf4"

SRCNAME = "Jinja2"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3

RDEPENDS_${PN} = "python3-markupsafe"

BBCLASSEXTEND = "native nativesdk"
