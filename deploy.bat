@echo off
SET REPOSITORY_ID=%1
SET REPOSITORY_URL=%2
call mvn -DaltDeploymentRepository=%REPOSITORY_ID%::default::%REPOSITORY_URL% clean deploy