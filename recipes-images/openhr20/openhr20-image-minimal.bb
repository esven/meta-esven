IMAGE_PREPROCESS_COMMAND = "rootfs_update_timestamp"
IMAGE_FEATURES += "read-only-rootfs"
IMAGE_FEATURES_remove = "package-management"

SYSTEMD_DEFAULT_TARGET = "multi-user.target"

DISTRO_UPDATE_ALTERNATIVES ??= ""
ROOTFS_PKGMANAGE_PKGS ?= '${@base_conditional("ONLINE_PACKAGE_MANAGEMENT", "none", "", "${ROOTFS_PKGMANAGE} ${DISTRO_UPDATE_ALTERNATIVES}", d)}'

CONMANPKGS ?= "connman connman-client connman-plugin-loopback connman-plugin-ethernet connman-plugin-wifi connman-systemd"
CONMANPKGS_libc-uclibc = ""

IMAGE_INSTALL += " \
	packagegroup-boot \
	packagegroup-basic \
	ca-certificates \
	haveged \
	lsof \
	ldd \
	file \
	kernel-modules \
	hr20cmd \
	openhr20-frontend \
	${ROOTFS_PKGMANAGE_PKGS} \
	${CONMANPKGS} \
	systemd \
	busybox \
"

# Disable udev-hwdb installation as it is not installable on read-only images
BAD_RECOMMENDATIONS = "udev-hwdb"

IMAGE_DEV_MANAGER   = "udev"
IMAGE_INIT_MANAGER  = "systemd"
IMAGE_INITSCRIPTS   = " "
IMAGE_LOGIN_MANAGER = "busybox shadow"


remove_unneeded_files() {
	if [ -f ${IMAGE_ROOTFS}/usr/lib/tmpfiles.d/etc.conf ] ; then
		rm ${IMAGE_ROOTFS}/usr/lib/tmpfiles.d/etc.conf
	fi
	if [ -f ${IMAGE_ROOTFS}/usr/lib/tmpfiles.d/systemd-remote.conf ] ; then
		rm ${IMAGE_ROOTFS}/usr/lib/tmpfiles.d/systemd-remote.conf
	fi
	if [ -f ${IMAGE_ROOTFS}/usr/lib/tmpfiles.d/x11.conf ] ; then
		rm ${IMAGE_ROOTFS}/usr/lib/tmpfiles.d/x11.conf
	fi
	if [ -f ${IMAGE_ROOTFS}${sysconfdir}/udhcpc.d/50default ] ; then
		rm ${IMAGE_ROOTFS}${sysconfdir}/udhcpc.d/50default
	fi
	
	find ${IMAGE_ROOTFS}${systemd_unitdir}/system/ -name wpa_supplicant\* -exec rm '{}' \;
	find ${IMAGE_ROOTFS}${systemd_unitdir}/system/ -name dnsmasq\* -exec rm '{}' \;
	find ${IMAGE_ROOTFS}${systemd_unitdir}/system/ -name hostapd\* -exec rm '{}' \;
	find ${IMAGE_ROOTFS}${systemd_unitdir}/system/ -name ntpclient\* -exec rm '{}' \;
	# Automounter not available in this image
	find ${IMAGE_ROOTFS}${systemd_unitdir}/system/ -name \*.automount -exec rm '{}' \; 
	
	if [ -d "${IMAGE_ROOTFS}${sysconfdir}/init.d" ]; then
		echo "Removing init scripts"
		find ${IMAGE_ROOTFS}${sysconfdir}/init.d -type f -exec rm '{}' \;
	fi
	
}

change_default_runlevel() {
	if [ -f ${IMAGE_ROOTFS}${systemd_unitdir}/system/default.target ] ; then
		echo "Changing Default-Runlevel for ${IMAGE_ROOTFS}"
		# Delete default.target
		rm ${IMAGE_ROOTFS}${systemd_unitdir}/system/default.target
		(cd ${IMAGE_ROOTFS}${systemd_unitdir}/system && ln -s multi-user.target default.target)
	fi
}

qemu_create_disk() {
	echo "Creating additional qemu disk"
	if [ -f "${WORKDIR}/hdb.disk" ]; then
		rm ${WORKDIR}/hdb.disk
	fi
	# Create 4GB Disk file
	dd if=/dev/zero of=${WORKDIR}/hdb.disk bs=131072 count=32768
	#Partition the file
	sfdisk --in-order --Linux --unit M ${WORKDIR}/hdb.disk <<-__EOF__
1,20,0x83,-
,15,0x83,*
,15,0x83,*
,,0x05,-
,670,0x83,-
,670,0x83,-
,670,0x83,-
,,0x83,-
__EOF__
}


do_deploy_append() {
	if [ -f "${WORKDIR}/hdb.disk" ]; then
		install -m 0644 ${WORKDIR}/hdb.disk ${DEPLOYDIR}/hdb.disk
	fi
}

ROOTFS_POSTPROCESS_COMMAND += " remove_unneeded_files; change_default_runlevel ; "
# ROOTFS_POSTPROCESS_COMMAND_append_qemuall = " qemu_create_disk; "


#Angstrom image to test systemd

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

export IMAGE_BASENAME = "openhr20-image-minimal"

inherit image