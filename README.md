meta-esven
==========

My own OpenEmbedded Layer for custom projects.

## Currently implementing:
* [openhr20-frontend](https://github.com/OpenHR20/OpenHR20/tree/master/rfmsrc/frontend)
* [cve-search](https://github.com/cve-search/cve-search)
* Some Extensions for PandaBoard ES

### CVE-Search
The target of this project is to implement a class that looks up possible CVEs, CPEs, etc. in a mongodb database. To use this, you need to add the layer to your bblayers.conf and add the following line to your local.conf

> INHERIT += " cve_search"

Afterwards you can search for possible CVEs by running a command like the following:

> bitbake glibc -c cve_search

## Requirements:
To use this layer, you will need bitbake, oe-core and meta-openembedded layers.
You can find good instructions on how to get started here: [OpenEmbedded Getting started](http://www.openembedded.org/wiki/Getting_started) and here:  [OE-Core Standalone Setup](http://www.openembedded.org/wiki/OE-Core_Standalone_Setup)
Plus you will need to checkout meta-openembedded and add those layers to your bblayers.conf
