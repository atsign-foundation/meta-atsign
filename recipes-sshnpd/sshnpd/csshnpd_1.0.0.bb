DESCRIPTION = "Recipe to build C sshnpd from release tarball"
HOMEPAGE = "https://noports.com"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "https://github.com/atsign-foundation/noports/releases/download/c${PV}/csshnpd-c${PV}.tar.gz"
SRC_URI[sha256sum] = "e71ee216f989cd9cbc047f1c2e301122d41e6e08c9886156a8e286c7cef05da3"

S = "${WORKDIR}/csshnpd-c${PV}"

inherit cmake

#https://stackoverflow.com/questions/78885680/how-to-make-it-work-in-yocto-recipe-cmake-fetchcontent
EXTRA_OECMAKE += "-DFETCHCONTENT_FULLY_DISCONNECTED=OFF"
EXTRA_OECMAKE += "-DBUILD_SHARED_LIBS=off"
EXTRA_OECMAKE += "-DBUILD_TESTS=off"

#https://stackoverflow.com/questions/42538230/how-to-add-preprocessor-definition-in-cmake-project-build-by-yocto
TARGET_CFLAGS += "-DCMAKE_C_FLAGS=-fhonour-copts -Wno-calloc-transposed-args -Wno-error -pthread -lrt"

#Allow network access for CMake FetchContent
#https://mickey-happygolucky.hatenablog.com/entry/2022/04/29/203217
do_configure[network] = "1"
do_compile[network] = "1"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 sshnpd/at_activate ${D}${bindir}
	install -m 0755 sshnpd/sshnpd ${D}${bindir}
}
