# Scalavelli project

Project for PPS.
Use openjdk11 and scala 2.12.8.

### Build status
| Branch | Build | Coverage |
| --- | --- | --- |
| Master | [![Build Status](https://travis-ci.com/Part-Time-Team/scalavelli.svg?branch=master)](https://travis-ci.com/Part-Time-Team/scalavelli) | [![codecov](https://codecov.io/gh/Part-Time-Team/scalavelli/branch/master/graph/badge.svg)](https://codecov.io/gh/Part-Time-Team/scalavelli) |
| Dev | [![Build Status](https://travis-ci.com/Part-Time-Team/scalavelli.svg?branch=dev)](https://travis-ci.com/Part-Time-Team/scalavelli) | [![codecov](https://codecov.io/gh/Part-Time-Team/scalavelli/branch/dev/graph/badge.svg)](https://codecov.io/gh/Part-Time-Team/scalavelli) |

### How to compile
Move to main dir of project and execute following commands to compile executable packages

```shell script
sbt pack
```

You can also modify configuration files to execute the server on a different machine. You have to change the Constant as:

```scala
// Run on a LAN machine.
final val SERVER_ADDRESS = "192.168.43.21"
final val SERVER_PORT = 8081
```

```scala
// Run on the local machine.
final val SERVER_ADDRESS = "localhost"
final val SERVER_PORT = 5150
```

Look to our travis.yml file for packaging scripts.

### How to execute

This command produce executable scripts in client and server directories. You can execute them with those commands:

| Project | Linux | Windows |
| --- | --- | --- |
| Server | `./server/target/pack/bin/scalavelli-server` | `.\server\target\pack\bin\scalavelli-server` |
| Client | `./client/target/pack/bin/applauncher` | `.\client\target\pack\bin\applauncher` |

Be aware to execute first the server and after that how many clients you want.

We don't provide guide to deploy on remote machine or containers. 