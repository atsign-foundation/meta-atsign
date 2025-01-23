<h1><a href="https://atsign.com#gh-light-mode-only"><img width=250px
src="https://atsign.com/wp-content/uploads/2022/05/atsign-logo-horizontal-color2022.svg#gh-light-mode-only"
alt="The Atsign Foundation"></a>
<a href="https://atsign.com#gh-dark-mode-only"><img width=250px
src="https://atsign.com/wp-content/uploads/2023/08/atsign-logo-horizontal-reverse2022-Color.svg#gh-dark-mode-only"
alt="The Atsign Foundation"></a></h1>

# meta-atsign

This repo was created to build a [CMake](https://cmake.org/) based layer for
[Yocto](https://www.yoctoproject.org/)

## recipes-sshnpd/sshnpd

This is a work in progress recipe to build the C sshnpd daemon for Yocto.

As our CMake builds presently use FetchContent to pull down dependencies
the recipe is configured to allow network access at build time.

The recipe can be added to an image config with:

```
IMAGE_INSTALL:append = " csshnpd"
```

### Using the recipe

This has been tested using the `scarthgap` branch (presently 5.0.2).

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
  /home/chris/git/github/atsign-foundation/meta-atsign \
  "
```

In `build/conf/local.conf`:

```conf
IMAGE_INSTALL:append = " csshnpd"
```

### Creating a test image

First source the poky build environment:

```sh
. oe-init-build-env
```

The build is done with:

```sh
bitbake core-image-sato
```

## Maintainers

Created by [@cpswan](https://github.com/cpswan)
