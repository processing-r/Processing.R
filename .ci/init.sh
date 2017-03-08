#!/usr/bin/env bash

# init.sh establishes a initial environment to build Processing.R in travis.
# Most of the code is copied from `scripts/generate-ant-file.sh`

# Timestamped log, e.g. log "cluster created".
#
# Input:
#   $1 Log string.
function log {
  echo -e "[`TZ=Asia/Shanghai date`] ${1}"
}

root=$(dirname "${BASH_SOURCE}")/..

# Paths
# Those paths are not needed when building runner.jar,
# but will be used in the future to package the mode.
modes="/mock-user/modes"
executable="/mock-user/Processing"
# Those paths are important to build runner.jar.
processing="$HOME/processing"
core="$HOME/processing/core/library/"
pde="$HOME/processing/app/"
renjin="lib/renjin-script-engine-0.8.2194-jar-with-dependencies.jar"

# Build core.jar.
cd ${core}/..
ant build
cd - > /dev/null

# Build pde.jar.
cd ${pde}
ant build
cd - > /dev/null

cd ${root}
cp build.xml.template build.xml
# Interpret config template.
log "Inject the config to build.xml.template"
perl -i -pe "s|\@\@modes\@\@|${modes}|g" build.xml
perl -i -pe "s|\@\@executable\@\@|${executable}|g" build.xml
perl -i -pe "s|\@\@processing\@\@|${processing}|g" build.xml
perl -i -pe "s|\@\@core\@\@|${core}|g" build.xml
perl -i -pe "s|\@\@pde\@\@|${pde}|g" build.xml
perl -i -pe "s|\@\@renjin\@\@|${renjin}|g" build.xml
cd - > /dev/null
