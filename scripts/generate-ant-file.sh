#!/usr/bin/env bash

source "$(dirname "${BASH_SOURCE}")/utils/generator-util.sh"

# Path to be changed
modes="~/Documents/Processing/modes"
executable="/Applications/Processing.app/Contents/MacOS/Processing"
core="/Applications/Processing.app/Contents/Java/core.jar"
pde="/Applications/Processing.app/Contents/Java/pde.jar"

# Call functions in utils/generator-util.sh
generate-build-config ${modes} ${executable} ${core} ${pde}
