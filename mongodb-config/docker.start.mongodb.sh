#!/bin/bash
# docker run -d --name mias-mongodb -p 27017:27017 -v ~/docker/mias-mongodb:/data/db mongo
docker run -d --name mias-mongodb -p 27017:27017 mongo
