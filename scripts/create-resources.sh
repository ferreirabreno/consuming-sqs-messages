#!/bin/sh

# create sns topics
awslocal sns create-topic --name book-topic
awslocal sns create-topic --name song-topic

# create sqs topics
awslocal sqs create-queue --queue-name book-queue
awslocal sqs create-queue --queue-name song-queue

# subscribe queues to sns
awslocal sns subscribe --protocol sqs \
  --topic-arn arn:aws:sns:us-east-1:000000000000:book-topic \
  --notification-endpoint arn:aws:sqs:us-east-1:000000000000:book-queue

awslocal sns subscribe --protocol sqs \
  --topic-arn arn:aws:sns:us-east-1:000000000000:song-topic \
  --notification-endpoint arn:aws:sqs:us-east-1:000000000000:song-queue
