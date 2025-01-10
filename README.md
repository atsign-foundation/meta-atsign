<h1><a href="https://atsign.com#gh-light-mode-only"><img width=250px
src="https://atsign.com/wp-content/uploads/2022/05/atsign-logo-horizontal-color2022.svg#gh-light-mode-only"
alt="The Atsign Foundation"></a>
<a href="https://atsign.com#gh-dark-mode-only"><img width=250px
src="https://atsign.com/wp-content/uploads/2023/08/atsign-logo-horizontal-reverse2022-Color.svg#gh-dark-mode-only"
alt="The Atsign Foundation"></a></h1>

# meta-atsign

This repo was created to test building a [CMake](https://cmake.org/) based
layer for [Yocto](https://www.yoctoproject.org/)

## recipes-sshnpd/sshnpd

This is a work in progress recipe to build the C sshnpd daemon for Yocto.

There are presently a number of ugly hacks in place to deal with our use of
FetchContent in the CMake build.

At present csshnpd will build standalone, but isn't working as part of an
image. [#2](https://github.com/atsign-foundation/meta-atsign/issues/2)

## recipes-hello/hellocmake

The example code is copied from the
[cpswan-hello-cmake](https://github.com/atsign-foundation/Atsign_OpenWRT_packages/tree/cpswan-hello-cmake)
branch of the Atsign_OpenWRT_packages repo.

The framework is based on the
[Hello World CMake Recipe](https://github.com/joaocfernandes/Learn-Yocto/blob/master/develop/Recipe-CMake.md)
from [Learn Yocto](https://github.com/joaocfernandes/Learn-Yocto)

## Why, What, How?

### Why?

This was created as a stepping stone to building a CMake sshnpd layer.

### What?

[Learn Yocto](https://github.com/joaocfernandes/Learn-Yocto) provides
a guide to creating a Raspberry Pi base build, though this was done
with the `kirkstone` branch (presently 4.0.16).

The `build/conf/bblayers.conf` in `poky` is:

```conf
# POKY_BBLAYERS_CONF_VERSION is increased each time build/conf/bblayers.conf
# changes incompatibly
POKY_BBLAYERS_CONF_VERSION = "2"

BBPATH = "${TOPDIR}"
BBFILES ?= ""

BBLAYERS ?= " \
  /home/chris/git/git.yoctoproject.org/poky/meta \
  /home/chris/git/git.yoctoproject.org/poky/meta-poky \
  /home/chris/git/git.yoctoproject.org/poky/meta-yocto-bsp \
  /home/chris/git/git.yoctoproject.org/poky/meta-openembedded/meta-oe \
  /home/chris/git/git.yoctoproject.org/poky/meta-openembedded/meta-multimedia \
  /home/chris/git/git.yoctoproject.org/poky/meta-openembedded/meta-networking \
  /home/chris/git/git.yoctoproject.org/poky/meta-openembedded/meta-python \
  /home/chris/git/git.yoctoproject.org/poky/meta-raspberrypi \
  /home/chris/git/git.yoctoproject.org/poky/meta-hellocmake \
  "
```

In `build/conf/local.conf`:

```conf
MACHINE = "raspberrypi3-64"

EXTRA_IMAGE_FEATURES ?= "debug-tweaks ssh-server-openssh"

IMAGE_INSTALL:append = " hellocmake"

IMAGE_ROOTFS_EXTRA_SPACE = "524288"

ENABLE_UART = "1"

BB_HASHSERVE_UPSTREAM = "hashserv.yocto.io:8687"
SSTATE_MIRRORS ?= "file://.* http://sstate.yoctoproject.org/all/PATH;downloadfilename=PATH"
```

### How?

First source the poky build environment:

```sh
. oe-init-build-env
```

The build is done with:

```sh
bitbake -k core-image-base
```

This creates a `core-image-base-raspberrypi3-64.wic.bz2` in
`$POKYROOT/build/tmp/deploy/images/raspberrypi3-64/`

The decompressed `.wic` file can then be imaged onto an SD card for the Pi.

## Maintainers

Created by [@cpswan](https://github.com/cpswan)
