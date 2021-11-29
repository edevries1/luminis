#!/usr/bin/env bash

set -euxo pipefail

mvn clean package

aws cloudformation package --template-file infra/template.yml --s3-bucket mt940data --output-template-file infra/out.yml
aws cloudformation deploy --stack-name exam1test --template-file infra/out.yml --capabilities CAPABILITY_NAMED_IAM
