#!/usr/bin/env bash

# Timestamped log, e.g. log "cluster created".
#
# Input:
#   $1 Log string.
function log {
  echo -e "[`TZ=Asia/Shanghai date`] ${1}"
}

root=$(dirname "${BASH_SOURCE}")/..

# Path
modes="/Users/gaoce/Documents/Processing/modes"
executable="/Applications/Processing.app/Contents/MacOS/Processing"
processing="../processing"
core="../processing/core/library/"
pde="../processing/app/"
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
