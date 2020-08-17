#!/bin/bash

# Create necessary folders.
if [ ! -d "public" ]; then
  mkdir public
fi

if [ ! -d "public/scoverage-reports"]; then
  mkdir public/scoverage-reports

  if [ ! -d "public/scoverage-reports/client" ]; then
    mkdir public/scoverage-reports/client
  fi

  if [ ! -d "public/scoverage-reports/server" ]; then
    mkdir public/scoverage-reports/server
  fi
fi

if [ ! -d "public/api" ]; then
  mkdir public/api

  if [ ! -d "public/api/client" ]; then
    mkdir public/api/client
  fi

  if [ ! -d "public/api/server" ]; then
    mkdir public/api/server
  fi

  if [ ! -d "public/api/commons" ]; then
    mkdir public/api/commons
  fi

  if [ ! -d "public/api/core" ]; then
    mkdir public/api/core
  fi
fi

echo "************************************************"
echo "**           Copy scoverage reports           **"
echo "************************************************"

ls -lhRG

cp -fr source/* public/

# Copy scoverage report html files.
cp -r target/scala-*/scoverage-report/* public/scoverage-reports/

# Copy scaladoc html files.
cp -r target/scala-*/api/* public/api/
cp -r client/target/scala-*/api/* public/api/client
cp -r server/target/scala-*/api/* public/api/server
cp -r commons/target/scala-*/api/* public/api/commons
cp -r core/target/scala-*/api/* public/api/core