do_cve_search() {
        ${STAGING_BINDIR_NATIVE}/search.py -p ${PN}:${PV} -o json || \
        die "cve-search execution failed."
}

do_cve_search[depends] = "cve-search-native:do_update_db"

addtask cve_search before do_build

EXPORT_FUNCTIONS do_cve_search
