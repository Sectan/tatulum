#!/bin/sh

# Cassandra port 9042 on the container is mapped to port 33333 on the localhost
docker run --name tatulum-cassandra -d -p 33333:9042 cassandra
