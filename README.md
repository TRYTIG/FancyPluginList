# Fancy Plugin List
Want to display your plugin list in style?
Use Fancy Plugin List.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

---

# Introduction

Fancy Plugin List overrides the /plugins and /pl command. It allows you to display your plugins in a better looking way.
![](images/PluginList.PNG "List")

You can click on the plugin name to view more information about it or by running `/plugin <plugin name>`
![](images/PluginInfo.PNG "Info")

## Commands

| Command | Description | Aliases | Permissions |
|---|---|---|---|
| ``/pl`` | Shows the list of plugins | ``/plugin``, ``/plugins`` | NONE |
| ``/pl <NAME>`` | Shows specific information about a plugin | ``/plugin``, ``/plugins`` | NONE |

**Disclaimer**: This plugin will remove any permission to view your server plugins. If you do not want people to view your plugins, this plugin may not be for you.

## Installing

Simply copy the JAR file into your server plugins folder and restart.

## Building

#### Requirements
* JDK 8 or newer
* Git

#### Compiling
```shell
git clone https://github.com/TRYTIG/FancyPluginList
cd FPList
./gradlew build
```
Compiled JAR files will be in the `build/libs` directory

## License

Fancy Plugin List is licensed  under the permissive MIT license. See [`LICENSE.txt`](LICENSE.txt) for more information.