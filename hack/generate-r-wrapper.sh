#!/usr/bin/env bash

ROOT=$(dirname "${BASH_SOURCE}")/..

cd ${ROOT}
echo '' > ./src/rprocessing/r/core.R
while IFS='' read -r line || [[ -n "$line" ]]; do
    echo "${line} = processing\$${line}" >> ./src/rprocessing/r/core.R
done < "./hack/functions.txt"
cd - > /dev/null
