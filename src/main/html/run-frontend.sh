#!/bin/bash

docker run -d --rm --name lesath-client -v $(pwd):/app -p 8080:8080 lesath:client
