
cd E:\ldphome\gitwkps\dep-service-monitor
e:
dir

set GRADLE_OPTS=-Xmx512m -XX:MaxPermSize=512m -Xdebug -Xrunjdwp:transport=dt_socket,address=9000,server=y,suspend=n
set GRADLE_USER_HOME=%UserProfile%\.gradle

gradle jettyRun 2>&1 1>log.txt  2>>log.txt  1>>con   2>>con & pause