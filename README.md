# Scalavelli project 

### Build status
| Branch | Status |
| --- | --- |
| Master | [![Build Status](https://travis-ci.com/Part-Time-Team/scalavelli.svg?branch=master)](https://travis-ci.com/Part-Time-Team/scalavelli) |
| Dev | [![Build Status](https://travis-ci.com/Part-Time-Team/scalavelli.svg?branch=dev)](https://travis-ci.com/Part-Time-Team/scalavelli) |

### Coverage status
| Branch | Status |
| --- | --- |
| Master | [![codecov](https://codecov.io/gh/Part-Time-Team/scalavelli/branch/master/graph/badge.svg)](https://codecov.io/gh/Part-Time-Team/scalavelli) |
| Dev | [![codecov](https://codecov.io/gh/Part-Time-Team/scalavelli/branch/dev/graph/badge.svg)](https://codecov.io/gh/Part-Time-Team/scalavelli) |

Project for PPS.

Use openjdk11 and scala 2.12.8.

### How to execute
Move to main dir of project and execute following commands to compile executable packages

```shell script
sbt pack
```

This command produce executable scripts in client and server directories. You can execute them with those commands:

| Project | Command |
| --- | --- |
| Server | `./server/target/pack/bin/scalavelli-server` |
| Client | `./client/target/pack/bin/applauncher` |

Look to our travis.yml file for packaging scripts.

Be aware to execute first the server and after that how many clients you want.
You can also modify configuration files to execute server on a different machine.
We don't provide guide to deploy on remote machine or containers. 