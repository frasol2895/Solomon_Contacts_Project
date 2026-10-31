@echo off
cd /d "%~dp0"
where javac >nul 2>nul
if errorlevel 1 (
 echo Install JDK 17 or newer and add its bin folder to PATH.
 pause
 exit /b 1
)
if not exist bin mkdir bin
javac -cp "lib/*" -d bin src/*.java
if errorlevel 1 (
 pause
 exit /b 1
)
java -cp "bin;lib/*" App
pause
