#!/usr/bin/env bash

# Timestamped log, e.g. log "cluster created".
#
# Input:
#   $1 Log string.
function log {
  echo -e "[`date`] ${1}"
}

root=$(dirname "${BASH_SOURCE}")/..

cd ${root}
java -jar /code/runner/RLangMode.jar examples/test-draw.R
cd - > /dev/null
