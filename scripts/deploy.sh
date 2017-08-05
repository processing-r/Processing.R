#!/usr/bin/env bash

# Usage: deploy.sh <version> <full version>

ROOT=$(dirname "${BASH_SOURCE}")/..
numberOfParams=2

# Timestamped log, e.g. log "started to build the binary".
#
# Input:
#   $1 Log string.
function log {
  echo -e "[`TZ=Asia/Shanghai date`] ${1}"
}

function help {
  log "Usage: deploy.sh <version>"
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
  date=`date "+%Y/%m/%d %R"`

  perl -i -pe "s|\@\@mode-version\@\@|${version}|g" docs/RLangMode.txt
  perl -i -pe "s|\@\@pretty-version\@\@|${full_version}|g" docs/RLangMode.txt

  scripts/generate-ant-file.sh
  ant package
  cd dist/
  zip -r RLangMode.zip RLangMode/
  cd - > /dev/null
  mv dist/RLangMode.zip docs/

  cd - > /dev/null
}

deploy $*
