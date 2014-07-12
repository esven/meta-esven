require recipes-bsp/u-boot/u-boot-ti.inc

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://README;beginline=1;endline=22;md5=2687c5ebfd9cb284491c3204b726ea29"

DESCRIPTION = "Mainline u-boot bootloader"

COMPATIBLE_MACHINE = "(pandaboard|panda-es)"

DEFAULT_PREFERENCE = "-1"

PR = "r1"

SRC_URI = "git://git.denx.de/u-boot.git;protocol=git;branch=${BRANCH} \
	https://raw.githubusercontent.com/eewiki/u-boot-patches/master/v2014.07-rc3/0001-omap4_common-uEnv.txt-bootz-n-fixes.patch \
	"

SRC_URI[md5sum] = "2d9821f82b612ea9f68942555cff757b"
SRC_URI[sha256sum] = "0c095fc53bd577ccafb6a232a7b4043686478ef89edf91c382917fa2c3d5cad1"

BRANCH ?= "master"

SRCREV = "76b21026ceb5a6a83fc53b0ecdf425f240318022"

SPL_BINARY = "MLO"
