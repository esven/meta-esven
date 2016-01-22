DESCRIPTION = "Python 2 and 3 compatibility utilities"
DESCRIPTION = "Six is a Python 2 and 3 compatibility library. It provides \
utility functions for smoothing over the differences between the Python \
versions with the goal of writing Python code that is compatible on both Python \
versions. See the documentation for more information on what is provided."
HOMEPAGE = "http://pypi.python.org/pypi/six/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6f00d4a50713fa859858dd9abaa35b21"
PR = "r0"

SRC_URI = "https://pypi.python.org/packages/source/s/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"
SRC_URI[md5sum] = "34eed507548117b2ab523ab14b2f8b55"
SRC_URI[sha256sum] = "105f8d68616f8248e24bf0e9372ef04d3cc10104f1980f54d57b2ce73a5ad56a"

SRCNAME = "six"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
