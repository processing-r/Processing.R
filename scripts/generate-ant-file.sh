#!/usr/bin/env bash

source "$(dirname "${BASH_SOURCE}")/utils/generator-util.sh"

# Path to be changed
modes="${HOME}/Documents/Processing/modes"
executable="/Applications/Processing.app/Contents/MacOS/Processing"
core="/Applications/Processing.app/Contents/Java/core/library"
pde="/Applications/Processing.app/Contents/Java/pde.jar"
version="local"
pretty_version="pretty-local"

# Call functions in utils/generator-util.sh
generate-build-config ${modes} ${executable} ${core} ${pde} ${version} ${pretty_version}
