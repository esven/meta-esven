SUMMARY = "Python HTTP for Humans"
DESCRIPTION = "Most existing Python modules for sending HTTP requests are extremely \
verbose and cumbersome. Python’s builtin urllib2 module provides most of the HTTP \
capabilities you should need, but the api is thoroughly broken. It requires an \
enormous amount of work (even method overrides) to perform the simplest of tasks."
HOMEPAGE = "http://python-requests.org"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=58c7e163c9f8ee037246da101c6afd1e"
PR = "r0"

SRC_URI = "https://pypi.python.org/packages/source/r/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"
SRC_URI[md5sum] = "0b7f480d19012ec52bab78292efd976d"
SRC_URI[sha256sum] = "c577815dd00f1394203fc44eb979724b098f88264a9ef898ee45b8e5e9cf587f"

SRCNAME = "requests"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
