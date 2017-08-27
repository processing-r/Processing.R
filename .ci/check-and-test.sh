#!/usr/bin/env bash

ROOT=$(dirname "${BASH_SOURCE}")/..

cd ${ROOT}
if [[ "$TRAVIS_OS_NAME" == "linux" ]]
  then
    docker run -v=$(pwd):/app --workdir=/app coala/base coala --ci
    xvfb-run -a -s "-screen 0 1024x768x24" ant report
  fi
cd > /dev/null
