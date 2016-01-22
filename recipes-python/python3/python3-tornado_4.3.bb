DESCRIPTION = "Tornado is a Python web framework and asynchronous networking library, originally developed at FriendFeed."
DESCRIPTION = "Tornado is a Python web framework and asynchronous networking \
library, originally developed at FriendFeed. By using non-blocking network \
I/O, Tornado can scale to tens of thousands of open connections, making it \
ideal for long polling, WebSockets, and other applications that require a \
long-lived connection to each user."
HOMEPAGE = "http://www.tornadoweb.org/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://setup.py;startline=2;endline=15;md5=5ab7571a79ad62d0ca7aea308bac7560"
PR = "r0"

SRC_URI = "https://pypi.python.org/packages/source/t/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"
SRC_URI[md5sum] = "d13a99dc0b60ba69f5f8ec1235e5b232"
SRC_URI[sha256sum] = "c9c2d32593d16eedf2cec1b6a41893626a2649b40b21ca9c4cac4243bde2efbf"

SRCNAME = "tornado"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3

PACKAGES =+ "\
    ${PN}-test \
"

RDEPENDS_${PN}-test += "${PN} python3-unittest"

FILES_${PN}-test = " \
    ${libdir}/${PYTHON_DIR}/site-packages/${SRCNAME}/test \
    ${libdir}/${PYTHON_DIR}/site-packages/${SRCNAME}/testing.py* \
"

BBCLASSEXTEND = "native nativesdk"
