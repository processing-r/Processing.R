#!/usr/bin/env bash

# Usage: deploy.sh <version> <full version>
#
# Example:
#        cd scripts
#        ./deploy.sh 107 v1.0.7
#
# Requires jdk8, which may be set with JAVA_HOME env variables.
# See RELEASE.md for details on using this script.

ROOT=$(dirname "${BASH_SOURCE}")/..
numberOfParams=2

# Timestamped log, e.g. log "started to build the binary".
#
# Input:
#   $1 Log string.
function log {
  echo -e "[$(date)] ${1}"
}

function help {
  log "Usage: deploy.sh <version> <full version>"
}

function deploy {
  if [[ $# -ne ${numberOfParams} ]]; then
    log "The number of parameters is $#, which does not match ${FUNCNAME} in ${BASH_SOURCE}."
    help
    exit 1
  fi

  cd ${ROOT}

  mkdir -p docs
  cp resources/RLangMode.txt docs/RLangMode.txt

  log "Injecting version into docs/RLangMode.txt"
  # Get commit ID.
  commitid=$(git log -n1 --format="%h")
  version=${1}
  full_version=${2}
  date=$(date "+%Y/%m/%d %R")

  perl -i -pe "s|\@\@mode-version\@\@|${version}|g" docs/RLangMode.txt
  perl -i -pe "s|\@\@pretty-version\@\@|${full_version}|g" docs/RLangMode.txt

  source "scripts/utils/generator-util.sh"

  # Path to be changed
  modes="${HOME}/Documents/Processing/modes"
  executable="/Applications/Processing.app/Contents/MacOS/Processing"
  core="/Applications/Processing.app/Contents/Java/core/library"
  pde="/Applications/Processing.app/Contents/Java/pde.jar"

  # Call functions in utils/generator-util.sh
  generate-build-config ${modes} ${executable} ${core} ${pde} ${version} ${full_version}

  ant package
  cd dist/
  zip -r RLangMode.zip RLangMode/
  cd - > /dev/null
  mv dist/RLangMode.zip docs/

  cd - > /dev/null
}

deploy $*
