#!/usr/bin/env bash

ROOT=$(dirname "${BASH_SOURCE}")/..

cd ${ROOT}
if [[ "$TRAVIS_OS_NAME" == "linux" ]]
  then
    .ci/init-linux.sh
  else
    .ci/init-macos.sh
  fi
cd > /dev/null
