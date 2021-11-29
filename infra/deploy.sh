#!/usr/bin/env bash

set -euxo pipefail

aws cloudformation package --template-file template.yml --s3-bucket mt940data --output-template-file out.yml
aws cloudformation deploy --stack-name exam1test --template-file out.yml --capabilities CAPABILITY_NAMED_IAM
