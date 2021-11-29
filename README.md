# luminis

This lambda function reads data from an S3 bucket and processes it. The files need to be placed in a subfolder of `incoming/`. The generated report will be placed in `outbound/`.

## Output

The output generated is an XML document with the following structure:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<results>
   <incorrectBalance>
      <transaction reference="112414">
         <description>Candy for Rik Bakker</description>
      </transaction>
      <transaction reference="131317">
         <description>Tickets for Vincent Theuß</description>
      </transaction>
   </incorrectBalance>
   <duplicates />
</results>
```

## Deploying

Create an S3 bucket to host the code, then alter the `deploy.sh` script to use that bucket name. Then execute `./deploy.sh` to build the code and apply the CloudFormation templates.


