#!/usr/bin/env bash

set -euxo pipefail

BUCKET=mt940bucket
FILENAME=$(dd if=/dev/random bs=8 count=1 2>/dev/null | od -An -tx1 | tr -d ' \t\n')
aws s3 cp ./testfiles/records.xml s3://${BUCKET}/incoming/${FILENAME}.xml
