@echo off
rem See http://www.mojavelinux.com/blog/archives/2007/03/remote_debugging_with_jetty/
setlocal
set MAVEN_OPTS=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=4000,server=y,suspend=n
mvn jetty:run-war
