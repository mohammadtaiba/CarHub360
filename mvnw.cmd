@echo off
setlocal

where mvn.cmd >nul 2>nul
if %ERRORLEVEL%==0 (
  mvn.cmd %*
  exit /b %ERRORLEVEL%
)

where mvn >nul 2>nul
if %ERRORLEVEL%==0 (
  mvn %*
  exit /b %ERRORLEVEL%
)

where docker >nul 2>nul
if %ERRORLEVEL%==0 (
  docker run --rm -v carhub360-maven-cache:/root/.m2 -v "%cd%:/workspace" -w /workspace maven:3.9.6-eclipse-temurin-17 mvn %*
  exit /b %ERRORLEVEL%
)

echo Maven is not installed and Docker is not available. 1>&2
exit /b 1
