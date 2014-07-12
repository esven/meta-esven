SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

inherit kernel

require recipes-kernel/linux/linux-dtb.inc
require recipes-kernel/linux/setup-defconfig.inc

# Pull in the devicetree files into the rootfs
RDEPENDS_kernel-base += "kernel-devicetree"
PROVIDES = "virtual/kernel linux-mainline"

KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

COMPATIBLE_MACHINE = "(omap4|panda-es)"

S = "${WORKDIR}/git"

BRANCH = "linux-3.15.y"

# Corresponds to tag v3.15.5
SRCREV = "a484f1136a4a2e00b6a70567cf978f4ba52ba654"
PV = "3.15.5"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;protocol=git;branch=${BRANCH} \
	file://omap_twl4030/0002-mfd-twl-core-Fix-idle-mode-signaling-for-omaps-when-.patch \
	file://omap_twl4030/0003-ARM-OMAP3-Fix-idle-mode-signaling-for-sys_clkreq-and.patch \
	file://omap_twl4030/0004-ARM-OMAP3-Disable-broken-omap3_set_off_timings-funct.patch \
	file://omap_twl4030/0005-ARM-OMAP3-Fix-voltage-control-for-deeper-idle-states.patch \
	file://omap_twl4030/0006-ARM-dts-Configure-omap3-twl4030-I2C4-pins-by-default.patch \
	file://omap_twl4030/0007-ARM-OMAP2-Fix-voltage-scaling-init-for-device-tree.patch \
	file://omap_twl4030/0008-ARM-dts-Enable-N900-keyboard-sleep-leds-by-default.patch \
	file://omap_twl4030/0009-ARM-dts-Fix-omap-serial-wake-up-when-booted-with-dev.patch \
	file://omap_twl4030/0010-ARM-OMAP2-Enable-CPUidle-in-omap2plus_defconfig.patch \
	file://omap_twl4030/0011-mfd-twl4030-power-Add-generic-reset-configuration.patch \
	file://omap_twl4030/0012-mfd-twl4030-power-Add-recommended-idle-configuration.patch \
	file://omap_twl4030/0013-mfd-twl4030-power-Add-support-for-board-specific-con.patch \
	file://omap_twl4030/0014-mfd-twl4030power-Add-a-configuration-to-turn-off-osc.patch \
	file://omap_twl4030/0015-ARM-dts-Enable-twl4030-off-idle-configuration-for-se.patch \
	file://omap_sprz319_erratum_v2.1/0001-hack-omap-clockk-dpll5-apply-sprz319e-2.1-erratum-co.patch \
	file://fixes/0002-saucy-error-variable-ilace-set-but-not-used-Werror-u.patch \
    file://defconfig \
          "
