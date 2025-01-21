DESCRIPTION = "Recipe to build C sshnpd from release tarball"
HOMEPAGE = "https://noports.com"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "https://github.com/atsign-foundation/noports/releases/download/c${PV}/csshnpd-c${PV}.tar.gz"
SRC_URI[sha256sum] = "95ac9024edd0022fa2521535e6f43ba6ca42cd160f69365b880cc056ab3837e6"

S = "${WORKDIR}/csshnpd-c${PV}"

#https://stackoverflow.com/questions/64984897/yocto-package-requires-shared-library-but-no-providers-found-in-rdepends
#DEPENDS += " libcjson"
#RDEPENDS_${PN} += " libcjson.so.1()(64bit)"

#https://stackoverflow.com/questions/61144014/errors-including-shared-prebuilt-libraries-in-petalinux
INSANE_SKIP:${PN} += "file-rdeps"

#https://lists.yoctoproject.org/g/yocto/message/57305
#EXCLUDE_FROM_SHLIBS = "1"

inherit cmake

#https://stackoverflow.com/questions/47054582/yocto-recipe-using-cmake-cannot-find-git
#OECMAKE_FIND_ROOT_PATH_MODE_PROGRAM = "BOTH"

#https://stackoverflow.com/questions/78885680/how-to-make-it-work-in-yocto-recipe-cmake-fetchcontent
EXTRA_OECMAKE:append = "-DFETCHCONTENT_FULLY_DISCONNECTED=OFF"

#https://stackoverflow.com/questions/42538230/how-to-add-preprocessor-definition-in-cmake-project-build-by-yocto
TARGET_CFLAGS += "-DBUILD_SHARED_LIBS=off"
TARGET_CFLAGS += "-DBUILD_TESTS=off"
TARGET_CFLAGS += "-DCMAKE_C_FLAGS=-fhonour-copts -Wno-calloc-transposed-args -Wno-error -pthread -lrt"

#Allow network access for CMake FetchContent
#https://mickey-happygolucky.hatenablog.com/entry/2022/04/29/203217
do_configure[network] = "1"
do_compile[network] = "1"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 sshnpd/at_auth_cli ${D}${bindir}
	install -m 0755 sshnpd/atactivate ${D}${bindir}
	install -m 0755 sshnpd/sshnpd ${D}${bindir}
}
