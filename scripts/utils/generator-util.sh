#!/usr/bin/env bash

# Timestamped log, e.g. log "started to build the binary".
#
# Input:
#   $1 Log string.
function log {
  echo -e "[`TZ=Asia/Shanghai date`] ${1}"
}

function help {
  log "There are 4 paths needed: mode directory, path to executable, path to core.jar directory and path to pde.jar."
}

function generate-build-config {
  local numberOfParams=4
  local root=$(dirname "${BASH_SOURCE}")/../..

  if [[ $# -ne ${numberOfParams} ]]; then
    log "The number of parameters is $#, which does not match ${FUNCNAME} in ${BASH_SOURCE}."
    help
    exit 1
  fi
  # Static path
  local renjin="lib/renjin-script-engine-0.8.2194-jar-with-dependencies.jar"

  cd ${root}
  cp build.xml.template build.xml
  # Interpret config template.
  log "Inject the config to build.xml.template"
  perl -i -pe "s|\@\@modes\@\@|${1}|g" build.xml
  perl -i -pe "s|\@\@executable\@\@|${2}|g" build.xml
  perl -i -pe "s|\@\@core\@\@|${3}|g" build.xml
  perl -i -pe "s|\@\@pde\@\@|${4}|g" build.xml

  # Static path
  perl -i -pe "s|\@\@renjin\@\@|${renjin}|g" build.xml
  cd - > /dev/null
}
