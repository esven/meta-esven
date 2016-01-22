SUMMARY = "Various helpers to pass trusted data to untrusted environments and back."
HOMEPAGE = "http://github.com/mitsuhiko/itsdangerous"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f9f1dc24f720c143c2240df41fe5073b"

SRC_URI = "https://pypi.python.org/packages/source/i/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"
SRC_URI[md5sum] = "9f0c5f1eb43ff44d5455dab4b4efbe73"
SRC_URI[sha256sum] = "349f93e3a4b09cc59418854ab8013d027d246757c51744bf20069bc89016f578"

SRCNAME = "itsdangerous"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
