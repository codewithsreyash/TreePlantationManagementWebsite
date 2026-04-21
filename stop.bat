@echo off
echo =======================================================
echo Stopping Tree Plantation App (Port 8080)
echo =======================================================
echo.

set found=0
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":8080 " ^| findstr "LISTENING"') do (
    set found=1
    tasklist /FI "PID eq %%a" 2>NUL | find /I "%%a" >NUL
    if not errorlevel 1 (
        echo Found application running on PID: %%a... Stopping it now.
        taskkill /F /PID %%a
    )
)

if %found%==0 (
    echo The application is not currently running.
)

echo.
pause
